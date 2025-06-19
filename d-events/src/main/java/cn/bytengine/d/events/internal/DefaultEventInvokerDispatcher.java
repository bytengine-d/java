package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.EventExceptionHandler;
import cn.bytengine.d.events.RegisterOption;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.lang.CollectionTools;

import java.util.Collection;
import java.util.List;

/**
 * 默认的事件分发器
 *
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      DefaultEventInvokerDispatcher
 * <li>Date:    2024/5/7 15:29
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class DefaultEventInvokerDispatcher implements EventInvokerDispatcher {
    @Override
    public void dispatchEvent(
            List<InvokerRegistration> invokerRegistrations,
            String eventName,
            EventExceptionHandler eventExceptionHandler,
            Object... args) {
        for (InvokerRegistration invokerRegistration : invokerRegistrations) {
            processInvokerRegistration(invokerRegistration, eventName, eventExceptionHandler, args);
        }
    }

    /**
     * 调用调用器
     *
     * @param invokerRegistration   调用器注册信息
     * @param eventName             事件名称
     * @param eventExceptionHandler 事件通知失败回调
     * @param args                  调用参数
     */
    protected void processInvokerRegistration(InvokerRegistration invokerRegistration,
                                              String eventName,
                                              EventExceptionHandler eventExceptionHandler,
                                              Object[] args) {
        Collection<RegisterOption> options = invokerRegistration.getRegisterOptions();
        Invoker invoker = invokerRegistration.getInvoker();
        Invoker tempInvoker = invoker;
        try {
            if (CollectionTools.isNotEmpty(options)) {
                for (RegisterOption option : options) {
                    tempInvoker = option.option(eventName, tempInvoker);
                }
            }
            tempInvoker.invoke(args);
        } catch (Throwable ex) {
            eventExceptionHandler.handle(ex, eventName, invoker, args);
        }
    }
}
