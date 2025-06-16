package cn.bytengine.d.collection;

import cn.bytengine.d.lang.JdkTools;
import cn.bytengine.d.lang.MapTools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SafeConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
    public SafeConcurrentHashMap() {
        super();
    }

    public SafeConcurrentHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SafeConcurrentHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public SafeConcurrentHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SafeConcurrentHashMap(int initialCapacity,
                                 float loadFactor, int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        if (JdkTools.IS_JDK8) {
            return MapTools.computeIfAbsentForJdk8(this, key, mappingFunction);
        } else {
            return super.computeIfAbsent(key, mappingFunction);
        }
    }
}
