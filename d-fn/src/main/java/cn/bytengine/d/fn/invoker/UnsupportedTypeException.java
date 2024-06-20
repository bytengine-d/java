package cn.bytengine.d.fn.invoker;

/**
 * 不支持类型异常
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class UnsupportedTypeException extends RuntimeException {
    /**
     * 不支持类型异常，无异常信息，无原因
     */
    public UnsupportedTypeException() {
    }
    /**
     * 不支持类型异常，指定异常消息、无原因
     *
     * @param message 异常信息
     */
    public UnsupportedTypeException(String message) {
        super(message);
    }
    /**
     * 不支持类型异常，指定异常消息、指定原因
     *
     * @param message 异常信息
     * @param cause   指定原因
     */
    public UnsupportedTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * 不支持类型异常，无异常消息、指定原因
     *
     * @param cause 指定原因
     */
    public UnsupportedTypeException(Throwable cause) {
        super(cause);
    }
    /**
     * 不支持类型异常，无异常消息、指定原因
     *
     * @param message 异常信息
     * @param cause 指定原因
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace 是否写入调用堆栈信息
     */
    public UnsupportedTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
