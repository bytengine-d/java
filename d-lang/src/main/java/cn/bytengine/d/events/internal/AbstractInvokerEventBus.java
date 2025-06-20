package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.InvokerEventBus;
import cn.bytengine.d.events.RegisterOption;
import cn.bytengine.d.fn.Consumers;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.fn.invoker.InvokerFactory;

/**
 * Invoker EventBus实现
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      AbstractInvokerEventBus
 * <li>Date:    2024/5/7 16:26
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface AbstractInvokerEventBus extends InvokerEventBus {

    /**
     * 注册调用器监听
     *
     * @param eventName 事件名称
     * @param invoker   调用器
     * @param options   可选项处理
     * @return 当前Invoker EventBus
     */
    Invoker register(String eventName, Invoker invoker, RegisterOption... options);

    @Override
    default Invoker register(String eventName, Consumers.Consumer0 consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default Invoker registerWithThrows(String eventName, Consumers.Consumer0Throws consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1> Invoker register(String eventName, Consumers.Consumer1<T1> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1> Invoker registerWithThrows(String eventName, Consumers.Consumer1Throws<T1> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2> Invoker register(String eventName, Consumers.Consumer2<T1, T2> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2> Invoker registerWithThrows(String eventName, Consumers.Consumer2Throws<T1, T2> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3> Invoker register(String eventName, Consumers.Consumer3<T1, T2, T3> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3> Invoker registerWithThrows(String eventName, Consumers.Consumer3Throws<T1, T2, T3> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4> Invoker register(String eventName, Consumers.Consumer4<T1, T2, T3, T4> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4> Invoker registerWithThrows(String eventName, Consumers.Consumer4Throws<T1, T2, T3, T4> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5> Invoker register(String eventName, Consumers.Consumer5<T1, T2, T3, T4, T5> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5> Invoker registerWithThrows(String eventName, Consumers.Consumer5Throws<T1, T2, T3, T4, T5> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6> Invoker register(String eventName, Consumers.Consumer6<T1, T2, T3, T4, T5, T6> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6> Invoker registerWithThrows(String eventName, Consumers.Consumer6Throws<T1, T2, T3, T4, T5, T6> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7> Invoker register(String eventName, Consumers.Consumer7<T1, T2, T3, T4, T5, T6, T7> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7> Invoker registerWithThrows(String eventName, Consumers.Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8> Invoker register(String eventName, Consumers.Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8> Invoker registerWithThrows(String eventName, Consumers.Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker register(String eventName, Consumers.Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker registerWithThrows(String eventName, Consumers.Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker register(String eventName, Consumers.ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker registerWithThrows(String eventName, Consumers.ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker register(String eventName, Consumers.ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker registerWithThrows(String eventName, Consumers.ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker register(String eventName, Consumers.ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }

    @Override
    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker registerWithThrows(String eventName, Consumers.ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer, RegisterOption... options) {
        return register(eventName, InvokerFactory.createInvokerFromSerializable(consumer), options);
    }
}
