package cn.bytengine.d.assist;

import cn.bytengine.d.lang.reflect.ClassInfo;
import cn.bytengine.d.lang.reflect.ClassInfos;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 类辅助工具，主要用于缓存某类型对应的ClassAccessor
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassAccessors {
    private static final Map<Class<?>, ClassAccessor> classAccessorMap = new HashMap<>(16);
    private static final Map<String, ClassAccessor> classNameAccessorMap = new HashMap<>(16);

    public static void load() {
        ClassAccessorRegister.load();
    }

    public static ClassAccessor register(Class<?> key) {
        if (containsClass(key)) {
            return get(key);
        }
        ClassInfo[] classInfos = ClassInfos.register(key);
        ClassAccessor classAccessor = new ClassAccessor(classInfos[0]);
        classAccessorMap.put(key, classAccessor);
        classNameAccessorMap.put(key.getCanonicalName(), classAccessor);
        return classAccessor;
    }

    public static Collection<ClassAccessor> classAccessors() {
        return classAccessorMap.values();
    }

    public static Set<Map.Entry<Class<?>, ClassAccessor>> classes() {
        return classAccessorMap.entrySet();
    }

    public static void each(BiConsumer<? super Class<?>, ? super ClassAccessor> action) {
        classAccessorMap.forEach(action);
    }

    public static boolean containsClass(String key) {
        return classNameAccessorMap.containsKey(key);
    }

    public static boolean containsClass(Class<?> key) {
        return classAccessorMap.containsKey(key);
    }

    public static boolean containsClassAccessor(ClassInfo value) {
        return containsClassAccessor(new ClassAccessor(value));
    }

    public static boolean containsClassAccessor(ClassAccessor value) {
        return classAccessorMap.containsValue(value);
    }

    public static ClassAccessor get(Class<?> key) {
        return classAccessorMap.get(key);
    }

    public static ClassAccessor get(String key) {
        return classNameAccessorMap.get(key);
    }

    public static ClassAccessor[] of(Class<?>... classes) {
        return Arrays.stream(classes).map(ClassAccessors::register).toArray(ClassAccessor[]::new);
    }
}
