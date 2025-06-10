package cn.bytengine.d.assist;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 类辅助工具，主要用于缓存某类型对应的ClassInfo
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassAssists {
    private final Map<Class<?>, ClassInfo> classInfoMap = new HashMap<>(16);

    public ClassInfo register(Class<?> key, ClassInfo value) {
        return classInfoMap.put(key, value);
    }

    public ClassInfo register(Class<?> key) {
        return classInfoMap.put(key, ClassInfo.of(key));
    }

    public Collection<ClassInfo> classInfos() {
        return classInfoMap.values();
    }

    public Set<Map.Entry<Class<?>, ClassInfo>> classes() {
        return classInfoMap.entrySet();
    }

    public void eachClassInfo(BiConsumer<? super Class<?>, ? super ClassInfo> action) {
        classInfoMap.forEach(action);
    }

    public boolean containsClass(Class<?> key) {
        return classInfoMap.containsKey(key);
    }

    public boolean containsClassInfo(ClassInfo value) {
        return classInfoMap.containsValue(value);
    }

    public ClassInfo get(Class<?> key) {
        return classInfoMap.get(key);
    }

    public static ClassAssists of(Class<?>... classes) {
        ClassAssists classAssists = new ClassAssists();
        Arrays.stream(classes).map(ClassInfo::of).forEach(ci -> classAssists.classInfoMap.put(ci.getType(), ci));
        return classAssists;
    }

    static void checkInstance(Class<?> clazz, Object instance) {
        if (!clazz.isInstance(instance)) {
            throw new UnsupportedOperationException("the object is not type instance");
        }
    }
}
