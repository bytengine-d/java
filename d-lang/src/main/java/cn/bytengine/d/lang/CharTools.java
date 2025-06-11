package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class CharTools {
    private CharTools() {
    }

    public static final char SPACE = ' ';
    public static final char TAB = '	';
    public static final char DOT = '.';
    public static final char SLASH = '/';
    public static final char BACKSLASH = '\\';
    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char DASHED = '-';
    public static final char UNDERLINE = '_';
    public static final char COMMA = ',';
    public static final char DELIM_START = '{';
    public static final char DELIM_END = '}';
    public static final char BRACKET_START = '[';
    public static final char BRACKET_END = ']';
    public static final char DOUBLE_QUOTES = '"';
    public static final char SINGLE_QUOTE = '\'';
    public static final char AMP = '&';
    public static final char COLON = ':';
    public static final char AT = '@';

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

    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }
}
