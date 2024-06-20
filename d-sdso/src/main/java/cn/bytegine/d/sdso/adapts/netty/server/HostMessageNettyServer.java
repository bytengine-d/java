package cn.bytegine.d.sdso.adapts.netty.server;

import cn.bytegine.d.sdso.HostGroup;
import cn.bytegine.d.sdso.adapts.netty.HostMessageChannelInitializer;
import cn.bytegine.d.sdso.adapts.netty.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageNettyServer implements Closeable {
    private String serverAddress;
    private int serverPort;

    private EventLoopGroup boss;
    private EventLoopGroup work;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private HostMessageChannelInitializer initializer;
    private Channel channel;

    public HostMessageNettyServer(HostGroup hostGroup,
                                  NettyConfig nettyConfig,
                                  HostMessageChannelInitializer initializer) {
        this.initializer = initializer;
        this.init(hostGroup, nettyConfig);
    }

    protected void init(HostGroup hostGroup,
                        NettyConfig nettyConfig) {
        this.serverAddress = nettyConfig.getNettyServerAddressProvider().apply(hostGroup, nettyConfig);
        this.serverPort = nettyConfig.getNettyServerPortProvider().apply(hostGroup, nettyConfig);
    }


    public void start() {
        if (isRunning.get()) {
            return;
        }
        isRunning.set(true);
        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        ChannelFuture channelFuture = server.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer)
                .bind(serverAddress, serverPort);
        this.channel = channelFuture.channel();
        try {
            channelFuture.sync();
        } catch (InterruptedException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (!isRunning.get()) {
            return;
        }
        isRunning.set(false);
        this.boss.shutdownGracefully();
        this.work.shutdownGracefully();
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
