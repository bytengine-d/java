package cn.bytengine.d.fn.invoker;

/**
 * 调用异常
 */
public class InvocationException extends RuntimeException {
    /**
     * 调用异常，无异常消息、无原因
     */
    public InvocationException() {
    }

    /**
     * 调用异常，指定异常消息、无原因
     *
     * @param message 异常信息
     */
    public InvocationException(String message) {
        super(message);
    }

    /**
     * 调用异常，指定异常消息、指定原因
     *
     * @param message 异常信息
     * @param cause   指定原因
     */
    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * 调用异常，无异常消息、指定原因
     *
     * @param cause 指定原因
     */
    public InvocationException(Throwable cause) {
        super(cause);
    }

    /**
     * 调用异常，无异常消息、指定原因
     *
     * @param message 异常信息
     * @param cause 指定原因
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace 是否写入调用堆栈信息
     */
    public InvocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
