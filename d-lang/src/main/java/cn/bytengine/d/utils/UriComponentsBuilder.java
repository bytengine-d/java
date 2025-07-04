package cn.bytengine.d.utils;

import cn.bytengine.d.collection.LinkedMultiValueMap;
import cn.bytengine.d.collection.MultiValueMap;
import cn.bytengine.d.lang.ArrayTools;
import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.CollectionTools;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Builder for {@link UriComponents}. Use as follows:
 * <ol>
 * <li>Create a builder through a factory method, e.g. {@link #fromUriString(String)}.
 * <li>Set URI components (e.g. scheme, host, path, etc) through instance methods.
 * <li>Build the {@link UriComponents}.</li>
 * <li>Expand URI variables from a map or array or variable values.
 * <li>Encode via {@link UriComponents#encode()}.</li>
 * <li>Use {@link UriComponents#toUri()} or {@link UriComponents#toUriString()}.
 * </ol>
 *
 * <p>By default, URI parsing is based on the {@link ParserType#RFC RFC parser type},
 * which expects input strings to conform to RFC 3986 syntax. The alternative
 * {@link ParserType#WHAT_WG WhatWG parser type}, based on the algorithm from
 * the WhatWG <a href="https://url.spec.whatwg.org">URL Living Standard</a>
 * provides more lenient handling of a wide range of cases that occur in user
 * types URL's.
 *
 * @author Ban Tenio
 * @version 1.0
 * @see #newInstance()
 * @see #fromPath(String)
 * @see #fromUri(URI)
 */
public class UriComponentsBuilder implements Cloneable {

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("([^&=]+)(=?)([^&]+)?");

    private static final Object[] EMPTY_VALUES = new Object[0];

    private String scheme;

    private String ssp;

    private String userInfo;

    private String host;

    private String port;

    private CompositePathComponentBuilder pathBuilder;

    private final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

    private String fragment;

    private final Map<String, Object> uriVariables = new HashMap<>(4);

    private boolean encodeTemplate;

    private Charset charset = StandardCharsets.UTF_8;


    /**
     * Default constructor. Protected to prevent direct instantiation.
     *
     * @see #newInstance()
     * @see #fromPath(String)
     * @see #fromUri(URI)
     */
    protected UriComponentsBuilder() {
        this.pathBuilder = new CompositePathComponentBuilder();
    }

    /**
     * Create a deep copy of the given UriComponentsBuilder.
     *
     * @param other the other builder to copy from
     */
    protected UriComponentsBuilder(UriComponentsBuilder other) {
        this.scheme = other.scheme;
        this.ssp = other.ssp;
        this.userInfo = other.userInfo;
        this.host = other.host;
        this.port = other.port;
        this.pathBuilder = other.pathBuilder.cloneBuilder();
        this.uriVariables.putAll(other.uriVariables);
        this.queryParams.addAll(other.queryParams);
        this.fragment = other.fragment;
        this.encodeTemplate = other.encodeTemplate;
        this.charset = other.charset;
    }


    // Factory methods

    /**
     * Create a new, empty builder.
     *
     * @return the new {@code UriComponentsBuilder}
     */
    public static UriComponentsBuilder newInstance() {
        return new UriComponentsBuilder();
    }

    /**
     * Create a builder that is initialized with the given path.
     *
     * @param path the path to initialize with
     * @return the new {@code UriComponentsBuilder}
     */
    public static UriComponentsBuilder fromPath(String path) {
        UriComponentsBuilder builder = new UriComponentsBuilder();
        builder.path(path);
        return builder;
    }

    /**
     * Create a builder that is initialized from the given {@code URI}.
     * <p><strong>Note:</strong> the components in the resulting builder will be
     * in fully encoded (raw) form and further changes must also supply values
     * that are fully encoded.
     * In addition please use {@link #build(boolean)} with a value of "true" to
     * build the {@link UriComponents} instance in order to indicate that the
     * components are encoded.
     *
     * @param uri the URI to initialize with
     * @return the new {@code UriComponentsBuilder}
     */
    public static UriComponentsBuilder fromUri(URI uri) {
        UriComponentsBuilder builder = new UriComponentsBuilder();
        builder.uri(uri);
        return builder;
    }

    /**
     * Variant of {@link #fromUriString(String, ParserType)} that defaults to
     * the {@link ParserType#RFC} parsing.
     *
     * @param uri the URI to initialize with
     * @return the new {@code UriComponentsBuilder}
     */
    public static UriComponentsBuilder fromUriString(String uri) throws InvalidUrlException {
        AssertTools.notNull(uri, "URI must not be null");
        if (uri.isEmpty()) {
            return new UriComponentsBuilder();
        }
        return fromUriString(uri, ParserType.RFC);
    }

    /**
     * Create a builder that is initialized by parsing the given URI string.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously. Such values should be substituted for URI
     * variables to enable correct parsing:
     * <pre class="code">
     * String uriString = &quot;/hotels/42?filter={value}&quot;;
     * UriComponentsBuilder.fromUriString(uriString).buildAndExpand(&quot;hot&amp;cold&quot;);
     * </pre>
     *
     * @param uri        the URI string to initialize with
     * @param parserType the parsing algorithm to use
     * @return the new {@code UriComponentsBuilder}
     * @throws InvalidUrlException if {@code uri} cannot be parsed
     */
    public static UriComponentsBuilder fromUriString(String uri, ParserType parserType) throws InvalidUrlException {
        AssertTools.notNull(uri, "URI must not be null");
        if (uri.isEmpty()) {
            return new UriComponentsBuilder();
        }
        UriComponentsBuilder builder = new UriComponentsBuilder();
        switch (parserType) {
            case RFC: {
                RfcUriParser.UriRecord record = RfcUriParser.parse(uri);
                return builder.rfcUriRecord(record);
            }
            case WHAT_WG: {
                WhatWgUrlParser.UrlRecord record =
                        WhatWgUrlParser.parse(uri, WhatWgUrlParser.EMPTY_RECORD, null, null);
                return builder.whatWgUrlRecord(record);
            }
            default:
                return null;
        }
    }

    /**
     * Create a URI components builder from the given HTTP URL String.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously. Such values should be substituted for URI
     * variables to enable correct parsing:
     * <pre class="code">
     * String urlString = &quot;https://example.com/hotels/42?filter={value}&quot;;
     * UriComponentsBuilder.fromHttpUrl(urlString).buildAndExpand(&quot;hot&amp;cold&quot;);
     * </pre>
     *
     * @param httpUrl the source URI
     * @return the URI components of the URI
     */
    public static UriComponentsBuilder fromHttpUrl(String httpUrl) throws InvalidUrlException {
        return fromUriString(httpUrl);
    }

    /**
     * Create an instance by parsing the "Origin" header of an HTTP request.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6454">RFC 6454</a>
     *
     * @param origin the source origin
     * @return the URI components of the URI
     */
    public static UriComponentsBuilder fromOriginHeader(String origin) {
        return fromUriString(origin);
    }


    // Encode methods

    /**
     * Request to have the URI template pre-encoded at build time, and
     * URI variables encoded separately when expanded.
     * <p>In comparison to {@link UriComponents#encode()}, this method has the
     * same effect on the URI template, i.e. each URI component is encoded by
     * replacing non-ASCII and illegal (within the URI component type) characters
     * with escaped octets. However URI variables are encoded more strictly, by
     * also escaping characters with reserved meaning.
     * <p>For most cases, this method is more likely to give the expected result
     * because in treats URI variables as opaque data to be fully encoded, while
     * {@link UriComponents#encode()} is useful when intentionally expanding URI
     * variables that contain reserved characters.
     * <p>For example ';' is legal in a path but has reserved meaning. This
     * method replaces ";" with "%3B" in URI variables but not in the URI
     * template. By contrast, {@link UriComponents#encode()} never replaces ";"
     * since it is a legal character in a path.
     * <p>When not expanding URI variables at all, prefer use of
     * {@link UriComponents#encode()} since that will also encode anything that
     * incidentally looks like a URI variable.
     *
     * @return the URI components of the URI
     */
    public final UriComponentsBuilder encode() {
        return encode(StandardCharsets.UTF_8);
    }

    /**
     * A variant of {@link #encode()} with a charset other than "UTF-8".
     *
     * @param charset the charset to use for encoding
     * @return the URI components of the URI
     */
    public UriComponentsBuilder encode(Charset charset) {
        this.encodeTemplate = true;
        this.charset = charset;
        return this;
    }


    // Build methods

    /**
     * Build a {@code UriComponents} instance from the various components contained in this builder.
     *
     * @return the URI components
     */
    public UriComponents build() {
        return build(false);
    }

    /**
     * Variant of {@link #build()} to create a {@link UriComponents} instance
     * when components are already fully encoded. This is useful for example if
     * the builder was created via {@link UriComponentsBuilder#fromUri(URI)}.
     *
     * @param encoded whether the components in this builder are already encoded
     * @return the URI components
     * @throws IllegalArgumentException if any of the components contain illegal
     *                                  characters that should have been encoded.
     */
    public UriComponents build(boolean encoded) {
        return buildInternal(encoded ? EncodingHint.FULLY_ENCODED :
                (this.encodeTemplate ? EncodingHint.ENCODE_TEMPLATE : EncodingHint.NONE));
    }

    private UriComponents buildInternal(EncodingHint hint) {
        UriComponents result;
        if (this.ssp != null) {
            result = new OpaqueUriComponents(this.scheme, this.ssp, this.fragment);
        } else {
            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>(this.queryParams);
            HierarchicalUriComponents uric = new HierarchicalUriComponents(this.scheme, this.fragment,
                    this.userInfo, this.host, this.port, this.pathBuilder.build(), queryParams,
                    hint == EncodingHint.FULLY_ENCODED);
            result = (hint == EncodingHint.ENCODE_TEMPLATE ? uric.encodeTemplate(this.charset) : uric);
        }
        if (!this.uriVariables.isEmpty()) {
            result = result.expand(name -> this.uriVariables.getOrDefault(name, UriComponents.UriTemplateVariables.SKIP_VALUE));
        }
        return result;
    }

    /**
     * Build a {@code UriComponents} instance and replaces URI template variables
     * with the values from a map. This is a shortcut method which combines
     * calls to {@link #build()} and then {@link UriComponents#expand(Map)}.
     *
     * @param uriVariables the map of URI variables
     * @return the URI components with expanded values
     */
    public UriComponents buildAndExpand(Map<String, ?> uriVariables) {
        return build().expand(uriVariables);
    }

    /**
     * Build a {@code UriComponents} instance and replaces URI template variables
     * with the values from an array. This is a shortcut method which combines
     * calls to {@link #build()} and then {@link UriComponents#expand(Object...)}.
     *
     * @param uriVariableValues the URI variable values
     * @return the URI components with expanded values
     */
    public UriComponents buildAndExpand(Object... uriVariableValues) {
        return build().expand(uriVariableValues);
    }

    /**
     * Build a {@link URI} instance and replaces URI template variables
     * with the values from an array.
     *
     * @param uriVariables the map of URI variables
     * @return the URI
     */
    public URI build(Object... uriVariables) {
        return buildInternal(EncodingHint.ENCODE_TEMPLATE).expand(uriVariables).toUri();
    }

    /**
     * Build a {@link URI} instance and replaces URI template variables
     * with the values from a map.
     *
     * @param uriVariables the map of URI variables
     * @return the URI
     */
    public URI build(Map<String, ?> uriVariables) {
        return buildInternal(EncodingHint.ENCODE_TEMPLATE).expand(uriVariables).toUri();
    }

    /**
     * Build a URI String.
     * <p>Effectively, a shortcut for building, encoding, and returning the
     * String representation:
     * <pre class="code">
     * String uri = builder.build().encode().toUriString()
     * </pre>
     * <p>However if {@link #uriVariables(Map) URI variables} have been provided
     * then the URI template is pre-encoded separately from URI variables (see
     * {@link #encode()} for details), i.e. equivalent to:
     * <pre>
     * String uri = builder.encode().build().toUriString()
     * </pre>
     *
     * @return the URI string
     * @see UriComponents#toUriString()
     */
    public String toUriString() {
        return (this.uriVariables.isEmpty() ?
                build().encode().toUriString() :
                buildInternal(EncodingHint.ENCODE_TEMPLATE).toUriString());
    }


    // Instance methods

    /**
     * Initialize components of this builder from components of the given URI.
     *
     * @param uri the URI
     * @return this UriComponentsBuilder
     */
    public UriComponentsBuilder uri(URI uri) {
        AssertTools.notNull(uri, "URI must not be null");
        this.scheme = uri.getScheme();
        if (uri.isOpaque()) {
            this.ssp = uri.getRawSchemeSpecificPart();
            resetHierarchicalComponents();
        } else {
            if (uri.getRawUserInfo() != null) {
                this.userInfo = uri.getRawUserInfo();
            }
            if (uri.getHost() != null) {
                this.host = uri.getHost();
            }
            if (uri.getPort() != -1) {
                this.port = String.valueOf(uri.getPort());
            }
            if (CharSequenceTools.isNotEmpty(uri.getRawPath())) {
                this.pathBuilder = new CompositePathComponentBuilder();
                this.pathBuilder.addPath(uri.getRawPath());
            }
            if (CharSequenceTools.isNotEmpty(uri.getRawQuery())) {
                this.queryParams.clear();
                query(uri.getRawQuery());
            }
            resetSchemeSpecificPart();
        }
        if (uri.getRawFragment() != null) {
            this.fragment = uri.getRawFragment();
        }
        return this;
    }

    /**
     * Set or append individual URI components of this builder from the values
     * of the given {@link UriComponents} instance.
     * <p>For the semantics of each component (i.e. set vs append) check the
     * builder methods on this class. For example {@link #host(String)} sets
     * while {@link #path(String)} appends.
     *
     * @param uriComponents the UriComponents to copy from
     * @return this UriComponentsBuilder
     */
    public UriComponentsBuilder uriComponents(UriComponents uriComponents) {
        AssertTools.notNull(uriComponents, "UriComponents must not be null");
        uriComponents.copyToUriComponentsBuilder(this);
        return this;
    }

    /**
     * Internal method to initialize this builder from an RFC {@code UriRecord}.
     */
    private UriComponentsBuilder rfcUriRecord(RfcUriParser.UriRecord record) {
        scheme(record.getScheme());
        if (record.isOpaque()) {
            if (record.getPath() != null) {
                schemeSpecificPart(record.getPath());
            }
        } else {
            userInfo(record.getUser());
            host(record.getHost());
            port(record.getPort());
            if (record.getPath() != null) {
                path(record.getPath());
            }
            query(record.getQuery());
        }
        fragment(record.getFragment());
        return this;
    }

    /**
     * Internal method to initialize this builder from a WhatWG {@code UrlRecord}.
     */
    private UriComponentsBuilder whatWgUrlRecord(WhatWgUrlParser.UrlRecord record) {
        if (!record.scheme().isEmpty()) {
            scheme(record.scheme());
        }
        if (record.path().isOpaque()) {
            String ssp = record.path() + record.search();
            schemeSpecificPart(ssp);
        } else {
            userInfo(record.userInfo());
            String hostname = record.hostname();
            if (CharSequenceTools.isNotEmpty(hostname)) {
                host(hostname);
            }
            if (record.port() != null) {
                port(record.portString());
            }
            path(record.path().toString());
            query(record.query());
        }
        if (CharSequenceTools.isNotEmpty(record.fragment())) {
            fragment(record.fragment());
        }
        return this;
    }

    /**
     * Set the URI scheme which may contain URI template variables,
     * and may also be {@code null} to clear the scheme of this builder.
     *
     * @param scheme the URI scheme
     * @return the URI builder
     */
    public UriComponentsBuilder scheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Set the URI scheme-specific-part. When invoked, this method overwrites
     * {@linkplain #userInfo(String) user-info}, {@linkplain #host(String) host},
     * {@linkplain #port(int) port}, {@linkplain #path(String) path}, and
     * {@link #query(String) query}.
     *
     * @param ssp the URI scheme-specific-part, may contain URI template parameters
     * @return this UriComponentsBuilder
     */
    public UriComponentsBuilder schemeSpecificPart(String ssp) {
        this.ssp = ssp;
        resetHierarchicalComponents();
        return this;
    }

    /**
     * Set the URI user info which may contain URI template variables, and
     * may also be {@code null} to clear the user info of this builder.
     *
     * @param userInfo the URI user info
     * @return this URI builder
     */
    public UriComponentsBuilder userInfo(String userInfo) {
        this.userInfo = userInfo;
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Set the URI host which may contain URI template variables, and may also
     * be {@code null} to clear the host of this builder.
     *
     * @param host the URI host
     * @return this URI builder
     */
    public UriComponentsBuilder host(String host) {
        this.host = host;
        if (host != null) {
            resetSchemeSpecificPart();
        }
        return this;
    }

    /**
     * Set the URI port. Passing {@code -1} will clear the port of this builder.
     *
     * @param port the URI port
     * @return this URI builder
     */
    public UriComponentsBuilder port(int port) {
        AssertTools.isTrue(port >= -1, "Port must be >= -1");
        this.port = String.valueOf(port);
        if (port > -1) {
            resetSchemeSpecificPart();
        }
        return this;
    }

    /**
     * Set the URI port. Use this method only when the port needs to be
     * parameterized with a URI variable. Otherwise use {@link #port(int)}.
     * Passing {@code null} will clear the port of this builder.
     *
     * @param port the URI port
     * @return this URI builder
     */
    public UriComponentsBuilder port(String port) {
        this.port = port;
        if (port != null) {
            resetSchemeSpecificPart();
        }
        return this;
    }

    /**
     * Append to the path of this builder.
     * <p>The given value is appended as-is to previous {@link #path(String) path}
     * values without inserting any additional slashes. For example:
     * <pre class="code">
     *
     * builder.path("/first-").path("value/").path("/{id}").build("123")
     *
     * // Results is "/first-value/123"
     * </pre>
     * <p>By contrast {@link #pathSegment(String...) pathSegment} does insert
     * slashes between individual path segments. For example:
     * <pre class="code">
     *
     * builder.pathSegment("first-value", "second-value").path("/")
     *
     * // Results is "/first-value/second-value/"
     * </pre>
     * <p>The resulting full path is normalized to eliminate duplicate slashes.
     * <p><strong>Note:</strong> When inserting a URI variable value that
     * contains slashes in a {@link #path(String) path}, whether those are
     * encoded depends on the configured encoding mode. For more details, see
     * {@link UriComponentsBuilder#encode()}.
     *
     * @param path the URI path
     * @return this URI builder
     */
    public UriComponentsBuilder path(String path) {
        this.pathBuilder.addPath(path);
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Append to the path using path segments. For example:
     * <pre class="code">
     *
     * builder.pathSegment("first-value", "second-value", "{id}").build("123")
     *
     * // Results is "/first-value/second-value/123"
     * </pre>
     * <p>If slashes are present in a path segment, they are encoded:
     * <pre class="code">
     *
     * builder.pathSegment("ba/z", "{id}").build("a/b")
     *
     * // Results is "/ba%2Fz/a%2Fb"
     * </pre>
     * To insert a trailing slash, use the {@link #path} builder method:
     * <pre class="code">
     *
     * builder.pathSegment("first-value", "second-value").path("/")
     *
     * // Results is "/first-value/second-value/"
     * </pre>
     * <p>Empty path segments are ignored and therefore duplicate slashes do not
     * appear in the resulting full path.
     *
     * @param pathSegments the URI path segments
     * @return this URI builder
     */
    public UriComponentsBuilder pathSegment(String... pathSegments) throws IllegalArgumentException {
        this.pathBuilder.addPathSegments(pathSegments);
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Override the current path.
     *
     * @param path the URI path, or {@code null} for an empty path
     * @return this URI builder
     */
    public UriComponentsBuilder replacePath(String path) {
        this.pathBuilder = new CompositePathComponentBuilder();
        if (path != null) {
            this.pathBuilder.addPath(path);
        }
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Parse the given query string into query parameters where parameters are
     * separated with {@code '&'} and their values, if any, with {@code '='}.
     * The query may contain URI template variables.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param query the query string
     * @return this URI builder
     */
    public UriComponentsBuilder query(String query) {
        if (query != null) {
            Matcher matcher = QUERY_PARAM_PATTERN.matcher(query);
            while (matcher.find()) {
                String name = matcher.group(1);
                String eq = matcher.group(2);
                String value = matcher.group(3);
                queryParam(name, (value != null ? value : (CharSequenceTools.isNotEmpty(eq) ? "" : null)));
            }
            resetSchemeSpecificPart();
        } else {
            this.queryParams.clear();
        }
        return this;
    }

    /**
     * Parse the given query string into query parameters where parameters are
     * separated with {@code '&'} and their values, if any, with {@code '='}.
     * The query may contain URI template variables.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param query the query string
     * @return this URI builder
     */
    public UriComponentsBuilder replaceQuery(String query) {
        this.queryParams.clear();
        if (query != null) {
            query(query);
            resetSchemeSpecificPart();
        }
        return this;
    }

    /**
     * Append the given query parameter. Both the parameter name and values may
     * contain URI template variables to be expanded later from values. If no
     * values are given, the resulting URI will contain the query parameter name
     * only, for example, {@code "?foo"} instead of {@code "?foo=bar"}.
     * <p><strong>Note:</strong> encoding, if applied, will only encode characters
     * that are illegal in a query parameter name or value such as {@code "="}
     * or {@code "&"}. All others that are legal as per syntax rules in
     * <a href="https://tools.ietf.org/html/rfc3986">RFC 3986</a> are not
     * encoded. This includes {@code "+"} which sometimes needs to be encoded
     * to avoid its interpretation as an encoded space. Stricter encoding may
     * be applied by using a URI template variable along with stricter encoding
     * on variable values.
     *
     * @param name   the query parameter name
     * @param values the query parameter values
     * @see #queryParam(String, Collection)
     * @return this URI builder
     */
    public UriComponentsBuilder queryParam(String name, Object... values) {
        AssertTools.notNull(name, "Name must not be null");
        if (!ArrayTools.isEmpty(values)) {
            for (Object value : values) {
                String valueAsString = getQueryParamValue(value);
                this.queryParams.add(name, valueAsString);
            }
        } else {
            this.queryParams.add(name, null);
        }
        resetSchemeSpecificPart();
        return this;
    }

    private String getQueryParamValue(Object value) {
        if (value != null) {
            if (value instanceof Optional<?>) {
                Optional<?> optional = (Optional<?>) value;
                return optional.map(Object::toString).orElse(null);
            } else {
                return value.toString();
            }
        }
        return null;
    }

    /**
     * Variant of {@link #queryParam(String, Object...)} with a Collection.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param name   the query parameter name
     * @param values the query parameter values
     * @return this URI builder
     * @see #queryParam(String, Object...)
     */
    public UriComponentsBuilder queryParam(String name, Collection<?> values) {
        return queryParam(name, (CollectionTools.isEmpty(values) ? EMPTY_VALUES : values.toArray()));
    }

    /**
     * Delegates to either {@link #queryParam(String, Object...)} or
     * {@link #queryParam(String, Collection)} if the given {@link Optional} has
     * a value, or else if it is empty, no query parameter is added at all.
     *
     * @param name  the query parameter name
     * @param value an Optional, either empty or holding the query parameter value.
     * @return this URI builder
     */
    public UriComponentsBuilder queryParamIfPresent(String name, Optional<?> value) {
        value.ifPresent(v -> {
            if (v instanceof Collection<?>) {
                Collection<?> values = (Collection<?>) v;
                queryParam(name, values);
            } else {
                queryParam(name, v);
            }
        });
        return this;
    }

    /**
     * Add multiple query parameters and values.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param params the params
     * @return this URI builder
     */
    public UriComponentsBuilder queryParams(MultiValueMap<String, String> params) {
        if (params != null) {
            this.queryParams.addAll(params);
            resetSchemeSpecificPart();
        }
        return this;
    }

    /**
     * Set the query parameter values replacing existing values, or if no
     * values are given, the query parameter is removed.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param name   the query parameter name
     * @param values the query parameter values
     * @return this URI builder
     * @see #replaceQueryParam(String, Collection)
     */
    public UriComponentsBuilder replaceQueryParam(String name, Object... values) {
        AssertTools.notNull(name, "Name must not be null");
        this.queryParams.remove(name);
        if (!ArrayTools.isEmpty(values)) {
            queryParam(name, values);
        }
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Variant of {@link #replaceQueryParam(String, Object...)} with a Collection.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param name   the query parameter name
     * @param values the query parameter values
     * @return this URI builder
     * @see #replaceQueryParam(String, Object...)
     */
    public UriComponentsBuilder replaceQueryParam(String name, Collection<?> values) {
        return replaceQueryParam(name, (CollectionTools.isEmpty(values) ? EMPTY_VALUES : values.toArray()));
    }

    /**
     * Set the query parameter values after removing all existing ones.
     * <p><strong>Note: </strong> please, review the Javadoc of
     * {@link #queryParam(String, Object...)} for further notes on the treatment
     * and encoding of individual query parameters.
     *
     * @param params the query parameter name
     * @return this URI builder
     */
    public UriComponentsBuilder replaceQueryParams(MultiValueMap<String, String> params) {
        this.queryParams.clear();
        if (params != null) {
            this.queryParams.putAll(params);
        }
        return this;
    }

    /**
     * Set the URI fragment. The given fragment may contain URI template variables,
     * and may also be {@code null} to clear the fragment of this builder.
     *
     * @param fragment the URI fragment
     * @return this URI builder
     */
    public UriComponentsBuilder fragment(String fragment) {
        if (fragment != null) {
            AssertTools.notEmpty(fragment, "Fragment must not be empty");
            this.fragment = fragment;
        } else {
            this.fragment = null;
        }
        return this;
    }

    /**
     * Configure URI variables to be expanded at build time.
     * <p>The provided variables may be a subset of all required ones. At build
     * time, the available ones are expanded, while unresolved URI placeholders
     * are left in place and can still be expanded later.
     * <p>In contrast to {@link UriComponents#expand(Map)} or
     * {@link #buildAndExpand(Map)}, this method is useful when you need to
     * supply URI variables without building the {@link UriComponents} instance
     * just yet, or perhaps pre-expand some shared default values such as host
     * and port.
     *
     * @param uriVariables the URI variables to use
     * @return this UriComponentsBuilder
     */
    public UriComponentsBuilder uriVariables(Map<String, Object> uriVariables) {
        this.uriVariables.putAll(uriVariables);
        return this;
    }

    private void resetHierarchicalComponents() {
        this.userInfo = null;
        this.host = null;
        this.port = null;
        this.pathBuilder = new CompositePathComponentBuilder();
        this.queryParams.clear();
    }

    private void resetSchemeSpecificPart() {
        this.ssp = null;
    }

    void resetPortIfDefaultForScheme() {
        if (this.scheme != null &&
                (((this.scheme.equals("http") || this.scheme.equals("ws")) && "80".equals(this.port)) ||
                        ((this.scheme.equals("https") || this.scheme.equals("wss")) && "443".equals(this.port)))) {
            port(null);
        }
    }


    /**
     * Public declaration of Object's {@code clone()} method.
     * Delegates to {@link #cloneBuilder()}.
     */
    @Override
    public Object clone() {
        return cloneBuilder();
    }

    /**
     * Clone this {@code UriComponentsBuilder}.
     *
     * @return the cloned {@code UriComponentsBuilder} object
     */
    public UriComponentsBuilder cloneBuilder() {
        return new UriComponentsBuilder(this);
    }


    /**
     * Enum to provide a choice of URI parsers to use in {@link #fromUriString(String, ParserType)}.
     */
    public enum ParserType {

        /**
         * This parser type expects URI's to conform to RFC 3986 syntax.
         */
        RFC,

        /**
         * This parser follows the
         * <a href="https://url.spec.whatwg.org/#url-parsing">URL parsing algorithm</a>
         * in the WhatWG URL Living standard that browsers implement to align on
         * lenient handling of user typed URL's that may not conform to RFC syntax.
         *
         * @see <a href="https://url.spec.whatwg.org">URL Living Standard</a>
         * @see <a href="https://github.com/web-platform-tests/wpt/tree/master/url">URL tests</a>
         */
        WHAT_WG

    }


    private interface PathComponentBuilder {

        PathComponent build();

        PathComponentBuilder cloneBuilder();
    }


    private static class CompositePathComponentBuilder implements PathComponentBuilder {

        private final Deque<PathComponentBuilder> builders = new ArrayDeque<>();

        public void addPathSegments(String... pathSegments) {
            if (!ArrayTools.isEmpty(pathSegments)) {
                PathSegmentComponentBuilder psBuilder = getLastBuilder(PathSegmentComponentBuilder.class);
                FullPathComponentBuilder fpBuilder = getLastBuilder(FullPathComponentBuilder.class);
                if (psBuilder == null) {
                    psBuilder = new PathSegmentComponentBuilder();
                    this.builders.add(psBuilder);
                    if (fpBuilder != null) {
                        fpBuilder.removeTrailingSlash();
                    }
                }
                psBuilder.append(pathSegments);
            }
        }

        public void addPath(String path) {
            if (CharSequenceTools.isNotEmpty(path)) {
                PathSegmentComponentBuilder psBuilder = getLastBuilder(PathSegmentComponentBuilder.class);
                FullPathComponentBuilder fpBuilder = getLastBuilder(FullPathComponentBuilder.class);
                if (psBuilder != null) {
                    path = (path.startsWith("/") ? path : "/" + path);
                }
                if (fpBuilder == null) {
                    fpBuilder = new FullPathComponentBuilder();
                    this.builders.add(fpBuilder);
                }
                fpBuilder.append(path);
            }
        }

        @SuppressWarnings("unchecked")
        private <T> T getLastBuilder(Class<T> builderClass) {
            if (!this.builders.isEmpty()) {
                PathComponentBuilder last = this.builders.getLast();
                if (builderClass.isInstance(last)) {
                    return (T) last;
                }
            }
            return null;
        }

        @Override
        public PathComponent build() {
            int size = this.builders.size();
            List<PathComponent> components = new ArrayList<>(size);
            for (PathComponentBuilder componentBuilder : this.builders) {
                PathComponent pathComponent = componentBuilder.build();
                if (pathComponent != null) {
                    components.add(pathComponent);
                }
            }
            if (components.isEmpty()) {
                return HierarchicalUriComponents.NULL_PATH_COMPONENT;
            }
            if (components.size() == 1) {
                return components.get(0);
            }
            return new HierarchicalUriComponents.PathComponentComposite(components);
        }

        @Override
        public CompositePathComponentBuilder cloneBuilder() {
            CompositePathComponentBuilder compositeBuilder = new CompositePathComponentBuilder();
            for (PathComponentBuilder builder : this.builders) {
                compositeBuilder.builders.add(builder.cloneBuilder());
            }
            return compositeBuilder;
        }
    }


    private static class FullPathComponentBuilder implements PathComponentBuilder {

        private final StringBuilder path = new StringBuilder();

        public void append(String path) {
            this.path.append(path);
        }

        @Override
        public PathComponent build() {
            if (CharSequenceTools.isEmpty(this.path)) {
                return null;
            }
            String sanitized = getSanitizedPath(this.path);
            return new HierarchicalUriComponents.FullPathComponent(sanitized);
        }

        private static String getSanitizedPath(final StringBuilder path) {
            int index = path.indexOf("//");
            if (index >= 0) {
                StringBuilder sanitized = new StringBuilder(path);
                while (index != -1) {
                    sanitized.deleteCharAt(index);
                    index = sanitized.indexOf("//", index);
                }
                return sanitized.toString();
            }
            return path.toString();
        }

        public void removeTrailingSlash() {
            int index = this.path.length() - 1;
            if (this.path.charAt(index) == '/') {
                this.path.deleteCharAt(index);
            }
        }

        @Override
        public FullPathComponentBuilder cloneBuilder() {
            FullPathComponentBuilder builder = new FullPathComponentBuilder();
            builder.append(this.path.toString());
            return builder;
        }
    }


    private static class PathSegmentComponentBuilder implements PathComponentBuilder {

        private final List<String> pathSegments = new ArrayList<>();

        public void append(String... pathSegments) {
            for (String pathSegment : pathSegments) {
                if (CharSequenceTools.isNotEmpty(pathSegment)) {
                    this.pathSegments.add(pathSegment);
                }
            }
        }

        @Override
        public PathComponent build() {
            return (this.pathSegments.isEmpty() ? null :
                    new HierarchicalUriComponents.PathSegmentComponent(this.pathSegments));
        }

        @Override
        public PathSegmentComponentBuilder cloneBuilder() {
            PathSegmentComponentBuilder builder = new PathSegmentComponentBuilder();
            builder.pathSegments.addAll(this.pathSegments);
            return builder;
        }
    }


    private enum EncodingHint {ENCODE_TEMPLATE, FULLY_ENCODED, NONE}
}
