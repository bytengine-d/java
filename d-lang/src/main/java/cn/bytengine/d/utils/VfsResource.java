package cn.bytengine.d.utils;

import cn.bytengine.d.lang.AssertTools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * JBoss VFS based {@link Resource} implementation.
 *
 * <p>As of Spring 4.0, this class supports VFS 3.x on JBoss AS 6+
 * (package {@code org.jboss.vfs}) and is in particular compatible with
 * JBoss AS 7 and WildFly 8+.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class VfsResource extends AbstractResource {

    private final Object resource;


    /**
     * Create a new {@code VfsResource} wrapping the given resource handle.
     *
     * @param resource a {@code org.jboss.vfs.VirtualFile} instance
     *                 (untyped in order to avoid a static dependency on the VFS API)
     */
    public VfsResource(Object resource) {
        AssertTools.notNull(resource, "VirtualFile must not be null");
        this.resource = resource;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return VfsTools.getInputStream(this.resource);
    }

    @Override
    public boolean exists() {
        return VfsTools.exists(this.resource);
    }

    @Override
    public boolean isReadable() {
        return VfsTools.isReadable(this.resource);
    }

    @Override
    public URL getURL() throws IOException {
        try {
            return VfsTools.getURL(this.resource);
        } catch (Exception ex) {
            throw new IOException("Failed to obtain URL for file " + this.resource, ex);
        }
    }

    @Override
    public URI getURI() throws IOException {
        try {
            return VfsTools.getURI(this.resource);
        } catch (Exception ex) {
            throw new IOException("Failed to obtain URI for " + this.resource, ex);
        }
    }

    @Override
    public File getFile() throws IOException {
        return VfsTools.getFile(this.resource);
    }

    @Override
    public long contentLength() throws IOException {
        return VfsTools.getSize(this.resource);
    }

    @Override
    public long lastModified() throws IOException {
        return VfsTools.getLastModified(this.resource);
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        if (!relativePath.startsWith(".") && relativePath.contains("/")) {
            try {
                return new VfsResource(VfsTools.getChild(this.resource, relativePath));
            } catch (IOException ex) {
                // fall back to getRelative
            }
        }

        return new VfsResource(VfsTools.getRelative(ResourceTools.toRelativeURL(getURL(), relativePath)));
    }

    @Override
    public String getFilename() {
        return VfsTools.getName(this.resource);
    }

    @Override
    public String getDescription() {
        return "VFS resource [" + this.resource + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof VfsResource) {
            VfsResource that = (VfsResource) other;
            return this.resource.equals(that.resource);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.resource.hashCode();
    }
}
