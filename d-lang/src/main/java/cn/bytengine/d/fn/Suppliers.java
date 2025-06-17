package cn.bytengine.d.fn;

import java.io.Serializable;

/**
 * TODO
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
        R apply() throws Throwable;

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
