package cn.bytengine.d.fn.invoker;

import java.lang.invoke.MethodHandle;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class MethodHandleInvoker implements Invoker {
    private final MethodHandle methodHandle;

    public MethodHandleInvoker(MethodHandle methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public void invoke(Object[] args) throws Throwable {
//        return methodHandle.invokeWithArguments(args);
    }
}
