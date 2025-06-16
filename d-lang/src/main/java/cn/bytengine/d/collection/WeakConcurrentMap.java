package cn.bytengine.d.collection;

import cn.bytengine.d.lang.ReferenceTools;

import java.lang.ref.Reference;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class WeakConcurrentMap<K, V> extends ReferenceConcurrentMap<K, V> {

    public WeakConcurrentMap() {
        this(new SafeConcurrentHashMap<>());
    }

    public WeakConcurrentMap(ConcurrentMap<Reference<K>, V> raw) {
        super(raw, ReferenceTools.ReferenceType.WEAK);
    }
}
