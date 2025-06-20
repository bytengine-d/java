package cn.bytengine.d.lang;

import java.security.SecureRandom;
import java.util.BitSet;

/**
 * 字符工具类<br>
 * 部分工具来自于Apache Commons系列
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class CharTools {
    private CharTools() {
    }

    /**
     * 数字字符集合
     */
    public static final char[] NUMBER_CHARS = CharSequenceTools.NUMBERS.toCharArray();

    /**
     * 数字字符集合长度
     */
    public static final int NUMBER_CHARS_COUNT = NUMBER_CHARS.length;

    /**
     * 小写字母字符集合
     */
    public static final char[] LOWERCASE_ALPHA_CHARS = CharSequenceTools.LOWERCASE_LETTERS.toCharArray();

    /**
     * 小写字母字符集合长度
     */
    public static final int LOWERCASE_ALPHA_CHARS_COUNT = LOWERCASE_ALPHA_CHARS.length;

    /**
     * 大写字母字符集合
     */
    public static final char[] UPPERCASE_ALPHA_CHARS = CharSequenceTools.UPPERCASE_LETTERS.toCharArray();

    /**
     * 大写字母字符集合长度
     */
    public static final int UPPERCASE_ALPHA_CHARS_COUNT = UPPERCASE_ALPHA_CHARS.length;

    /**
     * 随机生成器
     */
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * 字符常量：空格符 {@code ' '}
     */
    public static final char SPACE = ' ';
    /**
     * 字符常量：制表符 {@code '\t'}
     */
    public static final char TAB = '	';
    /**
     * 字符常量：点 {@code '.'}
     */
    public static final char DOT = '.';
    /**
     * 字符常量：斜杠 {@code '/'}
     */
    public static final char SLASH = '/';
    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    public static final char BACKSLASH = '\\';
    /**
     * 字符常量：回车符 {@code '\r'}
     */
    public static final char CR = '\r';
    /**
     * 字符常量：换行符 {@code '\n'}
     */
    public static final char LF = '\n';
    /**
     * 字符常量：减号（连接符） {@code '-'}
     */
    public static final char DASHED = '-';
    /**
     * 字符常量：下划线 {@code '_'}
     */
    public static final char UNDERLINE = '_';
    /**
     * 字符常量：逗号 {@code ','}
     */
    public static final char COMMA = ',';
    /**
     * 字符常量：花括号（左） <code>'{'</code>
     */
    public static final char DELIM_START = '{';
    /**
     * 字符常量：花括号（右） <code>'}'</code>
     */
    public static final char DELIM_END = '}';
    /**
     * 字符常量：中括号（左） {@code '['}
     */
    public static final char BRACKET_START = '[';
    /**
     * 字符常量：中括号（右） {@code ']'}
     */
    public static final char BRACKET_END = ']';
    /**
     * 字符常量：双引号 {@code '"'}
     */
    public static final char DOUBLE_QUOTES = '"';
    /**
     * 字符常量：单引号 {@code '\''}
     */
    public static final char SINGLE_QUOTE = '\'';
    /**
     * 字符常量：与 {@code '&'}
     */
    public static final char AMP = '&';
    /**
     * 字符常量：冒号 {@code ':'}
     */
    public static final char COLON = ':';
    /**
     * 字符常量：艾特 {@code '@'}
     */
    public static final char AT = '@';

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000'
                // issue#I5UGSQ，Hangul Filler
                || c == '\u3164'
                // Braille Pattern Blank
                || c == '\u2800'
                // Zero Width Non-Joiner, ZWNJ
                || c == '\u200c'
                // MONGOLIAN VOWEL SEPARATOR
                || c == '\u180e';
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param value 被检查的对象
     * @return true表示为字符类
     */
    public static boolean isChar(Object value) {
        //noinspection ConstantConditions
        return value instanceof Character || value.getClass() == char.class;
    }

    /**
     * 字符转为字符串<br>
     * 如果为ASCII字符，使用缓存
     *
     * @param c 字符
     * @return 字符串
     * @see ASCIIStrCache#toString(char)
     */
    public static String toString(char c) {
        return ASCIIStrCache.toString(c);
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1              字符1
     * @param c2              字符2
     * @param caseInsensitive 是否忽略大小写
     * @return 是否相同
     */
    public static boolean equals(char c1, char c2, boolean caseInsensitive) {
        if (caseInsensitive) {
            return Character.toLowerCase(c1) == Character.toLowerCase(c2);
        }
        return c1 == c2;
    }

    /**
     * <p>
     * 检查是否为数字字符，数字字符指0~9
     * </p>
     *
     * <pre>
     *   CharTools.isNumber('a')  = false
     *   CharTools.isNumber('A')  = false
     *   CharTools.isNumber('3')  = true
     *   CharTools.isNumber('-')  = false
     *   CharTools.isNumber('\n') = false
     *   CharTools.isNumber('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为数字字符，数字字符指0~9
     */
    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * 根据指定字符集合范围，重新生成指定长度字符集合表
     *
     * @param sourceTable    指定字符集合
     * @param generateLength 生成长度
     * @param allowRepeat    是否允许重复
     * @return 生成新的字符集合
     */
    public static char[] generateTable(char[] sourceTable, int generateLength, boolean allowRepeat) {
        int len = sourceTable.length;
        BitSet placeholder = allowRepeat ? new BitSet(generateLength) : null;
        char[] result = new char[generateLength];
        int randIndex = 0;
        for (int idx = 0; idx < sourceTable.length; idx++) {
            randIndex = secureRandom.nextInt(len);
            if (allowRepeat && (placeholder.get(randIndex) || idx == randIndex)) {
                idx--;
                continue;
            }
            if (allowRepeat) {
                placeholder.set(randIndex);
            }
            result[idx] = sourceTable[randIndex];
        }
        return result;
    }
}
