package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.EventExceptionHandler;
import cn.bytengine.d.events.RegisterOption;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.lang.AssertTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 默认的调用器事件总线实现
 *
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      DefaultEventBus
 * <li>Date:    2024/5/7 16:25
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class DefaultInvokerEventBus implements AbstractInvokerEventBus {
    private final EventRouter eventRouter;
    private final EventInvokerDispatcher dispatcher;
    private final Map<EventInvokerKey, InvokerRegistration> eventInvokerRegistrationMap = new HashMap<>();


    /**
     * 指定调用器工厂，以及路由策略的调用器事件总线
     *
     * @param eventRouter    事件路由策略
     * @param dispatcher     事件分发器
     */
    public DefaultInvokerEventBus(EventRouter eventRouter,
                                  EventInvokerDispatcher dispatcher) {
        AssertTools.notNull(eventRouter, "EventRouter must not be null");
        AssertTools.notNull(dispatcher, "EventInvokerDispatcher must not be null");
        this.eventRouter = eventRouter;
        this.dispatcher = dispatcher;
    }

    @Override
    public void pub(String eventName, EventExceptionHandler eventExceptionHandler, Object... args) {
        List<InvokerRegistration> registrationList = eventRouter.matching(eventName);
        dispatcher.dispatchEvent(registrationList, eventName, eventExceptionHandler, args);
    }

    @Override
    public void unregister(String eventName, Invoker invoker) {
        EventInvokerKey key = new EventInvokerKey(eventName, invoker);
        InvokerRegistration invokerRegistration = eventInvokerRegistrationMap.get(key);
        if (invokerRegistration != null) {
            eventRouter.remove(eventName, invokerRegistration);
            eventInvokerRegistrationMap.remove(key);
        }
    }

    @Override
    public Invoker register(String eventName, Invoker invoker, RegisterOption... options) {
        InvokerRegistration invokerRegistration = new InvokerRegistration(invoker, options);
        eventRouter.add(eventName, invokerRegistration);
        eventInvokerRegistrationMap.put(new EventInvokerKey(eventName, invoker), invokerRegistration);
        return invoker;
    }

    private static class EventInvokerKey {
        final String eventName;
        final Invoker invoker;

        public EventInvokerKey(String eventName, Invoker invoker) {
            this.eventName = eventName;
            this.invoker = invoker;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            EventInvokerKey that = (EventInvokerKey) o;
            return Objects.equals(eventName, that.eventName) && Objects.equals(invoker, that.invoker);
        }

        @Override
        public int hashCode() {
            return Objects.hash(eventName, invoker);
        }

        @Override
        public String toString() {
            return "EventInvokerKey{" +
                    "eventName='" + eventName + '\'' +
                    ", invoker=" + invoker +
                    '}';
        }
    }
}
