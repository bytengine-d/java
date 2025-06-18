package cn.bytengine.d.collection;

import cn.bytengine.d.lang.CollectionTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation of {@link MultiValueMap} that wraps a {@link LinkedHashMap},
 * storing multiple values in an {@link ArrayList}.
 *
 * <p>This Map implementation is generally not thread-safe. It is primarily designed
 * for data structures exposed from request objects, for use in a single thread only.
 *
 * @param <K> the key type
 * @param <V> the value element type
 * @author Ban Tenio
 * @version 1.0
 */
public class LinkedMultiValueMap<K, V> extends MultiValueMapAdapter<K, V> implements Serializable, Cloneable {

    /**
     * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}.
     */
    public LinkedMultiValueMap() {
        super(new LinkedHashMap<>());
    }

    /**
     * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}
     * with an initial capacity that can accommodate the specified number of
     * elements without any immediate resize/rehash operations to be expected.
     *
     * @param expectedSize the expected number of elements (with a corresponding
     *                     capacity to be derived so that no resize/rehash operations are needed)
     * @see CollectionTools#newHashMap(int, boolean)
     */
    public LinkedMultiValueMap(int expectedSize) {
        super(CollectionTools.newHashMap(expectedSize, true));
    }

    /**
     * Copy constructor: Create a new LinkedMultiValueMap with the same mappings as
     * the specified Map. Note that this will be a shallow copy; its value-holding
     * List entries will get reused and therefore cannot get modified independently.
     * @param otherMap the Map whose mappings are to be placed in this Map
     * @see #clone()
     * @see #deepCopy()
     */
    public LinkedMultiValueMap(Map<K, List<V>> otherMap) {
        super(new LinkedHashMap<>(otherMap));
    }

    /**
     * Create a deep copy of this Map.
     * @return a copy of this Map, including a copy of each value-holding List entry
     * (consistently using an independent modifiable {@link ArrayList} for each entry)
     * along the lines of {@code MultiValueMap.addAll} semantics
     * @see #addAll(MultiValueMap)
     * @see #clone()
     */
    public LinkedMultiValueMap<K, V> deepCopy() {
        LinkedMultiValueMap<K, V> copy = new LinkedMultiValueMap<>(size());
        forEach((key, values) -> copy.put(key, new ArrayList<>(values)));
        return copy;
    }

    /**
     * Create a regular copy of this Map.
     * @return a shallow copy of this Map, reusing this Map's value-holding List entries
     * (even if some entries are shared or unmodifiable) along the lines of standard
     * {@code Map.put} semantics
     * @see #put(Object, List)
     * @see #putAll(Map)
     * @see LinkedMultiValueMap#LinkedMultiValueMap(Map)
     * @see #deepCopy()
     */
    @Override
    public LinkedMultiValueMap<K, V> clone() {
        return new LinkedMultiValueMap<>(this);
    }
}