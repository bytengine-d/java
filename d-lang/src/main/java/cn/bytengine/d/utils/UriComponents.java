package cn.bytengine.d.utils;

import cn.bytengine.d.collection.MultiValueMap;
import cn.bytengine.d.lang.AssertTools;

import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an immutable collection of URI components, mapping component type to
 * String values. Contains convenience getters for all components. Effectively similar
 * to {@link java.net.URI}, but with more powerful encoding options and support for
 * URI template variables.
 *
 * @author Ban Tenio
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class UriComponents implements Serializable {

    /**
     * Captures URI template variable names.
     */
    private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

    /**
     * TODO
     */
    private final String scheme;

    /**
     * TODO
     */
    private final String fragment;

    /**
     * 给定Scheme和Fragment创建UriComponents
     *
     * @param scheme   scheme
     * @param fragment fragment
     */
    protected UriComponents(String scheme, String fragment) {
        this.scheme = scheme;
        this.fragment = fragment;
    }
    // Component getters

    /**
     * Return the scheme. Can be {@code null}.
     *
     * @return scheme
     */
    public final String getScheme() {
        return this.scheme;
    }

    /**
     * Return the fragment. Can be {@code null}.
     *
     * @return fragment
     */
    public final String getFragment() {
        return this.fragment;
    }

    /**
     * Return the scheme specific part. Can be {@code null}.
     *
     * @return schemeSpecificPart
     */
    public abstract String getSchemeSpecificPart();

    /**
     * Return the user info. Can be {@code null}.
     *
     * @return userInfo
     */
    public abstract String getUserInfo();

    /**
     * Return the host. Can be {@code null}.
     *
     * @return host
     */
    public abstract String getHost();

    /**
     * Return the port. {@code -1} if no port has been set.
     *
     * @return port
     */
    public abstract int getPort();

    /**
     * Return the path. Can be {@code null}.
     *
     * @return path
     */
    public abstract String getPath();

    /**
     * Return the list of path segments. Empty if no path has been set.
     *
     * @return pathSegments
     */
    public abstract List<String> getPathSegments();

    /**
     * Return the query. Can be {@code null}.
     *
     * @return query
     */
    public abstract String getQuery();

    /**
     * Return the map of query parameters. Empty if no query has been set.
     *
     * @return queryParams
     */
    public abstract MultiValueMap<String, String> getQueryParams();


    /**
     * Invoke this <em>after</em> expanding URI variables to encode the
     * resulting URI component values.
     * <p>In comparison to {@link UriComponentsBuilder#encode()}, this method
     * <em>only</em> replaces non-ASCII and illegal (within a given URI
     * component type) characters, but not characters with reserved meaning.
     * For most cases, {@link UriComponentsBuilder#encode()} is more likely
     * to give the expected result.
     *
     * @return UriComponents
     * @see UriComponentsBuilder#encode()
     */
    public final UriComponents encode() {
        return encode(StandardCharsets.UTF_8);
    }

    /**
     * A variant of {@link #encode()} with a charset other than "UTF-8".
     *
     * @param charset the charset to use for encoding
     * @return UriComponents
     * @see UriComponentsBuilder#encode(Charset)
     */
    public abstract UriComponents encode(Charset charset);

    /**
     * Replace all URI template variables with the values from a given map.
     * <p>The given map keys represent variable names; the corresponding values
     * represent variable values. The order of variables is not significant.
     *
     * @param uriVariables the map of URI variables
     * @return the expanded URI components
     */
    public final UriComponents expand(Map<String, ?> uriVariables) {
        AssertTools.notNull(uriVariables, "'uriVariables' must not be null");
        return expandInternal(new MapTemplateVariables(uriVariables));
    }

    /**
     * Replace all URI template variables with the values from a given array.
     * <p>The given array represents variable values. The order of variables is significant.
     *
     * @param uriVariableValues the URI variable values
     * @return the expanded URI components
     */
    public final UriComponents expand(Object... uriVariableValues) {
        AssertTools.notNull(uriVariableValues, "'uriVariableValues' must not be null");
        return expandInternal(new VarArgsTemplateVariables(uriVariableValues));
    }

    /**
     * Replace all URI template variables with the values from the given
     * {@link UriTemplateVariables}.
     *
     * @param uriVariables the URI template values
     * @return the expanded URI components
     */
    public final UriComponents expand(UriTemplateVariables uriVariables) {
        AssertTools.notNull(uriVariables, "'uriVariables' must not be null");
        return expandInternal(uriVariables);
    }

    /**
     * Replace all URI template variables with the values from the given {@link
     * UriTemplateVariables}.
     *
     * @param uriVariables the URI template values
     * @return the expanded URI components
     */
    abstract UriComponents expandInternal(UriTemplateVariables uriVariables);

    /**
     * Normalize the path removing sequences like "path/..". Note that
     * normalization is applied to the full path, and not to individual path
     * segments.
     *
     * @return normalize
     */
    public abstract UriComponents normalize();

    /**
     * Concatenate all URI components to return the fully formed URI String.
     * <p>This method amounts to simple String concatenation of the current
     * URI component values and as such the result may contain illegal URI
     * characters, for example if URI variables have not been expanded or if
     * encoding has not been applied via {@link UriComponentsBuilder#encode()}
     * or {@link #encode()}.
     *
     * @return uri string
     */
    public abstract String toUriString();

    /**
     * Create a {@link URI} from this instance as follows:
     * <p>If the current instance is {@link #encode() encoded}, form the full
     * URI String via {@link #toUriString()}, and then pass it to the single
     * argument {@link URI} constructor which preserves percent encoding.
     * <p>If not yet encoded, pass individual URI component values to the
     * multi-argument {@link URI} constructor which quotes illegal characters
     * that cannot appear in their respective URI component.
     *
     * @return uri
     */
    public abstract URI toUri();

    /**
     * A simple pass-through to {@link #toUriString()}.
     *
     * @return uri string
     */
    @Override
    public final String toString() {
        return toUriString();
    }

    /**
     * Set all components of the given UriComponentsBuilder.
     *
     * @param builder UriComponentsBuilder
     */
    protected abstract void copyToUriComponentsBuilder(UriComponentsBuilder builder);


    // Static expansion helpers

    static String expandUriComponent(String source, UriTemplateVariables uriVariables) {
        return expandUriComponent(source, uriVariables, null);
    }

    static String expandUriComponent(String source, UriTemplateVariables uriVariables,
                                     UnaryOperator<String> encoder) {

        if (source == null) {
            return null;
        }
        if (source.indexOf('{') == -1) {
            return source;
        }
        if (source.indexOf(':') != -1) {
            source = sanitizeSource(source);
        }
        Matcher matcher = NAMES_PATTERN.matcher(source);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            String varName = getVariableName(match);
            Object varValue = uriVariables.getValue(varName);
            if (UriTemplateVariables.SKIP_VALUE.equals(varValue)) {
                continue;
            }
            String formatted = getVariableValueAsString(varValue);
            formatted = encoder != null ? encoder.apply(formatted) : Matcher.quoteReplacement(formatted);
            matcher.appendReplacement(sb, formatted);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Remove nested "{}" such as in URI vars with regular expressions.
     */
    private static String sanitizeSource(String source) {
        int level = 0;
        int lastCharIndex = 0;
        char[] chars = new char[source.length()];
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == '{') {
                level++;
            }
            if (c == '}') {
                level--;
            }
            if (level > 1 || (level == 1 && c == '}')) {
                continue;
            }
            chars[lastCharIndex++] = c;
        }
        return new String(chars, 0, lastCharIndex);
    }

    private static String getVariableName(String match) {
        int colonIdx = match.indexOf(':');
        return (colonIdx != -1 ? match.substring(0, colonIdx) : match);
    }

    private static String getVariableValueAsString(Object variableValue) {
        return (variableValue != null ? variableValue.toString() : "");
    }


    /**
     * Defines the contract for URI Template variables.
     *
     * @see HierarchicalUriComponents#expand
     */
    public interface UriTemplateVariables {

        /**
         * Constant for a value that indicates a URI variable name should be
         * ignored and left as is. This is useful for partial expanding of some
         * but not all URI variables.
         */
        Object SKIP_VALUE = UriTemplateVariables.class;

        /**
         * Get the value for the given URI variable name.
         * If the value is {@code null}, an empty String is expanded.
         * If the value is {@link #SKIP_VALUE}, the URI variable is not expanded.
         *
         * @param name the variable name
         * @return the variable value, possibly {@code null} or {@link #SKIP_VALUE}
         */
        Object getValue(String name);
    }


    /**
     * URI template variables backed by a map.
     */
    private static class MapTemplateVariables implements UriTemplateVariables {

        private final Map<String, ?> uriVariables;

        public MapTemplateVariables(Map<String, ?> uriVariables) {
            this.uriVariables = uriVariables;
        }

        @Override
        public Object getValue(String name) {
            if (!this.uriVariables.containsKey(name)) {
                throw new IllegalArgumentException("Map has no value for '" + name + "'");
            }
            return this.uriVariables.get(name);
        }
    }


    /**
     * URI template variables backed by a variable argument array.
     */
    private static class VarArgsTemplateVariables implements UriTemplateVariables {

        private final Iterator<Object> valueIterator;

        public VarArgsTemplateVariables(Object... uriVariableValues) {
            this.valueIterator = Arrays.asList(uriVariableValues).iterator();
        }

        @Override
        public Object getValue(String name) {
            if (!this.valueIterator.hasNext()) {
                throw new IllegalArgumentException("Not enough variable values available to expand '" + name + "'");
            }
            return this.valueIterator.next();
        }
    }

}
