package cn.bytengine.d.fn;

import java.io.Serializable;

/**
 * 枚举Consumer函数形式，和辅助类
 * <p>
 * Consumer0, Consumer1, Consumer2..., ConsumerC(Consumer12)
 * </p>
 * <p>
 * Consumer0-C都是Serializable，支持SerializedLambda
 * </p>
 */
public abstract class Consumers {

    private Consumers() {
    }

    /**
     * 无参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     */
    public interface Consumer0 extends Serializable {
        void accept();
    }

    /**
     * 无参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     */
    public interface Consumer0Throws extends Serializable {
        void accept() throws Throwable;
    }

    /**
     * 1个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     */
    public interface Consumer1<T1> extends Serializable {
        void accept(T1 arg1);
    }

    /**
     * 1个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     */
    public interface Consumer1Throws<T1> extends Serializable {
        void accept(T1 arg1) throws Throwable;
    }

    /**
     * 2个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     */
    public interface Consumer2<T1, T2> extends Serializable {
        void accept(T1 arg1, T2 arg2);
    }

    /**
     * 2个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     */
    public interface Consumer2Throws<T1, T2> extends Serializable {
        void accept(T1 arg1, T2 arg2) throws Throwable;
    }

    /**
     * 3个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     */
    public interface Consumer3<T1, T2, T3> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3);
    }

    /**
     * 3个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     */
    public interface Consumer3Throws<T1, T2, T3> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3) throws Throwable;
    }

    /**
     * 4个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     */
    public interface Consumer4<T1, T2, T3, T4> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }

    /**
     * 4个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     */
    public interface Consumer4Throws<T1, T2, T3, T4> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4) throws Throwable;
    }

    /**
     * 5个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     */
    public interface Consumer5<T1, T2, T3, T4, T5> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
    }

    /**
     * 5个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     */
    public interface Consumer5Throws<T1, T2, T3, T4, T5> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) throws Throwable;
    }

    /**
     * 6个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     */
    public interface Consumer6<T1, T2, T3, T4, T5, T6> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6);
    }

    /**
     * 6个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     */
    public interface Consumer6Throws<T1, T2, T3, T4, T5, T6> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6) throws Throwable;
    }

    /**
     * 7个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     */
    public interface Consumer7<T1, T2, T3, T4, T5, T6, T7> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7);
    }

    /**
     * 7个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     */
    public interface Consumer7Throws<T1, T2, T3, T4, T5, T6, T7> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7) throws Throwable;
    }

    /**
     * 8个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     */
    public interface Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8);
    }

    /**
     * 8个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <T7> 参数7类型
     * @param <T8> 参数8类型
     */
    public interface Consumer8Throws<T1, T2, T3, T4, T5, T6, T7, T8> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8) throws Throwable;
    }

    /**
     * 9个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9);
    }

    /**
     * 9个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface Consumer9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9) throws Throwable;
    }

    /**
     * 10个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface ConsumerA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA);
    }

    /**
     * 10个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface ConsumerAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA) throws Throwable;
    }

    /**
     * 11个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface ConsumerB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB);
    }

    /**
     * 11个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface ConsumerBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB) throws Throwable;
    }

    /**
     * 12个参数的Consumer形式
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface ConsumerC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB, TC argc);
    }

    /**
     * 12个参数的Consumer形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
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
    public interface ConsumerCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC> extends Serializable {
        void accept(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB, TC argc) throws Throwable;
    }
}
