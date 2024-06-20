package cn.bytengine.d.events;

import cn.bytengine.d.events.internal.NothingEventExceptionHandler;
import cn.bytengine.d.fn.Consumers;
import cn.bytengine.d.fn.invoker.Invoker;

/**
 * Invoker事件总线
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface InvokerEventBus {
    /**
     * 发布事件，无异常处理（忽略异常）
     *
     * @param eventName 事件名
     * @param args      参数列表
     */
    default void pub(String eventName, Object... args) {
        pub(eventName, NothingEventExceptionHandler.INSTANCE, args);
    }

    /**
     * 发布事件
     *
     * @param eventName             事件名
     * @param eventExceptionHandler 异常事件处理器
     * @param args                  参数列表
     */
    void pub(String eventName, EventExceptionHandler eventExceptionHandler, Object... args);

    /**
     * 注销指定事件监听
     *
     * @param eventName 事件名
     * @param invoker   调用器
     */
    void unregister(String eventName, Invoker invoker);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  无参消费者
     * @param options   注册选项
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    Invoker register(String eventName, Consumers.Consumer0 consumer, RegisterOption... options);


    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  无参消费者
     * @param options   注册选项
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    Invoker registerWithThrows(String eventName, Consumers.Consumer0Throws consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1> Invoker register(String eventName, Consumers.Consumer1<T1> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1> Invoker registerWithThrows(String eventName, Consumers.Consumer1Throws<T1> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2> Invoker register(String eventName, Consumers.Consumer2<T1, T2> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2> Invoker registerWithThrows(String eventName, Consumers.Consumer2Throws<T1, T2> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3> Invoker register(String eventName, Consumers.Consumer3<T1, T2, T3> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3> Invoker registerWithThrows(String eventName, Consumers.Consumer3Throws<T1, T2, T3> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4> Invoker register(String eventName, Consumers.Consumer4<T1, T2, T3, T4> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4> Invoker registerWithThrows(String eventName, Consumers.Consumer4Throws<T1, T2, T3, T4> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5> Invoker register(String eventName, Consumers.Consumer5<T1, T2, T3, T4, T5> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5> Invoker registerWithThrows(String eventName, Consumers.Consumer5Throws<T1, T2, T3, T4, T5> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6> Invoker register(String eventName, Consumers.Consumer6<T1, T2, T3, T4, T5, T6> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6> Invoker registerWithThrows(String eventName, Consumers.Consumer6Throws<T1, T2, T3, T4, T5, T6> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7> Invoker register(String eventName, Consumers.Consumer7<T1, T2, T3, T4, T5, T6, T7> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7> Invoker registerWithThrows(String eventName, Consumers.Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8> Invoker register(String eventName, Consumers.Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8> Invoker registerWithThrows(String eventName, Consumers.Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker register(String eventName, Consumers.Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker registerWithThrows(String eventName, Consumers.Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @param <TA>      参数10类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker register(String eventName, Consumers.ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @param <TA>      参数10类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker registerWithThrows(String eventName, Consumers.ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @param <TA>      参数10类型
     * @param <TB>      参数11类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker register(String eventName, Consumers.ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @param <TA>      参数10类型
     * @param <TB>      参数11类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker registerWithThrows(String eventName, Consumers.ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @param <TA>      参数10类型
     * @param <TB>      参数11类型
     * @param <TC>      参数12类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker register(String eventName, Consumers.ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer, RegisterOption... options);

    /**
     * 注册事件监听
     *
     * @param eventName 事件名
     * @param consumer  有参消费者
     * @param options   注册选项
     * @param <T1>      参数1类型
     * @param <T2>      参数2类型
     * @param <T3>      参数3类型
     * @param <T4>      参数4类型
     * @param <T5>      参数5类型
     * @param <T6>      参数6类型
     * @param <T7>      参数7类型
     * @param <T8>      参数8类型
     * @param <T9>      参数9类型
     * @param <TA>      参数10类型
     * @param <TB>      参数11类型
     * @param <TC>      参数12类型
     * @return 注册后实现Invoker，Invoker用于注销监听
     */
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker registerWithThrows(String eventName, Consumers.ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer, RegisterOption... options);
}
