package cn.bytengine.d.utils;

/**
 * TODO
 */

import cn.bytengine.d.lang.FileTools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

/**
 * Interface for a resource descriptor that abstracts from the actual
 * type of underlying resource, such as a file or class path resource.
 *
 * <p>An InputStream can be opened for every resource if it exists in
 * physical form, but a URL or File handle can just be returned for
 * certain resources. The actual behavior is implementation-specific.
 *
 * @author Ban Tenio
 * @version 1.0
 * @see #getInputStream()
 * @see #getURL()
 * @see #getURI()
 * @see #getFile()
 * @see WritableResource
 * @see ContextResource
 * @see UrlResource
 * @see FileUrlResource
 * @see FileSystemResource
 * @see ClassPathResource
 * @see ByteArrayResource
 * @see InputStreamResource
 */
public interface Resource extends InputStreamSource {

    /**
     * Determine whether this resource actually exists in physical form.
     * <p>This method performs a definitive existence check, whereas the
     * existence of a {@code Resource} handle only guarantees a valid
     * descriptor handle.
     *
     * @return TODO
     */
    boolean exists();

    /**
     * Indicate whether non-empty contents of this resource can be read via
     * {@link #getInputStream()}.
     * <p>Will be {@code true} for typical resource descriptors that exist
     * since it strictly implies {@link #exists()} semantics as of 5.1.
     * Note that actual content reading may still fail when attempted.
     * However, a value of {@code false} is a definitive indication
     * that the resource content cannot be read.
     * @see #getInputStream()
     * @see #exists()
     *
     * @return TODO
     */
    default boolean isReadable() {
        return exists();
    }

    /**
     * Indicate whether this resource represents a handle with an open stream.
     * If {@code true}, the InputStream cannot be read multiple times,
     * and must be read and closed to avoid resource leaks.
     * <p>Will be {@code false} for typical resource descriptors.
     *
     * @return TODO
     */
    default boolean isOpen() {
        return false;
    }

    /**
     * Determine whether this resource represents a file in a file system.
     * <p>A value of {@code true} strongly suggests (but does not guarantee)
     * that a {@link #getFile()} call will succeed.
     * <p>This is conservatively {@code false} by default.
     * @see #getFile()
     *
     * @return TODO
     */
    default boolean isFile() {
        return false;
    }

    /**
     * Return a URL handle for this resource.
     * @throws IOException if the resource cannot be resolved as URL,
     * i.e. if the resource is not available as a descriptor
     *
     * @return TODO
     */
    URL getURL() throws IOException;

    /**
     * Return a URI handle for this resource.
     * @throws IOException if the resource cannot be resolved as URI,
     * i.e. if the resource is not available as a descriptor
     *
     * @return TODO
     */
    URI getURI() throws IOException;

    /**
     * Return a File handle for this resource.
     * @throws java.io.FileNotFoundException if the resource cannot be resolved as
     * absolute file path, i.e. if the resource is not available in a file system
     * @throws IOException in case of general resolution/reading failures
     * @see #getInputStream()
     *
     * @return TODO
     */
    File getFile() throws IOException;

    /**
     * Return a {@link ReadableByteChannel}.
     * <p>It is expected that each call creates a <i>fresh</i> channel.
     * <p>The default implementation returns {@link Channels#newChannel(InputStream)}
     * with the result of {@link #getInputStream()}.
     * @return the byte channel for the underlying resource (must not be {@code null})
     * @throws java.io.FileNotFoundException if the underlying resource doesn't exist
     * @throws IOException if the content channel could not be opened
     * @see #getInputStream()
     */
    default ReadableByteChannel readableChannel() throws IOException {
        return Channels.newChannel(getInputStream());
    }

    /**
     * Return the contents of this resource as a byte array.
     * @return the contents of this resource as byte array
     * @throws java.io.FileNotFoundException if the resource cannot be resolved as
     * absolute file path, i.e. if the resource is not available in a file system
     * @throws IOException in case of general resolution/reading failures
     */
    default byte[] getContentAsByteArray() throws IOException {
        return FileTools.copyToByteArray(getInputStream());
    }

    /**
     * Return the contents of this resource as a string, using the specified charset.
     * @param charset the charset to use for decoding
     * @return the contents of this resource as a {@code String}
     * @throws java.io.FileNotFoundException if the resource cannot be resolved as
     * absolute file path, i.e. if the resource is not available in a file system
     * @throws IOException in case of general resolution/reading failures
     */
    default String getContentAsString(Charset charset) throws IOException {
        return FileTools.copyToString(new InputStreamReader(getInputStream(), charset));
    }

    /**
     * Determine the content length for this resource.
     * @throws IOException if the resource cannot be resolved
     * (in the file system or as some other known physical resource type)
     *
     * @return TODO
     */
    long contentLength() throws IOException;

    /**
     * Determine the last-modified timestamp for this resource.
     * @throws IOException if the resource cannot be resolved
     * (in the file system or as some other known physical resource type)
     *
     * @return TODO
     */
    long lastModified() throws IOException;

    /**
     * Create a resource relative to this resource.
     * @param relativePath the relative path (relative to this resource)
     * @return the resource handle for the relative resource
     * @throws IOException if the relative resource cannot be determined
     */
    Resource createRelative(String relativePath) throws IOException;

    /**
     * Determine the filename for this resource &mdash; typically the last
     * part of the path &mdash; for example, {@code "myfile.txt"}.
     * <p>Returns {@code null} if this type of resource does not
     * have a filename.
     * <p>Implementations are encouraged to return the filename unencoded.
     *
     * @return TODO
     */
    String getFilename();

    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     * <p>Implementations are also encouraged to return this value
     * from their {@code toString} method.
     * @see Object#toString()
     *
     * @return TODO
     */
    String getDescription();

}