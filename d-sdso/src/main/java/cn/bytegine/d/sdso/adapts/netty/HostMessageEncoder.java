package cn.bytegine.d.sdso.adapts.netty;

import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

import java.util.Map;

import static cn.bytegine.d.sdso.adapts.msgpack.Values.toValue;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageEncoder extends MessageToByteEncoder<HostMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HostMessage msg, ByteBuf out) throws Exception {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer();
        try (ByteBufOutputStream outputStream = new ByteBufOutputStream(buf);
             MessagePacker packer = MessagePack.newDefaultPacker(outputStream)) {
            packer.packString(msg.getSourceHostId())
                    .packString(msg.getTargetHostId())
                    .packLong(msg.getRequestTime().toEpochMilli())
                    .packString(msg.getRequestMessageId());

            boolean isReply = msg.isReply();
            packer.packBoolean(isReply);
            if (isReply) {
                packer.packString(msg.getReplyMessageId());
            }
            Map<String, Object> headers = msg.getHeaders();
            boolean hasHeaders = headers != null && !headers.isEmpty();
            packer.packBoolean(hasHeaders);
            if (hasHeaders) {
                packer.packValue(toValue(headers));
            }
            byte[] content = msg.getContent();
            packer.packBinaryHeader(content.length)
                    .writePayload(content);
            packer.flush();
            out.writeInt(msg.getMessageVersion());
            out.writeInt(buf.readableBytes());
            out.writeBytes(buf);
        }
    }
}
