package cn.bytengine.d.lang;

/**
 * 克隆支持类，提供默认的克隆方法
 *
 * @param <T> 继承类的类型
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
