package cn.bytengine.d.fn;

import cn.bytengine.d.lang.Cloneable;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CloneSupport<T> implements Cloneable<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
