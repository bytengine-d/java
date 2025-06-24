package cn.bytengine.d.http;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.ObjectTools;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.*;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

/**
 * Representation of the Content-Disposition type and parameters as defined in RFC 6266.
 *
 * @author Ban Tenio
 * @version 1.0
 * @see <a href="https://tools.ietf.org/html/rfc6266">RFC 6266</a>
 */
public final class ContentDisposition {

    private static final Pattern BASE64_ENCODED_PATTERN =
            Pattern.compile("=\\?([0-9a-zA-Z-_]+)\\?B\\?([+/0-9a-zA-Z]+=*)\\?=");

    private static final Pattern QUOTED_PRINTABLE_ENCODED_PATTERN =
            Pattern.compile("=\\?([0-9a-zA-Z-_]+)\\?Q\\?([!->@-~]+)\\?="); // Printable ASCII other than "?" or SPACE

    private static final String INVALID_HEADER_FIELD_PARAMETER_FORMAT =
            "Invalid header field parameter format (as defined in RFC 5987)";

    private static final BitSet PRINTABLE = new BitSet(256);


    static {
        // RFC 2045, Section 6.7, and RFC 2047, Section 4.2
        for (int i = 33; i <= 126; i++) {
            PRINTABLE.set(i);
        }
        PRINTABLE.set(61, false); // =
        PRINTABLE.set(63, false); // ?
        PRINTABLE.set(95, false); // _
    }

    private final String type;

    private final String name;

    private final String filename;

    private final Charset charset;

    private final Long size;

    private final ZonedDateTime creationDate;

    private final ZonedDateTime modificationDate;

    private final ZonedDateTime readDate;


    /**
     * Private constructor. See static factory methods in this class.
     */
    private ContentDisposition(String type, String name, String filename,
                               Charset charset, Long size, ZonedDateTime creationDate,
                               ZonedDateTime modificationDate, ZonedDateTime readDate) {

        this.type = type;
        this.name = name;
        this.filename = filename;
        this.charset = charset;
        this.size = size;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.readDate = readDate;
    }


    /**
     * Return whether the {@link #getType() type} is {@literal "attachment"}.
     *
     * @return TODO
     */
    public boolean isAttachment() {
        return (this.type != null && this.type.equalsIgnoreCase("attachment"));
    }

    /**
     * Return whether the {@link #getType() type} is {@literal "form-data"}.
     *
     * @return TODO
     */
    public boolean isFormData() {
        return (this.type != null && this.type.equalsIgnoreCase("form-data"));
    }

    /**
     * Return whether the {@link #getType() type} is {@literal "inline"}.
     *
     * @return TODO
     */
    public boolean isInline() {
        return (this.type != null && this.type.equalsIgnoreCase("inline"));
    }

    /**
     * Return the disposition type.
     *
     * @return TODO
     * @see #isAttachment()
     * @see #isFormData()
     * @see #isInline()
     */
    public String getType() {
        return this.type;
    }

    /**
     * Return the value of the {@literal name} parameter, or {@code null} if not defined.
     *
     * @return TODO
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the value of the {@literal filename} parameter, possibly decoded
     * from BASE64 encoding based on RFC 2047, or of the {@literal filename*}
     * parameter, possibly decoded as defined in the RFC 5987.
     *
     * @return TODO
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Return the charset defined in {@literal filename*} parameter, or {@code null} if not defined.
     *
     * @return TODO
     */
    public Charset getCharset() {
        return this.charset;
    }

    /**
     * Return the value of the {@literal size} parameter, or {@code null} if not defined.
     *
     * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
     *
     * @return TODO
     */
    public Long getSize() {
        return this.size;
    }

    /**
     * Return the value of the {@literal creation-date} parameter, or {@code null} if not defined.
     *
     * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
     *
     * @return TODO
     */
    public ZonedDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Return the value of the {@literal modification-date} parameter, or {@code null} if not defined.
     *
     * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
     *
     * @return TODO
     */
    public ZonedDateTime getModificationDate() {
        return this.modificationDate;
    }

