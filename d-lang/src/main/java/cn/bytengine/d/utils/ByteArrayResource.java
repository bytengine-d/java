package cn.bytengine.d.utils;

import cn.bytengine.d.lang.AssertTools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * {@link Resource} implementation for a given byte array.
 * <p>Creates a {@link ByteArrayInputStream} for the given byte array.
 *
 * <p>Useful for loading content from any given byte array,
 * without having to resort to a single-use {@link InputStreamResource}.
 * Particularly useful for creating mail attachments from local content,
 * where JavaMail needs to be able to read the stream multiple times.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ByteArrayResource extends AbstractResource {

    private final byte[] byteArray;

    private final String description;


    /**
     * Create a new {@code ByteArrayResource}.
     *
     * @param byteArray the byte array to wrap
     */
    public ByteArrayResource(byte[] byteArray) {
        this(byteArray, "resource loaded from byte array");
    }

    /**
     * Create a new {@code ByteArrayResource} with a description.
     *
     * @param byteArray   the byte array to wrap
     * @param description where the byte array comes from
     */
    public ByteArrayResource(byte[] byteArray, String description) {
        AssertTools.notNull(byteArray, "Byte array must not be null");
        this.byteArray = byteArray;
        this.description = (description != null ? description : "");
    }


    /**
     * Return the underlying byte array.
     *
     * @return TODO
     */
    public final byte[] getByteArray() {
        return this.byteArray;
    }

    /**
     * This implementation always returns {@code true}.
     *
     * @return TODO
     */
    @Override
    public boolean exists() {
        return true;
    }

    /**
     * This implementation returns the length of the underlying byte array.
     *
     * @return TODO
     */
    @Override
    public long contentLength() {
        return this.byteArray.length;
    }

    /**
     * This implementation returns a ByteArrayInputStream for the
     * underlying byte array.
     *
     * @return TODO
     * @see java.io.ByteArrayInputStream
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.byteArray);
    }

    @Override
    public byte[] getContentAsByteArray() throws IOException {
        int length = this.byteArray.length;
        byte[] result = new byte[length];
        System.arraycopy(this.byteArray, 0, result, 0, length);
        return result;
    }

    @Override
    public String getContentAsString(Charset charset) throws IOException {
        return new String(this.byteArray, charset);
    }

    /**
     * This implementation returns a description that includes the passed-in
     * {@code description}, if any.
     *
     * @return TODO
     */
    @Override
    public String getDescription() {
        return "Byte array resource [" + this.description + "]";
    }


    /**
     * This implementation compares the underlying byte array.
     *
     * @param other TODO
     * @return TODO
     * @see java.util.Arrays#equals(byte[], byte[])
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ByteArrayResource) {
            ByteArrayResource that = (ByteArrayResource) other;
            return Arrays.equals(this.byteArray, that.byteArray);
        }
        return false;
    }

    /**
     * This implementation returns the hash code based on the
     * underlying byte array.
     *
     * @return TODO
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.byteArray);
    }
}
