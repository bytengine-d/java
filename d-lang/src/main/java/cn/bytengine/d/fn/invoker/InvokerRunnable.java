package cn.bytengine.d.fn.invoker;

/**
 * Invoker的Runnable
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class InvokerRunnable implements Runnable {
    private final Invoker invoker;
    private final Object[] args;

    /**
     * 指定Invoker和参数创建RunnableInvoker
     *
     * @param invoker Invoker对象
     * @param args    参数列表
     */
    public InvokerRunnable(Invoker invoker, Object[] args) {
        this.invoker = invoker;
        this.args = args;
    }

    @Override
    public void run() {
        try {
            invoker.invoke(this.args);
        } catch (Throwable e) {
            throw new InvocationException(e);
        }
    }
}
