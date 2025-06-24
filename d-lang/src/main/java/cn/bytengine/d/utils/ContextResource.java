package cn.bytengine.d.utils;

/**
 * Extended interface for a resource that is loaded from an enclosing
 * 'context'.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface ContextResource extends Resource {
    /**
     * Return the path within the enclosing 'context'.
     * <p>This is typically path relative to a context-specific root directory,
     * for example, a ServletContext root or a PortletContext root.
     *
     * @return TODO
     */
    String getPathWithinContext();
}
