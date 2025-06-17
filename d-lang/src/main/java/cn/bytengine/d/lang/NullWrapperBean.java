package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class NullWrapperBean<T> {
    private final Class<T> clazz;

    public NullWrapperBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getWrappedClass() {
        return clazz;
    }
}
