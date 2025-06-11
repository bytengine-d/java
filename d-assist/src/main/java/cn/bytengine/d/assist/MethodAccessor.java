package cn.bytengine.d.assist;

import cn.bytengine.d.fn.invoker.InvocationException;
import cn.bytengine.d.fn.invoker.MetaInfoInvoker;
import cn.bytengine.d.lang.reflect.MethodInfo;
import cn.hutool.core.util.ArrayUtil;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class MethodAccessor {
    private final MethodInfo methodInfo;
    private final MetaInfoInvoker metaInfoInvoker;

    public MethodAccessor(MethodInfo methodInfo, MetaInfoInvoker metaInfoInvoker) {
        this.methodInfo = methodInfo;
        this.metaInfoInvoker = metaInfoInvoker;
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public MetaInfoInvoker getMetaInfoInvoker() {
        return metaInfoInvoker;
    }

    public Object invoke(Object me, Object... args) {
        try {
            return metaInfoInvoker.invoke(ArrayUtil.insert(args, 0, me));
        } catch (Throwable e) {
            throw new InvocationException(e);
        }
    }
}
