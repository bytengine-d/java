package cn.bytengine.d.fn.invoker;

import cn.bytengine.d.lang.reflect.MethodInfo;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class MetaInfoInvoker implements Invoker {
    private final Invoker delegate;
    private final MethodInfo methodInfo;

    public MetaInfoInvoker(Invoker delegate, MethodInfo methodInfo) {
        this.delegate = delegate;
        this.methodInfo = methodInfo;
    }

    public Invoker getDelegate() {
        return delegate;
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return delegate.invoke(args);
    }
}
