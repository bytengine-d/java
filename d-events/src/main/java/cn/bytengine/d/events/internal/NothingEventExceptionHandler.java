package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.EventExceptionHandler;
import cn.bytengine.d.fn.invoker.Invoker;

/**
 * 无操作的事件异常处理器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class NothingEventExceptionHandler implements EventExceptionHandler {
    /**
     * 单实例
     */
    public static final NothingEventExceptionHandler INSTANCE = new NothingEventExceptionHandler();

    private NothingEventExceptionHandler() {
    }

    @Override
    public void handle(Throwable e, String eventName, Invoker invoker, Object[] args) {

    }
}
