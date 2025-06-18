package cn.bytengine.d.lang.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * 类信息缓存
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ClassInfos {
    private ClassInfos() {
    }

    private static final Map<Class<?>, ClassInfo> classInfoMap = new HashMap<>(128);
    private static final Map<String, ClassInfo> classNameInfoMap = new HashMap<>(128);

    /**
     * 获取指定类型的类信息
     *
     * @param key 类信息实例
     * @return 类信息
     */
    public static ClassInfo get(Class<?> key) {
        return classInfoMap.get(key);
    }

    /**
     * 获取指定类名类信息
     *
     * @param className 类名
     * @return 类信息
     */
    public static ClassInfo get(String className) {
        return classNameInfoMap.get(className);
    }

    /**
     * 是否包含指定类型信息
     *
     * @param key 类信息实例
     * @return 是否包含类信息
     */
    public static boolean containsClass(Class<?> key) {
        return classInfoMap.containsKey(key);
    }

    /**
     * 是否包含指定类名的类信息
     *
     * @param value 类名
     * @return 是否包含类信息
     */
    public static boolean containsClassInfo(String value) {
        return classNameInfoMap.containsValue(value);
    }

    /**
     * 注册指定类信息实例集合，并返回对用类信息集合
     *
     * @param classes 类信息实例
     * @return 类信息集合
     */
    public static ClassInfo[] register(Class<?>... classes) {
        ClassInfo[] result = new ClassInfo[classes.length];
        Class<?> clazz;
        for (int idx = 0; idx < classes.length; idx++) {
            clazz = classes[idx];
            if (clazz == null) {
                result[idx] = null;
            } else if (containsClass(clazz)) {
                result[idx] = get(clazz);
            } else {
                result[idx] = ClassInfo.of(clazz);
                classInfoMap.put(clazz, result[idx]);
                classNameInfoMap.put(clazz.getCanonicalName(), result[idx]);
            }
        }
        return result;
    }
}
