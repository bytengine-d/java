package cn.bytengine.d.http;

import cn.bytengine.d.lang.AssertTools;

import java.io.Serializable;

/**
 * Represents an HTTP request method.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public final class HttpMethod implements Comparable<HttpMethod>, Serializable {

    private static final long serialVersionUID = -70133475680645360L;

    /**
     * The HTTP method {@code GET}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.3">HTTP 1.1, section 9.3</a>
     */
    public static final HttpMethod GET = new HttpMethod("GET");

    /**
     * The HTTP method {@code HEAD}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.4">HTTP 1.1, section 9.4</a>
     */
    public static final HttpMethod HEAD = new HttpMethod("HEAD");

    /**
     * The HTTP method {@code POST}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5">HTTP 1.1, section 9.5</a>
     */
    public static final HttpMethod POST = new HttpMethod("POST");

    /**
     * The HTTP method {@code PUT}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.6">HTTP 1.1, section 9.6</a>
     */
    public static final HttpMethod PUT = new HttpMethod("PUT");

    /**
     * The HTTP method {@code PATCH}.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc5789#section-2">RFC 5789</a>
     */
    public static final HttpMethod PATCH = new HttpMethod("PATCH");

    /**
     * The HTTP method {@code DELETE}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.7">HTTP 1.1, section 9.7</a>
     */
    public static final HttpMethod DELETE = new HttpMethod("DELETE");

    /**
     * The HTTP method {@code OPTIONS}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2">HTTP 1.1, section 9.2</a>
     */
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");

    /**
     * The HTTP method {@code TRACE}.
     *
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.8">HTTP 1.1, section 9.8</a>
     */
    public static final HttpMethod TRACE = new HttpMethod("TRACE");

    private static final HttpMethod[] values = new HttpMethod[]{GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE};
    /**
     * TODO
     */
    private final String name;


    private HttpMethod(String name) {
        this.name = name;
    }

    /**
     * Returns an array containing the standard HTTP methods. Specifically,
     * this method returns an array containing {@link #GET}, {@link #HEAD},
     * {@link #POST}, {@link #PUT}, {@link #PATCH}, {@link #DELETE},
     * {@link #OPTIONS}, and {@link #TRACE}.
     *
     * <p>Note that the returned value does not include any HTTP methods defined
     * in WebDav.
     *
     * @return TODO
     */
    public static HttpMethod[] values() {
        HttpMethod[] copy = new HttpMethod[values.length];
        System.arraycopy(values, 0, copy, 0, values.length);
        return copy;
    }

    /**
     * Return an {@code HttpMethod} object for the given value.
     *
     * @param method the method value as a String
     * @return the corresponding {@code HttpMethod}
     */
    public static HttpMethod valueOf(String method) {
        AssertTools.notNull(method, "Method must not be null");
        switch (method) {
            case "GET":
                return GET;
            case "HEAD":
                return HEAD;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "PATCH":
                return PATCH;
            case "DELETE":
                return DELETE;
            case "OPTIONS":
                return OPTIONS;
            case "TRACE":
                return TRACE;
            default:
                return new HttpMethod(method);
        }
    }


    /**
     * Return the name of this method, for example, "GET", "POST".
     *
     * @return TODO
     */
    public String name() {
        return this.name;
    }

    /**
     * Determine whether this {@code HttpMethod} matches the given method value.
     *
     * @param method the HTTP method as a String
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public boolean matches(String method) {
        return name().equals(method);
    }


    @Override
    public int compareTo(HttpMethod other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof HttpMethod) {
            HttpMethod that = (HttpMethod) other;
            return this.name.equals(that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
