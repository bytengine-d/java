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

    public static ClassInfo get(Class<?> key) {
        return classInfoMap.get(key);
    }

    public static ClassInfo get(String className) {
        return classNameInfoMap.get(className);
    }

    public static boolean containsClass(Class<?> key) {
        return classInfoMap.containsKey(key);
    }

    public static boolean containsClassInfo(ClassInfo value) {
        return classInfoMap.containsValue(value);
    }

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
