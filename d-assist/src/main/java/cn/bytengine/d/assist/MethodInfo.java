package cn.bytengine.d.assist;

import cn.bytengine.d.fn.invoker.MethodHandles;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 方法信息，包括方法签名和类型信息，涵盖MethodHandle
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class MethodInfo {
    private Class<?> clazz;
    private String name;
    private String sign;
    private boolean hasReturn;
    private Class<?> returnType;
    private int parameterLength = 0;
    private Class<?>[] parameters = new Class[0];
    private MethodHandle handle;

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public boolean isHasReturn() {
        return hasReturn;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<?>[] getParameters() {
        return parameters;
    }

    public MethodHandle getHandle() {
        return handle;
    }

    // region instance methods
    public Object invoke(Object me, Object... args) {
//        ClassAssists.checkInstance(getClazz(), me);
        try {
            return handle.invokeWithArguments(ArrayUtil.insert(args, 0, me));
        } catch (Throwable e) {
            return new RuntimeException(e);
        }
    }
    // endregion

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MethodInfo that = (MethodInfo) o;
        return Objects.equals(sign, that.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sign);
    }

    public static MethodInfo of(Class<?> clazz, Method method) {
        MethodInfo info = new MethodInfo();
        info.clazz = clazz;
        info.name = method.getName();
        info.returnType = method.getReturnType();
        info.hasReturn = info.returnType != null;
        info.parameters = method.getParameterTypes();
        info.parameterLength = info.parameters.length;
        info.handle = MethodHandles.getMethodHandler(clazz, info.name, info.parameters);
        info.sign = CharSequenceUtil.format("{} {}.{}({})", info.hasReturn ? info.returnType.getCanonicalName() : "void", clazz.getCanonicalName(), info.name, info.parameterLength == 0 ? CharSequenceUtil.EMPTY : CollUtil.join(ArrayUtil.map(info.parameters, Class::getCanonicalName), ","));
        return info;
    }
}
