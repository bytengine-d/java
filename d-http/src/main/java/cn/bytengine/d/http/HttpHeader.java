package cn.bytengine.d.http;

import cn.bytengine.d.collection.LinkedMultiValueMap;
import cn.bytengine.d.collection.MultiValueMap;
import cn.bytengine.d.lang.DatePatterns;
import cn.bytengine.d.lang.FastDateFormat;

import java.util.Collections;
import java.util.List;

/**
 * A data structure representing HTTP request or response headers, mapping String header names
 * to a list of String values, also offering accessors for common application-level data types.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class HttpHeader {
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
     *
     * @since 5.3.6
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
    /**
     * Date formats with time zone as specified in the HTTP RFC to use for parsing.
     */
    private static final FastDateFormat[] DATE_PARSERS = new FastDateFormat[]{
            DatePatterns.HTTP_GMT_RFC_1123_DATE_FORMAT,
            DatePatterns.HTTP_US_RFC_850_DATE_FORMAT,
            DatePatterns.HTTP_US_GMT_ANSI_C_ASC_TIME_DATE_FORMAT
    };

    private final MultiValueMap<String, String> headers;

    public HttpHeader() {
        this(new LinkedMultiValueMap<>());
    }

    public HttpHeader(HttpHeader header) {
        this(new LinkedMultiValueMap<>(header.headers));
    }

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
}
