package cn.bytengine.d.assist;

import cn.bytengine.d.fn.invoker.MetaInfoInvoker;
import cn.bytengine.d.fn.invoker.ReflectionOperationException;
import cn.bytengine.d.lang.reflect.ClassInfo;
import cn.bytengine.d.lang.reflect.MethodInfo;
import cn.bytengine.d.lang.reflect.PropertyInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class访问器，并提供实例访问方式
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassAccessor {
    private final ClassInfo classInfo;

    private final Map<String, PropertyAccessor> propertyAccessorMap = new HashMap<>(16);
    private final Map<String, MethodAccessor> methodAccessorMap = new HashMap<>(16);

    /**
     * 构造器
     *
     * @param classInfo 类信息
     */
    public ClassAccessor(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    /**
     * 获取类信息
     *
     * @return 类信息
     */
    public ClassInfo getClassInfo() {
        return classInfo;
    }

    /**
     * 添加指定方法信息调用器
     *
     * @param invoker 方法信息调用器
     */
    public void addMethodAccessor(MetaInfoInvoker invoker) {
        MethodInfo methodInfo = invoker.getMethodInfo();
        if (methodAccessorMap.containsKey(methodInfo.getMethodName())) {
            return;
        }
        if (!classInfo.getType().equals(methodInfo.getOwnClass())) {
            throw new ReflectionOperationException("The invoker is not element in this class");
        }
        methodAccessorMap.put(methodInfo.getMethodName(), new MethodAccessor(methodInfo, invoker));
    }

    /**
     * 获取方法访问器
     *
     * @param methodName 方法名称
     * @return 方法访问器
     */
    public MethodAccessor getMethodAccessor(String methodName) {
        return methodAccessorMap.get(methodName);
    }

    /**
     * 是否包含指定方法名访问器
     *
     * @param methodName 方法名称
     * @return 是否包含
     */
    public boolean containMethod(String methodName) {
        return methodAccessorMap.containsKey(methodName);
    }

    /**
     * 添加指定属性名称访问器
     *
     * @param propertyName 属性名称
     */
    public void addPropertyAccessor(String propertyName) {
        if (propertyAccessorMap.containsKey(propertyName)) {
            return;
        }
        if (!classInfo.containsProperty(propertyName)) {
            throw new ReflectionOperationException("The property is not element in this class");
        }
        PropertyInfo propertyInfo = classInfo.getProperty(propertyName);
        MethodAccessor getter = propertyInfo.isCanRead() ? methodAccessorMap.get(propertyInfo.getGetter().getMethodName()) : null;
        MethodAccessor setter = propertyInfo.isCanWrite() ? methodAccessorMap.get(propertyInfo.getSetter().getMethodName()) : null;
        propertyAccessorMap.put(propertyName, new PropertyAccessor(propertyInfo, getter, setter));
    }

    /**
     * 获取指定属性名访问器
     *
     * @param propertyName 属性名称
     * @return 属性访问器
     */
    public PropertyAccessor getPropertyAccessor(String propertyName) {
        return propertyAccessorMap.get(propertyName);
    }

    /**
     * 是否包含指定属性名访问器
     *
     * @param propertyName 属性名
     * @return 是否包含
     */
    public boolean containProperty(String propertyName) {
        return propertyAccessorMap.containsKey(propertyName);
    }

    // region instance methods

    /**
     * 获取指定实例属性值
     *
     * @param me           对象实例
     * @param propertyName 属性名
     * @param <T>          属性类型
     * @return 属性值
     */
    public <T> T get(Object me, String propertyName) {
        PropertyAccessor accessor = getPropertyAccessor(propertyName);
        if (accessor == null) {
            throw new ReflectionOperationException("The property is not element in this class");
        }
        return (T) accessor.get(me);
    }

    /**
     * 设置指定实例属性值
     *
     * @param me 对象实例
     * @param propertyName 属性名
     * @param value 设置值
     */
    public void set(Object me, String propertyName, Object value) {
        PropertyAccessor accessor = getPropertyAccessor(propertyName);
        if (accessor == null) {
            throw new ReflectionOperationException("The property is not element in this class");
        }
        accessor.set(me, value);
    }

    /**
     * 调用指定实例方法
     *
     * @param me 对象实例
     * @param methodName 方法名称
     * @param args 调用方法参数列表
     * @return 方法返回值
     * @param <T> 方法返回类型
     */
    public <T> T invoke(Object me, String methodName, Object... args) {
        MethodAccessor accessor = getMethodAccessor(methodName);
        if (accessor == null) {
            throw new ReflectionOperationException("The method is not element in this class");
        }
        return (T) accessor.invoke(me, args);
    }
    // endregion

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassAccessor that = (ClassAccessor) o;
        return Objects.equals(classInfo, that.classInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classInfo);
    }
}
