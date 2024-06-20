package cn.bytegine.d.sdso.adapts.netty;

import io.netty.channel.ChannelHandler;

/**
 * @author sunkaihan
 * @version 1.0
 */
public interface ChannelHandlerFactory {
    ChannelHandler channelHandler();
}
