package cn.bytengine.d.utils;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CollectionTools;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * Parser for URI's based on RFC 3986 syntax.
 *
 * @author Ban Tenio
 * @version 1.0
 * @see <a href="https://www.rfc-editor.org/info/rfc3986">RFC 3986</a>
 */
abstract class RfcUriParser {


    /**
     * Parse the given URI string.
     *
     * @param uri the input string to parse
     * @return {@link UriRecord} with the parsed components
     * @throws InvalidUrlException when the URI cannot be parsed, e.g. due to syntax errors
     */
    public static UriRecord parse(String uri) {
        return new InternalParser(uri).parse();
    }


    private static void verify(boolean expression, InternalParser parser, String message) {
        if (!expression) {
            fail(parser, message);
        }
    }

    private static void verifyIsHexDigit(char c, InternalParser parser, String message) {
        verify((c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F') || (c >= '0' && c <= '9'), parser, message);
    }

    private static void fail(InternalParser parser, String message) {
        throw new InvalidUrlException(message);
    }

    public static class UriRecord {
        private String scheme;
        private boolean isOpaque;
        private String user;
        private String host;
        private String port;
        private String path;
        private String query;
        private String fragment;

        /**
         * Empty URI components
         */
        public UriRecord() {
        }

        /**
         * Holds the parsed URI components.
         *
         * @param scheme   the scheme, for an absolute URI, or {@code null}
         * @param isOpaque if {@code true}, the path contains the remaining scheme-specific part
         * @param user     user information, if present in the authority
         * @param host     the host, if present in the authority
         * @param port     the port, if present in the authority
         * @param path     the path, if present
         * @param query    the query, if present
         * @param fragment the fragment, if present
         */
        public UriRecord(String scheme, boolean isOpaque, String user, String host, String port, String path, String query, String fragment) {
            this.scheme = scheme;
            this.isOpaque = isOpaque;
            this.user = user;
            this.host = host;
            this.port = port;
            this.path = path;
            this.query = query;
            this.fragment = fragment;
        }

        public String getScheme() {
            return scheme;
        }

        public UriRecord setScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public boolean isOpaque() {
            return isOpaque;
        }

        public UriRecord setOpaque(boolean opaque) {
            isOpaque = opaque;
            return this;
        }

        public String getUser() {
            return user;
        }

        public UriRecord setUser(String user) {
            this.user = user;
            return this;
        }

        public String getHost() {
            return host;
        }

        public UriRecord setHost(String host) {
            this.host = host;
            return this;
        }

        public String getPort() {
            return port;
        }

        public UriRecord setPort(String port) {
            this.port = port;
            return this;
        }

        public String getPath() {
            return path;
        }

        public UriRecord setPath(String path) {
            this.path = path;
            return this;
        }

        public String getQuery() {
            return query;
        }

        public UriRecord setQuery(String query) {
            this.query = query;
            return this;
        }

        public String getFragment() {
            return fragment;
        }

        public UriRecord setFragment(String fragment) {
            this.fragment = fragment;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            UriRecord uriRecord = (UriRecord) o;
            return isOpaque == uriRecord.isOpaque && Objects.equals(scheme, uriRecord.scheme) && Objects.equals(user, uriRecord.user) && Objects.equals(host, uriRecord.host) && Objects.equals(port, uriRecord.port) && Objects.equals(path, uriRecord.path) && Objects.equals(query, uriRecord.query) && Objects.equals(fragment, uriRecord.fragment);
        }

        @Override
        public int hashCode() {
            return Objects.hash(scheme, isOpaque, user, host, port, path, query, fragment);
        }

        @Override
        public String toString() {
            return "UriRecord{" +
                    "scheme='" + scheme + '\'' +
                    ", isOpaque=" + isOpaque +
                    ", user='" + user + '\'' +
                    ", host='" + host + '\'' +
                    ", port='" + port + '\'' +
                    ", path='" + path + '\'' +
                    ", query='" + query + '\'' +
                    ", fragment='" + fragment + '\'' +
                    '}';
        }
    }


    /**
     * Parse states with handling for each character.
     */
    private enum State {

        START {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                switch (c) {
                    case '/':
                        parser.advanceTo(HOST_OR_PATH, i);
                        break;
                    case ';':
                    case '.':
                        parser.advanceTo(PATH, i);
                        break;
                    case '%':
                        parser.markPercentEncoding().advanceTo(PATH, i);
                        break;
                    case '?':
                        parser.advanceTo(QUERY, i + 1);  // empty path
                        break;
                    case '#':
                        parser.advanceTo(FRAGMENT, i + 1);  // empty path
                        break;
                    case '*':
                        parser.advanceTo(WILDCARD);
                        break;
                    default:
                        if (parser.hasScheme()) {
                            parser.resolveIfOpaque().advanceTo(PATH, i);
                        } else {
                            parser.advanceTo(SCHEME_OR_PATH, i);
                        }
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.capturePath();
            }
        },

        HOST_OR_PATH {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                switch (c) {
                    case '/':
                        parser.componentIndex(i).captureHost().advanceTo(HOST, i + 1);  // empty host to start
                        break;
                    case '%':
                    case '@':
                    case ';':
                    case '?':
                    case '#':
                    case '.':
                        parser.index(--i);
                        parser.advanceTo(PATH);
                        break;
                    default:
                        parser.advanceTo(PATH);
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.capturePath();
            }
        },

        SCHEME_OR_PATH {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                switch (c) {
                    case ':':
                        parser.captureScheme().advanceTo(START);
                        break;
                    case '/':
                    case ';':
                        parser.advanceTo(PATH);
                        break;
                    case '%':
                        parser.markPercentEncoding().advanceTo(PATH);
                        break;
                    case '?':
                        parser.capturePath().advanceTo(QUERY, i + 1);
                        break;
                    case '#':
                        parser.capturePath().advanceTo(FRAGMENT);
                        break;
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.capturePath();
            }
        },

        HOST {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                switch (c) {
                    case '/':
                        parser.captureHost().advanceTo(PATH, i);
                        break;
                    case ':':
                        parser.captureHostIfNotEmpty().advanceTo(PORT, i + 1);
                        break;
                    case '?':
                        parser.captureHostIfNotEmpty().advanceTo(QUERY, i + 1);
                        break;
                    case '#':
                        parser.captureHostIfNotEmpty().advanceTo(FRAGMENT, i + 1);
                        break;
                    case '@':
                        parser.captureUser().componentIndex(i + 1);
                        break;
                    case '[':
                        verify(parser.isAtStartOfComponent(), parser, "Bad authority");
                        parser.advanceTo(IPV6);
                        break;
                    case '%':
                        parser.markPercentEncoding();
                        break;
                    default:
                        boolean isAllowed = (parser.processCurlyBrackets(c) ||
                                parser.countDownPercentEncodingInHost(c) ||
                                HierarchicalUriComponents.Type.URI.isUnreservedOrSubDelimiter(c));
                        verify(isAllowed, parser, "Bad authority");
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.captureHostIfNotEmpty();
            }
        },

        IPV6 {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                switch (c) {
                    case ']':
                        parser.index(++i);
                        parser.captureHost();
                        if (parser.hasNext()) {
                            if (parser.charAtIndex() == ':') {
                                parser.advanceTo(PORT, i + 1);
                            } else {
                                parser.advanceTo(PATH, i);
                            }
                        }
                        break;
                    case ':':
                        break;
                    default:
                        verifyIsHexDigit(c, parser, "Bad authority");
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                verify(parser.hasHost(), parser, "Bad authority");  // no closing ']'
            }
        },

        PORT {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                if (c == '@') {
                    verify(!parser.hasUser(), parser, "Bad authority");
                    parser.switchPortForFullPassword().advanceTo(HOST, i + 1);
                } else if (c == '/') {
                    parser.capturePort().advanceTo(PATH, i);
                } else if (c == '?' || c == '#') {
                    parser.capturePort().advanceTo((c == '?' ? QUERY : FRAGMENT), i + 1);
                } else if (!Character.isDigit(c)) {
                    if (parser.processCurlyBrackets(c)) {
                        return;
                    } else if (HierarchicalUriComponents.Type.URI.isUnreservedOrSubDelimiter(c) || c == '%') {
                        parser.switchPortForPassword().advanceTo(HOST);
                        return;
                    }
                    fail(parser, "Bad authority");
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.capturePort();
            }
        },

        PATH {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                if (!parser.countDownPercentEncodingInPath(c)) {
                    switch (c) {
                        case '?':
                            if (parser.isOpaque()) {
                                break;
                            }
                            parser.capturePath().advanceTo(QUERY, i + 1);
                            break;
                        case '#':
                            parser.capturePath().advanceTo(FRAGMENT, i + 1);
                            break;
                        case '%':
                            parser.markPercentEncoding();
                            break;
                    }
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.capturePath();
            }
        },

        QUERY {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                if (c == '#') {
                    parser.captureQuery().advanceTo(FRAGMENT, i + 1);
                }
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.captureQuery();
            }
        },

        FRAGMENT {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.captureFragmentIfNotEmpty();
            }
        },

        WILDCARD {
            @Override
            public void handleNext(InternalParser parser, char c, int i) {
                fail(parser, "Bad character '*'");
            }

            @Override
            public void handleEnd(InternalParser parser) {
                parser.capturePath();
            }
        };

        /**
         * Method to handle each character from the input string.
         *
         * @param parser provides access to parsing state, and helper methods
         * @param c      the current character
         * @param i      the current index
         */
        public abstract void handleNext(InternalParser parser, char c, int i);

        /**
         * Finalize handling at the end of the input.
         *
         * @param parser provides access to parsing state, and helper methods
         */
        public abstract void handleEnd(InternalParser parser);

    }


