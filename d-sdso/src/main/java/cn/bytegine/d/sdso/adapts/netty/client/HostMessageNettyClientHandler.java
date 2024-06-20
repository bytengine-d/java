package cn.bytegine.d.sdso.adapts.netty.client;

import cn.bytegine.d.sdso.adapts.netty.NettyHost;
import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageNettyClientHandler extends SimpleChannelInboundHandler<HostMessage> {
    private NettyHost nettyHost;

    public HostMessageNettyClientHandler(NettyHost nettyHost) {
        this.nettyHost = nettyHost;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HostMessage message) throws Exception {
        nettyHost.handle(channelHandlerContext, message);
    }
}
