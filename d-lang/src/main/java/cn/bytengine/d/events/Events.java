package cn.bytengine.d.events;

import cn.bytengine.d.events.internal.*;

/**
 * 事件总线构建服务类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Events {
    private Events() {
    }

    /**
     * 默认全局EventBus
     */
    private static final InvokerEventBus GLOBAL_EVENT_BUS;

    static {
        GLOBAL_EVENT_BUS = general().build();
    }

    /**
     * 构建DefaultInvokerEventBus构建器
     *
     * @return DefaultInvokerEventBusBuilder
     */
    public static DefaultInvokerEventBusBuilder general() {
        return new DefaultInvokerEventBusBuilder();
    }

    /**
     * 获取全局EventBus
     *
     * @return EventBus实例
     */
    public static InvokerEventBus global() {
        return GLOBAL_EVENT_BUS;
    }

    /**
     * DefaultInvokerEventBus构建器
     *
     * @author Ban Tenio
     * @version 1.0
     */
    public static final class DefaultInvokerEventBusBuilder {
        private EventRouter eventRouter = new DefaultEventRouter();
        private EventInvokerDispatcher dispatcher = new DefaultEventInvokerDispatcher();

        /**
         * 修改默认EventRouter策略
         *
         * @param eventRouter 事件路由
         * @return 当前构建器
         */
        public DefaultInvokerEventBusBuilder setEventRouter(EventRouter eventRouter) {
            this.eventRouter = eventRouter;
            return this;
        }

        /**
         * 修改默认EventInvokerDispatcher策略
         *
         * @param dispatcher 事件分发器
         * @return 当前构建器
         */
        public DefaultInvokerEventBusBuilder setDispatcher(EventInvokerDispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        /**
         * 根据指定策略创建默认InvokerEventBus事件总线
         *
         * @return 事件总线
         */
        public InvokerEventBus build() {
            return new DefaultInvokerEventBus(eventRouter, dispatcher);
        }
    }
}
