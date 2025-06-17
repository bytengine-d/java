package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CollectionTools;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
final class MultiToSingleValueMapAdapter<K, V> implements Map<K, V>, Serializable {

    private final MultiValueMap<K, V> targetMap;


    private transient Collection<V> values;


    private transient Set<Entry<K, V>> entries;


    /**
     * Wrap the given target {@link MultiValueMap} as a {@link Map} adapter.
     *
     * @param targetMap the target {@code MultiValue}
     */
    public MultiToSingleValueMapAdapter(MultiValueMap<K, V> targetMap) {
        AssertTools.notNull(targetMap, "'targetMap' must not be null");
        this.targetMap = targetMap;
    }


    @Override
    public int size() {
        return this.targetMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.targetMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<Entry<K, V>> i = entrySet().iterator();
        if (value == null) {
            while (i.hasNext()) {
                Entry<K, V> e = i.next();
                if (e.getValue() == null) {
                    return true;
                }
            }
        } else {
            while (i.hasNext()) {
                Entry<K, V> e = i.next();
                if (value.equals(e.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override

    public V get(Object key) {
        return adaptValue(this.targetMap.get(key));
    }


    @Override
    public V put(K key, V value) {
        return adaptValue(this.targetMap.put(key, adaptValue(value)));
    }

    @Override

    public V remove(Object key) {
        return adaptValue(this.targetMap.remove(key));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.targetMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.targetMap.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = this.values;
        if (values == null) {
            Collection<List<V>> targetValues = this.targetMap.values();
            values = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    Iterator<List<V>> targetIterator = targetValues.iterator();
                    return new Iterator<V>() {
                        @Override
                        public boolean hasNext() {
                            return targetIterator.hasNext();
                        }

                        @Override
                        public V next() {
                            return targetIterator.next().get(0);
                        }
                    };
                }

                @Override
                public int size() {
                    return targetValues.size();
                }
            };
            this.values = values;
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = this.entries;
        if (entries == null) {
            Set<Entry<K, List<V>>> targetEntries = this.targetMap.entrySet();
            entries = new AbstractSet<Entry<K, V>>() {
                @Override
                public Iterator<Entry<K, V>> iterator() {
                    Iterator<Entry<K, List<V>>> targetIterator = targetEntries.iterator();
                    return new Iterator<Entry<K, V>>() {
                        @Override
                        public boolean hasNext() {
                            return targetIterator.hasNext();
                        }

                        @Override
                        public Entry<K, V> next() {
                            Entry<K, List<V>> entry = targetIterator.next();
                            return new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue().get(0));
                        }
                    };
                }

                @Override
                public int size() {
                    return targetEntries.size();
                }
            };
            this.entries = entries;
        }
        return entries;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.targetMap.forEach((k, vs) -> action.accept(k, vs.get(0)));
    }


    private V adaptValue(List<V> values) {
        if (!CollectionTools.isEmpty(values)) {
            return values.get(0);
        } else {
            return null;
        }
    }


    private List<V> adaptValue(V value) {
        if (value != null) {
            return Collections.singletonList(value);
        } else {
            return null;
        }
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Map<?, ?>) {
            Map<?, ?> otherMap = (Map<?, ?>) other;
            if (size() == otherMap.size()) {
                try {
                    for (Entry<K, V> e : entrySet()) {
                        K key = e.getKey();
                        V value = e.getValue();
                        if (value == null) {
                            if (otherMap.get(key) != null || !otherMap.containsKey(key)) {
                                return false;
                            }
                        } else {
                            if (!value.equals(otherMap.get(key))) {
                                return false;
                            }
                        }
                    }
                    return true;
                } catch (ClassCastException | NullPointerException ignored) {
                    // fall through
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.targetMap.hashCode();
    }

    @Override
    public String toString() {
        return this.targetMap.toString();
    }
}
