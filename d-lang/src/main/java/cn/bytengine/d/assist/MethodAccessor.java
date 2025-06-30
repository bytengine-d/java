package cn.bytengine.d.assist;

import cn.bytengine.d.fn.invoker.InvocationException;
import cn.bytengine.d.fn.invoker.MetaInfoInvoker;
import cn.bytengine.d.lang.ArrayTools;
import cn.bytengine.d.lang.reflect.MethodInfo;

/**
 * 方法访问器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class MethodAccessor {
    private final MethodInfo methodInfo;
    private final MetaInfoInvoker metaInfoInvoker;

    /**
     * 构造器
     *
     * @param methodInfo      方法信息
     * @param metaInfoInvoker 方法信息调用器
     */
    public MethodAccessor(MethodInfo methodInfo, MetaInfoInvoker metaInfoInvoker) {
        this.methodInfo = methodInfo;
        this.metaInfoInvoker = metaInfoInvoker;
    }

    /**
     * 获取方法信息
     *
     * @return 方法信息
     */
    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    /**
     * 获取方法信息调用器
     *
     * @return 方法调用器
     */
    public MetaInfoInvoker getMetaInfoInvoker() {
        return metaInfoInvoker;
    }

    /**
     * 调用指定对象实例方法
     *
     * @param me   对象实例
     * @param args 调用方法参数列表
     * @return 方法返回值
     */
    public Object invoke(Object me, Object... args) {
        try {
            return metaInfoInvoker.invoke(ArrayTools.insert(args, 0, me));
        } catch (Throwable e) {
            throw new InvocationException(e);
        }
    }
}
