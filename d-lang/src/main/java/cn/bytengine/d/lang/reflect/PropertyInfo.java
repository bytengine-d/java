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
    private Class<?> ownClass;
    private String name;
    private Class<?> type;
    private boolean canRead;
    private MethodInfo getter;
    private boolean canWrite;
    private MethodInfo setter;

    /**
     * 获取属性所属类
     *
     * @return 属性所属类
     */
    public Class<?> getOwnClass() {
        return ownClass;
    }

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取属性类型
     *
     * @return 属性类型
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * 获取属性读取方法信息
     *
     * @return 属性读取方法信息
     */
    public MethodInfo getGetter() {
        return getter;
    }

    /**
     * 获取属性设置方法信息
     *
     * @return 属性设置方法信息
     */
    public MethodInfo getSetter() {
        return setter;
    }

    /**
     * 获取属性是否可读
     *
     * @return 属性是否可读
     */
    public boolean isCanRead() {
        return canRead;
    }

    /**
     * 获取属性是否可写
     *
     * @return 属性是否可写
     */
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

    /**
     * 根据属性描述信息，创建属性信息
     *
     * @param ownClass           属性所属类
     * @param propertyDescriptor 属性描述信息
     * @return 属性信息
     */
    public static PropertyInfo of(Class<?> ownClass, PropertyDescriptor propertyDescriptor) {
        return of(ownClass, propertyDescriptor.getName(), propertyDescriptor.getPropertyType(), propertyDescriptor.getReadMethod(), propertyDescriptor.getWriteMethod());
    }

    /**
     * 根据属性反射信息，创建属性信息
     *
     * @param ownClass 属性所属类
     * @param name     属性名
     * @param type     属性类型
     * @param getter   属性读取方法
     * @param setter   属性设置方法
     * @return 属性信息
     */
    public static PropertyInfo of(Class<?> ownClass, String name, Class<?> type, Method getter, Method setter) {
        MethodInfo reader = null;
        if (getter != null) {
            reader = MethodInfo.of(ownClass, getter);
        }
        MethodInfo writer = null;
        if (setter != null) {
            writer = MethodInfo.of(ownClass, setter);
        }
        return of(ownClass, name, type, reader, writer);
    }

    /**
     * 根据属性反射信息，创建属性信息
     *
     * @param ownClass 属性所属类
     * @param name     属性名
     * @param type     属性类型
     * @param getter   属性读取方法信息
     * @param setter   属性设置方法信息
     * @return 属性信息
     */
    public static PropertyInfo of(Class<?> ownClass, String name, Class<?> type, MethodInfo getter, MethodInfo setter) {
        PropertyInfo info = new PropertyInfo();
        info.name = name;
        info.ownClass = ownClass;
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
