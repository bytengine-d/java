package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
final class SingleToMultiValueMapAdapter<K, V> implements MultiValueMap<K, V>, Serializable {

    private final Map<K, V> targetMap;


    private transient Collection<List<V>> values;


    private transient Set<Entry<K, List<V>>> entries;


    public SingleToMultiValueMapAdapter(Map<K, V> targetMap) {
        AssertTools.notNull(targetMap, "'targetMap' must not be null");
        this.targetMap = targetMap;
    }

    @Override

    public V getFirst(K key) {
        return this.targetMap.get(key);
    }

    @Override
    public void add(K key, V value) {
        if (!this.targetMap.containsKey(key)) {
            this.targetMap.put(key, value);
        } else {
            throw new UnsupportedOperationException("Duplicate key: " + key);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addAll(K key, List<? extends V> values) {
        if (!this.targetMap.containsKey(key)) {
            put(key, (List<V>) values);
        } else {
            throw new UnsupportedOperationException("Duplicate key: " + key);
        }
    }

    @Override
    public void addAll(MultiValueMap<K, V> values) {
        values.forEach(this::addAll);
    }

    @Override
    public void set(K key, V value) {
        this.targetMap.put(key, value);
    }

    @Override
    public void setAll(Map<K, V> values) {
        this.targetMap.putAll(values);
    }

    @Override
    public Map<K, V> toSingleValueMap() {
        return Collections.unmodifiableMap(this.targetMap);
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
        Iterator<Entry<K, List<V>>> i = entrySet().iterator();
        if (value == null) {
            while (i.hasNext()) {
                Entry<K, List<V>> e = i.next();
                if (e.getValue() == null || e.getValue().isEmpty()) {
                    return true;
                }
            }
        } else {
            while (i.hasNext()) {
                Entry<K, List<V>> e = i.next();
                if (value.equals(e.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<V> get(Object key) {
        V value = this.targetMap.get(key);
        return (value != null) ? Collections.singletonList(value) : null;
    }

    @Override
    public List<V> put(K key, List<V> values) {
        if (values.isEmpty()) {
            V result = this.targetMap.put(key, null);
            return (result != null) ? Collections.singletonList(result) : null;
        } else if (values.size() == 1) {
            V result = this.targetMap.put(key, values.get(0));
            return (result != null) ? Collections.singletonList(result) : null;
        } else {
            throw new UnsupportedOperationException("Duplicate key: " + key);
        }
    }

    @Override
    public List<V> remove(Object key) {
        V result = this.targetMap.remove(key);
        return (result != null) ? Collections.singletonList(result) : null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> map) {
        for (Entry<? extends K, ? extends List<V>> entry : map.entrySet()) {
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
    public Collection<List<V>> values() {
        Collection<List<V>> values = this.values;
        if (values == null) {
            Collection<V> targetValues = this.targetMap.values();
            values = new AbstractCollection<List<V>>() {
                @Override
                public Iterator<List<V>> iterator() {
                    Iterator<V> targetIterator = targetValues.iterator();
                    return new Iterator<List<V>>() {
                        @Override
                        public boolean hasNext() {
                            return targetIterator.hasNext();
                        }

                        @Override
                        public List<V> next() {
                            return Collections.singletonList(targetIterator.next());
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
    public Set<Entry<K, List<V>>> entrySet() {
        Set<Entry<K, List<V>>> entries = this.entries;
        if (entries == null) {
            Set<Entry<K, V>> targetEntries = this.targetMap.entrySet();
            entries = new AbstractSet<Entry<K, List<V>>>() {
                @Override
                public Iterator<Entry<K, List<V>>> iterator() {
                    Iterator<Entry<K, V>> targetIterator = targetEntries.iterator();
                    return new Iterator<Entry<K, List<V>>>() {
                        @Override
                        public boolean hasNext() {
                            return targetIterator.hasNext();
                        }

                        @Override
                        public Entry<K, List<V>> next() {
                            Entry<K, V> entry = targetIterator.next();
                            return new AbstractMap.SimpleImmutableEntry<>(entry.getKey(),
                                    Collections.singletonList(entry.getValue()));
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
    public void forEach(BiConsumer<? super K, ? super List<V>> action) {
        this.targetMap.forEach((k, v) -> action.accept(k, Collections.singletonList(v)));
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
                    for (Entry<K, List<V>> e : entrySet()) {
                        K key = e.getKey();
                        List<V> values = e.getValue();
                        if (values == null) {
                            if (otherMap.get(key) != null || !otherMap.containsKey(key)) {
                                return false;
                            }
                        } else {
                            if (!values.equals(otherMap.get(key))) {
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
