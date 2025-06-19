package cn.bytengine.d.events;

import cn.bytengine.d.fn.invoker.Invoker;

/**
 * 事件注册选项
 */
public interface RegisterOption {
    /**
     * 注册选项
     *
     * @param eventName 事件名称
     * @param invoker   调用器
     * @return 选项影响调用器
     */
    default Invoker option(String eventName, Invoker invoker) {
        return invoker;
    }
}
