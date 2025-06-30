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

    /**
     * 加载注册的类访问器注册器
     *
     * @see ClassAccessorRegister#load()
     */
    public static void load() {
        ClassAccessorRegister.load();
    }

    /**
     * 注册指定类型
     *
     * @param key 指定类型
     * @return 类访问器
     */
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

    /**
     * 清理已注册类访问器
     *
     * @return 被清理类访问器
     */
    public static Collection<ClassAccessor> classAccessors() {
        return classAccessorMap.values();
    }

    /**
     * 获取所有注册类访问器
     *
     * @return 类访问器集合
     */
    public static Set<Map.Entry<Class<?>, ClassAccessor>> classes() {
        return classAccessorMap.entrySet();
    }

    /**
     * 遍历注册类访问器
     *
     * @param action 类访问器消费函数
     */
    public static void each(BiConsumer<? super Class<?>, ? super ClassAccessor> action) {
        classAccessorMap.forEach(action);
    }

    /**
     * 是否注册指定类访问器
     *
     * @param key 类名称
     * @return 是否已注册
     */
    public static boolean containsClass(String key) {
        return classNameAccessorMap.containsKey(key);
    }

    /**
     * 是否注册指定类访问器
     *
     * @param key 指定类
     * @return 是否已注册
     */
    public static boolean containsClass(Class<?> key) {
        return classAccessorMap.containsKey(key);
    }

    /**
     * 是否注册类访问器
     *
     * @param value 类信息
     * @return 是否已注册
     */
    public static boolean containsClassAccessor(ClassInfo value) {
        return containsClassAccessor(new ClassAccessor(value));
    }

    /**
     * 判断类访问器已经注册
     *
     * @param value 类访问器
     * @return 是否已注册
     */
    public static boolean containsClassAccessor(ClassAccessor value) {
        return classAccessorMap.containsValue(value);
    }

    /**
     * 获取指定类的访问器
     *
     * @param key 类型
     * @return 类访问器
     */
    public static ClassAccessor get(Class<?> key) {
        return classAccessorMap.get(key);
    }

    /**
     * 获取指定类的访问器
     *
     * @param key 类名
     * @return 类访问器
     */
    public static ClassAccessor get(String key) {
        return classNameAccessorMap.get(key);
    }

    /**
     * 给定类集合，获取类访问器集合
     *
     * @param classes 类集合
     * @return 类访问器集合
     */
    public static ClassAccessor[] of(Class<?>... classes) {
        return Arrays.stream(classes).map(ClassAccessors::register).toArray(ClassAccessor[]::new);
    }
}
