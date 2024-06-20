package cn.bytengine.d.fn.invoker;

/**
 * 反射操作异常
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ReflectionOperationException extends RuntimeException {
    /**
     * 反射操作异常，无异常信息，无原因
     */
    public ReflectionOperationException() {
    }
    /**
     * 反射操作异常，指定异常消息、无原因
     *
     * @param message 异常信息
     */
    public ReflectionOperationException(String message) {
        super(message);
    }
    /**
     * 反射操作异常，指定异常消息、指定原因
     *
     * @param message 异常信息
     * @param cause   指定原因
     */
    public ReflectionOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * 反射操作异常，无异常消息、指定原因
     *
     * @param cause 指定原因
     */
    public ReflectionOperationException(Throwable cause) {
        super(cause);
    }
    /**
     * 反射操作异常，无异常消息、指定原因
     *
     * @param message 异常信息
     * @param cause 指定原因
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace 是否写入调用堆栈信息
     */
    public ReflectionOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
