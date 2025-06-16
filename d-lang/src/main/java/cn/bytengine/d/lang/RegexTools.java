package cn.bytengine.d.lang;

import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class RegexTools {
    private RegexTools() {
    }

    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }

    public static boolean isMatch(String regex, CharSequence content) {
        if (content == null) {
            // 提供null的字符串为不匹配
            return false;
        }

        if (CharSequenceTools.isEmpty(regex)) {
            // 正则不存在则为全匹配
            return true;
        }

        final Pattern pattern = PatternPool.get(regex, Pattern.DOTALL);
        return isMatch(pattern, content);
    }
}