    /**
     * Return the value of the {@literal read-date} parameter, or {@code null} if not defined.
     *
     * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
     *
     * @return TODO
     */
    public ZonedDateTime getReadDate() {
        return this.readDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ContentDisposition) {
            ContentDisposition that = (ContentDisposition) other;
            return (ObjectTools.nullSafeEquals(this.type, that.type) &&
                    ObjectTools.nullSafeEquals(this.name, that.name) &&
                    ObjectTools.nullSafeEquals(this.filename, that.filename) &&
                    ObjectTools.nullSafeEquals(this.charset, that.charset) &&
                    ObjectTools.nullSafeEquals(this.size, that.size) &&
                    ObjectTools.nullSafeEquals(this.creationDate, that.creationDate) &&
                    ObjectTools.nullSafeEquals(this.modificationDate, that.modificationDate) &&
                    ObjectTools.nullSafeEquals(this.readDate, that.readDate));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectTools.nullSafeHash(this.type, this.name, this.filename,
                this.charset, this.size, this.creationDate, this.modificationDate, this.readDate);
    }

    /**
     * Return the header value for this content disposition as defined in RFC 6266.
     *
     * @see #parse(String)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.type != null) {
            sb.append(this.type);
        }
        if (this.name != null) {
            sb.append("; name=\"");
            sb.append(this.name).append('\"');
        }
        if (this.filename != null) {
            if (this.charset == null || StandardCharsets.US_ASCII.equals(this.charset)) {
                sb.append("; filename=\"");
                sb.append(encodeQuotedPairs(this.filename)).append('\"');
            } else {
                sb.append("; filename=\"");
                sb.append(encodeQuotedPrintableFilename(this.filename, this.charset)).append('\"');
                sb.append("; filename*=");
                sb.append(encodeRfc5987Filename(this.filename, this.charset));
            }
        }
        if (this.size != null) {
            sb.append("; size=");
            sb.append(this.size);
        }
        if (this.creationDate != null) {
            sb.append("; creation-date=\"");
            sb.append(RFC_1123_DATE_TIME.format(this.creationDate));
            sb.append('\"');
        }
        if (this.modificationDate != null) {
            sb.append("; modification-date=\"");
            sb.append(RFC_1123_DATE_TIME.format(this.modificationDate));
            sb.append('\"');
        }
        if (this.readDate != null) {
            sb.append("; read-date=\"");
            sb.append(RFC_1123_DATE_TIME.format(this.readDate));
            sb.append('\"');
        }
        return sb.toString();
    }


    /**
     * Return a builder for a {@code ContentDisposition} of type {@literal "attachment"}.
     *
     * @return TODO
     */
    public static Builder attachment() {
        return builder("attachment");
    }

    /**
     * Return a builder for a {@code ContentDisposition} of type {@literal "form-data"}.
     *
     * @return TODO
     */
    public static Builder formData() {
        return builder("form-data");
    }

    /**
     * Return a builder for a {@code ContentDisposition} of type {@literal "inline"}.
     *
     * @return TODO
     */
    public static Builder inline() {
        return builder("inline");
    }

    /**
     * Return a builder for a {@code ContentDisposition}.
     *
     * @param type the disposition type like for example {@literal inline},
     *             {@literal attachment}, or {@literal form-data}
     * @return the builder
     */
    public static Builder builder(String type) {
        return new BuilderImpl(type);
    }

    /**
     * Return an empty content disposition.
     *
     * @return TODO
     */
    public static ContentDisposition empty() {
        return new ContentDisposition("", null, null, null, null, null, null, null);
    }

