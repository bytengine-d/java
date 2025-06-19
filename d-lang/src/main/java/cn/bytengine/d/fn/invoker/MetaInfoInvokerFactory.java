package cn.bytengine.d.fn.invoker;

import cn.bytengine.d.fn.Consumers;
import cn.bytengine.d.fn.Functions;
import cn.bytengine.d.lang.reflect.ClassInfo;
import cn.bytengine.d.lang.reflect.ClassInfos;
import cn.bytengine.d.lang.reflect.MethodInfo;

import java.lang.invoke.SerializedLambda;
import java.util.NoSuchElementException;

/**
 * 方法信息调用器构建工厂
 *
 * @author Ban Tenio
 * @version 1.0
 * @see MetaInfoInvoker
 */
public interface MetaInfoInvokerFactory {
    /**
     * 根据Lambda表达式和Invoker代理，创建方法信息调用器
     *
     * @param invoker          调用器
     * @param serializedLambda SerializedLambda实例，Lambda信息
     * @param isConsumer       是否包含返回值
     * @return 返回函数方法信息的调用器
     */
    static MetaInfoInvoker buildMetaInfoInvoker(Invoker invoker, SerializedLambda serializedLambda, Boolean isConsumer) {
        String implClassName = serializedLambda.getImplClass().replaceAll("\\/", ".");
        ClassInfo classInfo = ClassInfos.get(implClassName);
        String methodName = serializedLambda.getImplMethodName();
        String implMethodSignature = serializedLambda.getImplMethodSignature();
        MethodInfo methodInfo = classInfo.findAllMethods(methodName)
                .stream()
                .filter(mi -> mi.getMethodTypeSign().equals(implMethodSignature))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("the method name not existed in type"));

        return new MetaInfoInvoker(invoker, methodInfo);
    }

    // region createInvoker from consumer

    /**
     * 创建调用器
     *
     * @param consumer 无参消费者
     * @return 调用器
     */
    static MetaInfoInvoker createInvoker(Consumers.Consumer0 consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }


    /**
     * 创建调用器
     *
     * @param consumer 无参消费者
     * @return 调用器
     */
    static MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer0Throws consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @return 调用器
     */
    static <T1> MetaInfoInvoker createInvoker(Consumers.Consumer1<T1> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @return 调用器
     */
    static <T1> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer1Throws<T1> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    static <T1, T2> MetaInfoInvoker createInvoker(Consumers.Consumer2<T1, T2> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    /**
     * 创建调用器
     *
     * @param consumer 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @return 调用器
     */
    static <T1, T2> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer2Throws<T1, T2> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3> MetaInfoInvoker createInvoker(Consumers.Consumer3<T1, T2, T3> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer3Throws<T1, T2, T3> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4> MetaInfoInvoker createInvoker(Consumers.Consumer4<T1, T2, T3, T4> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer4Throws<T1, T2, T3, T4> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5> MetaInfoInvoker createInvoker(Consumers.Consumer5<T1, T2, T3, T4, T5> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer5Throws<T1, T2, T3, T4, T5> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6> MetaInfoInvoker createInvoker(Consumers.Consumer6<T1, T2, T3, T4, T5, T6> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer6Throws<T1, T2, T3, T4, T5, T6> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7> MetaInfoInvoker createInvoker(Consumers.Consumer7<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8> MetaInfoInvoker createInvoker(Consumers.Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> MetaInfoInvoker createInvoker(Consumers.Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> MetaInfoInvoker createInvokerWithThrows(Consumers.Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> MetaInfoInvoker createInvoker(Consumers.ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> MetaInfoInvoker createInvokerWithThrows(Consumers.ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> MetaInfoInvoker createInvoker(Consumers.ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> MetaInfoInvoker createInvokerWithThrows(Consumers.ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> MetaInfoInvoker createInvoker(Consumers.ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> MetaInfoInvoker createInvokerWithThrows(Consumers.ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> consumer) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(consumer, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, R> MetaInfoInvoker createInvoker(Functions.Function1<T1, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function1Throws<T1, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, R> MetaInfoInvoker createInvoker(Functions.Function2<T1, T2, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function2Throws<T1, T2, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, R> MetaInfoInvoker createInvoker(Functions.Function3<T1, T2, T3, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function3Throws<T1, T2, T3, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, R> MetaInfoInvoker createInvoker(Functions.Function4<T1, T2, T3, T4, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function4Throws<T1, T2, T3, T4, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, R> MetaInfoInvoker createInvoker(Functions.Function5<T1, T2, T3, T4, T5, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function5Throws<T1, T2, T3, T4, T5, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    /**
     * 创建调用器
     *
     * @param function 有参消费者
     * @param <T1>     参数1类型
     * @param <T2>     参数2类型
     * @param <T3>     参数3类型
     * @param <T5>     参数5类型
     * @param <T4>     参数4类型
     * @param <T6>     参数6类型
     * @param <R>      返回值类型
     * @return 调用器
     */
    static <T1, T2, T3, T4, T5, T6, R> MetaInfoInvoker createInvoker(Functions.Function6<T1, T2, T3, T4, T5, T6, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function6Throws<T1, T2, T3, T4, T5, T6, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, R> MetaInfoInvoker createInvoker(Functions.Function7<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function7Throws<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> MetaInfoInvoker createInvoker(Functions.Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function8Throws<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> MetaInfoInvoker createInvoker(Functions.Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> MetaInfoInvoker createInvokerWithThrow(Functions.Function9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> MetaInfoInvoker createInvoker(Functions.FunctionA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> MetaInfoInvoker createInvokerWithThrow(Functions.FunctionAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> MetaInfoInvoker createInvoker(Functions.FunctionB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> MetaInfoInvoker createInvokerWithThrow(Functions.FunctionBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> MetaInfoInvoker createInvoker(Functions.FunctionC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
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
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> MetaInfoInvoker createInvokerWithThrow(Functions.FunctionCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> function) {
        return (MetaInfoInvoker) InvokerFactory.createInvokerFromSerializable(function, MetaInfoInvokerFactory::buildMetaInfoInvoker);
    }

    // endregion
}
