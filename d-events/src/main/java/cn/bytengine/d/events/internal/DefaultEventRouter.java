package cn.bytengine.d.events.internal;

import cn.hutool.core.collection.CollUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认事件路由，根据事件名完全匹配
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      DefaultEventRouter
 * <li>Date:    2024/5/7 11:49
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class DefaultEventRouter implements EventRouter {
    private final Map<String, List<InvokerRegistration>> invokerMap = new ConcurrentHashMap<>();

    @Override
    public void add(String eventName, InvokerRegistration invoker) {
        invokerMap.computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>()).add(invoker);
    }

    @Override
    public void remove(String eventName, InvokerRegistration invoker) {
        List<InvokerRegistration> invokerList = invokerMap.get(eventName);
        if (CollUtil.isNotEmpty(invokerMap)) {
            invokerList.remove(invoker);
        }
    }

    @Override
    public void remove(String eventName) {
        invokerMap.remove(eventName);
    }

    @Override
    public boolean has(String eventName, InvokerRegistration invoker) {
        List<InvokerRegistration> invokerList = invokerMap.get(eventName);
        if (CollUtil.isNotEmpty(invokerList)) {
            return invokerList.contains(invoker);
        }
        return false;
    }

    @Override
    public boolean has(String eventName) {
        return CollUtil.isNotEmpty(invokerMap.get(eventName));
    }

    @Override
    public List<InvokerRegistration> matching(String eventName) {
        List<InvokerRegistration> invokerList = invokerMap.get(eventName);
        return CollUtil.isNotEmpty(invokerList) ? Collections.unmodifiableList(invokerList) : Collections.emptyList();
    }
}
