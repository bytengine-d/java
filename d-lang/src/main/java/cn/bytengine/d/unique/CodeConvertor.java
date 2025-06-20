package cn.bytengine.d.unique;

/**
 * Code转码器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface CodeConvertor {
    /**
     * 数字转码
     *
     * @param number 数字
     * @return 码
     */
    String encode(long number);

    /**
     * 码转为数字
     *
     * @param code 码
     * @return 数字
     */
    long decode(String code);
}
