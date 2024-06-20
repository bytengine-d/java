package cn.bytegine.d.sdso.adapts.netty;

import cn.bytegine.d.sdso.Host;
import cn.bytegine.d.sdso.HostGroup;
import cn.bytegine.d.sdso.message.HostMessage;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;

import java.util.function.BiFunction;

/**
 * @author sunkaihan
 * @version 1.0
 */
public interface NettyConfig {
    AttributeKey<HostMessage> NETTY_CONFIG_ATTRIBUTE_KEY = AttributeKey.valueOf("netty_config");

    BiFunction<Host, NettyConfig, EventLoopGroup> getWorkEventLoopFactory();

    void setWorkEventLoopFactory(BiFunction<Host, NettyConfig, EventLoopGroup> eventLoopGroupFactory);

    BiFunction<Host, NettyConfig, Class<? extends Channel>> getWorkChannelClassFn();

    void setWorkChannelClassFn(BiFunction<Host, NettyConfig, Class<? extends Channel>> workChannelClassFn);

    BiFunction<Host, NettyConfig, EventLoopGroup> getBossEventLoopFactory();

    void setBossEventLoopFactory(BiFunction<Host, NettyConfig, EventLoopGroup> eventLoopGroupFactory);

    BiFunction<Host, NettyConfig, ByteBufAllocator> getByteBufAllocatorProvider();

    void setByteBufAllocatorProvider(BiFunction<Host, NettyConfig, ByteBufAllocator> byteBufAllocatorProvider);

    BiFunction<HostGroup, NettyConfig, String> getNettyServerAddressProvider();

    void setNettyServerAddressProvider(BiFunction<HostGroup, NettyConfig, String> nettyServerAddressProvider);

    BiFunction<HostGroup, NettyConfig, Integer> getNettyServerPortProvider();

    void setNettyServerPortProvider(BiFunction<HostGroup, NettyConfig, Integer> nettyServerPortProvider);
}
