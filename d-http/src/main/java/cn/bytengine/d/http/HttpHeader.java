package cn.bytengine.d.http;

import cn.bytengine.d.collection.LinkedCaseInsensitiveMap;
import cn.bytengine.d.collection.LinkedMultiValueMap;
import cn.bytengine.d.collection.MultiValueMap;
import cn.bytengine.d.lang.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * A data structure representing HTTP request or response headers, mapping String header names
 * to a list of String values, also offering accessors for common application-level data types.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class HttpHeader implements MultiValueMap<String, String> {
    /**
     * The HTTP {@code Accept} header field name.
     */
    public static final String ACCEPT = "Accept";
    /**
     * The HTTP {@code Accept-Charset} header field name.
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    /**
     * The HTTP {@code Accept-Encoding} header field name.
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    /**
     * The HTTP {@code Accept-Language} header field name.
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    /**
     * The HTTP {@code Accept-Patch} header field name.
     */
    public static final String ACCEPT_PATCH = "Accept-Patch";
    /**
     * The HTTP {@code Accept-Ranges} header field name.
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";
    /**
     * The CORS {@code Access-Control-Allow-Credentials} response header field name.
     */
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    /**
     * The CORS {@code Access-Control-Allow-Headers} response header field name.
     */
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    /**
     * The CORS {@code Access-Control-Allow-Methods} response header field name.
     */
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    /**
     * The CORS {@code Access-Control-Allow-Origin} response header field name.
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    /**
     * The CORS {@code Access-Control-Expose-Headers} response header field name.
     */
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    /**
     * The CORS {@code Access-Control-Max-Age} response header field name.
     */
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    /**
     * The CORS {@code Access-Control-Request-Headers} request header field name.
     */
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    /**
     * The CORS {@code Access-Control-Request-Method} request header field name.
     */
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    /**
     * The HTTP {@code Age} header field name.
     */
    public static final String AGE = "Age";
    /**
     * The HTTP {@code Allow} header field name.
     */
    public static final String ALLOW = "Allow";
    /**
     * The HTTP {@code Authorization} header field name.
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * The HTTP {@code Cache-Control} header field name.
     */
    public static final String CACHE_CONTROL = "Cache-Control";
    /**
     * The HTTP {@code Connection} header field name.
     */
    public static final String CONNECTION = "Connection";
    /**
     * The HTTP {@code Content-Encoding} header field name.
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";
    /**
     * The HTTP {@code Content-Disposition} header field name.
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    /**
     * The HTTP {@code Content-Language} header field name.
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";
    /**
     * The HTTP {@code Content-Length} header field name.
     */
    public static final String CONTENT_LENGTH = "Content-Length";
    /**
     * The HTTP {@code Content-Location} header field name.
     */
    public static final String CONTENT_LOCATION = "Content-Location";
    /**
     * The HTTP {@code Content-Range} header field name.
     */
    public static final String CONTENT_RANGE = "Content-Range";
    /**
     * The HTTP {@code Content-Type} header field name.
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * The HTTP {@code Cookie} header field name.
     */
    public static final String COOKIE = "Cookie";
    /**
     * The HTTP {@code Date} header field name.
     */
    public static final String DATE = "Date";
    /**
     * The HTTP {@code ETag} header field name.
     */
    public static final String ETAG = "ETag";
    /**
     * The HTTP {@code Expect} header field name.
     */
    public static final String EXPECT = "Expect";
    /**
     * The HTTP {@code Expires} header field name.
     */
    public static final String EXPIRES = "Expires";
    /**
     * The HTTP {@code From} header field name.
     */
    public static final String FROM = "From";
    /**
     * The HTTP {@code Host} header field name.
     */
    public static final String HOST = "Host";
    /**
     * The HTTP {@code If-Match} header field name.
     */
    public static final String IF_MATCH = "If-Match";
    /**
     * The HTTP {@code If-Modified-Since} header field name.
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    /**
     * The HTTP {@code If-None-Match} header field name.
     */
    public static final String IF_NONE_MATCH = "If-None-Match";
    /**
     * The HTTP {@code If-Range} header field name.
     */
    public static final String IF_RANGE = "If-Range";
    /**
     * The HTTP {@code If-Unmodified-Since} header field name.
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    /**
     * The HTTP {@code Last-Modified} header field name.
     */
    public static final String LAST_MODIFIED = "Last-Modified";
    /**
     * The HTTP {@code Link} header field name.
     */
    public static final String LINK = "Link";
    /**
     * The HTTP {@code Location} header field name.
     */
    public static final String LOCATION = "Location";
    /**
     * The HTTP {@code Max-Forwards} header field name.
     */
    public static final String MAX_FORWARDS = "Max-Forwards";
    /**
     * The HTTP {@code Origin} header field name.
     */
    public static final String ORIGIN = "Origin";
    /**
     * The HTTP {@code Pragma} header field name.
     */
    public static final String PRAGMA = "Pragma";
    /**
     * The HTTP {@code Proxy-Authenticate} header field name.
     */
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
    /**
     * The HTTP {@code Proxy-Authorization} header field name.
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
    /**
     * The HTTP {@code Range} header field name.
     */
    public static final String RANGE = "Range";
    /**
     * The HTTP {@code Referer} header field name.
     */
    public static final String REFERER = "Referer";
    /**
     * The HTTP {@code Retry-After} header field name.
     */
    public static final String RETRY_AFTER = "Retry-After";
    /**
     * The HTTP {@code Server} header field name.
     */
    public static final String SERVER = "Server";
    /**
     * The HTTP {@code Set-Cookie} header field name.
     */
    public static final String SET_COOKIE = "Set-Cookie";
    /**
     * The HTTP {@code Set-Cookie2} header field name.
     */
    public static final String SET_COOKIE2 = "Set-Cookie2";
    /**
     * The HTTP {@code TE} header field name.
     */
    public static final String TE = "TE";
    /**
     * The HTTP {@code Trailer} header field name.
     */
    public static final String TRAILER = "Trailer";
    /**
     * The HTTP {@code Transfer-Encoding} header field name.
     */
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    /**
     * The HTTP {@code Upgrade} header field name.
     */
    public static final String UPGRADE = "Upgrade";
    /**
     * The HTTP {@code User-Agent} header field name.
     */
    public static final String USER_AGENT = "User-Agent";
    /**
     * The HTTP {@code Vary} header field name.
     */
    public static final String VARY = "Vary";
    /**
     * The HTTP {@code Via} header field name.
     */
    public static final String VIA = "Via";
    /**
     * The HTTP {@code Warning} header field name.
     */
    public static final String WARNING = "Warning";
    /**
     * The HTTP {@code WWW-Authenticate} header field name.
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    /**
     * Date formats with time zone as specified in the HTTP RFC to use for formatting.
     */
    private static final FastDateFormat DATE_FORMATTER = DatePatterns.HTTP_US_GMT_RFC_IMF_FIX_DATE_FORMAT;

    private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols(Locale.ROOT);

    private static final ZoneId GMT = ZoneId.of("GMT");
    /**
     * Date formats with time zone as specified in the HTTP RFC to use for parsing.
     */
    private static final FastDateFormat[] DATE_PARSERS = new FastDateFormat[]{
            DatePatterns.HTTP_GMT_RFC_1123_DATE_FORMAT,
            DatePatterns.HTTP_US_RFC_850_DATE_FORMAT,
            DatePatterns.HTTP_US_GMT_ANSI_C_ASC_TIME_DATE_FORMAT
    };

    final MultiValueMap<String, String> headers;

    /**
     * 构造器
     */
    public HttpHeader() {
        this(new LinkedMultiValueMap<>());
    }

    /**
     * 复制HttpHeader
     *
     * @param header 被复制Header集合
     */
    public HttpHeader(HttpHeader header) {
        this(new LinkedMultiValueMap<>(header.headers));
    }

    /**
     * 给定数据源创建Header
     *
     * @param headers 数据源
     */
    public HttpHeader(MultiValueMap<String, String> headers) {
        this.headers = headers;
    }


    /**
     * Get the list of header values for the given header name, if any.
     *
     * @param headerName the header name
     * @return the list of header values, or an empty list
     */
    public List<String> getOrEmpty(Object headerName) {
        List<String> values = get(headerName);
        return (values != null ? values : Collections.emptyList());
    }

    /**
     * Set the list of acceptable {@linkplain MediaType media types},
     * as specified by the {@code Accept} header.
     *
     * @param acceptableMediaTypes TODO
     */
    public void setAccept(List<MediaType> acceptableMediaTypes) {
        set(ACCEPT, MediaType.toString(acceptableMediaTypes));
    }

    /**
     * Return the list of acceptable {@linkplain MediaType media types},
     * as specified by the {@code Accept} header.
     * <p>Returns an empty list when the acceptable media types are unspecified.
     *
     * @return TODO
     */
    public List<MediaType> getAccept() {
        return MediaType.parseMediaTypes(get(ACCEPT));
    }

    /**
     * Set the acceptable language ranges, as specified by the
     * {@literal Accept-Language} header.
     *
     * @param languages TODO
     */
    public void setAcceptLanguage(List<Locale.LanguageRange> languages) {
        AssertTools.notNull(languages, "LanguageRange List must not be null");
        DecimalFormat decimal = new DecimalFormat("0.0", DECIMAL_FORMAT_SYMBOLS);
        List<String> values = languages.stream()
                .map(range ->
                        range.getWeight() == Locale.LanguageRange.MAX_WEIGHT ?
                                range.getRange() :
                                range.getRange() + ";q=" + decimal.format(range.getWeight()))
                .collect(Collectors.toList());
        set(ACCEPT_LANGUAGE, toCommaDelimitedString(values));
    }

    /**
     * Return the language ranges from the {@literal "Accept-Language"} header.
     * <p>If you only need sorted, preferred locales only use
     * {@link #getAcceptLanguageAsLocales()} or if you need to filter based on
     * a list of supported locales you can pass the returned list to
     * {@link Locale#filter(List, Collection)}.
     *
     * @return TODO
     * @throws IllegalArgumentException if the value cannot be converted to a language range
     */
    public List<Locale.LanguageRange> getAcceptLanguage() {
        String value = getFirst(ACCEPT_LANGUAGE);
        if (CharSequenceTools.isNotEmpty(value)) {
            try {
                return Locale.LanguageRange.parse(value);
            } catch (IllegalArgumentException ignored) {
                String[] tokens = CharSequenceTools.tokenizeToStringArray(value, ",");
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = CharSequenceTools.trimTrailingCharacter(tokens[i], ';');
                }
                value = ArrayTools.arrayToCommaDelimitedString(tokens);
                return Locale.LanguageRange.parse(value);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Variant of {@link #setAcceptLanguage(List)} using {@link Locale}'s.
     *
     * @param locales TODO
     */
    public void setAcceptLanguageAsLocales(List<Locale> locales) {
        setAcceptLanguage(locales.stream()
                .map(locale -> new Locale.LanguageRange(locale.toLanguageTag()))
                .collect(Collectors.toList()));
    }

    /**
     * A variant of {@link #getAcceptLanguage()} that converts each
     * {@link java.util.Locale.LanguageRange} to a {@link Locale}.
     *
     * @return the locales or an empty list
     * @throws IllegalArgumentException if the value cannot be converted to a locale
     */
    public List<Locale> getAcceptLanguageAsLocales() {
        List<Locale.LanguageRange> ranges = getAcceptLanguage();
        if (ranges.isEmpty()) {
            return Collections.emptyList();
        }

        List<Locale> locales = new ArrayList<>(ranges.size());
        for (Locale.LanguageRange range : ranges) {
            if (!range.getRange().startsWith("*")) {
                locales.add(Locale.forLanguageTag(range.getRange()));
            }
        }
        return locales;
    }

    /**
     * Set the list of acceptable {@linkplain MediaType media types} for
     * {@code PATCH} methods, as specified by the {@code Accept-Patch} header.
     *
     * @param mediaTypes TODO
     */
    public void setAcceptPatch(List<MediaType> mediaTypes) {
        set(ACCEPT_PATCH, MediaType.toString(mediaTypes));
    }


    /**
     * Return the list of acceptable {@linkplain MediaType media types} for
     * {@code PATCH} methods, as specified by the {@code Accept-Patch} header.
     * <p>Returns an empty list when the acceptable media types are unspecified.
     *
     * @return TODO
     */
    public List<MediaType> getAcceptPatch() {
        return MediaType.parseMediaTypes(get(ACCEPT_PATCH));
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Credentials} response header.
     *
     * @param allowCredentials TODO
     */
    public void setAccessControlAllowCredentials(boolean allowCredentials) {
        set(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.toString(allowCredentials));
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Credentials} response header.
     *
     * @return TODO
     */
    public boolean getAccessControlAllowCredentials() {
        return Boolean.parseBoolean(getFirst(ACCESS_CONTROL_ALLOW_CREDENTIALS));
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Headers} response header.
     *
     * @param allowedHeaders TODO
     */
    public void setAccessControlAllowHeaders(List<String> allowedHeaders) {
        set(ACCESS_CONTROL_ALLOW_HEADERS, toCommaDelimitedString(allowedHeaders));
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Headers} response header.
     *
     * @return TODO
     */
    public List<String> getAccessControlAllowHeaders() {
        return getValuesAsList(ACCESS_CONTROL_ALLOW_HEADERS);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Methods} response header.
     *
     * @param allowedMethods TODO
     */
    public void setAccessControlAllowMethods(List<HttpMethod> allowedMethods) {
        set(ACCESS_CONTROL_ALLOW_METHODS, CollectionTools.collectionToDelimitedString(allowedMethods, ","));
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Methods} response header.
     *
     * @return TODO
     */
    public List<HttpMethod> getAccessControlAllowMethods() {
        String value = getFirst(ACCESS_CONTROL_ALLOW_METHODS);
        if (value != null) {
            String[] tokens = CharSequenceTools.tokenizeToStringArray(value, ",");
            List<HttpMethod> result = new ArrayList<>(tokens.length);
            for (String token : tokens) {
                HttpMethod method = HttpMethod.valueOf(token);
                result.add(method);
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Origin} response header.
     *
     * @param allowedOrigin TODO
     */
    public void setAccessControlAllowOrigin(String allowedOrigin) {
        setOrRemove(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigin);
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Origin} response header.
     *
     * @return TODO
     */
    public String getAccessControlAllowOrigin() {
        return getFieldValues(ACCESS_CONTROL_ALLOW_ORIGIN);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Expose-Headers} response header.
     *
     * @param exposedHeaders TODO
     */
    public void setAccessControlExposeHeaders(List<String> exposedHeaders) {
        set(ACCESS_CONTROL_EXPOSE_HEADERS, toCommaDelimitedString(exposedHeaders));
    }

    /**
     * Return the value of the {@code Access-Control-Expose-Headers} response header.
     *
     * @return TODO
     */
    public List<String> getAccessControlExposeHeaders() {
        return getValuesAsList(ACCESS_CONTROL_EXPOSE_HEADERS);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Max-Age} response header.
     *
     * @param maxAge TODO
     */
    public void setAccessControlMaxAge(Duration maxAge) {
        set(ACCESS_CONTROL_MAX_AGE, Long.toString(maxAge.getSeconds()));
    }

    /**
     * Set the (new) value of the {@code Access-Control-Max-Age} response header.
     *
     * @param maxAge TODO
     */
    public void setAccessControlMaxAge(long maxAge) {
        set(ACCESS_CONTROL_MAX_AGE, Long.toString(maxAge));
    }

    /**
     * Return the value of the {@code Access-Control-Max-Age} response header.
     * <p>Returns -1 when the max age is unknown.
     *
     * @return TODO
     */
    public long getAccessControlMaxAge() {
        String value = getFirst(ACCESS_CONTROL_MAX_AGE);
        return (value != null ? Long.parseLong(value) : -1);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Request-Headers} request header.
     *
     * @param requestHeaders TODO
     */
    public void setAccessControlRequestHeaders(List<String> requestHeaders) {
        set(ACCESS_CONTROL_REQUEST_HEADERS, toCommaDelimitedString(requestHeaders));
    }

    /**
     * Return the value of the {@code Access-Control-Request-Headers} request header.
     *
     * @return TODO
     */
    public List<String> getAccessControlRequestHeaders() {
        return getValuesAsList(ACCESS_CONTROL_REQUEST_HEADERS);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Request-Method} request header.
     *
     * @param requestMethod TODO
     */
    public void setAccessControlRequestMethod(HttpMethod requestMethod) {
        setOrRemove(ACCESS_CONTROL_REQUEST_METHOD, (requestMethod != null ? requestMethod.name() : null));
    }

    /**
     * Return the value of the {@code Access-Control-Request-Method} request header.
     *
     * @return TODO
     */
    public HttpMethod getAccessControlRequestMethod() {
        String requestMethod = getFirst(ACCESS_CONTROL_REQUEST_METHOD);
        if (requestMethod != null) {
            return HttpMethod.valueOf(requestMethod);
        } else {
            return null;
        }
    }

    /**
     * Set the list of acceptable {@linkplain Charset charsets},
     * as specified by the {@code Accept-Charset} header.
     *
     * @param acceptableCharsets TODO
     */
    public void setAcceptCharset(List<Charset> acceptableCharsets) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Charset charset : acceptableCharsets) {
            joiner.add(charset.name().toLowerCase(Locale.ROOT));
        }
        set(ACCEPT_CHARSET, joiner.toString());
    }

    /**
     * Return the list of acceptable {@linkplain Charset charsets},
     * as specified by the {@code Accept-Charset} header.
     *
     * @return TODO
     */
    public List<Charset> getAcceptCharset() {
        String value = getFirst(ACCEPT_CHARSET);
        if (value != null) {
            String[] tokens = CharSequenceTools.tokenizeToStringArray(value, ",");
            List<Charset> result = new ArrayList<>(tokens.length);
            for (String token : tokens) {
                int paramIdx = token.indexOf(';');
                String charsetName;
                if (paramIdx == -1) {
                    charsetName = token;
                } else {
                    charsetName = token.substring(0, paramIdx);
                }
                if (!charsetName.equals("*")) {
                    result.add(Charset.forName(charsetName));
                }
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Set the set of allowed {@link HttpMethod HTTP methods},
     * as specified by the {@code Allow} header
     *
     * @param allowedMethods TODO
     */
    public void setAllow(Set<HttpMethod> allowedMethods) {
        set(ALLOW, CollectionTools.collectionToDelimitedString(allowedMethods, ","));
    }

    /**
     * Return the set of allowed {@link HttpMethod HTTP methods},
     * as specified by the {@code Allow} header.
     * <p>Returns an empty set when the allowed methods are unspecified.
     *
     * @return TODO
     */
    public Set<HttpMethod> getAllow() {
        String value = getFirst(ALLOW);
        if (CharSequenceTools.isNotEmpty(value)) {
            String[] tokens = CharSequenceTools.tokenizeToStringArray(value, ",");
            Set<HttpMethod> result = CollectionTools.newLinkedHashSet(tokens.length);
            for (String token : tokens) {
                HttpMethod method = HttpMethod.valueOf(token);
                result.add(method);
            }
            return result;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * Basic Authentication based on the given username and password.
     * <p>Note that this method only supports characters in the
     * {@link StandardCharsets#ISO_8859_1 ISO-8859-1} character set.
     *
     * @param username the username
     * @param password the password
     * @throws IllegalArgumentException if either {@code user} or
     *                                  {@code password} contain characters that cannot be encoded to ISO-8859-1
     * @see #setBasicAuth(String)
     * @see #setBasicAuth(String, String, Charset)
     * @see #encodeBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     */
    public void setBasicAuth(String username, String password) {
        setBasicAuth(username, password, null);
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * Basic Authentication based on the given username and password.
     *
     * @param username the username
     * @param password the password
     * @param charset  the charset to use to convert the credentials into an octet
     *                 sequence. Defaults to {@linkplain StandardCharsets#ISO_8859_1 ISO-8859-1}.
     * @throws IllegalArgumentException if {@code username} or {@code password}
     *                                  contains characters that cannot be encoded to the given charset
     * @see #setBasicAuth(String)
     * @see #setBasicAuth(String, String)
     * @see #encodeBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     */
    public void setBasicAuth(String username, String password, Charset charset) {
        setBasicAuth(encodeBasicAuth(username, password, charset));
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * Basic Authentication based on the given {@linkplain #encodeBasicAuth
     * encoded credentials}.
     * <p>Favor this method over {@link #setBasicAuth(String, String)} and
     * {@link #setBasicAuth(String, String, Charset)} if you wish to cache the
     * encoded credentials.
     *
     * @param encodedCredentials the encoded credentials
     * @throws IllegalArgumentException if supplied credentials string is
     *                                  {@code null} or blank
     * @see #setBasicAuth(String, String)
     * @see #setBasicAuth(String, String, Charset)
     * @see #encodeBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     */
    public void setBasicAuth(String encodedCredentials) {
        AssertTools.isTrue(CharSequenceTools.isBlank(encodedCredentials), "'encodedCredentials' must not be null or blank");
        set(AUTHORIZATION, "Basic " + encodedCredentials);
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * the given Bearer token.
     *
     * @param token the Base64 encoded token
     * @see <a href="https://tools.ietf.org/html/rfc6750">RFC 6750</a>
     */
    public void setBearerAuth(String token) {
        set(AUTHORIZATION, "Bearer " + token);
    }

    /**
     * Set a configured {@link CacheControl} instance as the
     * new value of the {@code Cache-Control} header.
     *
     * @param cacheControl TODO
     */
    public void setCacheControl(CacheControl cacheControl) {
        setOrRemove(CACHE_CONTROL, cacheControl.getHeaderValue());
    }

    /**
     * Set the (new) value of the {@code Cache-Control} header.
     *
     * @param cacheControl TODO
     */
    public void setCacheControl(String cacheControl) {
        setOrRemove(CACHE_CONTROL, cacheControl);
    }

    /**
     * Return the value of the {@code Cache-Control} header.
     *
     * @return TODO
     */
    public String getCacheControl() {
        return getFieldValues(CACHE_CONTROL);
    }

    /**
     * Set the (new) value of the {@code Connection} header.
     *
     * @param connection TODO
     */
    public void setConnection(String connection) {
        set(CONNECTION, connection);
    }

    /**
     * Set the (new) value of the {@code Connection} header.
     *
     * @param connection TODO
     */
    public void setConnection(List<String> connection) {
        set(CONNECTION, toCommaDelimitedString(connection));
    }

    /**
     * Return the value of the {@code Connection} header.
     *
     * @return TODO
     */
    public List<String> getConnection() {
        return getValuesAsList(CONNECTION);
    }

    /**
     * Set the {@code Content-Disposition} header when creating a
     * {@code "multipart/form-data"} request.
     * <p>Applications typically would not set this header directly but
     * rather prepare a {@code MultiValueMap<String, Object>}, containing an
     * Object or a {@link cn.bytengine.d.utils.Resource} for each part,
     * and then pass that to the {@code RestTemplate} or {@code WebClient}.
     *
     * @param name     the control name
     * @param filename the filename (may be {@code null})
     * @see #getContentDisposition()
     */
    public void setContentDispositionFormData(String name, String filename) {
        AssertTools.notNull(name, "Name must not be null");
        ContentDisposition.Builder disposition = ContentDisposition.formData().name(name);
        if (CharSequenceTools.isNotEmpty(filename)) {
            disposition.filename(filename);
        }
        setContentDisposition(disposition.build());
    }

    /**
     * Set the {@literal Content-Disposition} header.
     * <p>This could be used on a response to indicate if the content is
     * expected to be displayed inline in the browser or as an attachment to be
     * saved locally.
     * <p>It can also be used for a {@code "multipart/form-data"} request.
     * For more details see notes on {@link #setContentDispositionFormData}.
     *
     * @param contentDisposition TODO
     * @see #getContentDisposition()
     */
    public void setContentDisposition(ContentDisposition contentDisposition) {
        set(CONTENT_DISPOSITION, contentDisposition.toString());
    }

    /**
     * Return a parsed representation of the {@literal Content-Disposition} header.
     *
     * @return TODO
     * @see #setContentDisposition(ContentDisposition)
     */
    public ContentDisposition getContentDisposition() {
        String contentDisposition = getFirst(CONTENT_DISPOSITION);
        if (CharSequenceTools.isNotEmpty(contentDisposition)) {
            return ContentDisposition.parse(contentDisposition);
        }
        return ContentDisposition.empty();
    }

    /**
     * Set the {@link Locale} of the content language,
     * as specified by the {@literal Content-Language} header.
     * <p>Use {@code put(CONTENT_LANGUAGE, list)} if you need
     * to set multiple content languages.</p>
     *
     * @param locale TODO
     */
    public void setContentLanguage(Locale locale) {
        setOrRemove(CONTENT_LANGUAGE, (locale != null ? locale.toLanguageTag() : null));
    }

    /**
     * Get the first {@link Locale} of the content languages, as specified by the
     * {@code Content-Language} header.
     * <p>Use {@link #getValuesAsList(String)} if you need to get multiple content
     * languages.
     *
     * @return the first {@code Locale} of the content languages, or {@code null}
     * if unknown
     */
    public Locale getContentLanguage() {
        return getValuesAsList(CONTENT_LANGUAGE)
                .stream()
                .findFirst()
                .map(Locale::forLanguageTag)
                .orElse(null);
    }

    /**
     * Set the length of the body in bytes, as specified by the
     * {@code Content-Length} header.
     *
     * @param contentLength content length (greater than or equal to zero)
     * @throws IllegalArgumentException if the content length is negative
     */
    public void setContentLength(long contentLength) {
        if (contentLength < 0) {
            throw new IllegalArgumentException("Content-Length must be a non-negative number");
        }
        set(CONTENT_LENGTH, Long.toString(contentLength));
    }

    /**
     * Return the length of the body in bytes, as specified by the
     * {@code Content-Length} header.
     * <p>Returns -1 when the content-length is unknown.
     *
     * @return TODO
     */
    public long getContentLength() {
        String value = getFirst(CONTENT_LENGTH);
        return (value != null ? Long.parseLong(value) : -1);
    }

    /**
     * Set the {@linkplain MediaType media type} of the body,
     * as specified by the {@code Content-Type} header.
     *
     * @param mediaType TODO
     */
    public void setContentType(MediaType mediaType) {
        if (mediaType != null) {
            AssertTools.isTrue(!mediaType.isWildcardType(), "Content-Type cannot contain wildcard type '*'");
            AssertTools.isTrue(!mediaType.isWildcardSubtype(), "Content-Type cannot contain wildcard subtype '*'");
            set(CONTENT_TYPE, mediaType.toString());
        } else {
            remove(CONTENT_TYPE);
        }
    }

    /**
     * Return the {@linkplain MediaType media type} of the body, as specified
     * by the {@code Content-Type} header.
     * <p>Returns {@code null} when the {@code Content-Type} header is not set.
     *
     * @return TODO
     * @throws InvalidMediaTypeException if the media type value cannot be parsed
     */
    public MediaType getContentType() {
        String value = getFirst(CONTENT_TYPE);
        return (CharSequenceTools.isNotEmpty(value) ? MediaType.parseMediaType(value) : null);
    }

    /**
     * Set the date and time at which the message was created, as specified
     * by the {@code Date} header.
     *
     * @param date TODO
     */
    public void setDate(ZonedDateTime date) {
        setZonedDateTime(DATE, date);
    }

    /**
     * Set the date and time at which the message was created, as specified
     * by the {@code Date} header.
     *
     * @param date TODO
     */
    public void setDate(Instant date) {
        setInstant(DATE, date);
    }

    /**
     * Set the date and time at which the message was created, as specified
     * by the {@code Date} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param date TODO
     */
    public void setDate(long date) {
        setDate(DATE, date);
    }

    /**
     * Return the date and time at which the message was created, as specified
     * by the {@code Date} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @throws IllegalArgumentException if the value cannot be converted to a date
     */
    public long getDate() {
        return getFirstDate(DATE);
    }

    /**
     * Set the (new) entity tag of the body, as specified by the {@code ETag} header.
     *
     * @param tag TODO
     */
    public void setETag(String tag) {
        if (tag != null) {
            set(ETAG, ETag.quoteETagIfNecessary(tag));
        } else {
            remove(ETAG);
        }
    }

    /**
     * Return the entity tag of the body, as specified by the {@code ETag} header.
     *
     * @return TODO
     */
    public String getETag() {
        return getFirst(ETAG);
    }

    /**
     * Set the duration after which the message is no longer valid,
     * as specified by the {@code Expires} header.
     *
     * @param expires TODO
     */
    public void setExpires(ZonedDateTime expires) {
        setZonedDateTime(EXPIRES, expires);
    }

    /**
     * Set the date and time at which the message is no longer valid,
     * as specified by the {@code Expires} header.
     *
     * @param expires TODO
     */
    public void setExpires(Instant expires) {
        setInstant(EXPIRES, expires);
    }

    /**
     * Set the date and time at which the message is no longer valid,
     * as specified by the {@code Expires} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param expires TODO
     */
    public void setExpires(long expires) {
        setDate(EXPIRES, expires);
    }

    /**
     * Return the date and time at which the message is no longer valid,
     * as specified by the {@code Expires} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getExpires() {
        return getFirstDate(EXPIRES, false);
    }

    /**
     * Set the (new) value of the {@code Host} header.
     * <p>If the given {@linkplain InetSocketAddress#getPort() port} is {@code 0},
     * the host header will only contain the
     * {@linkplain InetSocketAddress#getHostString() host name}.
     *
     * @param host TODO
     */
    public void setHost(InetSocketAddress host) {
        if (host != null) {
            String value = host.getHostString();
            int port = host.getPort();
            if (port != 0) {
                value = value + ":" + port;
            }
            set(HOST, value);
        } else {
            remove(HOST);
        }
    }

    /**
     * Return the value of the {@code Host} header, if available.
     * <p>If the header value does not contain a port, the
     * {@linkplain InetSocketAddress#getPort() port} in the returned address will
     * be {@code 0}.
     *
     * @return TODO
     */
    public InetSocketAddress getHost() {
        String value = getFirst(HOST);
        if (value == null) {
            return null;
        }

        String host = null;
        int port = 0;
        int separator = (value.startsWith("[") ? value.indexOf(':', value.indexOf(']')) : value.lastIndexOf(':'));
        if (separator != -1) {
            host = value.substring(0, separator);
            String portString = value.substring(separator + 1);
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException ex) {
                // ignore
            }
        }

        if (host == null) {
            host = value;
        }
        return InetSocketAddress.createUnresolved(host, port);
    }

    /**
     * Set the (new) value of the {@code If-Match} header.
     *
     * @param ifMatch TODO
     */
    public void setIfMatch(String ifMatch) {
        set(IF_MATCH, ifMatch);
    }

    /**
     * Set the (new) value of the {@code If-Match} header.
     *
     * @param ifMatchList TODO
     */
    public void setIfMatch(List<String> ifMatchList) {
        set(IF_MATCH, toCommaDelimitedString(ifMatchList));
    }

    /**
     * Return the value of the {@code If-Match} header.
     *
     * @return TODO
     * @throws IllegalArgumentException if parsing fails
     */
    public List<String> getIfMatch() {
        return getETagValuesAsList(IF_MATCH);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifModifiedSince TODO
     */
    public void setIfModifiedSince(ZonedDateTime ifModifiedSince) {
        setZonedDateTime(IF_MODIFIED_SINCE, ifModifiedSince.withZoneSameInstant(GMT));
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifModifiedSince TODO
     */
    public void setIfModifiedSince(Instant ifModifiedSince) {
        setInstant(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    /**
     * Set the (new) value of the {@code If-Modified-Since} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param ifModifiedSince TODO
     */
    public void setIfModifiedSince(long ifModifiedSince) {
        setDate(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    /**
     * Return the value of the {@code If-Modified-Since} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getIfModifiedSince() {
        return getFirstDate(IF_MODIFIED_SINCE, false);
    }

    /**
     * Set the (new) value of the {@code If-None-Match} header.
     *
     * @param ifNoneMatch TODO
     */
    public void setIfNoneMatch(String ifNoneMatch) {
        set(IF_NONE_MATCH, ifNoneMatch);
    }

    /**
     * Set the (new) values of the {@code If-None-Match} header.
     *
     * @param ifNoneMatchList TODO
     */
    public void setIfNoneMatch(List<String> ifNoneMatchList) {
        set(IF_NONE_MATCH, toCommaDelimitedString(ifNoneMatchList));
    }

    /**
     * Return the value of the {@code If-None-Match} header.
     *
     * @return TODO
     * @throws IllegalArgumentException if parsing fails
     */
    public List<String> getIfNoneMatch() {
        return getETagValuesAsList(IF_NONE_MATCH);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifUnmodifiedSince TODO
     */
    public void setIfUnmodifiedSince(ZonedDateTime ifUnmodifiedSince) {
        setZonedDateTime(IF_UNMODIFIED_SINCE, ifUnmodifiedSince.withZoneSameInstant(GMT));
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifUnmodifiedSince TODO
     */
    public void setIfUnmodifiedSince(Instant ifUnmodifiedSince) {
        setInstant(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    /**
     * Set the (new) value of the {@code If-Unmodified-Since} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param ifUnmodifiedSince TODO
     */
    public void setIfUnmodifiedSince(long ifUnmodifiedSince) {
        setDate(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    /**
     * Return the value of the {@code If-Unmodified-Since} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getIfUnmodifiedSince() {
        return getFirstDate(IF_UNMODIFIED_SINCE, false);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param lastModified TODO
     */
    public void setLastModified(ZonedDateTime lastModified) {
        setZonedDateTime(LAST_MODIFIED, lastModified.withZoneSameInstant(GMT));
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param lastModified TODO
     */
    public void setLastModified(Instant lastModified) {
        setInstant(LAST_MODIFIED, lastModified);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param lastModified TODO
     */
    public void setLastModified(long lastModified) {
        setDate(LAST_MODIFIED, lastModified);
    }

    /**
     * Return the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getLastModified() {
        return getFirstDate(LAST_MODIFIED, false);
    }

    /**
     * Set the (new) location of a resource,
     * as specified by the {@code Location} header.
     *
     * @param location TODO
     */
    public void setLocation(URI location) {
        setOrRemove(LOCATION, (location != null ? location.toASCIIString() : null));
    }

    /**
     * Return the (new) location of a resource
     * as specified by the {@code Location} header.
     * <p>Returns {@code null} when the location is unknown.
     *
     * @return TODO
     */
    public URI getLocation() {
        String value = getFirst(LOCATION);
        return (value != null ? URI.create(value) : null);
    }

    /**
     * Set the (new) value of the {@code Origin} header.
     *
     * @param origin TODO
     */
    public void setOrigin(String origin) {
        setOrRemove(ORIGIN, origin);
    }

    /**
     * Return the value of the {@code Origin} header.
     *
     * @return TODO
     */
    public String getOrigin() {
        return getFirst(ORIGIN);
    }

    /**
     * Set the (new) value of the {@code Pragma} header.
     *
     * @param pragma TODO
     */
    public void setPragma(String pragma) {
        setOrRemove(PRAGMA, pragma);
    }

    /**
     * Return the value of the {@code Pragma} header.
     *
     * @return TODO
     */
    public String getPragma() {
        return getFirst(PRAGMA);
    }

    /**
     * Sets the (new) value of the {@code Range} header.
     *
     * @param ranges TODO
     */
    public void setRange(List<HttpRange> ranges) {
        String value = HttpRange.toString(ranges);
        set(RANGE, value);
    }

    /**
     * Return the value of the {@code Range} header.
     * <p>Returns an empty list when the range is unknown.
     *
     * @return TODO
     */
    public List<HttpRange> getRange() {
        String value = getFirst(RANGE);
        return HttpRange.parseRanges(value);
    }

    /**
     * Set the (new) value of the {@code Upgrade} header.
     *
     * @param upgrade TODO
     */
    public void setUpgrade(String upgrade) {
        setOrRemove(UPGRADE, upgrade);
    }

    /**
     * Return the value of the {@code Upgrade} header.
     *
     * @return TODO
     */
    public String getUpgrade() {
        return getFirst(UPGRADE);
    }

    /**
     * Set the request header names (for example, "Accept-Language") for which the
     * response is subject to content negotiation and variances based on the
     * value of those request headers.
     *
     * @param requestHeaders the request header names
     */
    public void setVary(List<String> requestHeaders) {
        set(VARY, toCommaDelimitedString(requestHeaders));
    }

    /**
     * Return the request header names subject to content negotiation.
     *
     * @return TODO
     */
    public List<String> getVary() {
        return getValuesAsList(VARY);
    }

    /**
     * Set the given date under the given header name after formatting it as a string
     * using the RFC-1123 date-time formatter. The equivalent of
     * {@link #set(String, String)} but for date headers.
     *
     * @param date       TODO
     * @param headerName TODO
     */
    public void setZonedDateTime(String headerName, ZonedDateTime date) {
        set(headerName, DATE_FORMATTER.format(date));
    }

    /**
     * Set the given date under the given header name after formatting it as a string
     * using the RFC-1123 date-time formatter. The equivalent of
     * {@link #set(String, String)} but for date headers.
     *
     * @param headerName TODO
     * @param date       TODO
     */
    public void setInstant(String headerName, Instant date) {
        setZonedDateTime(headerName, ZonedDateTime.ofInstant(date, GMT));
    }

    /**
     * Set the given date under the given header name after formatting it as a string
     * using the RFC-1123 date-time formatter. The equivalent of
     * {@link #set(String, String)} but for date headers.
     *
     * @param headerName TODO
     * @param date       TODO
     * @see #setZonedDateTime(String, ZonedDateTime)
     */
    public void setDate(String headerName, long date) {
        setInstant(headerName, Instant.ofEpochMilli(date));
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return -1 if there is no value, or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName the header name
     * @return the parsed date header, or -1 if none
     * @see #getFirstZonedDateTime(String)
     */
    public long getFirstDate(String headerName) {
        return getFirstDate(headerName, true);
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return -1 if there is no value or also in case of an invalid value
     * (if {@code rejectInvalid=false}), or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName    the header name
     * @param rejectInvalid whether to reject invalid values with an
     *                      {@link IllegalArgumentException} ({@code true}) or rather return -1
     *                      in that case ({@code false})
     * @return the parsed date header, or -1 if none (or invalid)
     * @see #getFirstZonedDateTime(String, boolean)
     */
    private long getFirstDate(String headerName, boolean rejectInvalid) {
        ZonedDateTime zonedDateTime = getFirstZonedDateTime(headerName, rejectInvalid);
        return (zonedDateTime != null ? zonedDateTime.toInstant().toEpochMilli() : -1);
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return {@code null} if there is no value, or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName the header name
     * @return the parsed date header, or {@code null} if none
     */
    public ZonedDateTime getFirstZonedDateTime(String headerName) {
        return getFirstZonedDateTime(headerName, true);
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return {@code null} if there is no value or also in case of an invalid value
     * (if {@code rejectInvalid=false}), or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName    the header name
     * @param rejectInvalid whether to reject invalid values with an
     *                      {@link IllegalArgumentException} ({@code true}) or rather return {@code null}
     *                      in that case ({@code false})
     * @return the parsed date header, or {@code null} if none (or invalid)
     */
    private ZonedDateTime getFirstZonedDateTime(String headerName, boolean rejectInvalid) {
        String headerValue = getFirst(headerName);
        if (headerValue == null) {
            // No header value sent at all
            return null;
        }
        if (headerValue.length() >= 3) {
            // Short "0" or "-1" like values are never valid HTTP date headers...
            // Let's only bother with DateTimeFormatter parsing for long enough values.

            // See https://stackoverflow.com/questions/12626699/if-modified-since-http-header-passed-by-ie9-includes-length
            int parametersIndex = headerValue.indexOf(';');
            if (parametersIndex != -1) {
                headerValue = headerValue.substring(0, parametersIndex);
            }

            for (FastDateFormat dateFormatter : DATE_PARSERS) {
                try {
                    return ZonedDateTime.parse(headerValue, dateFormatter.getDateTimeFormatter());
                } catch (DateTimeParseException ex) {
                    // ignore
                }
            }

        }
        if (rejectInvalid) {
            throw new IllegalArgumentException("Cannot parse date value \"" + headerValue +
                    "\" for \"" + headerName + "\" header");
        }
        return null;
    }

    /**
     * Return all values of a given header name, even if this header is set
     * multiple times.
     * <p>This method supports double-quoted values, as described in
     * <a href="https://www.rfc-editor.org/rfc/rfc9110.html#section-5.5-8">RFC
     * 9110, section 5.5</a>.
     *
     * @param headerName the header name
     * @return all associated values
     */
    public List<String> getValuesAsList(String headerName) {
        List<String> values = get(headerName);
        if (values != null) {
            List<String> result = new ArrayList<>();
            for (String value : values) {
                if (value != null) {
                    result.addAll(tokenizeQuoted(value));
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    private static List<String> tokenizeQuoted(String str) {
        List<String> tokens = new ArrayList<>();
        boolean quoted = false;
        boolean trim = true;
        StringBuilder builder = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch == '"') {
                if (builder.length() == 0) {
                    quoted = true;
                } else if (quoted) {
                    quoted = false;
                    trim = false;
                } else {
                    builder.append(ch);
                }
            } else if (ch == '\\' && quoted && i < str.length() - 1) {
                builder.append(str.charAt(++i));
            } else if (ch == ',' && !quoted) {
                addToken(builder, tokens, trim);
                builder.setLength(0);
                trim = false;
            } else if (quoted || (builder.length() != 0 && trim) || !Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        if (builder.length() != 0) {
            addToken(builder, tokens, trim);
        }
        return tokens;
    }

    private static void addToken(StringBuilder builder, List<String> tokens, boolean trim) {
        String token = builder.toString();
        if (trim) {
            token = token.trim();
        }
        if (!token.isEmpty()) {
            tokens.add(token);
        }
    }

    /**
     * Remove the well-known {@code "Content-*"} HTTP headers.
     * <p>Such headers should be cleared from the response if the intended
     * body can't be written due to errors.
     */
    public void clearContentHeaders() {
        this.headers.remove(HttpHeader.CONTENT_DISPOSITION);
        this.headers.remove(HttpHeader.CONTENT_ENCODING);
        this.headers.remove(HttpHeader.CONTENT_LANGUAGE);
        this.headers.remove(HttpHeader.CONTENT_LENGTH);
        this.headers.remove(HttpHeader.CONTENT_LOCATION);
        this.headers.remove(HttpHeader.CONTENT_RANGE);
        this.headers.remove(HttpHeader.CONTENT_TYPE);
    }

    /**
     * Retrieve a combined result from the field values of the ETag header.
     *
     * @param name the header name
     * @return the combined result
     * @throws IllegalArgumentException if parsing fails
     */
    protected List<String> getETagValuesAsList(String name) {
        List<String> values = get(name);
        if (values == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (String value : values) {
            if (value != null) {
                List<ETag> tags = ETag.parse(value);
                AssertTools.isTrue(CollectionTools.isEmpty(tags), "Could not parse header '" + name + "' with value '" + value + "'");
                for (ETag tag : tags) {
                    result.add(tag.formattedTag());
                }
            }
        }
        return result;
    }

    /**
     * Retrieve a combined result from the field values of multivalued headers.
     *
     * @param headerName the header name
     * @return the combined result
     */
    protected String getFieldValues(String headerName) {
        List<String> headerValues = get(headerName);
        return (headerValues != null ? toCommaDelimitedString(headerValues) : null);
    }

    /**
     * Turn the given list of header values into a comma-delimited result.
     *
     * @param headerValues the list of header values
     * @return a combined result with comma delimitation
     */
    protected String toCommaDelimitedString(List<String> headerValues) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String val : headerValues) {
            if (val != null) {
                joiner.add(val);
            }
        }
        return joiner.toString();
    }

    /**
     * Set the given header value, or remove the header if {@code null}.
     *
     * @param headerName  the header name
     * @param headerValue the header value, or {@code null} for none
     */
    private void setOrRemove(String headerName, String headerValue) {
        if (headerValue != null) {
            set(headerName, headerValue);
        } else {
            remove(headerName);
        }
    }


    // MultiValueMap implementation

    /**
     * Return the first header value for the given header name, if any.
     *
     * @param headerName the header name
     * @return the first header value, or {@code null} if none
     */
    @Override
    public String getFirst(String headerName) {
        return this.headers.getFirst(headerName);
    }

    /**
     * Add the given, single header value under the given name.
     *
     * @param headerName  the header name
     * @param headerValue the header value
     * @throws UnsupportedOperationException if adding headers is not supported
     * @see #put(String, List)
     * @see #set(String, String)
     */
    @Override
    public void add(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
    }

    @Override
    public void addAll(String key, List<? extends String> values) {
        this.headers.addAll(key, values);
    }

    @Override
    public void addAll(MultiValueMap<String, String> values) {
        this.headers.addAll(values);
    }

    /**
     * Set the given, single header value under the given name.
     *
     * @param headerName  the header name
     * @param headerValue the header value
     * @throws UnsupportedOperationException if adding headers is not supported
     * @see #put(String, List)
     * @see #add(String, String)
     */
    @Override
    public void set(String headerName, String headerValue) {
        this.headers.set(headerName, headerValue);
    }

    @Override
    public void setAll(Map<String, String> values) {
        this.headers.setAll(values);
    }

    @Override
    public Map<String, String> toSingleValueMap() {
        return this.headers.toSingleValueMap();
    }

    @Override
    public Map<String, String> asSingleValueMap() {
        return this.headers.asSingleValueMap();
    }

    // Map implementation

    @Override
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.headers.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return this.headers.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        return this.headers.put(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        return this.headers.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> map) {
        this.headers.putAll(map);
    }

    @Override
    public void clear() {
        this.headers.clear();
    }

    @Override
    public List<String> putIfAbsent(String key, List<String> value) {
        return this.headers.putIfAbsent(key, value);
    }

    // Map/MultiValueMap methods that can have duplicate header names: size/keySet/values/entrySet/forEach

    /**
     * Return the number of headers in the collection. This can be inflated,
     * see {@link HttpHeader class level javadoc}.
     */
    @Override
    public int size() {
        return this.headers.size();
    }

    /**
     * Return a {@link Set} view of header names. This can include multiple
     * casing variants of a given header name, see
     * {@link HttpHeader class level javadoc}.
     */
    @Override
    public Set<String> keySet() {
        return this.headers.keySet();
    }

    /**
     * Return a {@link Collection} view of all the header values, reconstructed
     * from iterating over the {@link #keySet()}. This can include duplicates if
     * multiple casing variants of a given header name are tracked, see
     * {@link HttpHeader class level javadoc}.
     */
    @Override
    public Collection<List<String>> values() {
        return this.headers.values();
    }

    /**
     * Return a {@link Set} views of header entries, reconstructed from
     * iterating over the {@link #keySet()}. This can include duplicate entries
     * if multiple casing variants of a given header name are tracked, see
     * {@link HttpHeader class level javadoc}.
     *
     * @see #headerSet()
     */
    @Override
    public Set<Map.Entry<String, List<String>>> entrySet() {
        return this.headers.entrySet();
    }

    /**
     * Perform an action over each header, as when iterated via
     * {@link #entrySet()}. This can include duplicate entries
     * if multiple casing variants of a given header name are tracked, see
     * {@link HttpHeader class level javadoc}.
     *
     * @param action the action to be performed for each entry
     */
    @Override
    public void forEach(BiConsumer<? super String, ? super List<String>> action) {
        this.headers.forEach(action);
    }

    /**
     * Return a view of the headers as an entry {@code Set} of key-list pairs.
     * Both {@link Iterator#remove()} and {@link java.util.Map.Entry#setValue}
     * are supported and mutate the headers.
     * <p>This collection is guaranteed to contain one entry per header name
     * even if the backing structure stores multiple casing variants of names,
     * at the cost of first copying the names into a case-insensitive set for
     * filtering the iteration.
     *
     * @return a {@code Set} view that iterates over all headers in a
     * case-insensitive manner
     */
    public Set<Map.Entry<String, List<String>>> headerSet() {
        return new CaseInsensitiveEntrySet(this.headers);
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof HttpHeader) {
            HttpHeader that = (HttpHeader) other;
            return unwrap(this).equals(unwrap(that));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.headers.hashCode();
    }

    @Override
    public String toString() {
        return formatHeaders(this.headers);
    }


    /**
     * Apply a read-only {@code HttpHeader} wrapper around the given headers, if necessary.
     * <p>Also caches the parsed representations of the "Accept" and "Content-Type" headers.
     *
     * @param headers the headers to expose
     * @return a read-only variant of the headers, or the original headers as-is
     * (in case it happens to be a read-only {@code HttpHeader} instance already)
     */
    public static HttpHeader readOnlyHttpHeader(MultiValueMap<String, String> headers) {
        if (headers instanceof HttpHeader) {
            HttpHeader httpHeader = (HttpHeader) headers;
            return readOnlyHttpHeader(httpHeader);
        }
        return new ReadOnlyHttpHeader(headers);
    }

    /**
     * Apply a read-only {@code HttpHeader} wrapper around the given headers, if necessary.
     * <p>Also caches the parsed representations of the "Accept" and "Content-Type" headers.
     *
     * @param headers the headers to expose
     * @return a read-only variant of the headers, or the original headers as-is if already read-only
     */
    public static HttpHeader readOnlyHttpHeader(HttpHeader headers) {
        AssertTools.notNull(headers, "HttpHeader must not be null");
        return (headers instanceof ReadOnlyHttpHeader ? headers : new ReadOnlyHttpHeader(headers.headers));
    }

    /**
     * Remove any read-only wrapper that may have been previously applied around
     * the given headers via {@link #readOnlyHttpHeader(HttpHeader)}.
     * <p>Once the writable instance is mutated, the read-only instance is likely
     * to be out of sync and should be discarded.
     *
     * @param headers the headers to expose
     * @return a writable variant of the headers, or the original headers as-is
     */
    public static HttpHeader writableHttpHeader(HttpHeader headers) {
        return new HttpHeader(headers);
    }

    /**
     * Helps to format HTTP header values, as HTTP header values themselves can
     * contain comma-separated values, can become confusing with regular
     * {@link Map} formatting that also uses commas between entries.
     * <p>Additionally, this method displays the native list of header names
     * with the mention {@code with native header names} if the underlying
     * implementation stores multiple casing variants of header names (see
     * {@link HttpHeader class level javadoc}).
     *
     * @param headers the headers to format
     * @return the headers to a String
     */
    public static String formatHeaders(MultiValueMap<String, String> headers) {
        Set<String> headerNames = toCaseInsensitiveSet(headers.keySet());
        String suffix = "]";
        if (headerNames.size() != headers.size()) {
            suffix = "] with native header names " + headers.keySet();
        }

        return headerNames.stream()
                .map(headerName -> {
                    List<String> values = headers.get(headerName);
                    AssertTools.notNull(values, "Expected at least one value for header " + headerName);
                    return headerName + ":" + (values.size() == 1 ?
                            "\"" + values.get(0) + "\"" :
                            values.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")));
                })
                .collect(Collectors.joining(", ", "[", suffix));
    }

    /**
     * Encode the given username and password into Basic Authentication credentials.
     * <p>The encoded credentials returned by this method can be supplied to
     * {@link #setBasicAuth(String)} to set the Basic Authentication header.
     *
     * @param username the username
     * @param password the password
     * @param charset  the charset to use to convert the credentials into an octet
     *                 sequence. Defaults to {@linkplain StandardCharsets#ISO_8859_1 ISO-8859-1}.
     * @return TODO
     * @throws IllegalArgumentException if {@code username} or {@code password}
     *                                  contains characters that cannot be encoded to the given charset
     * @see #setBasicAuth(String)
     * @see #setBasicAuth(String, String)
     * @see #setBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     */
    public static String encodeBasicAuth(String username, String password, Charset charset) {
        AssertTools.notNull(username, "Username must not be null");
        AssertTools.isTrue(username.contains(":"), "Username must not contain a colon");
        AssertTools.notNull(password, "Password must not be null");
        if (charset == null) {
            charset = StandardCharsets.ISO_8859_1;
        }

        CharsetEncoder encoder = charset.newEncoder();
        if (!encoder.canEncode(username) || !encoder.canEncode(password)) {
            throw new IllegalArgumentException(
                    "Username or password contains characters that cannot be encoded to " + charset.displayName());
        }

        String credentialsString = username + ":" + password;
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsString.getBytes(charset));
        return new String(encodedBytes, charset);
    }


    private static MultiValueMap<String, String> unwrap(HttpHeader headers) {
        while (headers.headers instanceof HttpHeader) {
            headers = (HttpHeader) headers.headers;
        }
        return headers.headers;
    }

    // Package-private: used in ResponseCookie
    static String formatDate(long date) {
        Instant instant = Instant.ofEpochMilli(date);
        ZonedDateTime time = ZonedDateTime.ofInstant(instant, GMT);
        return DATE_FORMATTER.format(time);
    }


    private static Set<String> toCaseInsensitiveSet(Set<String> originalSet) {
        final Set<String> deduplicatedSet = Collections.newSetFromMap(
                new LinkedCaseInsensitiveMap<>(originalSet.size(), Locale.ROOT));
        // add/addAll (put/putAll in LinkedCaseInsensitiveMap) retain the casing of the last occurrence.
        // Here we prefer the first.
        for (String header : originalSet) {
            //noinspection RedundantCollectionOperation
            if (!deduplicatedSet.contains(header)) {
                deduplicatedSet.add(header);
            }
        }
        return deduplicatedSet;
    }


    private static final class CaseInsensitiveEntrySet extends AbstractSet<Map.Entry<String, List<String>>> {

        private final MultiValueMap<String, String> headers;
        private final Set<String> deduplicatedNames;

        public CaseInsensitiveEntrySet(MultiValueMap<String, String> headers) {
            this.headers = headers;
            this.deduplicatedNames = toCaseInsensitiveSet(headers.keySet());
        }

        @Override
        public Iterator<Map.Entry<String, List<String>>> iterator() {
            return new CaseInsensitiveIterator(this.deduplicatedNames.iterator());
        }

        @Override
        public int size() {
            return this.deduplicatedNames.size();
        }

        private final class CaseInsensitiveIterator implements Iterator<Map.Entry<String, List<String>>> {

            private final Iterator<String> namesIterator;

            private String currentName;

            private CaseInsensitiveIterator(Iterator<String> namesIterator) {
                this.namesIterator = namesIterator;
                this.currentName = null;
            }

            @Override
            public boolean hasNext() {
                return this.namesIterator.hasNext();
            }

            @Override
            public Map.Entry<String, List<String>> next() {
                this.currentName = this.namesIterator.next();
                return new CaseInsensitiveEntry(this.currentName);
            }

            @Override
            public void remove() {
                if (this.currentName == null) {
                    throw new IllegalStateException("No current Header in iterator");
                }
                if (!CaseInsensitiveEntrySet.this.headers.containsKey(this.currentName)) {
                    throw new IllegalStateException("Header not present: " + this.currentName);
                }
                CaseInsensitiveEntrySet.this.headers.remove(this.currentName);
            }
        }

        private final class CaseInsensitiveEntry implements Map.Entry<String, List<String>> {

            private final String key;

            CaseInsensitiveEntry(String key) {
                this.key = key;
            }

            @Override
            public String getKey() {
                return this.key;
            }

            @Override
            public List<String> getValue() {
                return Objects.requireNonNull(CaseInsensitiveEntrySet.this.headers.get(this.key));
            }

            @Override
            public List<String> setValue(List<String> value) {
                List<String> previousValues = Objects.requireNonNull(
                        CaseInsensitiveEntrySet.this.headers.get(this.key));
                CaseInsensitiveEntrySet.this.headers.put(this.key, value);
                return previousValues;
            }
        }
    }
}
