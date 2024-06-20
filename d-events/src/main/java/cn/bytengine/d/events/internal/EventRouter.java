package cn.bytengine.d.events.internal;

import java.util.List;

/**
 * 事件路由策略
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      Router
 * <li>Date:    2024/5/7 11:33
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface EventRouter {
    /**
     * 添加事件调用
     *
     * @param eventName 事件名称
     * @param invoker   调用器
     */
    void add(final String eventName, final InvokerRegistration invoker);

    /**
     * 删除指定事件中指定调用器
     *
     * @param eventName 事件名称
     * @param invoker   调用器
     */
    void remove(final String eventName, final InvokerRegistration invoker);

    /**
     * 清空指定事件调用器
     *
     * @param eventName 事件名称
     */
    void remove(final String eventName);

    /**
     * 指定事件是否包含调用器
     *
     * @param eventName 事件名称
     * @param invoker   调用器
     * @return 是否包含指定调用器
     */
    boolean has(final String eventName, final InvokerRegistration invoker);

    /**
     * 指定事件是否有调用器监听
     *
     * @param eventName 事件名称
     * @return 是否有调用器在事件监听列表
     */
    boolean has(final String eventName);

    /**
     * 根据事件名，匹配调用器队列
     *
     * @param eventName 事件名称
     * @return 调用器列表
     */
    List<InvokerRegistration> matching(final String eventName);
}