    /**
     * Delegates to {@link State}s for handling of character one by one, holds
     * parsing state, and exposes helper methods.
     */
    private static class InternalParser {

        private static final Set<String> hierarchicalSchemes = CollectionTools.newHashSet("ftp", "file", "http", "https", "ws", "wss");

        private final String uri;

        private String scheme;

        String user;

        private String host;

        private String path;

        String port;

        String query;

        String fragment;

        private State state = State.START;

        private int index;

        private int componentIndex;

        boolean isOpaque;

        private int remainingPercentEncodedChars;

        private boolean inUtf16Sequence;

        private boolean inPassword;

        private int openCurlyBracketCount;


        public InternalParser(String uri) {
            this.uri = uri;
        }

        // Check internal state

        public boolean hasScheme() {
            return (this.scheme != null);
        }

        public boolean isOpaque() {
            return this.isOpaque;
        }

        public boolean hasUser() {
            return (this.user != null);
        }

        public boolean hasHost() {
            return (this.host != null);
        }

        public boolean isAtStartOfComponent() {
            return (this.index == this.componentIndex);
        }

        // Top-level parse loop, iterate over chars and delegate to states

        public UriRecord parse() {
            AssertTools.isTrue(this.state == State.START && this.index == 0, "Internal Error");

            while (hasNext()) {
                this.state.handleNext(this, charAtIndex(), this.index);
                this.index++;
            }

            this.state.handleEnd(this);

            return new UriRecord(this.scheme, this.isOpaque,
                    this.user, this.host, this.port, this.path, this.query, this.fragment);
        }

