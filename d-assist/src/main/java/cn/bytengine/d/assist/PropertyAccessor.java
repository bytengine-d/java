package cn.bytengine.d.assist;

import cn.bytengine.d.lang.reflect.PropertyInfo;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class PropertyAccessor {
    private final PropertyInfo propertyInfo;
    private final MethodAccessor getter;
    private final MethodAccessor setter;

    public PropertyAccessor(PropertyInfo propertyInfo, MethodAccessor getter, MethodAccessor setter) {
        this.propertyInfo = propertyInfo;
        this.getter = getter;
        this.setter = setter;
    }

    public PropertyInfo getPropertyInfo() {
        return propertyInfo;
    }

    public MethodAccessor getGetter() {
        return getter;
    }

    public MethodAccessor getSetter() {
        return setter;
    }

    public void set(Object me, Object value) {
        setter.invoke(me, value);
    }

    public Object get(Object me) {
        return getter.invoke(me);
    }
}
