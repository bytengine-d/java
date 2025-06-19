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

    /**
     * 获取方法声明所属类名称
     *
     * @return 方法声明所属类名称
     */
    public String getDeclaringClassName() {
        return this.method.getDeclaringClass().getName();
    }

    /**
     * 获取方法返回类型名称
     *
     * @return 返回类型名称
     */
    public String getReturnTypeName() {
        return this.method.getReturnType().getName();
    }

    /**
     * 获取方法是否虚方法
     *
     * @return 方法是否虚方法
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(this.method.getModifiers());
    }

    /**
     * 获取方法是否静态方法
     *
     * @return 方法是否静态方法
     */
    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    /**
     * 获取方法是否不可重写
     *
     * @return 方法是否不可重写
     */
    public boolean isFinal() {
        return Modifier.isFinal(this.method.getModifiers());
    }

    /**
     * 获取方法是否可以重写
     *
     * @return 方法是否可以重写
     */
    public boolean isOverridable() {
        return !isStatic() && !isFinal() && !isPrivate();
    }

    /**
     * 获取方法是否私有
     *
     * @return 方法是否私有
     */
    private boolean isPrivate() {
        return Modifier.isPrivate(this.method.getModifiers());
    }

    /**
     * 获取方法签名字符串
     *
     * @return 方法签名字符串
     */
    public String getMethodSign() {
        return methodSign;
    }

    /**
     * 获取方法类型签名
     *
     * @return 方法类型签名
     */
    public String getMethodTypeSign() {
        return methodTypeSign;
    }

    /**
     * 方法是否有返回值
     *
     * @return 是否有返回值
     */
    public boolean isHasReturn() {
        return hasReturn;
    }

    /**
     * 获取方法返回类型
     *
     * @return 方法返回类型
     */
    public Class<?> getReturnType() {
        return returnType;
    }

    /**
     * 获取方法参数数量
     *
     * @return 方法参数数量
     */
    public int getParameterLength() {
        return parameterLength;
    }

    /**
     * 获取方法参数类型集合
     *
     * @return 方法参数类型集合
     */
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

    /**
     * 给定方法所属类型和方法实例，创建方法信息
     *
     * @param ownClass 方法所属类
     * @param method   方法实例
     * @return 方法信息
     */
    public static MethodInfo of(Class<?> ownClass, Method method) {
        MethodInfo info = new MethodInfo();
        info.method = method;
        info.ownClass = ownClass;
        info.returnType = method.getReturnType();
        info.hasReturn = info.returnType != null;
        info.parameters = method.getParameterTypes();
        info.parameterLength = info.parameters.length;
        info.methodTypeSign = methodSignatureString(method);
        info.methodSign = CharSequenceTools.format("{}.{}{}", ownClass.getCanonicalName(), info.getMethodName(), info.methodTypeSign);
        return info;
    }

    /**
     * 获取方法类型签名
     *
     * @param methodInstance 方法实例
     * @return 方法类型签名
     */
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
