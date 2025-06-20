package cn.bytengine.d.events;

import cn.bytengine.d.fn.invoker.Invoker;

/**
 * 事件异常通知
 *
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events
 * <li>ClassName:      ExceptionHandler
 * <li>Date:    2024/5/7 16:10
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface EventExceptionHandler {
    /**
     * 处理事件通知过程中的异常
     *
     * @param e         异常
     * @param eventName 事件名
     * @param invoker   调用器
     * @param args      参数列表
     */
    void handle(Throwable e, String eventName, Invoker invoker, Object[] args);
}
