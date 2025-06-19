package cn.bytengine.d.fn;

import java.io.Serializable;

/**
 * 枚举Function函数形式，和辅助类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Suppliers {
    private Suppliers() {
    }

    /**
     * 无参数的Supplier形式
     *
     * @param <R> 返回参数类型
     * @author Ban Tenio
     * @version 1.0
     */
    public interface Supplier0<R> extends Serializable {
        /**
         * 获得结果。
         *
         * @return 结果
         */
        R apply();
    }

    /**
     * 无参数的Supplier形式, 有异常声明
     *
     * @param <R> 返回参数类型
     * @author Ban Tenio
     * @version 1.0
     */
    public interface Supplier0WithThrows<R> extends Serializable {
        /**
         * 获得结果。
         *
         * @return 结果
         */
        R apply() throws Throwable;

        /**
         * 获得结果。并将处理异常转换为RuntimeException
         *
         * @return 结果
         */
        default R applyWithRuntimeException() {
            try {
                return apply();
            } catch (Throwable e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        }
    }
}
