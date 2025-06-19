package cn.bytengine.d.lang;

/**
 * 克隆支持接口
 *
 * @param <T> 实现克隆接口的类型
 * @author Ban Tenio
 * @version 1.0
 */
public interface Cloneable<T> extends java.lang.Cloneable {
    T clone();
}