    /**
     * Parse a {@literal Content-Disposition} header value as defined in RFC 2183.
     *
     * @param contentDisposition the {@literal Content-Disposition} header value
     * @return the parsed content disposition
     * @see #toString()
     */
    public static ContentDisposition parse(String contentDisposition) {
        List<String> parts = tokenize(contentDisposition);
        String type = parts.get(0);
        String name = null;
        String filename = null;
        Charset charset = null;
        Long size = null;
        ZonedDateTime creationDate = null;
        ZonedDateTime modificationDate = null;
        ZonedDateTime readDate = null;
        for (int i = 1; i < parts.size(); i++) {
            String part = parts.get(i);
            int eqIndex = part.indexOf('=');
            if (eqIndex != -1) {
                String attribute = part.substring(0, eqIndex).toLowerCase(Locale.ROOT);
                String value = (part.startsWith("\"", eqIndex + 1) && part.endsWith("\"") ?
                        part.substring(eqIndex + 2, part.length() - 1) :
                        part.substring(eqIndex + 1));
                if (attribute.equals("name")) {
                    name = value;
                } else if (attribute.equals("filename*")) {
                    int idx1 = value.indexOf('\'');
                    int idx2 = value.indexOf('\'', idx1 + 1);
                    if (idx1 != -1 && idx2 != -1) {
                        charset = Charset.forName(value.substring(0, idx1).trim());
                        AssertTools.isTrue(UTF_8.equals(charset) || ISO_8859_1.equals(charset),
                                "Charset must be UTF-8 or ISO-8859-1");
                        filename = decodeRfc5987Filename(value.substring(idx2 + 1), charset);
                    } else {
                        // US ASCII
                        filename = decodeRfc5987Filename(value, StandardCharsets.US_ASCII);
                    }
                } else if (attribute.equals("filename") && (filename == null)) {
                    if (value.startsWith("=?")) {
                        Matcher matcher = BASE64_ENCODED_PATTERN.matcher(value);
                        if (matcher.find()) {
                            Base64.Decoder decoder = Base64.getDecoder();
                            StringBuilder builder = new StringBuilder();
                            do {
                                charset = Charset.forName(matcher.group(1));
                                byte[] decoded = decoder.decode(matcher.group(2));
                                builder.append(new String(decoded, charset));
                            }
                            while (matcher.find());

                            filename = builder.toString();
                        } else {
                            matcher = QUOTED_PRINTABLE_ENCODED_PATTERN.matcher(value);
                            if (matcher.find()) {
                                StringBuilder builder = new StringBuilder();
                                do {
                                    charset = Charset.forName(matcher.group(1));
                                    String decoded = decodeQuotedPrintableFilename(matcher.group(2), charset);
                                    builder.append(decoded);
                                }
                                while (matcher.find());

                                filename = builder.toString();
                            } else {
                                filename = value;
                            }
                        }
                    } else if (value.indexOf('\\') != -1) {
                        filename = decodeQuotedPairs(value);
                    } else {
                        filename = value;
                    }
                } else if (attribute.equals("size")) {
                    size = Long.parseLong(value);
                } else if (attribute.equals("creation-date")) {
                    try {
                        creationDate = ZonedDateTime.parse(value, RFC_1123_DATE_TIME);
                    } catch (DateTimeParseException ex) {
                        // ignore
                    }
                } else if (attribute.equals("modification-date")) {
                    try {
                        modificationDate = ZonedDateTime.parse(value, RFC_1123_DATE_TIME);
                    } catch (DateTimeParseException ex) {
                        // ignore
                    }
                } else if (attribute.equals("read-date")) {
                    try {
                        readDate = ZonedDateTime.parse(value, RFC_1123_DATE_TIME);
                    } catch (DateTimeParseException ex) {
                        // ignore
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid content disposition format");
            }
        }
        return new ContentDisposition(type, name, filename, charset, size, creationDate, modificationDate, readDate);
    }

    private static List<String> tokenize(String headerValue) {
        int index = headerValue.indexOf(';');
        String type = (index >= 0 ? headerValue.substring(0, index) : headerValue).trim();
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Content-Disposition header must not be empty");
        }
        List<String> parts = new ArrayList<>();
        parts.add(type);
        if (index >= 0) {
            do {
                int nextIndex = index + 1;
                boolean quoted = false;
                boolean escaped = false;
                while (nextIndex < headerValue.length()) {
                    char ch = headerValue.charAt(nextIndex);
                    if (ch == ';') {
                        if (!quoted) {
                            break;
                        }
                    } else if (!escaped && ch == '"') {
                        quoted = !quoted;
                    }
                    escaped = (!escaped && ch == '\\');
                    nextIndex++;
                }
                String part = headerValue.substring(index + 1, nextIndex).trim();
                if (!part.isEmpty()) {
                    parts.add(part);
                }
                index = nextIndex;
            }
            while (index < headerValue.length());
        }
        return parts;
    }

    /**
     * Decode the given header field param as described in RFC 5987.
     * <p>Only the US-ASCII, UTF-8 and ISO-8859-1 charsets are supported.
     *
     * @param filename the filename
     * @param charset  the charset for the filename
     * @return the encoded header field param
     * @see <a href="https://tools.ietf.org/html/rfc5987">RFC 5987</a>
     */
    private static String decodeRfc5987Filename(String filename, Charset charset) {
        AssertTools.notNull(filename, "'filename' must not be null");
        AssertTools.notNull(charset, "'charset' must not be null");

        byte[] value = filename.getBytes(charset);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int index = 0;
        while (index < value.length) {
            byte b = value[index];
            if (isRFC5987AttrChar(b)) {
                baos.write((char) b);
                index++;
            } else if (b == '%' && index < value.length - 2) {
                char[] array = new char[]{(char) value[index + 1], (char) value[index + 2]};
                try {
                    baos.write(Integer.parseInt(String.valueOf(array), 16));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException(INVALID_HEADER_FIELD_PARAMETER_FORMAT, ex);
                }
                index += 3;
            } else {
                throw new IllegalArgumentException(INVALID_HEADER_FIELD_PARAMETER_FORMAT);
            }
        }
        return new String(baos.toByteArray(), charset);
    }

    private static boolean isRFC5987AttrChar(byte c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
                c == '!' || c == '#' || c == '$' || c == '&' || c == '+' || c == '-' ||
                c == '.' || c == '^' || c == '_' || c == '`' || c == '|' || c == '~';
    }

    /**
     * Decode the given header field param as described in RFC 2047.
     *
     * @param filename the filename
     * @param charset  the charset for the filename
     * @return the decoded header field param
     * @see <a href="https://tools.ietf.org/html/rfc2047">RFC 2047</a>
     */
    private static String decodeQuotedPrintableFilename(String filename, Charset charset) {
        AssertTools.notNull(filename, "'filename' must not be null");
        AssertTools.notNull(charset, "'charset' must not be null");

        byte[] value = filename.getBytes(US_ASCII);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int index = 0;
        while (index < value.length) {
            byte b = value[index];
            if (b == '_') { // RFC 2047, section 4.2, rule (2)
                baos.write(' ');
                index++;
            } else if (b == '=' && index < value.length - 2) {
                int i1 = Character.digit((char) value[index + 1], 16);
                int i2 = Character.digit((char) value[index + 2], 16);
                if (i1 == -1 || i2 == -1) {
                    throw new IllegalArgumentException("Not a valid hex sequence: " + filename.substring(index));
                }
                baos.write((i1 << 4) | i2);
                index += 3;
            } else {
                baos.write(b);
                index++;
            }
        }
        return new String(baos.toByteArray(), charset);
    }

    /**
     * Encode the given header field param as described in RFC 2047.
     *
     * @param filename the filename
     * @param charset  the charset for the filename
     * @return the encoded header field param
     * @see <a href="https://tools.ietf.org/html/rfc2047">RFC 2047</a>
     */
    private static String encodeQuotedPrintableFilename(String filename, Charset charset) {
        AssertTools.notNull(filename, "'filename' must not be null");
        AssertTools.notNull(charset, "'charset' must not be null");

        byte[] source = filename.getBytes(charset);
        StringBuilder sb = new StringBuilder(source.length << 1);
        sb.append("=?");
        sb.append(charset.name());
        sb.append("?Q?");
        for (byte b : source) {
            if (b == 32) { // RFC 2047, section 4.2, rule (2)
                sb.append('_');
            } else if (isPrintable(b)) {
                sb.append((char) b);
            } else {
                sb.append('=');
                char ch1 = hexDigit(b >> 4);
                char ch2 = hexDigit(b);
                sb.append(ch1);
                sb.append(ch2);
            }
        }
        sb.append("?=");
        return sb.toString();
    }

    private static boolean isPrintable(byte c) {
        int b = c;
        if (b < 0) {
            b = 256 + b;
        }
        return PRINTABLE.get(b);
    }

    private static String encodeQuotedPairs(String filename) {
        if (filename.indexOf('"') == -1 && filename.indexOf('\\') == -1) {
            return filename;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filename.length(); i++) {
            char c = filename.charAt(i);
            if (c == '"' || c == '\\') {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String decodeQuotedPairs(String filename) {
        StringBuilder sb = new StringBuilder();
        int length = filename.length();
        for (int i = 0; i < length; i++) {
            char c = filename.charAt(i);
            if (filename.charAt(i) == '\\' && i + 1 < length) {
                i++;
                char next = filename.charAt(i);
                if (next != '"' && next != '\\') {
                    sb.append(c);
                }
                sb.append(next);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Encode the given header field param as describe in RFC 5987.
     *
     * @param input   the header field param
     * @param charset the charset of the header field param string,
     *                only the US-ASCII, UTF-8 and ISO-8859-1 charsets are supported
     * @return the encoded header field param
     * @see <a href="https://tools.ietf.org/html/rfc5987">RFC 5987</a>
     */
    private static String encodeRfc5987Filename(String input, Charset charset) {
        AssertTools.notNull(input, "'input' must not be null");
        AssertTools.notNull(charset, "'charset' must not be null");
        AssertTools.isTrue(!StandardCharsets.US_ASCII.equals(charset), "ASCII does not require encoding");
        AssertTools.isTrue(UTF_8.equals(charset) || ISO_8859_1.equals(charset), "Only UTF-8 and ISO-8859-1 are supported");

        byte[] source = input.getBytes(charset);
        StringBuilder sb = new StringBuilder(source.length << 1);
        sb.append(charset.name());
        sb.append("''");
        for (byte b : source) {
            if (isRFC5987AttrChar(b)) {
                sb.append((char) b);
            } else {
                sb.append('%');
                char hex1 = hexDigit(b >> 4);
                char hex2 = hexDigit(b);
                sb.append(hex1);
                sb.append(hex2);
            }
        }
        return sb.toString();
    }

    private static char hexDigit(int b) {
        return Character.toUpperCase(Character.forDigit(b & 0xF, 16));
    }


    /**
     * A mutable builder for {@code ContentDisposition}.
     */
    public interface Builder {

        /**
         * Set the value of the {@literal name} parameter.
         *
         * @param name TODO
         * @return TODO
         */
        Builder name(String name);

        /**
         * Set the value of the {@literal filename} parameter. The given
         * filename will be formatted as quoted-string, as defined in RFC 2616,
         * section 2.2, and any quote characters within the filename value will
         * be escaped with a backslash, for example, {@code "foo\"bar.txt"} becomes
         * {@code "foo\\\"bar.txt"}.
         *
         * @param filename TODO
         * @return TODO
         */
        Builder filename(String filename);

        /**
         * Set the value of the {@code filename} that will be encoded as
         * defined in RFC 5987. Only the US-ASCII, UTF-8, and ISO-8859-1
         * charsets are supported.
         * <p><strong>Note:</strong> Do not use this for a
         * {@code "multipart/form-data"} request since
         * <a href="https://tools.ietf.org/html/rfc7578#section-4.2">RFC 7578, Section 4.2</a>
         * and also RFC 5987 mention it does not apply to multipart requests.
         *
         * @param filename TODO
         * @param charset  TODO
         * @return TODO
         */
        Builder filename(String filename, Charset charset);

        /**
         * Set the value of the {@literal size} parameter.
         *
         * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
         *
         * @param size TODO
         * @return TODO
         */
        Builder size(Long size);

        /**
         * Set the value of the {@literal creation-date} parameter.
         *
         * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
         *
         * @param creationDate TODO
         * @return TODO
         */
        Builder creationDate(ZonedDateTime creationDate);

        /**
         * Set the value of the {@literal modification-date} parameter.
         *
         * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
         *
         * @param modificationDate TODO
         * @return TODO
         */
        Builder modificationDate(ZonedDateTime modificationDate);

        /**
         * Set the value of the {@literal read-date} parameter.
         *
         * <a href="https://tools.ietf.org/html/rfc6266#appendix-B">RFC 6266, Appendix B</a>,
         *
         * @param readDate TODO
         * @return TODO
         */
        Builder readDate(ZonedDateTime readDate);

        /**
         * Build the content disposition.
         *
         * @return TODO
         */
        ContentDisposition build();
    }


    private static class BuilderImpl implements Builder {

        private final String type;

        private String name;

        private String filename;

        private Charset charset;

        private Long size;

        private ZonedDateTime creationDate;

        private ZonedDateTime modificationDate;

        private ZonedDateTime readDate;

        public BuilderImpl(String type) {
            AssertTools.isTrue(CharSequenceTools.isBlank(type), "'type' must not be not empty");
            this.type = type;
        }

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        @Override
        public Builder filename(String filename, Charset charset) {
            this.filename = filename;
            this.charset = charset;
            return this;
        }

        @Override
        @SuppressWarnings("deprecation")
        public Builder size(Long size) {
            this.size = size;
            return this;
        }

        @Override
        @SuppressWarnings("deprecation")
        public Builder creationDate(ZonedDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        @Override
        @SuppressWarnings("deprecation")
        public Builder modificationDate(ZonedDateTime modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        @Override
        @SuppressWarnings("deprecation")
        public Builder readDate(ZonedDateTime readDate) {
            this.readDate = readDate;
            return this;
        }

        @Override
        public ContentDisposition build() {
            return new ContentDisposition(this.type, this.name, this.filename, this.charset,
                    this.size, this.creationDate, this.modificationDate, this.readDate);
        }
    }

}
