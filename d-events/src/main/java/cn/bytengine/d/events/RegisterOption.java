package cn.bytengine.d.events;

import cn.bytengine.d.fn.invoker.Invoker;

/**
 * 事件注册选项
 */
public interface RegisterOption {
    default Invoker option(String eventName, Invoker invoker) {
        return invoker;
    }
}
