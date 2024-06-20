package cn.bytegine.d.sdso;

import cn.bytegine.d.sdso.message.HostMessage;

import java.util.Collection;

/**
 * @author sunkaihan
 * @version 1.0
 */
public interface HostGroup {

    Collection<Host> getGroupItems();

    void addHost(Host host);

    Host getHost(String hostId);

    void removeHost(String hostId);

    void removeHost(Host host);

    default void sendMessage(String hostId, HostMessage message) {
        getHost(hostId).sendMessage(message);
    }

    default void sendGroupMessage(HostMessage message) {
        getGroupItems().forEach(host -> host.sendMessage(message));
    }
}
