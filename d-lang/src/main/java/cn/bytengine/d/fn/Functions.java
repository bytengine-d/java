package cn.bytengine.d.fn;

import java.io.Serializable;

/**
 * 枚举Function函数形式，和辅助类
 * <p>
 * Function1, Function2..., FunctionC(Function12)
 * </p>
 * <p>
 * Function1-C都是Serializable，支持SerializedLambda
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Functions {
    private Functions() {
    }

    /**
     * 1个参数的Function形式
     *
     * @param <T1> 参数1类型
     * @param <R>  返回参数类型
     * @author Ban Tenio
     * @version 1.0
     */
    public interface Function1<T1, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @return 结果
         */
        R apply(T1 arg1);
    }

    /**
     * 1个参数的Function形式, 有异常声明
     *
     * @param <T1> 参数1类型
     * @param <R>  返回参数类型
     * @author Ban Tenio
     * @version 1.0
     */
    public interface Function1Throws<T1, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1) throws Throwable;
    }

    /**
     * 2个参数的Function形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <R> 返回参数类型
     */
    public interface Function2<T1, T2, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2);
    }

    /**
     * 2个参数的Function形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <R> 返回参数类型
     */
    public interface Function2Throws<T1, T2, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2) throws Throwable;
    }

    /**
     * 3个参数的Function形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <R> 返回参数类型
     */
    public interface Function3<T1, T2, T3, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3);
    }

    /**
     * 3个参数的Function形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <R> 返回参数类型
     */
    public interface Function3Throws<T1, T2, T3, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3) throws Throwable;
    }

    /**
     * 4个参数的Function形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <R> 返回参数类型
     */
    public interface Function4<T1, T2, T3, T4, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }

    /**
     * 4个参数的Function形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <R> 返回参数类型
     */
    public interface Function4Throws<T1, T2, T3, T4, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4) throws Throwable;
    }

    /**
     * 5个参数的Function形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <R> 返回参数类型
     */
    public interface Function5<T1, T2, T3, T4, T5, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
    }

    /**
     * 5个参数的Function形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <R> 返回参数类型
     */
    public interface Function5Throws<T1, T2, T3, T4, T5, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) throws Throwable;
    }

    /**
     * 6个参数的Function形式
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <R> 返回参数类型
     */
    public interface Function6<T1, T2, T3, T4, T5, T6, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6);
    }

    /**
     * 6个参数的Function形式, 有异常声明
     *
     * @author Ban Tenio
     * @version 1.0
     * @param <T1> 参数1类型
     * @param <T2> 参数2类型
     * @param <T3> 参数3类型
     * @param <T4> 参数4类型
     * @param <T5> 参数5类型
     * @param <T6> 参数6类型
     * @param <R> 返回参数类型
     */

    public interface Function6Throws<T1, T2, T3, T4, T5, T6, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6) throws Throwable;
    }

    /**
     * 7个参数的Function形式
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
     * @param <R> 返回参数类型
     */
    public interface Function7<T1, T2, T3, T4, T5, T6, T7, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7);
    }

    /**
     * 7个参数的Function形式, 有异常声明
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
     * @param <R> 返回参数类型
     */
    public interface Function7Throws<T1, T2, T3, T4, T5, T6, T7, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7) throws Throwable;
    }

    /**
     * 8个参数的Function形式
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
     * @param <R> 返回参数类型
     */
    public interface Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8);
    }

    /**
     * 8个参数的Function形式, 有异常声明
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
     * @param <R> 返回参数类型
     */
    public interface Function8Throws<T1, T2, T3, T4, T5, T6, T7, T8, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8) throws Throwable;
    }

    /**
     * 9个参数的Function形式
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
     * @param <R> 返回参数类型
     */
    public interface Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9);
    }

    /**
     * 9个参数的Function形式, 有异常声明
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
     * @param <R> 返回参数类型
     */
    public interface Function9Throws<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9) throws Throwable;
    }

    /**
     * 10个参数的Function形式
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
     * @param <R> 返回参数类型
     */
    public interface FunctionA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @param argA 参数10
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA);
    }

    /**
     * 10个参数的Function形式, 有异常声明
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
     * @param <R> 返回参数类型
     */
    public interface FunctionAThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @param argA 参数10
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA) throws Throwable;
    }

    /**
     * 11个参数的Function形式
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
     * @param <R> 返回参数类型
     */
    public interface FunctionB<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @param argA 参数10
         * @param argB 参数11
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB);
    }

    /**
     * 11个参数的Function形式, 有异常声明
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
     * @param <R> 返回参数类型
     */
    public interface FunctionBThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @param argA 参数10
         * @param argB 参数11
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB) throws Throwable;
    }

    /**
     * 12个参数的Function形式
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
     * @param <R> 返回参数类型
     */
    public interface FunctionC<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @param argA 参数10
         * @param argB 参数11
         * @param argC 参数12
         * @return 结果
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB, TC argC);
    }

    /**
     * 12个参数的Function形式, 有异常声明
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
     * @param <R> 返回参数类型
     */
    public interface FunctionCThrows<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, R> extends Serializable {
        /**
         * 对给定的参数应用此函数
         *
         * @param arg1 参数1
         * @param arg2 参数2
         * @param arg3 参数3
         * @param arg4 参数4
         * @param arg5 参数5
         * @param arg6 参数6
         * @param arg7 参数7
         * @param arg8 参数8
         * @param arg9 参数9
         * @param argA 参数10
         * @param argB 参数11
         * @param argC 参数12
         * @return 结果
         * @throws Throwable 执行出现异常
         */
        R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9, TA argA, TB argB, TC argC) throws Throwable;
    }
}
