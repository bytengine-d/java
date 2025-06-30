package cn.bytengine.d.assist;

import cn.bytengine.d.lang.reflect.PropertyInfo;

/**
 * 属性访问器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class PropertyAccessor {
    private final PropertyInfo propertyInfo;
    private final MethodAccessor getter;
    private final MethodAccessor setter;

    /**
     * 根据指定属性信息和属性访问器创建属性访问器
     *
     * @param propertyInfo 属性信息
     * @param getter       属性读取器
     * @param setter       属性设置器
     */
    public PropertyAccessor(PropertyInfo propertyInfo, MethodAccessor getter, MethodAccessor setter) {
        this.propertyInfo = propertyInfo;
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * 获取属性信息
     *
     * @return 属性信息
     */
    public PropertyInfo getPropertyInfo() {
        return propertyInfo;
    }

    /**
     * 获取属性读取器
     *
     * @return 属性读取器
     */
    public MethodAccessor getGetter() {
        return getter;
    }

    /**
     * 获取属性设置器
     *
     * @return 属性设置器
     */
    public MethodAccessor getSetter() {
        return setter;
    }

    /**
     * 设置指定实例属性值
     *
     * @param me    实例对象
     * @param value 设置值
     */
    public void set(Object me, Object value) {
        setter.invoke(me, value);
    }

    /**
     * 读取指定实例属性值
     *
     * @param me 实例对象
     * @return 属性值
     */
    public Object get(Object me) {
        return getter.invoke(me);
    }
}
