package cn.bytengine.d.lang.reflect;

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
        MethodInfo reader = null;
        if (getter != null) {
            reader = MethodInfo.of(clazz, getter);
        }
        MethodInfo writer = null;
        if (setter != null) {
            writer = MethodInfo.of(clazz, setter);
        }
        return of(clazz, name, type, reader, writer);
    }

    public static PropertyInfo of(Class<?> clazz, String name, Class<?> type, MethodInfo getter, MethodInfo setter) {
        PropertyInfo info = new PropertyInfo();
        info.name = name;
        info.clazz = clazz;
        info.type = type;
        if (getter != null) {
            info.canRead = true;
            info.getter = getter;
        }
        if (setter != null) {
            info.canWrite = true;
            info.setter = setter;
        }
        return info;
    }
}