        public boolean hasNext() {
            return (this.index < this.uri.length());
        }

        public char charAtIndex() {
            return this.uri.charAt(this.index);
        }

        // Transitions and index updates

        public void advanceTo(State state) {
            this.state = state;
            this.openCurlyBracketCount = 0;
        }

        public void advanceTo(State state, int componentIndex) {
            this.componentIndex = componentIndex;
            advanceTo(state);
        }

        public InternalParser componentIndex(int componentIndex) {
            this.componentIndex = componentIndex;
            return this;
        }

        public void index(int index) {
            this.index = index;
        }

        // Component capture

        public InternalParser resolveIfOpaque() {
            this.isOpaque = (this.uri.charAt(this.index) != '/' && !hierarchicalSchemes.contains(this.scheme));
            return this;
        }

        public InternalParser captureScheme() {
            String scheme = captureComponent("scheme");
            this.scheme = (!scheme.contains("{") ? scheme.toLowerCase(Locale.ROOT) : scheme);
            return this;
        }

        public InternalParser captureUser() {
            this.inPassword = false;
            this.user = captureComponent("user");
            return this;
        }

        public InternalParser captureHost() {
            verify(this.remainingPercentEncodedChars == 0 && !this.inPassword, this, "Bad authority");
            this.host = captureComponent("host");
            return this;
        }

