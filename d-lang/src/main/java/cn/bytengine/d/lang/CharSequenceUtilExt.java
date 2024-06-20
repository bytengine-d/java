package cn.bytengine.d.lang;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.function.Supplier;

/**
 * HuTool CharSequenceUtil扩展
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.lang
 * <li>ClassName:      CharSequenceUtilExt
 * <li>Date:    2024/5/7 11:12
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CharSequenceUtilExt extends CharSequenceUtil {
    /**
     * 检查是否为空白字符序列，如果是将使用默认值提供函数
     *
     * @param str             检查的字符序列
     * @param defaultSupplier 默认值提供函数
     * @return 原字符序列或默认值
     */
    public static String blankToDefault(CharSequence str, Supplier<String> defaultSupplier) {
        return isBlank(str) ? defaultSupplier.get() : str.toString();
    }

    /**
     * 检查是否为空字符序列或者为null，如果是将使用默认值提供函数
     *
     * @param str             检查的字符序列
     * @param defaultSupplier 默认值提供函数
     * @return 原字符序列或默认值
     */
    public static String emptyToDefault(CharSequence str, Supplier<String> defaultSupplier) {
        return isEmpty(str) ? defaultSupplier.get() : str.toString();
    }
}
