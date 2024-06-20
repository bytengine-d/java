package cn.bytegine.d.sdso.adapts.netty;

import cn.bytegine.d.sdso.AbstractHost;
import cn.bytegine.d.sdso.Host;
import cn.bytegine.d.sdso.adapts.netty.client.HostMessageNettyClient;
import cn.bytegine.d.sdso.adapts.netty.client.HostMessageNettyClientHandler;
import cn.bytegine.d.sdso.message.HostMessage;
import cn.hutool.core.text.CharSequenceUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class NettyHost extends AbstractHost<NettyHost> {

    public HostMessageNettyClient client;

    public NettyHost(String hostId, NettyConfig config) {
        super(hostId);
        client = buildHostMessageClient(this, config);
    }

    protected HostMessageNettyClient buildHostMessageClient(Host host, NettyConfig config) {
        return new HostMessageNettyClient(host, config, buildClientChannelInitializer(config));
    }

    protected HostMessageChannelInitializer buildClientChannelInitializer(NettyConfig config) {
        return new HostMessageChannelInitializer(this::buildClientChannelHandlers);
    }

    protected List<ChannelHandlerFactory> buildClientChannelHandlers() {
        return Arrays.asList(HostMessageEncoder::new,
                HostMessageDecoder::new,
                () -> new HostMessageNettyClientHandler(this));
    }

    @Override
    public void sendMessage(HostMessage message) {
        if (CharSequenceUtil.equals(getId(), message.getSourceHostId())) {
            throw new IllegalArgumentException("Unsupported to send message for self.");
        }
        client.sendHostMessage(this, message);
    }

    public void handle(ChannelHandlerContext ctx, HostMessage message) {
        // TODO process host HostMessage
    }
}
