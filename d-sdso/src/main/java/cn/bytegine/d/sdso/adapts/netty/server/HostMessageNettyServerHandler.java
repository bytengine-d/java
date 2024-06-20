package cn.bytegine.d.sdso.adapts.netty.server;

import cn.bytegine.d.sdso.adapts.netty.NettyHostGroup;
import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageNettyServerHandler extends SimpleChannelInboundHandler<HostMessage> {
    private NettyHostGroup hostGroup;

    public HostMessageNettyServerHandler(NettyHostGroup hostGroup) {
        this.hostGroup = hostGroup;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HostMessage message) throws Exception {
        hostGroup.handle(channelHandlerContext, message);
    }
}
