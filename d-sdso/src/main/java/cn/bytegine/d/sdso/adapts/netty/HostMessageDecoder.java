package cn.bytegine.d.sdso.adapts.netty;

import cn.bytegine.d.sdso.message.BaseHostMessage;
import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static cn.bytegine.d.sdso.adapts.msgpack.Values.toData;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageDecoder extends ByteToMessageDecoder {
    private static final AttributeKey<HostMessage<? extends HostMessage<?>>> HOST_MESSAGE_ATTRIBUTE_KEY = AttributeKey.valueOf("sdso_host_message");
    private static final AttributeKey<Integer> HOST_MESSAGE_LENGTH_ATTRIBUTE_KEY = AttributeKey.valueOf("sdso_host_message_length");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf inBuf, List<Object> out) throws Exception {
        Attribute<HostMessage<? extends HostMessage<?>>> attr = ctx.channel().attr(HOST_MESSAGE_ATTRIBUTE_KEY);
        // 检查是否初始化消息体对象
        if (!ctx.channel().hasAttr(HOST_MESSAGE_ATTRIBUTE_KEY)) {
            // 未初始化检查，检查消息头是否可读
            if (inBuf.readableBytes() < 8) {
                return;
            }
            HostMessage hostMessage = new BaseHostMessage<>();
            int messageVersion = inBuf.readInt();
            hostMessage.setMessageVersion(messageVersion);
            int messageLength = inBuf.readInt();
            // 读取完消息头，初始化消息体对象
            attr.set(hostMessage);
            ctx.channel().attr(HOST_MESSAGE_LENGTH_ATTRIBUTE_KEY).set(messageLength);
        }
        // 检查消息长度是否已经初始化，未初始化状态不应该执行此步骤
        if (!ctx.channel().hasAttr(HOST_MESSAGE_LENGTH_ATTRIBUTE_KEY)) {
            // TODO Exception
            return;
        }
        int messageLength = ctx.channel().attr(HOST_MESSAGE_LENGTH_ATTRIBUTE_KEY).get();
        // 检查消息是否接收完成
        if (inBuf.readableBytes() < messageLength) {
            return;
        }
        // 读取消息体内容
        HostMessage hostMessage = ctx.channel().attr(HOST_MESSAGE_ATTRIBUTE_KEY).get();
        try (ByteBufInputStream in = new ByteBufInputStream(inBuf)) {
            MessageUnpacker unPacker = MessagePack.newDefaultUnpacker(in);
            hostMessage.setSourceHostId(unPacker.unpackString())
                    .setTargetHostId(unPacker.unpackString())
                    .setRequestTime(Instant.ofEpochMilli(unPacker.unpackLong()))
                    .setRequestMessageId(unPacker.unpackString());
            boolean isReply = unPacker.unpackBoolean();
            if (isReply) {
                hostMessage.setReplyMessageId(unPacker.unpackString());
            }
            boolean hasHeaders = unPacker.unpackBoolean();
            if (hasHeaders) {
                Map<String, Object> headers = toData(unPacker.unpackValue(), Map.class);
                hostMessage.addHeaders(headers);
            }
            byte[] content = toData(unPacker.unpackValue(), byte[].class);
            hostMessage.setContent(content);

            out.add(hostMessage);
        }
    }
}
