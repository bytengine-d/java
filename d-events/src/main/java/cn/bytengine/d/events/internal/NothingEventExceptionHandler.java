package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.EventExceptionHandler;
import cn.bytengine.d.fn.invoker.Invoker;

/**
 * 无操作的事件异常处理器
 *
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      NothingEventExceptionHandler
 * <li>Date:    2024/5/7 16:13
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class NothingEventExceptionHandler implements EventExceptionHandler {
    public static final NothingEventExceptionHandler INSTANCE = new NothingEventExceptionHandler();

    private NothingEventExceptionHandler() {
    }

    @Override
    public void handle(Throwable e, String eventName, Invoker invoker, Object[] args) {

    }
}
