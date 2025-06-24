package cn.bytengine.d.utils;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.PathTools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * {@link Resource} implementation for {@code java.net.URL} locators.
 * Supports resolution as a {@code URL} and also as a {@code File} in
 * case of the {@code "file:"} protocol.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class UrlResource extends AbstractFileResolvingResource {

    private static final String AUTHORIZATION = "Authorization";


    /**
     * Original URI, if available; used for URI and File access.
     */
    private final URI uri;

    /**
     * Original URL, used for actual access.
     */
    private final URL url;

    /**
     * Cleaned URL String (with normalized path), used for comparisons.
     */
    private volatile String cleanedUrl;


    /**
     * Create a new {@code UrlResource} based on the given URL object.
     *
     * @param url a URL
     * @see #UrlResource(URI)
     * @see #UrlResource(String)
     */
    public UrlResource(URL url) {
        AssertTools.notNull(url, "URL must not be null");
        this.uri = null;
        this.url = url;
    }

    /**
     * Create a new {@code UrlResource} based on the given URI object.
     *
     * @param uri a URI
     * @throws MalformedURLException if the given URL path is not valid
     */
    public UrlResource(URI uri) throws MalformedURLException {
        AssertTools.notNull(uri, "URI must not be null");
        this.uri = uri;
        this.url = uri.toURL();
    }

    /**
     * Create a new {@code UrlResource} based on a URI path.
     * <p>Note: The given path needs to be pre-encoded if necessary.
     *
     * @param path a URI path
     * @throws MalformedURLException if the given URI path is not valid
     * @see ResourceTools#toURI(String)
     */
    public UrlResource(String path) throws MalformedURLException {
        AssertTools.notNull(path, "Path must not be null");
        String cleanedPath = PathTools.cleanPath(path);
        URI uri;
        URL url;

        try {
            // Prefer URI construction with toURL conversion (as of 6.1)
            uri = ResourceTools.toURI(cleanedPath);
            url = uri.toURL();
        } catch (URISyntaxException | IllegalArgumentException ex) {
            uri = null;
            url = ResourceTools.toURL(path);
        }

        this.uri = uri;
        this.url = url;
        this.cleanedUrl = cleanedPath;
    }

    /**
     * Create a new {@code UrlResource} based on a URI specification.
     * <p>The given parts will automatically get encoded if necessary.
     *
     * @param protocol the URL protocol to use (for example, "jar" or "file" - without colon);
     *                 also known as "scheme"
     * @param location the location (for example, the file path within that protocol);
     *                 also known as "scheme-specific part"
     * @throws MalformedURLException if the given URL specification is not valid
     * @see java.net.URI#URI(String, String, String)
     */
    public UrlResource(String protocol, String location) throws MalformedURLException {
        this(protocol, location, null);
    }

    /**
     * Create a new {@code UrlResource} based on a URI specification.
     * <p>The given parts will automatically get encoded if necessary.
     *
     * @param protocol the URL protocol to use (for example, "jar" or "file" - without colon);
     *                 also known as "scheme"
     * @param location the location (for example, the file path within that protocol);
     *                 also known as "scheme-specific part"
     * @param fragment the fragment within that location (for example, anchor on an HTML page,
     *                 as following after a "#" separator)
     * @throws MalformedURLException if the given URL specification is not valid
     * @see java.net.URI#URI(String, String, String)
     */
    public UrlResource(String protocol, String location, String fragment) throws MalformedURLException {
        try {
            this.uri = new URI(protocol, location, fragment);
            this.url = this.uri.toURL();
        } catch (URISyntaxException ex) {
            MalformedURLException exToThrow = new MalformedURLException(ex.getMessage());
            exToThrow.initCause(ex);
            throw exToThrow;
        }
    }


    /**
     * Create a new {@code UrlResource} from the given {@link URI}.
     * <p>This factory method is a convenience for {@link #UrlResource(URI)} that
     * catches any {@link MalformedURLException} and rethrows it wrapped in an
     * {@link UncheckedIOException}; suitable for use in {@link java.util.stream.Stream}
     * and {@link java.util.Optional} APIs or other scenarios when a checked
     * {@link IOException} is undesirable.
     *
     * @param uri a URI
     * @return TODO
     * @throws UncheckedIOException if the given URL path is not valid
     * @see #UrlResource(URI)
     */
    public static UrlResource from(URI uri) throws UncheckedIOException {
        try {
            return new UrlResource(uri);
        } catch (MalformedURLException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * Create a new {@code UrlResource} from the given URL path.
     * <p>This factory method is a convenience for {@link #UrlResource(String)}
     * that catches any {@link MalformedURLException} and rethrows it wrapped in an
     * {@link UncheckedIOException}; suitable for use in {@link java.util.stream.Stream}
     * and {@link java.util.Optional} APIs or other scenarios when a checked
     * {@link IOException} is undesirable.
     *
     * @param path a URL path
     * @return TODO
     * @throws UncheckedIOException if the given URL path is not valid
     * @see #UrlResource(String)
     */
    public static UrlResource from(String path) throws UncheckedIOException {
        try {
            return new UrlResource(path);
        } catch (MalformedURLException ex) {
            throw new UncheckedIOException(ex);
        }
    }


    /**
     * Lazily determine a cleaned URL for the given original URL.
     */
    private String getCleanedUrl() {
        String cleanedUrl = this.cleanedUrl;
        if (cleanedUrl != null) {
            return cleanedUrl;
        }
        String originalPath = (this.uri != null ? this.uri : this.url).toString();
        cleanedUrl = PathTools.cleanPath(originalPath);
        this.cleanedUrl = cleanedUrl;
        return cleanedUrl;
    }


    /**
     * This implementation opens an InputStream for the given URL.
     * <p>It sets the {@code useCaches} flag to {@code false},
     * mainly to avoid jar file locking on Windows.
     *
     * @see java.net.URL#openConnection()
     * @see java.net.URLConnection#setUseCaches(boolean)
     * @see java.net.URLConnection#getInputStream()
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        customizeConnection(con);
        try {
            return con.getInputStream();
        } catch (IOException ex) {
            // Close the HTTP connection (if applicable).
            if (con instanceof HttpURLConnection) {
                HttpURLConnection httpCon = (HttpURLConnection) con;
                httpCon.disconnect();
            }
            throw ex;
        }
    }

    @Override
    protected void customizeConnection(URLConnection con) throws IOException {
        super.customizeConnection(con);
        String userInfo = this.url.getUserInfo();
        if (userInfo != null) {
            String encodedCredentials = Base64.getUrlEncoder().encodeToString(userInfo.getBytes());
            con.setRequestProperty(AUTHORIZATION, "Basic " + encodedCredentials);
        }
    }

    /**
     * This implementation returns the underlying URL reference.
     */
    @Override
    public URL getURL() {
        return this.url;
    }

    /**
     * This implementation returns the underlying URI directly,
     * if possible.
     */
    @Override
    public URI getURI() throws IOException {
        if (this.uri != null) {
            return this.uri;
        } else {
            return super.getURI();
        }
    }

    @Override
    public boolean isFile() {
        if (this.uri != null) {
            return super.isFile(this.uri);
        } else {
            return super.isFile();
        }
    }

    /**
     * This implementation returns a File reference for the underlying URL/URI,
     * provided that it refers to a file in the file system.
     *
     * @see ResourceTools#getFile(java.net.URL, String)
     */
    @Override
    public File getFile() throws IOException {
        if (this.uri != null) {
            return super.getFile(this.uri);
        } else {
            return super.getFile();
        }
    }

    /**
     * This implementation creates a {@code UrlResource}, delegating to
     * {@link #createRelativeURL(String)} for adapting the relative path.
     *
     * @see #createRelativeURL(String)
     */
    @Override
    public Resource createRelative(String relativePath) throws MalformedURLException {
        return new UrlResource(createRelativeURL(relativePath));
    }

    /**
     * This delegate creates a {@code java.net.URL}, applying the given path
     * relative to the path of the underlying URL of this resource descriptor.
     * <p>A leading slash will get dropped; a "#" symbol will get encoded.
     * Note that this method effectively cleans the combined path as of 6.1.
     *
     * @param relativePath TODO
     * @return TODO
     * @throws MalformedURLException TODO
     * @see #createRelative(String)
     * @see ResourceTools#toRelativeURL(URL, String)
     */
    protected URL createRelativeURL(String relativePath) throws MalformedURLException {
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        return ResourceTools.toRelativeURL(this.url, relativePath);
    }

    /**
     * This implementation returns the URL-decoded name of the file that this URL
     * refers to.
     *
     * @see java.net.URL#getPath()
     * @see java.net.URLDecoder#decode(String, String)
     */
    @Override
    public String getFilename() {
        if (this.uri != null) {
            String path = this.uri.getPath();
            if (path != null) {
                // Prefer URI path: decoded and has standard separators
                return PathTools.getFilename(this.uri.getPath());
            }
        }
        // Otherwise, process URL path
        String filename = PathTools.getFilename(PathTools.cleanPath(this.url.getPath()));
        return (filename != null ? decode(filename, StandardCharsets.UTF_8) : null);
    }

    /**
     * This implementation returns a description that includes the URL.
     */
    @Override
    public String getDescription() {
        return "URL [" + (this.uri != null ? this.uri : this.url) + "]";
    }


    /**
     * This implementation compares the underlying URL references.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof UrlResource) {
            UrlResource that = (UrlResource) other;
            return getCleanedUrl().equals(that.getCleanedUrl());
        }
        return false;
    }

    /**
     * This implementation returns the hash code of the underlying URL reference.
     */
    @Override
    public int hashCode() {
        return getCleanedUrl().hashCode();
    }

    /**
     * Decodes an {@code application/x-www-form-urlencoded} string using
     * a specific {@linkplain Charset Charset}.
     * The supplied charset is used to determine
     * what characters are represented by any consecutive sequences of the
     * form "<i>{@code %xy}</i>".
     * <p>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilities.</em>
     *
     * @param s       the {@code String} to decode
     * @param charset the given charset
     * @return the newly decoded {@code String}
     * @throws NullPointerException     if {@code s} or {@code charset} is {@code null}
     * @throws IllegalArgumentException if the implementation encounters illegal
     *                                  characters
     * @implNote This implementation will throw an {@link java.lang.IllegalArgumentException}
     * when illegal strings are encountered.
     * @spec https://www.w3.org/TR/html4 HTML 4.01 Specification
     */
    private static String decode(String s, Charset charset) {
        Objects.requireNonNull(charset, "Charset");
        boolean needToChange = false;
        int numChars = s.length();
        StringBuilder sb = new StringBuilder(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    i++;
                    needToChange = true;
                    break;
                case '%':
                    /*
                     * Starting with this instance of %, process all
                     * consecutive substrings of the form %xy. Each
                     * substring %xy will yield a byte. Convert all
                     * consecutive  bytes obtained this way to whatever
                     * character(s) they represent in the provided
                     * encoding.
                     */

                    try {

                        // (numChars-i)/3 is an upper bound for the number
                        // of remaining bytes
                        if (bytes == null)
                            bytes = new byte[(numChars - i) / 3];
                        int pos = 0;

                        while (((i + 2) < numChars) &&
                                (c == '%')) {
                            int v = parseInt(s, i + 1, i + 3, 16);
                            if (v < 0)
                                throw new IllegalArgumentException(
                                        "URLDecoder: Illegal hex characters in escape "
                                                + "(%) pattern - negative value");
                            bytes[pos++] = (byte) v;
                            i += 3;
                            if (i < numChars)
                                c = s.charAt(i);
                        }

                        // A trailing, incomplete byte encoding such as
                        // "%x" will cause an exception to be thrown

                        if ((i < numChars) && (c == '%'))
                            throw new IllegalArgumentException(
                                    "URLDecoder: Incomplete trailing escape (%) pattern");

                        sb.append(new String(bytes, 0, pos, charset));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - "
                                        + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
                    sb.append(c);
                    i++;
                    break;
            }
        }

        return (needToChange ? sb.toString() : s);
    }

    /**
     * Parses the {@link CharSequence} argument as a signed {@code int} in the
     * specified {@code radix}, beginning at the specified {@code beginIndex}
     * and extending to {@code endIndex - 1}.
     *
     * <p>The method does not take steps to guard against the
     * {@code CharSequence} being mutated while parsing.
     *
     * @param s          the {@code CharSequence} containing the {@code int}
     *                   representation to be parsed
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @param radix      the radix to be used while parsing {@code s}.
     * @return the signed {@code int} represented by the subsequence in
     * the specified radix.
     * @throws NullPointerException      if {@code s} is null.
     * @throws IndexOutOfBoundsException if {@code beginIndex} is
     *                                   negative, or if {@code beginIndex} is greater than
     *                                   {@code endIndex} or if {@code endIndex} is greater than
     *                                   {@code s.length()}.
     * @throws NumberFormatException     if the {@code CharSequence} does not
     *                                   contain a parsable {@code int} in the specified
     *                                   {@code radix}, or if {@code radix} is either smaller than
     *                                   {@link java.lang.Character#MIN_RADIX} or larger than
     *                                   {@link java.lang.Character#MAX_RADIX}.
     */
    private static int parseInt(CharSequence s, int beginIndex, int endIndex, int radix)
            throws NumberFormatException {
        Objects.requireNonNull(s);
        checkFromToIndex(beginIndex, endIndex, s.length(), null);

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " greater than Character.MAX_RADIX");
        }

        boolean negative = false;
        int i = beginIndex;
        int limit = -Integer.MAX_VALUE;

        if (i < endIndex) {
            char firstChar = s.charAt(i);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+') {
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
                i++;
                if (i == endIndex) { // Cannot have lone "+" or "-"
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
            }
            int multmin = limit / radix;
            int result = 0;
            while (i < endIndex) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                int digit = Character.digit(s.charAt(i), radix);
                if (digit < 0 || result < multmin) {
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
                result *= radix;
                if (result < limit + digit) {
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
                i++;
                result -= digit;
            }
            return negative ? result : -result;
        } else {
            throw new NumberFormatException("For input string: \"" + "" + "\"" +
                    (radix == 10 ?
                            "" :
                            " under radix " + radix));
        }
    }

    /**
     * Checks if the sub-range from {@code fromIndex} (inclusive) to
     * {@code toIndex} (exclusive) is within the bounds of range from {@code 0}
     * (inclusive) to {@code length} (exclusive).
     *
     * <p>The sub-range is defined to be out of bounds if any of the following
     * inequalities is true:
     * <ul>
     *  <li>{@code fromIndex < 0}</li>
     *  <li>{@code fromIndex > toIndex}</li>
     *  <li>{@code toIndex > length}</li>
     *  <li>{@code length < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * <p>If the sub-range is out of bounds, then a runtime exception is
     * thrown that is the result of applying the following arguments to the
     * exception formatter: the name of this method, {@code checkFromToIndex};
     * and an unmodifiable list of integers whose values are, in order, the
     * out-of-bounds arguments {@code fromIndex}, {@code toIndex}, and {@code length}.
     *
     * @param <X>       the type of runtime exception to throw if the arguments are
     *                  out of bounds
     * @param fromIndex the lower-bound (inclusive) of the sub-range
     * @param toIndex   the upper-bound (exclusive) of the sub-range
     * @param length    the upper-bound (exclusive) the range
     * @param oobef     the exception formatter that when applied with this
     *                  method name and out-of-bounds arguments returns a runtime
     *                  exception.  If {@code null} or returns {@code null} then, it is as
     *                  if an exception formatter produced from an invocation of
     *                  {@code outOfBoundsExceptionFormatter(IndexOutOfBounds::new)} is used
     *                  instead (though it may be more efficient).
     *                  Exceptions thrown by the formatter are relayed to the caller.
     * @return {@code fromIndex} if the sub-range within bounds of the range
     * @throws X                         if the sub-range is out of bounds and the exception factory
     *                                   function is non-{@code null}
     * @throws IndexOutOfBoundsException if the sub-range is out of bounds and
     *                                   the exception factory function is {@code null}
     */
    public static <X extends RuntimeException>
    int checkFromToIndex(int fromIndex, int toIndex, int length,
                         BiFunction<String, List<Number>, X> oobef) {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > length)
            throw new IndexOutOfBoundsException();
        return fromIndex;
    }
}
