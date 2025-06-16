package cn.bytengine.d.lang;


import java.lang.ref.*;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ReferenceTools {
    private ReferenceTools() {
    }

    public static <T> Reference<T> create(ReferenceType type, T referent) {
        return create(type, referent, null);
    }

    public static <T> Reference<T> create(ReferenceType type, T referent, ReferenceQueue<T> queue) {
        switch (type) {
            case SOFT:
                return new SoftReference<>(referent, queue);
            case WEAK:
                return new WeakReference<>(referent, queue);
            case PHANTOM:
                return new PhantomReference<>(referent, queue);
            default:
                return null;
        }
    }

    public enum ReferenceType {
        SOFT,
        WEAK,
        PHANTOM
    }
}
