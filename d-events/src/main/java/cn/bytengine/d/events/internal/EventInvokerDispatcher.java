package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.EventExceptionHandler;

import java.util.List;

/**
 * 事件Invoker分发器
 *
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      InvokerDispatcher
 * <li>Date:    2024/5/7 13:25
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface EventInvokerDispatcher {
    /**
     * 分发
     *
     * @param invoker               调用器列表
     * @param eventName             事件名
     * @param eventExceptionHandler 事件异常处理器
     * @param args                  事件参数
     */
    void dispatchEvent(List<InvokerRegistration> invoker,
                       String eventName,
                       EventExceptionHandler eventExceptionHandler,
                       Object... args);
}
