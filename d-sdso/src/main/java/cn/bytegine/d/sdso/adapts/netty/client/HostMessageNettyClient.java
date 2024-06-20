package cn.bytegine.d.sdso.adapts.netty.client;

import cn.bytegine.d.sdso.Host;
import cn.bytegine.d.sdso.adapts.netty.HostMessageChannelInitializer;
import cn.bytegine.d.sdso.adapts.netty.NettyConfig;
import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageNettyClient implements Closeable {
    private Bootstrap bootstrap;
    private EventLoopGroup group;
    private AtomicBoolean initialization = new AtomicBoolean(false);

    public HostMessageNettyClient(Host host,
                                  NettyConfig nettyConfig,
                                  HostMessageChannelInitializer initializer) {
        bootstrap = buildBootstrap(host, nettyConfig);
        bootstrap.handler(initializer);
    }

    protected Bootstrap buildBootstrap(Host host, NettyConfig nettyConfig) {
        EventLoopGroup workEventLoop = nettyConfig.getWorkEventLoopFactory().apply(host, nettyConfig);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workEventLoop)
                .channel(nettyConfig.getWorkChannelClassFn().apply(host, nettyConfig));
        group = workEventLoop;
        return bootstrap;
    }

    public void sendHostMessage(Host target, HostMessage hostMessage) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(target.getAddress(), target.getPort()).sync();
            channelFuture.channel().writeAndFlush(hostMessage).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (initialization.get()) {
            group.shutdownGracefully();
        }
    }

    protected void check() {
        if (!initialization.get()) {
            throw new IllegalStateException("Client is not ready");
        }
    }
}
