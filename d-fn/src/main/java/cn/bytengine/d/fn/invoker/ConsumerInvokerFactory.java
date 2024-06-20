package cn.bytengine.d.fn.invoker;

import cn.bytengine.d.fn.Consumers;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * ConsumerX调用器工厂
 * <p>
 * 封装
 * </p>
 */
public class ConsumerInvokerFactory implements InvokerFactory {
    private static final String CHECK_CONSUMERS_PREFIX_FLAG = Consumers.class.getName().replaceAll("\\.", "\\/") + '$';
    private static final Map<String, Function<Serializable, Invoker>> CONSUMER_INVOKER_MAPPING = new ConcurrentHashMap<>();

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

        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer0Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer0Invoker((Consumers.Consumer0Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer1Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer1Invoker((Consumers.Consumer1Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer2Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer2Invoker((Consumers.Consumer2Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer3Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer3Invoker((Consumers.Consumer3Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer4Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer4Invoker((Consumers.Consumer4Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer5Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer5Invoker((Consumers.Consumer5Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer6Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer6Invoker((Consumers.Consumer6Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer7Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer7Invoker((Consumers.Consumer7Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer8Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer8Invoker((Consumers.Consumer8Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.Consumer9Throws.class.getName().replaceAll("\\.", "\\/"), serializable -> consumer9Invoker((Consumers.Consumer9Throws) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerAThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerAInvoker((Consumers.ConsumerAThrows) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerBThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerBInvoker((Consumers.ConsumerBThrows) serializable));
        CONSUMER_INVOKER_MAPPING.put(Consumers.ConsumerCThrows.class.getName().replaceAll("\\.", "\\/"), serializable -> consumerCInvoker((Consumers.ConsumerCThrows) serializable));
    }

    /**
     * 创建无参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     */
    public static Invoker consumer0Invoker(Consumers.Consumer0 consumer) {
        return args -> consumer.accept();
    }

    /**
     * 创建无参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     */
    public static Invoker consumer0Invoker(Consumers.Consumer0Throws consumer) {
        return args -> consumer.accept();
    }

    /**
     * 创建1个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @return 调用器
     */
    public static <T1> Invoker consumer1Invoker(Consumers.Consumer1<T1> consumer) {
        return args -> consumer.accept((T1) args[0]);
    }

    /**
     * 创建1个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @return 调用器
     */
    public static <T1> Invoker consumer1Invoker(Consumers.Consumer1Throws<T1> consumer) {
        return args -> consumer.accept((T1) args[0]);
    }

    /**
     * 创建2个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     */
    public static <T1, T2> Invoker consumer2Invoker(Consumers.Consumer2<T1, T2> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1]);
    }

    /**
     * 创建2个参数调用器
     *
     * @param consumer Consumer
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    public static <T1, T2> Invoker consumer2Invoker(Consumers.Consumer2Throws<T1, T2> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1]);
    }

    /**
     * 创建3个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     */
    public static <T1, T2, T3> Invoker consumer3Invoker(Consumers.Consumer3<T1, T2, T3> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2]);
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
    public static <T1, T2, T3> Invoker consumer3Invoker(Consumers.Consumer3Throws<T1, T2, T3> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2]);
    }

    /**
     * 创建4个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     */
    public static <T1, T2, T3, T4> Invoker consumer4Invoker(Consumers.Consumer4<T1, T2, T3, T4> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
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
    public static <T1, T2, T3, T4> Invoker consumer4Invoker(Consumers.Consumer4Throws<T1, T2, T3, T4> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
    }

    /**
     * 创建5个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     */
    public static <T1, T2, T3, T4, T5> Invoker consumer5Invoker(Consumers.Consumer5<T1, T2, T3, T4, T5> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4]);
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
    public static <T1, T2, T3, T4, T5> Invoker consumer5Invoker(Consumers.Consumer5Throws<T1, T2, T3, T4, T5> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4]);
    }

    /**
     * 创建6个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     */
    public static <T1, T2, T3, T4, T5, T6> Invoker consumer6Invoker(Consumers.Consumer6<T1, T2, T3, T4, T5, T6> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
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
    public static <T1, T2, T3, T4, T5, T6> Invoker consumer6Invoker(Consumers.Consumer6Throws<T1, T2, T3, T4, T5, T6> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
    }

    /**
     * 创建7个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     */
    public static <T1, T2, T3, T4, T5, T6, T7> Invoker consumer7Invoker(Consumers.Consumer7<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6]);
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
    public static <T1, T2, T3, T4, T5, T6, T7> Invoker consumer7Invoker(Consumers.Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6]);
    }

    /**
     * 创建8个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Invoker consumer8Invoker(Consumers.Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7]);
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Invoker consumer8Invoker(Consumers.Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7]);
    }

    /**
     * 创建9个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     * @param <T9> 参数9类型
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker consumer9Invoker(Consumers.Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8]);
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Invoker consumer9Invoker(Consumers.Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8]);
    }

    /**
     * 创建10个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     * @param <T9> 参数9类型
     * @param <TA> 参数10类型
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker consumerAInvoker(Consumers.ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9]);
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> Invoker consumerAInvoker(Consumers.ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9]);
    }

    /**
     * 创建11个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     * @param <T9> 参数9类型
     * @param <TA> 参数10类型
     * @param <TB> 参数11类型
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker consumerBInvoker(Consumers.ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[10]);
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> Invoker consumerBInvoker(Consumers.ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[10]);
    }

    /**
     * 创建12个参数调用器
     *
     * @param consumer Consumer
     * @return 调用器
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     * @param <T9> 参数9类型
     * @param <TA> 参数10类型
     * @param <TB> 参数11类型
     * @param <TC> 参数12类型
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker consumerCInvoker(Consumers.ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[11], (TC) args[12]);
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> Invoker consumerCInvoker(Consumers.ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return args -> consumer.accept((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5], (T7) args[6], (T8) args[7], (T9) args[8], (TA) args[9], (TB) args[11], (TC) args[12]);
    }

    public Invoker create(Serializable serializable) {
        SerializedLambda serializedLambda = MethodHandles.getSerializedLambda(serializable);
        String targetFunctionalClassName = serializedLambda.getFunctionalInterfaceClass();
        if (targetFunctionalClassName.startsWith(CHECK_CONSUMERS_PREFIX_FLAG)) {
            return CONSUMER_INVOKER_MAPPING.get(targetFunctionalClassName).apply(serializable);
        }
        throw new UnsupportedTypeException();
    }
}
