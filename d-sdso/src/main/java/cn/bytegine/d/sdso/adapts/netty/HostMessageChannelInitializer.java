package cn.bytegine.d.sdso.adapts.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class HostMessageChannelInitializer extends ChannelInitializer<SocketChannel> {
    private Supplier<List<ChannelHandlerFactory>> channelHandlersSupplier;

    public HostMessageChannelInitializer(Supplier<List<ChannelHandlerFactory>> channelHandlersSupplier) {
        this.channelHandlersSupplier = channelHandlersSupplier;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        channelHandlersSupplier.get().forEach(f -> pipeline.addLast(f.channelHandler()));
    }
}
