package cn.bytengine.d.unique;

/**
 * 唯一值生成器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface UniqueGenerator {
    /**
     * 生成唯一值
     *
     * @return 唯一值
     */
    String nextUnique();
}
