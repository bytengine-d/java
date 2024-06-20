package cn.bytegine.d.sdso;

import cn.hutool.core.lang.Assert;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class DefaultHostGroup implements HostGroup {
    private Map<String, Host> groupItems;
    private Collection<Host> hosts;

    public DefaultHostGroup() {
        this.groupItems = buildGroupItemsMap();
        this.hosts = buildHostCollection();
    }

    protected Map<String, Host> buildGroupItemsMap() {
        return new ConcurrentHashMap<>();
    }

    protected Collection<Host> buildHostCollection() {
        return new CopyOnWriteArraySet<>();
    }

    @Override
    public Collection<Host> getGroupItems() {
        return hosts;
    }

    @Override
    public void addHost(Host host) {
        Assert.notNull(host, "The host instance must not null.");
        if (groupItems.containsKey(host.getId())) {
            return;
        }
        groupItems.put(host.getId(), host);
        hosts.add(host);
    }

    @Override
    public Host getHost(String hostId) {
        Assert.notBlank(hostId, "The hostId must not blank or null.");
        return groupItems.get(hostId);
    }

    @Override
    public void removeHost(String hostId) {
        Assert.notBlank(hostId, "The hostId must not blank or null.");
        if (groupItems.containsKey(hostId)) {
            Host removed = groupItems.remove(hostId);
            hosts.remove(removed);
        }
    }

    @Override
    public void removeHost(Host host) {
        Assert.notNull(host, "The host instance must not null.");
    }
}
