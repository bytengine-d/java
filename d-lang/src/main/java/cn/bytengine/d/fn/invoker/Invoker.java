package cn.bytengine.d.fn.invoker;

/**
 * 调用器，封装可以被调用的逻辑
 * <p>
 * 使用场景中，封装某方法调用
 * </p>
 */
public interface Invoker {
    /**
     * 调用封装逻辑
     *
     * @param args 调用参数列表
     * @throws Throwable 调用产生的异常
     */
    void invoke(Object[] args) throws Throwable;
}
