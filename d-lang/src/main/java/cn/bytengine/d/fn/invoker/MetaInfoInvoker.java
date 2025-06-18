package cn.bytengine.d.fn.invoker;

import cn.bytengine.d.lang.reflect.MethodInfo;

/**
 * 包含方法反射信息和委托调用器的调用器
 *
 * @see MethodInfo
 * @author Ban Tenio
 * @version 1.0
 */
public class MetaInfoInvoker implements Invoker {
    private final Invoker delegate;
    private final MethodInfo methodInfo;

    /**
     * 根据方法调用器和方法反射信息包装Invoker，可在调用时提供方法信息
     *
     * @param delegate   调用代理
     * @param methodInfo 方法反射信息
     */
    public MetaInfoInvoker(Invoker delegate, MethodInfo methodInfo) {
        this.delegate = delegate;
        this.methodInfo = methodInfo;
    }

    /**
     * 获取调用器代理
     *
     * @return 调用器
     */
    public Invoker getDelegate() {
        return delegate;
    }

    /**
     * 获取方法信息
     *
     * @return 方法信息
     */
    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return delegate.invoke(args);
    }
}
