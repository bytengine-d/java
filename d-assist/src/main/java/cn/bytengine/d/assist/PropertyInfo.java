package cn.bytengine.d.assist;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 类的属性信息，包括属性类型，涵盖
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class PropertyInfo {
    private Class<?> clazz;
    private String name;
    private Class<?> type;
    private boolean canRead;
    private MethodInfo getter;
    private boolean canWrite;
    private MethodInfo setter;

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public MethodInfo getGetter() {
        return getter;
    }

    public MethodInfo getSetter() {
        return setter;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    // region instance method
    public <T> T getPropertyValue(Object instance) {
        if (!isCanRead()) {
            throw new UnsupportedOperationException("the property can not read");
        }
        return (T) getGetter().invoke(instance);
    }

    public void setPropertyValue(Object instance, Object value) {
        if (!isCanWrite()) {
            throw new UnsupportedOperationException("the property can not write");
        }
        getSetter().invoke(instance, value);
    }
    // endregion

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PropertyInfo that = (PropertyInfo) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public static PropertyInfo of(Class<?> clazz, PropertyDescriptor propertyDescriptor) {
        return of(clazz, propertyDescriptor.getName(), propertyDescriptor.getPropertyType(), propertyDescriptor.getReadMethod(), propertyDescriptor.getWriteMethod());
    }

    public static PropertyInfo of(Class<?> clazz, String name, Class<?> type, Method getter, Method setter) {
        PropertyInfo info = new PropertyInfo();
        info.name = name;
        info.clazz = clazz;
        info.type = type;
        if (getter != null) {
            info.canRead = true;
            info.getter = MethodInfo.of(clazz, getter);
        }
        if (setter != null) {
            info.canWrite = true;
            info.setter = MethodInfo.of(clazz, setter);
        }
        return info;
    }
}
