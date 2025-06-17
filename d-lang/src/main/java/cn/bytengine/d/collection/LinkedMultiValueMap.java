package cn.bytengine.d.collection;

import cn.bytengine.d.lang.CollectionTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class LinkedMultiValueMap<K, V> extends MultiValueMapAdapter<K, V> implements Serializable, Cloneable {

    public LinkedMultiValueMap() {
        super(new LinkedHashMap<>());
    }

    public LinkedMultiValueMap(int expectedSize) {
        super(CollectionTools.newHashMap(expectedSize, true));
    }

    public LinkedMultiValueMap(Map<K, List<V>> otherMap) {
        super(new LinkedHashMap<>(otherMap));
    }

    public LinkedMultiValueMap<K, V> deepCopy() {
        LinkedMultiValueMap<K, V> copy = new LinkedMultiValueMap<>(size());
        forEach((key, values) -> copy.put(key, new ArrayList<>(values)));
        return copy;
    }

    @Override
    public LinkedMultiValueMap<K, V> clone() {
        return new LinkedMultiValueMap<>(this);
    }
}