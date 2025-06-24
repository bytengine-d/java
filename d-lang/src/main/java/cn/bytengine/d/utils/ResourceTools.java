package cn.bytengine.d.utils;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.PathTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.util.Locale;

/**
 * Utility methods for resolving resource locations to files in the
 * file system. Mainly for internal use within the framework.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ResourceTools {
    private ResourceTools() {
    }

    /**
     * Pseudo URL prefix for loading from the class path: "classpath:".
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * URL prefix for loading from the file system: "file:".
     */
    public static final String FILE_URL_PREFIX = "file:";

    /**
     * URL prefix for loading from a jar file: "jar:".
     */
    public static final String JAR_URL_PREFIX = "jar:";

    /**
     * URL prefix for loading from a war file on Tomcat: "war:".
     */
    public static final String WAR_URL_PREFIX = "war:";

    /**
     * URL protocol for a file in the file system: "file".
     */
    public static final String URL_PROTOCOL_FILE = "file";

    /**
     * URL protocol for an entry from a jar file: "jar".
     */
    public static final String URL_PROTOCOL_JAR = "jar";

    /**
     * URL protocol for an entry from a war file: "war".
     */
    public static final String URL_PROTOCOL_WAR = "war";

    /**
     * URL protocol for an entry from a zip file: "zip".
     */
    public static final String URL_PROTOCOL_ZIP = "zip";

    /**
     * URL protocol for an entry from a WebSphere jar file: "wsjar".
     */
    public static final String URL_PROTOCOL_WSJAR = "wsjar";

    /**
     * URL protocol for an entry from a JBoss jar file: "vfszip".
     */
    public static final String URL_PROTOCOL_VFSZIP = "vfszip";

    /**
     * URL protocol for a JBoss file system resource: "vfsfile".
     */
    public static final String URL_PROTOCOL_VFSFILE = "vfsfile";

    /**
     * URL protocol for a general JBoss VFS resource: "vfs".
     */
    public static final String URL_PROTOCOL_VFS = "vfs";

    /**
     * File extension for a regular jar file: ".jar".
     */
    public static final String JAR_FILE_EXTENSION = ".jar";

    /**
     * Separator between JAR URL and file path within the JAR: "!/".
     */
    public static final String JAR_URL_SEPARATOR = "!/";

    /**
     * Special separator between WAR URL and jar part on Tomcat.
     */
    public static final String WAR_URL_SEPARATOR = "*/";

    /**
     * Create a URI instance for the given URL,
     * replacing spaces with "%20" URI encoding first.
     *
     * @param url the URL to convert into a URI instance
     * @return the URI instance
     * @throws URISyntaxException if the URL wasn't a valid URI
     * @see java.net.URL#toURI()
     * @see #toURI(String)
     */
    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * Create a URI instance for the given location String,
     * replacing spaces with "%20" URI encoding first.
     *
     * @param location the location String to convert into a URI instance
     * @return the URI instance
     * @throws URISyntaxException if the location wasn't a valid URI
     * @see #toURI(URL)
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(CharSequenceTools.replace(location, " ", "%20"));
    }

    /**
     * Determine whether the given URL points to a resource in the file system,
     * i.e. has protocol "file", "vfsfile" or "vfs".
     *
     * @param url the URL to check
     * @return whether the URL has been identified as a file system URL
     * @see #isJarURL(URL)
     */
    public static boolean isFileURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_FILE.equals(protocol) || URL_PROTOCOL_VFSFILE.equals(protocol) ||
                URL_PROTOCOL_VFS.equals(protocol));
    }

    /**
     * Resolve the given resource URL to a {@code java.io.File},
     * i.e. to a file in the file system.
     *
     * @param resourceUrl the resource URL to resolve
     * @param description a description of the original resource that
     *                    the URL was created for (for example, a class path location)
     * @return a corresponding File object
     * @throws FileNotFoundException if the URL cannot be resolved to
     *                               a file in the file system
     */
    public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
        AssertTools.notNull(resourceUrl, "Resource URL must not be null");
        if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
            throw new FileNotFoundException(
                    description + " cannot be resolved to absolute file path " +
                            "because it does not reside in the file system: " + resourceUrl);
        }
        try {
            // URI decoding for special characters such as spaces.
            return new File(toURI(resourceUrl).getSchemeSpecificPart());
        } catch (URISyntaxException ex) {
            // Fallback for URLs that are not valid URIs (should hardly ever happen).
            return new File(resourceUrl.getFile());
        }
    }

    /**
     * Resolve the given resource URL to a {@code java.io.File},
     * i.e. to a file in the file system.
     *
     * @param resourceUrl the resource URL to resolve
     * @return a corresponding File object
     * @throws FileNotFoundException if the URL cannot be resolved to
     *                               a file in the file system
     * @see #getFile(URL, String)
     */
    public static File getFile(URL resourceUrl) throws FileNotFoundException {
        return getFile(resourceUrl, "URL");
    }

    /**
     * Resolve the given resource URI to a {@code java.io.File},
     * i.e. to a file in the file system.
     *
     * @param resourceUri the resource URI to resolve
     * @return a corresponding File object
     * @throws FileNotFoundException if the URL cannot be resolved to
     *                               a file in the file system
     * @see #getFile(URI, String)
     */
    public static File getFile(URI resourceUri) throws FileNotFoundException {
        return getFile(resourceUri, "URI");
    }

    /**
     * Resolve the given resource URI to a {@code java.io.File},
     * i.e. to a file in the file system.
     *
     * @param resourceUri the resource URI to resolve
     * @param description a description of the original resource that
     *                    the URI was created for (for example, a class path location)
     * @return a corresponding File object
     * @throws FileNotFoundException if the URL cannot be resolved to
     *                               a file in the file system
     */
    public static File getFile(URI resourceUri, String description) throws FileNotFoundException {
        AssertTools.notNull(resourceUri, "Resource URI must not be null");
        if (!URL_PROTOCOL_FILE.equals(resourceUri.getScheme())) {
            throw new FileNotFoundException(
                    description + " cannot be resolved to absolute file path " +
                            "because it does not reside in the file system: " + resourceUri);
        }
        return new File(resourceUri.getSchemeSpecificPart());
    }

    /**
     * Determine whether the given URL points to a resource in a jar file
     * &mdash; for example, whether the URL has protocol "jar", "war, "zip",
     * "vfszip", or "wsjar".
     *
     * @param url the URL to check
     * @return whether the URL has been identified as a JAR URL
     * @see #isJarFileURL(URL)
     */
    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_WAR.equals(protocol) ||
                URL_PROTOCOL_ZIP.equals(protocol) || URL_PROTOCOL_VFSZIP.equals(protocol) ||
                URL_PROTOCOL_WSJAR.equals(protocol));
    }

    /**
     * Determine whether the given URL points to a jar file itself,
     * that is, has protocol "file" and ends with the ".jar" extension.
     *
     * @param url the URL to check
     * @return whether the URL has been identified as a JAR file URL
     * @see #extractJarFileURL(URL)
     */
    public static boolean isJarFileURL(URL url) {
        return (URL_PROTOCOL_FILE.equals(url.getProtocol()) &&
                url.getPath().toLowerCase(Locale.ROOT).endsWith(JAR_FILE_EXTENSION));
    }

    /**
     * Extract the URL for the actual jar file from the given URL
     * (which may point to a resource in a jar file or to a jar file itself).
     *
     * @param jarUrl the original URL
     * @return the URL for the actual jar file
     * @throws MalformedURLException if no valid jar file URL could be extracted
     * @see #extractArchiveURL(URL)
     */
    public static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
        String urlFile = jarUrl.getFile();
        int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
        if (separatorIndex != -1) {
            String jarFile = urlFile.substring(0, separatorIndex);
            try {
                return toURL(jarFile);
            } catch (MalformedURLException ex) {
                // Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
                // This usually indicates that the jar file resides in the file system.
                if (!jarFile.startsWith("/")) {
                    jarFile = "/" + jarFile;
                }
                return toURL(FILE_URL_PREFIX + jarFile);
            }
        } else {
            return jarUrl;
        }
    }

    /**
     * Extract the URL for the outermost archive from the given jar/war URL
     * (which may point to a resource in a jar file or to a jar file itself).
     * <p>In the case of a jar file nested within a war file, this will return
     * a URL to the war file since that is the one resolvable in the file system.
     *
     * @param jarUrl the original URL
     * @return the URL for the actual jar file
     * @throws MalformedURLException if no valid jar file URL could be extracted
     * @see #extractJarFileURL(URL)
     */
    public static URL extractArchiveURL(URL jarUrl) throws MalformedURLException {
        String urlFile = jarUrl.getFile();

        int endIndex = urlFile.indexOf(WAR_URL_SEPARATOR);
        if (endIndex != -1) {
            // Tomcat's "war:file:...mywar.war*/WEB-INF/lib/myjar.jar!/myentry.txt"
            String warFile = urlFile.substring(0, endIndex);
            if (URL_PROTOCOL_WAR.equals(jarUrl.getProtocol())) {
                return toURL(warFile);
            }
            int startIndex = warFile.indexOf(WAR_URL_PREFIX);
            if (startIndex != -1) {
                return toURL(warFile.substring(startIndex + WAR_URL_PREFIX.length()));
            }
        }

        // Regular "jar:file:...myjar.jar!/myentry.txt"
        return extractJarFileURL(jarUrl);
    }

    /**
     * Create a clean URL instance for the given location String,
     * going through URI construction and then URL conversion.
     *
     * @param location the location String to convert into a URL instance
     * @return the URL instance
     * @throws MalformedURLException if the location wasn't a valid URL
     * @see java.net.URI#toURL()
     * @see #toURI(String)
     */
    public static URL toURL(String location) throws MalformedURLException {
        try {
            // Prefer URI construction with toURL conversion (as of 6.1)
            return toURI(PathTools.cleanPath(location)).toURL();
        } catch (URISyntaxException | IllegalArgumentException ex) {
            // Lenient fallback to deprecated URL constructor,
            // e.g. for decoded location Strings with percent characters.
            return new URL(location);
        }
    }

    /**
     * Create a clean URL instance for the given root URL and relative path,
     * going through URI construction and then URL conversion.
     *
     * @param root         the root URL to start from
     * @param relativePath the relative path to apply
     * @return the relative URL instance
     * @throws MalformedURLException if the end result is not a valid URL
     * @see #toURL(String)
     * @see PathTools#applyRelativePath
     */
    public static URL toRelativeURL(URL root, String relativePath) throws MalformedURLException {
        // # can appear in filenames, java.net.URL should not treat it as a fragment
        relativePath = CharSequenceTools.replace(relativePath, "#", "%23");

        // Retain original URL instance, potentially including custom URLStreamHandler.
        return new URL(root, PathTools.cleanPath(PathTools.applyRelativePath(root.toString(), relativePath)));
    }

    /**
     * Set the {@link URLConnection#setUseCaches "useCaches"} flag on the
     * given connection, preferring {@code false} but leaving the flag at
     * its JVM default value for jar resources (typically {@code true}).
     *
     * @param con the URLConnection to set the flag on
     * @see URLConnection#setUseCaches
     */
    public static void useCachesIfNecessary(URLConnection con) {
        if (!(con instanceof JarURLConnection)) {
            con.setUseCaches(false);
        }
    }
}
