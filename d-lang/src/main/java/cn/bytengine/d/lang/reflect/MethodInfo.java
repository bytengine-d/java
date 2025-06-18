package cn.bytengine.d.lang.reflect;

import cn.bytengine.d.lang.CharSequenceTools;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 方法信息，包括方法签名和类型信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class MethodInfo {
    private Method method;
    private Class<?> ownClass;
    private String methodSign;
    private String methodTypeSign;
    private boolean hasReturn;
    private Class<?> returnType;
    private int parameterLength = 0;
    private Class<?>[] parameters = new Class[0];

    /**
     * 获取方法实例
     *
     * @return 方法实例
     */
    public Method getMethod() {
        return method;
    }

    /**
     * 获取方法信息所属类
     *
     * @return 方法所属类
     */
    public Class<?> getOwnClass() {
        return ownClass;
    }

    /**
     * 获取方法名
     *
     * @return 方法名称
     */
    public String getMethodName() {
        return this.method.getName();
    }

    public String getDeclaringClassName() {
        return this.method.getDeclaringClass().getName();
    }

    public String getReturnTypeName() {
        return this.method.getReturnType().getName();
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.method.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.method.getModifiers());
    }

    public boolean isOverridable() {
        return !isStatic() && !isFinal() && !isPrivate();
    }

    private boolean isPrivate() {
        return Modifier.isPrivate(this.method.getModifiers());
    }

    public String getMethodSign() {
        return methodSign;
    }

    public String getMethodTypeSign() {
        return methodTypeSign;
    }

    public boolean isHasReturn() {
        return hasReturn;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public int getParameterLength() {
        return parameterLength;
    }

    public Class<?>[] getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MethodInfo that = (MethodInfo) o;
        return Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(methodSign);
    }

    public static MethodInfo of(Class<?> ownClass, Method method) {
        MethodInfo info = new MethodInfo();
        info.method = method;
        info.ownClass = ownClass;
        info.returnType = method.getReturnType();
        info.hasReturn = info.returnType != null;
        info.parameters = method.getParameterTypes();
        info.parameterLength = info.parameters.length;
        info.methodTypeSign = methodSignatureString(method);
        info.methodSign = CharSequenceTools.format("{}.{}{}}", ownClass.getCanonicalName(), info.getMethodName(), info.methodTypeSign);
        return info;
    }

    public static String methodSignatureString(Method methodInstance) {
        Type[] parameterTypes = methodInstance.getGenericParameterTypes();
        StringBuilder buf = new StringBuilder("(");
        Type paramItemClass;
        for (int idx = 0; idx < parameterTypes.length; idx++) {
            paramItemClass = parameterTypes[idx];
            if (paramItemClass instanceof Class<?>) {
                buf.append(((Class<?>) paramItemClass).descriptorString());
            }
        }
        buf.append(')');
        buf.append(methodInstance.getReturnType().descriptorString());
        return buf.toString();
    }
}
