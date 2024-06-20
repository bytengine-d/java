package cn.bytengine.d.events;

import cn.bytengine.d.events.internal.*;
import cn.bytengine.d.fn.invoker.ConsumerInvokerFactory;
import cn.bytengine.d.fn.invoker.InvokerFactory;

/**
 * 事件总线构建服务类
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events
 * <li>ClassName:      Events
 * <li>Date:    2024/5/7 17:13
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Events {
    private Events() {
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
     * DefaultInvokerEventBus构建器
     *
     * @author Ban Tenio
     * @version 1.0
     */
    public static final class DefaultInvokerEventBusBuilder {
        private InvokerFactory invokerFactory = new ConsumerInvokerFactory();
        private EventRouter eventRouter = new DefaultEventRouter();
        private EventInvokerDispatcher dispatcher = new DefaultEventInvokerDispatcher();

        /**
         * 修改默认InvokerFactory策略
         *
         * @param invokerFactory 调用器工厂
         * @return 当前构建器
         */
        public DefaultInvokerEventBusBuilder setInvokerFactory(InvokerFactory invokerFactory) {
            this.invokerFactory = invokerFactory;
            return this;
        }

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
            return new DefaultInvokerEventBus(invokerFactory, eventRouter, dispatcher);
        }
    }
}
