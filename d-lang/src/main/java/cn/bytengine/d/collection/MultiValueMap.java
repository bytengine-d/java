package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface MultiValueMap<K, V> extends Map<K, List<V>> {

    V getFirst(K key);

    void add(K key, V value);

    void addAll(K key, List<? extends V> values);

    void addAll(MultiValueMap<K, V> values);

    default void addIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            add(key, value);
        }
    }

    void set(K key, V value);

    void setAll(Map<K, V> values);

    Map<K, V> toSingleValueMap();

    default Map<K, V> asSingleValueMap() {
        return new MultiToSingleValueMapAdapter<>(this);
    }

    static <K, V> MultiValueMap<K, V> fromSingleValue(Map<K, V> map) {
        AssertTools.notNull(map, "Map must not be null");
        return new SingleToMultiValueMapAdapter<>(map);
    }

    static <K, V> MultiValueMap<K, V> fromMultiValue(Map<K, List<V>> map) {
        AssertTools.notNull(map, "Map must not be null");
        return new MultiValueMapAdapter<>(map);
    }

    static <K, V> MultiValueMap<K, V> newMultiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    static <K, V> MultiValueMap<K, V> newMultiValueMap(int expectedSize) {
        return new LinkedMultiValueMap<>(expectedSize);
    }

    static <K, V> MultiValueMap<K, V> newMultiValueMap(Map<K, List<V>> source) {
        return new LinkedMultiValueMap<>(source);
    }
}
