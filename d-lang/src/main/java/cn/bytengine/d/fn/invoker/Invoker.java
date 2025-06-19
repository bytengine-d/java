package cn.bytengine.d.fn.invoker;

/**
 * 调用器，封装可以被调用的逻辑
 * <p>
 * 使用场景中，封装某方法调用
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface Invoker {
    /**
     * 调用封装逻辑
     *
     * @param args 调用参数列表
     * @return 返回结果
     * @throws Throwable 调用产生的异常
     */
    Object invoke(Object[] args) throws Throwable;
}
