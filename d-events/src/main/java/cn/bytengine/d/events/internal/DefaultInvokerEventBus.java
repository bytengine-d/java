package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.EventExceptionHandler;
import cn.bytengine.d.events.RegisterOption;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.hutool.core.lang.Assert;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.List;

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
    private final MultiKeyMap<Object, InvokerRegistration> eventInvokerRegistrationMap = new MultiKeyMap<>();


    /**
     * 指定调用器工厂，以及路由策略的调用器事件总线
     *
     * @param eventRouter    事件路由策略
     * @param dispatcher     事件分发器
     */
    public DefaultInvokerEventBus(EventRouter eventRouter,
                                  EventInvokerDispatcher dispatcher) {
        Assert.notNull(eventRouter, "EventRouter must not be null");
        Assert.notNull(dispatcher, "EventInvokerDispatcher must not be null");
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
        InvokerRegistration invokerRegistration = eventInvokerRegistrationMap.get(eventName, invoker);
        if (invokerRegistration != null) {
            eventRouter.remove(eventName, invokerRegistration);
            eventInvokerRegistrationMap.remove(eventName, invoker);
        }
    }

    @Override
    public Invoker register(String eventName, Invoker invoker, RegisterOption... options) {
        InvokerRegistration invokerRegistration = new InvokerRegistration(invoker, options);
        eventRouter.add(eventName, invokerRegistration);
        eventInvokerRegistrationMap.put(eventName, invoker, invokerRegistration);
        return invoker;
    }
}
