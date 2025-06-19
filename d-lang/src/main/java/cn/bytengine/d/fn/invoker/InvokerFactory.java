package cn.bytengine.d.fn.invoker;

import cn.bytengine.d.fn.Consumers;
import cn.bytengine.d.fn.Functions;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 调用器工厂
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface InvokerFactory {

    /**
     * 根据指定的标记Serializable的Functional对象，创建调用器对象
     *
     * @param serializable 标记Serializable的Functional对象
     * @return 封装Functional调用器
     */
    Invoker create(Serializable serializable);

    // region consumer functions

    /**
     * 创建无参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     */
    static Invoker consumer0Invoker(Consumers.Consumer0 consumer) {
        return args -> {
            consumer.accept();
            return null;
        };
    }

    /**
     * 创建无参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     */
    static Invoker consumer0InvokerWithThrow(Consumers.Consumer0Throws consumer) {
        return args -> {
            consumer.accept();
            return null;
        };
    }

    /**
     * 创建1个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @return 调用器
     */
    static <T1> Invoker consumer1Invoker(Consumers.Consumer1<T1> consumer) {
        return args -> {
            consumer.accept((T1) args[0]);
            return null;
        };
    }

    /**
     * 创建1个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @return 调用器
     */
    static <T1> Invoker consumer1InvokerWithThrow(Consumers.Consumer1Throws<T1> consumer) {
        return args -> {
            consumer.accept((T1) args[0]);
            return null;
        };
    }

    /**
     * 创建2个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    static <T1, T2> Invoker consumer2Invoker(Consumers.Consumer2<T1, T2> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1]);
            return null;
        };
    }

    /**
     * 创建2个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    static <T1, T2> Invoker consumer2InvokerWithThrow(Consumers.Consumer2Throws<T1, T2> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1]);
            return null;
        };
    }

    /**
     * 创建3个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @return 调用器
     */
    static <T1, T2, T3> Invoker consumer3Invoker(Consumers.Consumer3<T1, T2, T3> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2]);
            return null;
        };
    }

    /**
     * 创建3个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @return 调用器
     */
    static <T1, T2, T3> Invoker consumer3InvokerWithThrow(Consumers.Consumer3Throws<T1, T2, T3> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2]);
            return null;
        };
    }

    /**
     * 创建4个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @return 调用器
     */
    static <T1, T2, T3, T4> Invoker consumer4Invoker(Consumers.Consumer4<T1, T2, T3, T4> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
            return null;
        };
    }

    /**
     * 创建4个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @return 调用器
     */
    static <T1, T2, T3, T4> Invoker consumer4InvokerWithThrow(Consumers.Consumer4Throws<T1, T2, T3, T4> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
            return null;
        };
    }

    /**
     * 创建5个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5> Invoker consumer5Invoker(Consumers.Consumer5<T1, T2, T3, T4, T5> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4]);
            return null;
        };
    }

    /**
     * 创建5个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5> Invoker consumer5InvokerWithThrow(Consumers.Consumer5Throws<T1, T2, T3, T4, T5> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4]);
            return null;
        };
    }

    /**
     * 创建6个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6> Invoker consumer6Invoker(Consumers.Consumer6<T1, T2, T3, T4, T5, T6> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
            return null;
        };
    }

    /**
     * 创建6个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6> Invoker consumer6InvokerWithThrow(Consumers.Consumer6Throws<T1, T2, T3, T4, T5, T6> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
            return null;
        };
    }

    /**
     * 创建7个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7> Invoker consumer7Invoker(Consumers.Consumer7<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6]);
            return null;
        };
    }

    /**
     * 创建7个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7> Invoker consumer7InvokerWithThrow(Consumers.Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6]);
            return null;
        };
    }

    /**
     * 创建8个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8> Invoker consumer8Invoker(Consumers.Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7]);
            return null;
        };
    }

    /**
     * 创建8个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8> Invoker consumer8InvokerWithThrow(Consumers.Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7]);
            return null;
        };
    }

    /**
     * 创建9个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker consumer9Invoker(Consumers.Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8]);
            return null;
        };
    }

    /**
     * 创建9个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker consumer9InvokerWithThrow(Consumers.Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8]);
            return null;
        };
    }

    /**
     * 创建10个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker consumerAInvoker(Consumers.ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9]);
            return null;
        };
    }

    /**
     * 创建10个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker consumerAInvokerWithThrow(Consumers.ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9]);
            return null;
        };
    }

    /**
     * 创建11个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker consumerBInvoker(Consumers.ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[10]);
            return null;
        };
    }

    /**
     * 创建11个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker consumerBInvokerWithThrow(Consumers.ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[10]);
            return null;
        };
    }

    /**
     * 创建12个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker consumerCInvoker(Consumers.ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[11], (TC) args[12]);
            return null;
        };
    }

    /**
     * 创建12个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker consumerCInvokerWithThrow(Consumers.ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return args -> {
            consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[11], (TC) args[12]);
            return null;
        };
    }

    // endregion

    // region function functions

    /**
     * 创建1个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, R> Invoker function1Invoker(Functions.Function1<T1, R> function) {
        return args -> function.apply((T1) args[0]);
    }

    /**
     * 创建1个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, R> Invoker function1InvokerWithThrow(Functions.Function1Throws<T1, R> function) {
        return args -> function.apply((T1) args[0]);
    }

    /**
     * 创建2个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, R> Invoker function2Invoker(Functions.Function2<T1, T2, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1]);
    }

    /**
     * 创建2个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, R> Invoker function2InvokerWithThrow(Functions.Function2Throws<T1, T2, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1]);
    }

    /**
     * 创建3个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, R> Invoker function3Invoker(Functions.Function3<T1, T2, T3, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2]);
    }

    /**
     * 创建3个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, R> Invoker function3InvokerWithThrow(Functions.Function3Throws<T1, T2, T3, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2]);
    }

    /**
     * 创建4个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, R> Invoker function4Invoker(Functions.Function4<T1, T2, T3, T4, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
    }

    /**
     * 创建4个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, R> Invoker function4InvokerWithThrow(Functions.Function4Throws<T1, T2, T3, T4, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
    }

    /**
     * 创建5个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, R> Invoker function5Invoker(Functions.Function5<T1, T2, T3, T4, T5, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4]);
    }

    /**
     * 创建5个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, R> Invoker function5InvokerWithThrow(Functions.Function5Throws<T1, T2, T3, T4, T5, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4]);
    }

    /**
     * 创建6个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, R> Invoker function6Invoker(Functions.Function6<T1, T2, T3, T4, T5, T6, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
    }

    /**
     * 创建6个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, R> Invoker function6InvokerWithThrow(Functions.Function6Throws<T1, T2, T3, T4, T5, T6, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
    }

    /**
     * 创建7个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, R> Invoker function7Invoker(Functions.Function7<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6]);
    }

    /**
     * 创建7个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, R> Invoker function7InvokerWithThrow(Functions.Function7Throws<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6]);
    }

    /**
     * 创建8个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> Invoker function8Invoker(Functions.Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7]);
    }

    /**
     * 创建8个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> Invoker function8InvokerWithThrow(Functions.Function8Throws<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7]);
    }

    /**
     * 创建9个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Invoker function9Invoker(Functions.Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8]);
    }

    /**
     * 创建9个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Invoker function9InvokerWithThrow(Functions.Function9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8]);
    }

    /**
     * 创建10个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> Invoker functionAInvoker(Functions.FunctionA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9]);
    }

    /**
     * 创建10个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> Invoker functionAInvokerWithThrow(Functions.FunctionAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9]);
    }

    /**
     * 创建11个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> Invoker functionBInvoker(Functions.FunctionB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[10]);
    }

    /**
     * 创建11个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> Invoker functionBInvokerWithThrow(Functions.FunctionBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[10]);
    }

    /**
     * 创建12个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> Invoker functionCInvoker(Functions.FunctionC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[11], (TC) args[12]);
    }

    /**
     * 创建12个参数调用器
     *
     * @param function Function
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @param <R>      返回参数类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> Invoker functionCInvokerWithThrow(Functions.FunctionCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> function) {
        return args -> function.apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[11], (TC) args[12]);
    }
    // endregion

    /**
     * 根据Serializable Lambda创建Invoker
     *
     * @param serializable Serializable化的Lambda
     * @return 调用器
     */
    static Invoker createInvokerFromSerializable(Serializable serializable) {
        return createInvokerFromSerializable(serializable, null);
    }

    /**
     * 根据Serializable Lambda创建Invoker
     *
     * @param serializable Serializable化的Lambda
     * @param factory      Invoker工厂函数
     * @return 调用器
     */
    static Invoker createInvokerFromSerializable(Serializable serializable, Functions.Function3<Invoker, SerializedLambda, Boolean, Invoker> factory) {
        SerializedLambda serializedLambda = MethodHandles.getSerializedLambda(serializable);
        String targetFunctionalClassName = serializedLambda.getFunctionalInterfaceClass();

        boolean isConsumer = targetFunctionalClassName.startsWith(Common.CHECK_CONSUMERS_PREFIX_FLAG);
        boolean isFunction = targetFunctionalClassName.startsWith(Common.CHECK_FUNCTIONS_PREFIX_FLAG);

        if (isConsumer
                || isFunction) {
            Invoker invoker = Common.CONSUMER_INVOKER_MAPPING.get(targetFunctionalClassName).apply(serializable);
            if (factory != null) {
                invoker = factory.apply(invoker, serializedLambda, isConsumer);
            }
            return invoker;
        }
        throw new UnsupportedTypeException();
    }

    // region createInvoker from consumer

    /**
     * 创建调用器
     *
     * @param consumer 无参消费者
     * @return 调用器
     */
    static Invoker createInvoker(Consumers.Consumer0 consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }


    /**
     * 创建调用器
     *
     * @param consumer 无参消费者
     * @return 调用器
     */
    static Invoker createInvokerWithThrows(Consumers.Consumer0Throws consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @return 调用器
     */
    static <T1> Invoker createInvoker(Consumers.Consumer1<T1> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @return 调用器
     */
    static <T1> Invoker createInvokerWithThrows(Consumers.Consumer1Throws<T1> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    static <T1, T2> Invoker createInvoker(Consumers.Consumer2<T1, T2> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    static <T1, T2> Invoker createInvokerWithThrows(Consumers.Consumer2Throws<T1, T2> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @return 调用器
     */
    static <T1, T2, T3> Invoker createInvoker(Consumers.Consumer3<T1, T2, T3> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @return 调用器
     */
    static <T1, T2, T3> Invoker createInvokerWithThrows(Consumers.Consumer3Throws<T1, T2, T3> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @return 调用器
     */
    static <T1, T2, T3, T4> Invoker createInvoker(Consumers.Consumer4<T1, T2, T3, T4> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @return 调用器
     */
    static <T1, T2, T3, T4> Invoker createInvokerWithThrows(Consumers.Consumer4Throws<T1, T2, T3, T4> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5> Invoker createInvoker(Consumers.Consumer5<T1, T2, T3, T4, T5> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5> Invoker createInvokerWithThrows(Consumers.Consumer5Throws<T1, T2, T3, T4, T5> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6> Invoker createInvoker(Consumers.Consumer6<T1, T2, T3, T4, T5, T6> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6> Invoker createInvokerWithThrows(Consumers.Consumer6Throws<T1, T2, T3, T4, T5, T6> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7> Invoker createInvoker(Consumers.Consumer7<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7> Invoker createInvokerWithThrows(Consumers.Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8> Invoker createInvoker(Consumers.Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8> Invoker createInvokerWithThrows(Consumers.Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker createInvoker(Consumers.Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker createInvokerWithThrows(Consumers.Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker createInvoker(Consumers.ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker createInvokerWithThrows(Consumers.ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker createInvoker(Consumers.ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker createInvokerWithThrows(Consumers.ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker createInvoker(Consumers.ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker createInvokerWithThrows(Consumers.ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return InvokerFactory.createInvokerFromSerializable(consumer);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, R> Invoker createInvoker(Functions.Function1<T1, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }
    // endregion

    // region createInvoker from function

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, R> Invoker createInvokerWithThrow(Functions.Function1Throws<T1, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, R> Invoker createInvoker(Functions.Function2<T1, T2, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, R> Invoker createInvokerWithThrow(Functions.Function2Throws<T1, T2, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, R> Invoker createInvoker(Functions.Function3<T1, T2, T3, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, R> Invoker createInvokerWithThrow(Functions.Function3Throws<T1, T2, T3, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, R> Invoker createInvoker(Functions.Function4<T1, T2, T3, T4, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, R> Invoker createInvokerWithThrow(Functions.Function4Throws<T1, T2, T3, T4, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, R> Invoker createInvoker(Functions.Function5<T1, T2, T3, T4, T5, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, R> Invoker createInvokerWithThrow(Functions.Function5Throws<T1, T2, T3, T4, T5, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, R> Invoker createInvoker(Functions.Function6<T1, T2, T3, T4, T5, T6, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, R> Invoker createInvokerWithThrow(Functions.Function6Throws<T1, T2, T3, T4, T5, T6, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, R> Invoker createInvoker(Functions.Function7<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, R> Invoker createInvokerWithThrow(Functions.Function7Throws<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> Invoker createInvoker(Functions.Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> Invoker createInvokerWithThrow(Functions.Function8Throws<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Invoker createInvoker(Functions.Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Invoker createInvokerWithThrow(Functions.Function9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> Invoker createInvoker(Functions.FunctionA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> Invoker createInvokerWithThrow(Functions.FunctionAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> Invoker createInvoker(Functions.FunctionB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> Invoker createInvokerWithThrow(Functions.FunctionBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> Invoker createInvoker(Functions.FunctionC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T4>     参数4类型
     * @param <T5>     参数5类型
     * @param <T6>     参数6类型
     * @param <T7>     参数7类型
     * @param <T8>     参数8类型
     * @param <T9>     参数9类型
     * @param <TA>     参数10类型
     * @param <TB>     参数11类型
     * @param <TC>     参数12类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> Invoker createInvokerWithThrow(Functions.FunctionCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> function) {
        return InvokerFactory.createInvokerFromSerializable(function);
    }

    // endregion

    /**
     * InvokerFactory公共内容辅助类
     *
     * @author Ban Tenio
     * @version 1.0
     */
    abstract class Common {
        /**
         * Consumers类名前缀
         */
        static final String CHECK_CONSUMERS_PREFIX_FLAG = Consumers.class.getName().replaceAll("\\.", "\\/") + '$';
        /**
         * Functions类名前缀
         */
        static final String CHECK_FUNCTIONS_PREFIX_FLAG = Functions.class.getName().replaceAll("\\.", "\\/") + '$';
        /**
         * InvokerFactory映射关系
         */
        static final Map<String, Function<Serializable, Invoker>> CONSUMER_INVOKER_MAPPING = new ConcurrentHashMap<>();

        static {
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer0.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer0Invoker((Consumers.Consumer0) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer1.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer1Invoker((Consumers.Consumer1) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer2.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer2Invoker((Consumers.Consumer2) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer3.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer3Invoker((Consumers.Consumer3) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer4.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer4Invoker((Consumers.Consumer4) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer5.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer5Invoker((Consumers.Consumer5) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer6.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer6Invoker((Consumers.Consumer6) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer7.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer7Invoker((Consumers.Consumer7) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer8.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer8Invoker((Consumers.Consumer8) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer9.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer9Invoker((Consumers.Consumer9) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerA.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerAInvoker((Consumers.ConsumerA) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerB.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerBInvoker((Consumers.ConsumerB) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerC.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerCInvoker((Consumers.ConsumerC) serializable));

            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer0Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer0InvokerWithThrow((Consumers.Consumer0Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer1Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer1InvokerWithThrow((Consumers.Consumer1Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer2Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer2InvokerWithThrow((Consumers.Consumer2Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer3Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer3InvokerWithThrow((Consumers.Consumer3Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer4Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer4InvokerWithThrow((Consumers.Consumer4Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer5Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer5InvokerWithThrow((Consumers.Consumer5Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer6Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer6InvokerWithThrow((Consumers.Consumer6Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer7Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer7InvokerWithThrow((Consumers.Consumer7Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer8Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer8InvokerWithThrow((Consumers.Consumer8Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer9Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer9InvokerWithThrow((Consumers.Consumer9Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerAThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerAInvokerWithThrow((Consumers.ConsumerAThrows) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerBThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerBInvokerWithThrow((Consumers.ConsumerBThrows) serializable));
            CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerCThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerCInvokerWithThrow((Consumers.ConsumerCThrows) serializable));

            CONSUMER_INVOKER_MAPPING.put(Functions.Function1.class.getName().replaceAll("\\.", "\\/"), serializable -> function1Invoker((Functions.Function1) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function2.class.getName().replaceAll("\\.", "\\/"), serializable -> function2Invoker((Functions.Function2) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function3.class.getName().replaceAll("\\.", "\\/"), serializable -> function3Invoker((Functions.Function3) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function4.class.getName().replaceAll("\\.", "\\/"), serializable -> function4Invoker((Functions.Function4) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function5.class.getName().replaceAll("\\.", "\\/"), serializable -> function5Invoker((Functions.Function5) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function6.class.getName().replaceAll("\\.", "\\/"), serializable -> function6Invoker((Functions.Function6) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function7.class.getName().replaceAll("\\.", "\\/"), serializable -> function7Invoker((Functions.Function7) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function8.class.getName().replaceAll("\\.", "\\/"), serializable -> function8Invoker((Functions.Function8) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function9.class.getName().replaceAll("\\.", "\\/"), serializable -> function9Invoker((Functions.Function9) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.FunctionA.class.getName().replaceAll("\\.", "\\/"), serializable -> functionAInvoker((Functions.FunctionA) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.FunctionB.class.getName().replaceAll("\\.", "\\/"), serializable -> functionBInvoker((Functions.FunctionB) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.FunctionC.class.getName().replaceAll("\\.", "\\/"), serializable -> functionCInvoker((Functions.FunctionC) serializable));

            CONSUMER_INVOKER_MAPPING.put(Functions.Function1Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function1InvokerWithThrow((Functions.Function1Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function2Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function2InvokerWithThrow((Functions.Function2Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function3Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function3InvokerWithThrow((Functions.Function3Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function4Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function4InvokerWithThrow((Functions.Function4Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function5Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function5InvokerWithThrow((Functions.Function5Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function6Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function6InvokerWithThrow((Functions.Function6Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function7Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function7InvokerWithThrow((Functions.Function7Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function8Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function8InvokerWithThrow((Functions.Function8Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.Function9Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> function9InvokerWithThrow((Functions.Function9Throws) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.FunctionAThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> functionAInvokerWithThrow((Functions.FunctionAThrows) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.FunctionBThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> functionBInvokerWithThrow((Functions.FunctionBThrows) serializable));
            CONSUMER_INVOKER_MAPPING.put(Functions.FunctionCThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> functionCInvokerWithThrow((Functions.FunctionCThrows) serializable));
        }
    }
}
