package cn.bytegine.d.sdso.adapts.netty;

import cn.bytegine.d.sdso.DefaultHostGroup;
import cn.bytegine.d.sdso.HostGroup;
import cn.bytegine.d.sdso.adapts.netty.server.HostMessageNettyServer;
import cn.bytegine.d.sdso.adapts.netty.server.HostMessageNettyServerHandler;
import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class NettyHostGroup extends DefaultHostGroup {

    private HostMessageNettyServer hostMessageNettyServer;

    public NettyHostGroup(NettyConfig config) {
        this.hostMessageNettyServer = buildHostMessageNettyServer(this, config);
        this.hostMessageNettyServer.start();
    }

    protected HostMessageNettyServer buildHostMessageNettyServer(HostGroup hostGroup, NettyConfig config) {
        return new HostMessageNettyServer(hostGroup, config, buildClientChannelInitializer(config));
    }

    protected HostMessageChannelInitializer buildClientChannelInitializer(NettyConfig config) {
        return new HostMessageChannelInitializer(this::buildServerChannelHandlers);
    }

    protected List<ChannelHandlerFactory> buildServerChannelHandlers() {
        return Arrays.asList(HostMessageEncoder::new,
                HostMessageDecoder::new,
                () -> new HostMessageNettyServerHandler(this));
    }

    public void handle(ChannelHandlerContext ctx, HostMessage message) {
        // TODO process group HostMessage
    }
}
