package cn.bytengine.d.lang;

import cn.hutool.core.text.finder.Finder;
import cn.hutool.core.text.finder.StrFinder;

import java.util.Map;
import java.util.function.Supplier;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class CharSequenceTools {
    public static final String EMPTY_JSON = "{}";
    public static final String NULL = "null";
    public static final String EMPTY = "";
    public static final String SPACE = " ";

    public static final char C_BACKSLASH = CharTools.BACKSLASH;
    public static final int INDEX_NOT_FOUND = Finder.INDEX_NOT_FOUND;

    private CharSequenceTools() {
    }

    public static boolean isBlank(CharSequence str) {
        final int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (false == CharTools.isBlankChar(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

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

    public static String formatWith(String strPattern, String placeHolder, Object... argArray) {
        if (isBlank(strPattern) || isBlank(placeHolder) || ArrayTools.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();
        final int placeHolderLength = placeHolder.length();

        // 初始化定义好的长度以获得更好的性能
        final StringBuilder buf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(placeHolder, handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return strPattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                buf.append(strPattern, handledPosition, strPatternLength);
                return buf.toString();
            }

            // 转义符
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {// 转义符
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH) {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    buf.append(strPattern, handledPosition, delimIndex - 1);
                    buf.append(StringTools.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + placeHolderLength;
                } else {
                    // 占位符被转义
                    argIndex--;
                    buf.append(strPattern, handledPosition, delimIndex - 1);
                    buf.append(placeHolder.charAt(0));
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                buf.append(strPattern, handledPosition, delimIndex);
                buf.append(StringTools.utf8Str(argArray[argIndex]));
                handledPosition = delimIndex + placeHolderLength;
            }
        }

        // 加入最后一个占位符后所有的字符
        buf.append(strPattern, handledPosition, strPatternLength);

        return buf.toString();
    }

    public static String format(String strPattern, Object... argArray) {
        return formatWith(strPattern, EMPTY_JSON, argArray);
    }

    public static String format(CharSequence template, Map<?, ?> map, boolean ignoreNull) {
        if (null == template) {
            return null;
        }
        if (null == map || map.isEmpty()) {
            return template.toString();
        }

        String template2 = template.toString();
        String value;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            value = StringTools.utf8Str(entry.getValue());
            if (null == value && ignoreNull) {
                continue;
            }
            template2 = replace(template2, "{" + entry.getKey() + "}", value);
        }
        return template2;
    }

    public static String replace(CharSequence str, CharSequence searchStr, CharSequence replacement) {
        return replace(str, 0, searchStr, replacement, false);
    }

    public static String replace(CharSequence str, CharSequence searchStr, CharSequence replacement, boolean ignoreCase) {
        return replace(str, 0, searchStr, replacement, ignoreCase);
    }

    public static String replace(CharSequence str, int fromIndex, CharSequence searchStr, CharSequence replacement, boolean ignoreCase) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return StringTools.str(str);
        }
        if (null == replacement) {
            replacement = EMPTY;
        }

        final int strLength = str.length();
        final int searchStrLength = searchStr.length();
        if (strLength < searchStrLength) {
            // issue#I4M16G@Gitee
            return StringTools.str(str);
        }

        if (fromIndex > strLength) {
            return StringTools.str(str);
        } else if (fromIndex < 0) {
            fromIndex = 0;
        }

        final StringBuilder result = new StringBuilder(strLength - searchStrLength + replacement.length());
        if (0 != fromIndex) {
            result.append(str.subSequence(0, fromIndex));
        }

        int preIndex = fromIndex;
        int index;
        while ((index = indexOf(str, searchStr, preIndex, ignoreCase)) > -1) {
            result.append(str.subSequence(preIndex, index));
            result.append(replacement);
            preIndex = index + searchStrLength;
        }

        if (preIndex < strLength) {
            // 结尾部分
            result.append(str.subSequence(preIndex, strLength));
        }
        return result.toString();
    }

    public static int indexOf(CharSequence text, CharSequence searchStr, int from, boolean ignoreCase) {
        if (isEmpty(text) || isEmpty(searchStr)) {
            if (equals(text, searchStr)) {
                return 0;
            } else {
                return INDEX_NOT_FOUND;
            }
        }
        return new StrFinder(searchStr, ignoreCase).setText(text).start(from);
    }

    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }

    public static boolean equalsAnyIgnoreCase(CharSequence str1, CharSequence... strs) {
        return equalsAny(str1, true, strs);
    }

    public static boolean equalsAny(CharSequence str1, CharSequence... strs) {
        return equalsAny(str1, false, strs);
    }

    public static boolean equalsAny(CharSequence str1, boolean ignoreCase, CharSequence... strs) {
        if (ArrayTools.isEmpty(strs)) {
            return false;
        }

        for (CharSequence str : strs) {
            if (equals(str1, str, ignoreCase)) {
                return true;
            }
        }
        return false;
    }
}
