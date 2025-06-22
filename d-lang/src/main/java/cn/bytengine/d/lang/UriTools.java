package cn.bytengine.d.lang;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * URI工具辅助API
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class UriTools {
    private UriTools() {
    }

    /**
     * Decode the given encoded URI component.
     * <p>See {@link #decode(String, Charset)} for the decoding rules.
     *
     * @param source   the encoded String
     * @param encoding the character encoding to use
     * @return the decoded value
     * @throws IllegalArgumentException when the given source contains invalid encoded sequences
     * @see #decode(String, Charset)
     * @see java.net.URLDecoder#decode(String, String)
     */
    public static String decode(String source, String encoding) {
        return decode(source, Charset.forName(encoding));
    }

    /**
     * Decode the given encoded URI component value. Based on the following rules:
     * <ul>
     * <li>Alphanumeric characters {@code "a"} through {@code "z"}, {@code "A"} through {@code "Z"},
     * and {@code "0"} through {@code "9"} stay the same.</li>
     * <li>Special characters {@code "-"}, {@code "_"}, {@code "."}, and {@code "*"} stay the same.</li>
     * <li>A sequence "<i>{@code %xy}</i>" is interpreted as a hexadecimal representation of the character.</li>
     * <li>For all other characters (including those already decoded), the output is undefined.</li>
     * </ul>
     *
     * @param source  the encoded String
     * @param charset the character set
     * @return the decoded value
     * @throws IllegalArgumentException when the given source contains invalid encoded sequences
     * @see java.net.URLDecoder#decode(String, String)
     */
    public static String decode(String source, Charset charset) {
        int length = source.length();
        if (length == 0) {
            return source;
        }
        AssertTools.notNull(charset, "Charset must not be null");

        ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
        boolean changed = false;
        for (int i = 0; i < length; i++) {
            int ch = source.charAt(i);
            if (ch == '%') {
                if (i + 2 < length) {
                    char hex1 = source.charAt(i + 1);
                    char hex2 = source.charAt(i + 2);
                    int u = Character.digit(hex1, 16);
                    int l = Character.digit(hex2, 16);
                    if (u == -1 || l == -1) {
                        throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                    }
                    baos.write((char) ((u << 4) + l));
                    i += 2;
                    changed = true;
                } else {
                    throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                }
            } else {
                baos.write(ch);
            }
        }
        return (changed ? new String(baos.toByteArray(), charset) : source);
    }
}
