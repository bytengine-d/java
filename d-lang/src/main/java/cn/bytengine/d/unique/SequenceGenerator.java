package cn.bytengine.d.unique;

/**
 * 序列生成器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface SequenceGenerator {
    /**
     * 生成唯一数字
     *
     * @return 唯一数字
     */
    long nextNumber();
}
