package cn.bytengine.d.lang;


import cn.bytengine.d.collection.WeakConcurrentMap;

import java.lang.ref.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ReferenceTools {
    private ReferenceTools() {
    }

    private static final WeakConcurrentMap<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new WeakConcurrentMap<>();

    public static <T> Reference<T> create(ReferenceType type, T referent) {
        return create(type, referent, null);
    }

    public static <T> Reference<T> create(ReferenceType type, T referent, ReferenceQueue<T> queue) {
        switch (type) {
            case SOFT:
                return new SoftReference<>(referent, queue);
            case WEAK:
                return new WeakReference<>(referent, queue);
            case PHANTOM:
                return new PhantomReference<>(referent, queue);
            default:
                return null;
        }
    }

    public static Constructor<?>[] getConstructorsDirectly(Class<?> beanClass) throws SecurityException {
        return beanClass.getDeclaredConstructors();
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> beanClass) throws SecurityException {
        AssertTools.notNull(beanClass);
        return (Constructor<T>[]) CONSTRUCTORS_CACHE.computeIfAbsent(beanClass, () -> getConstructorsDirectly(beanClass));
    }

    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (null != accessibleObject && false == accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        if (null == clazz) {
            return null;
        }

        final Constructor<?>[] constructors = getConstructors(clazz);
        Class<?>[] pts;
        for (Constructor<?> constructor : constructors) {
            pts = constructor.getParameterTypes();
            if (TypeTools.isAllAssignableFrom(pts, parameterTypes)) {
                // 构造可访问
                setAccessible(constructor);
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz, Object... params) {
        if (ArrayTools.isEmpty(params)) {
            final Constructor<T> constructor = getConstructor(clazz);
            if (null == constructor) {
                throw new RuntimeException(CharSequenceTools.format("No constructor for [{}]", clazz));
            }
            try {
                return constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(CharSequenceTools.format("Instance class [{}] error!", clazz), e);
            }
        }

        final Class<?>[] paramTypes = TypeTools.getClasses(params);
        final Constructor<T> constructor = getConstructor(clazz, paramTypes);
        if (null == constructor) {
            throw new RuntimeException(CharSequenceTools.format("No Constructor matched for parameter types: [{}]", new Object[]{paramTypes}));
        }
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(CharSequenceTools.format("Instance class [{}] error!", clazz), e);
        }
    }

    public enum ReferenceType {
        SOFT,
        WEAK,
        PHANTOM
    }
}
