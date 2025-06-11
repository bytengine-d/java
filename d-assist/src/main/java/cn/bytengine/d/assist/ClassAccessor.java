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

    public ClassAccessor(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

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

    public MethodAccessor getMethodAccessor(String methodName) {
        return methodAccessorMap.get(methodName);
    }

    public boolean containMethod(String methodName) {
        return methodAccessorMap.containsKey(methodName);
    }

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

    public PropertyAccessor getPropertyAccessor(String propertyName) {
        return propertyAccessorMap.get(propertyName);
    }

    public boolean containProperty(String propertyName) {
        return propertyAccessorMap.containsKey(propertyName);
    }

    // region instance methods
    public <T> T get(Object me, String propertyName) {
        PropertyAccessor accessor = getPropertyAccessor(propertyName);
        if (accessor == null) {
            throw new ReflectionOperationException("The property is not element in this class");
        }
        return (T) accessor.get(me);
    }

    public void set(Object me, String propertyName, Object value) {
        PropertyAccessor accessor = getPropertyAccessor(propertyName);
        if (accessor == null) {
            throw new ReflectionOperationException("The property is not element in this class");
        }
        accessor.set(me, value);
    }

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