        public InternalParser captureHostIfNotEmpty() {
            if (this.index > this.componentIndex) {
                captureHost();
            }
            return this;
        }

        public InternalParser capturePort() {
            verify(this.openCurlyBracketCount == 0, this, "Bad authority");
            this.port = captureComponent("port");
            return this;
        }

        public InternalParser capturePath() {
            this.path = captureComponent("path");
            return this;
        }

        public InternalParser captureQuery() {
            this.query = captureComponent("query");
            return this;
        }

        public void captureFragmentIfNotEmpty() {
            if (this.index > this.componentIndex + 1) {
                this.fragment = captureComponent("fragment");
            }
        }

        public InternalParser switchPortForFullPassword() {
            this.user = this.host + ":" + captureComponent();
            return this;
        }

        public InternalParser switchPortForPassword() {
            this.inPassword = true;
            if (this.host != null) {
                this.componentIndex = (this.componentIndex - this.host.length() - 1);
                this.host = null;
            }
            return this;
        }

        private String captureComponent(String logPrefix) {
            return captureComponent();
        }

        private String captureComponent() {
            return this.uri.substring(this.componentIndex, this.index);
        }

        public InternalParser markPercentEncoding() {
            verify(this.remainingPercentEncodedChars == 0, this, "Bad encoding");
            this.remainingPercentEncodedChars = 2;
            this.inUtf16Sequence = false;
            return this;
        }

        // Encoding and curly bracket handling

        /**
         * Return true if character was part of percent encoded sequence.
         */
        public boolean countDownPercentEncodingInHost(char c) {
            if (this.remainingPercentEncodedChars == 0) {
                return false;
            }
            this.remainingPercentEncodedChars--;
            verifyIsHexDigit(c, this, "Bad authority");
            return true;
        }

        /**
         * Return true if character was part of percent encoded sequence.
         */
        public boolean countDownPercentEncodingInPath(char c) {
            if (this.remainingPercentEncodedChars == 0) {
                return false;
            }
            if (this.remainingPercentEncodedChars == 2 && c == 'u' && !this.inUtf16Sequence) {
                this.inUtf16Sequence = true;
                this.remainingPercentEncodedChars = 4;
                return true;
            }
            this.remainingPercentEncodedChars--;
            verifyIsHexDigit(c, this, "Bad path");
            this.inUtf16Sequence &= (this.remainingPercentEncodedChars > 0);
            return true;
        }

        /**
         * Return true if the character is within curly brackets.
         */
        public boolean processCurlyBrackets(char c) {
            if (c == '{') {
                this.openCurlyBracketCount++;
                return true;
            } else if (c == '}') {
                if (this.openCurlyBracketCount > 0) {
                    this.openCurlyBracketCount--;
                    return true;
                }
                return false;
            }
            return (this.openCurlyBracketCount > 0);
        }

        @Override
        public String toString() {
            return "[State=" + this.state + ", index=" + this.index + ", componentIndex=" + this.componentIndex +
                    ", uri='" + this.uri + "', scheme='" + this.scheme + "', user='" + this.user +
                    "', host='" + this.host + "', path='" + this.path + "', port='" + this.port +
                    "', query='" + this.query + "', fragment='" + this.fragment + "']";
        }
    }

}
