package cn.bytengine.d.http;

import cn.bytengine.d.lang.CharSequenceTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ETag for HTTP conditional requests.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ETag {

    private static final ETag WILDCARD = new ETag("*", false);

    private final String tag;

    private final boolean weak;

    /**
     * Represents an ETag for HTTP conditional requests.
     *
     * @param tag  the unquoted tag value
     * @param weak whether the entity tag is for weak or strong validation
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7232">RFC 7232</a>
     */
    public ETag(String tag, boolean weak) {
        this.tag = tag;
        this.weak = weak;
    }

    /**
     * 获取Tag
     *
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 获取Weak
     *
     * @return weak
     */
    public boolean isWeak() {
        return weak;
    }

    /**
     * Whether this a wildcard tag matching to any entity tag value.
     *
     * @return TODO
     */
    public boolean isWildcard() {
        return (this == WILDCARD);
    }

    /**
     * Perform a strong or weak comparison to another {@link ETag}.
     *
     * @param other  the ETag to compare to
     * @param strong whether to perform strong or weak comparison
     * @return whether there is a match or not
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-8.8.3.2">RFC 9110, Section 8.8.3.2</a>
     */
    public boolean compare(ETag other, boolean strong) {
        if (CharSequenceTools.isEmpty(getTag()) || CharSequenceTools.isEmpty(other.getTag())) {
            return false;
        }

        if (strong && (isWeak() || other.isWeak())) {
            return false;
        }

        return getTag().equals(other.getTag());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ETag) {
            ETag oet = (ETag) other;
            return this.tag.equals(oet.tag) && this.weak == oet.weak;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = this.tag.hashCode();
        result = 31 * result + Boolean.hashCode(this.weak);
        return result;
    }

    @Override
    public String toString() {
        return formattedTag();
    }

    /**
     * Return the fully formatted tag including "W/" prefix and quotes.
     *
     * @return TODO
     */
    public String formattedTag() {
        if (isWildcard()) {
            return "*";
        }
        return (this.weak ? "W/" : "") + "\"" + this.tag + "\"";
    }


    /**
     * Create an {@link ETag} instance from a String representation.
     *
     * @param rawValue the formatted ETag value
     * @return the created instance
     */
    public static ETag create(String rawValue) {
        boolean weak = rawValue.startsWith("W/");
        if (weak) {
            rawValue = rawValue.substring(2);
        }
        if (rawValue.length() > 2 && rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
            rawValue = rawValue.substring(1, rawValue.length() - 1);
        }
        return new ETag(rawValue, weak);
    }

    /**
     * Parse entity tags from an "If-Match" or "If-None-Match" header.
     *
     * @param source the source string to parse
     * @return the parsed ETags
     */
    public static List<ETag> parse(String source) {

        List<ETag> result = new ArrayList<>();
        State state = State.BEFORE_QUOTES;
        int startIndex = -1;
        boolean weak = false;

        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            if (state == State.IN_QUOTES) {
                if (c == '"') {
                    String tag = source.substring(startIndex, i);
                    if (CharSequenceTools.isNotEmpty(tag)) {
                        result.add(new ETag(tag, weak));
                    }
                    state = State.AFTER_QUOTES;
                    startIndex = -1;
                    weak = false;
                }
                continue;
            }

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (c == ',') {
                state = State.BEFORE_QUOTES;
                continue;
            }

            if (state == State.BEFORE_QUOTES) {
                if (c == '*') {
                    result.add(WILDCARD);
                    state = State.AFTER_QUOTES;
                    continue;
                }
                if (c == '"') {
                    state = State.IN_QUOTES;
                    startIndex = i + 1;
                    continue;
                }
                if (c == 'W' && source.length() > i + 2) {
                    if (source.charAt(i + 1) == '/' && source.charAt(i + 2) == '"') {
                        state = State.IN_QUOTES;
                        i = i + 2;
                        startIndex = i + 1;
                        weak = true;
                        continue;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Add quotes around the ETag value if not present already.
     *
     * @param tag the ETag value
     * @return the resulting, quoted value
     */
    public static String quoteETagIfNecessary(String tag) {
        if (tag.startsWith("W/\"")) {
            if (tag.length() > 3 && tag.endsWith("\"")) {
                return tag;
            }
        } else if (tag.startsWith("\"")) {
            if (tag.length() > 1 && tag.endsWith("\"")) {
                return tag;
            }
        }
        return ("\"" + tag + "\"");
    }


    private enum State {

        BEFORE_QUOTES, IN_QUOTES, AFTER_QUOTES

    }
}
