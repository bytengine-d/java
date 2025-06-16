package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface Finder {

    int INDEX_NOT_FOUND = -1;

    int start(int from);

    int end(int start);

    default Finder reset() {
        return this;
    }
}
