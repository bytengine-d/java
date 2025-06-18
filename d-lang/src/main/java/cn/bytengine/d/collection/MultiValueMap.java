package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;

import java.util.List;
import java.util.Map;

/**
 * Extension of the {@code Map} interface that stores multiple values.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface MultiValueMap<K, V> extends Map<K, List<V>> {

    /**
     * Return the first value for the given key.
     *
     * @param key the key
     * @return the first value for the specified key, or {@code null} if none
     */
    V getFirst(K key);

    /**
     * Add the given single value to the current list of values for the given key.
     * @param key the key
     * @param value the value to be added
     */
    void add(K key, V value);

    /**
     * Add all the values of the given list to the current list of values for the given key.
     * @param key they key
     * @param values the values to be added
     */
    void addAll(K key, List<? extends V> values);

    /**
     * Add all the values of the given {@code MultiValueMap} to the current values.
     * @param values the values to be added
     */
    void addAll(MultiValueMap<K, V> values);

    /**
     * {@link #add(Object, Object) Add} the given value, only when the map does not
     * {@link #containsKey(Object) contain} the given key.
     * @param key the key
     * @param value the value to be added
     */
    default void addIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            add(key, value);
        }
    }

    /**
     * Set the given single value under the given key.
     * @param key the key
     * @param value the value to set
     */
    void set(K key, V value);

    /**
     * Set the given values under.
     * @param values the values.
     */
    void setAll(Map<K, V> values);

    /**
     * Return a {@code Map} with the first values contained in this {@code MultiValueMap}.
     * The difference between this method and {@link #asSingleValueMap()} is
     * that this method returns a copy of the entries of this map, whereas
     * the latter returns a view.
     * @return a single value representation of this map
     */
    Map<K, V> toSingleValueMap();

    /**
     * Return this map as a {@code Map} with the first values contained in this
     * {@code MultiValueMap}.
     * <p>The difference between this method and {@link #toSingleValueMap()} is
     * that this method returns a view of the entries of this map, whereas
     * the latter returns a copy.
     * @return a single value representation of this map
     */
    default Map<K, V> asSingleValueMap() {
        return new MultiToSingleValueMapAdapter<>(this);
    }

    /**
     * Return a {@code MultiValueMap<K, V>} that adapts the given single-value
     * {@code Map<K, V>}.
     * <p>The returned map cannot map multiple values to the same key,
     * and doing so results in an {@link UnsupportedOperationException}.
     * Use {@link #fromMultiValue(Map)} to support multiple values.
     * @param map the map to be adapted
     * @param <K> the key type
     * @param <V> the value element type
     * @return a multi-value-map that delegates to {@code map}
     * @see #fromMultiValue(Map)
     */
    static <K, V> MultiValueMap<K, V> fromSingleValue(Map<K, V> map) {
        AssertTools.notNull(map, "Map must not be null");
        return new SingleToMultiValueMapAdapter<>(map);
    }

    /**
     * Return a {@code MultiValueMap<K, V>} that adapts the given multi-value
     * {@code Map<K, List<V>>}.
     * @param map the map to be adapted
     * @param <K> the key type
     * @param <V> the value element type
     * @return a multi-value-map that delegates to {@code map}
     * @see #fromSingleValue(Map)
     */
    static <K, V> MultiValueMap<K, V> fromMultiValue(Map<K, List<V>> map) {
        AssertTools.notNull(map, "Map must not be null");
        return new MultiValueMapAdapter<>(map);
    }

    /**
     * Return a {@code MultiValueMap<K, V>} that is instance of implemented
     *
     * @return a multi-value-map instance
     * @param <K> the key type
     * @param <V> the value element type
     */
    static <K, V> MultiValueMap<K, V> newMultiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    /**
     * Return a {@code MultiValueMap<K, V>} that is instance of implemented
     *
     * @return a multi-value-map instance
     * @param expectedSize the expected number of elements (with a corresponding
     *                     capacity to be derived so that no resize/rehash operations are needed)
     * @param <K> the key type
     * @param <V> the value element type
     */
    static <K, V> MultiValueMap<K, V> newMultiValueMap(int expectedSize) {
        return new LinkedMultiValueMap<>(expectedSize);
    }

    /**
     * Return a {@code MultiValueMap<K, V>} that is instance of implemented
     *
     * @return a multi-value-map instance
     * @param source the Map whose mappings are to be placed in this Map
     * @param <K> the key type
     * @param <V> the value element type
     */
    static <K, V> MultiValueMap<K, V> newMultiValueMap(Map<K, List<V>> source) {
        return new LinkedMultiValueMap<>(source);
    }
}
