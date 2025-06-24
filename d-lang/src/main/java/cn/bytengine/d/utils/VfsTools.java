package cn.bytengine.d.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import java.net.URL;

/**
 * Utility for detecting and accessing JBoss VFS in the classpath.
 * <p><b>Note:</b> This is an internal class and should not be used outside the framework.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class VfsTools {
    private VfsTools() {
    }


    private static final String VFS3_PKG = "org.jboss.vfs.";
    private static final String VFS_NAME = "VFS";

    private static final Method VFS_METHOD_GET_ROOT_URL;
    private static final Method VFS_METHOD_GET_ROOT_URI;

    private static final Method VIRTUAL_FILE_METHOD_EXISTS;
    private static final Method VIRTUAL_FILE_METHOD_GET_INPUT_STREAM;
    private static final Method VIRTUAL_FILE_METHOD_GET_SIZE;
    private static final Method VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED;
    private static final Method VIRTUAL_FILE_METHOD_TO_URL;
    private static final Method VIRTUAL_FILE_METHOD_TO_URI;
    private static final Method VIRTUAL_FILE_METHOD_GET_NAME;
    private static final Method VIRTUAL_FILE_METHOD_GET_PATH_NAME;
    private static final Method VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE;
    private static final Method VIRTUAL_FILE_METHOD_GET_CHILD;

    /**
     * TODO
     */
    protected static final Class<?> VIRTUAL_FILE_VISITOR_INTERFACE;
    /**
     * TODO
     */
    protected static final Method VIRTUAL_FILE_METHOD_VISIT;

    private static final Field VISITOR_ATTRIBUTES_FIELD_RECURSE;

    static {
        ClassLoader loader = VfsTools.class.getClassLoader();
        try {
            Class<?> vfsClass = loader.loadClass(VFS3_PKG + VFS_NAME);
            VFS_METHOD_GET_ROOT_URL = vfsClass.getMethod("getChild", URL.class);
            VFS_METHOD_GET_ROOT_URI = vfsClass.getMethod("getChild", URI.class);

            Class<?> virtualFile = loader.loadClass(VFS3_PKG + "VirtualFile");
            VIRTUAL_FILE_METHOD_EXISTS = virtualFile.getMethod("exists");
            VIRTUAL_FILE_METHOD_GET_INPUT_STREAM = virtualFile.getMethod("openStream");
            VIRTUAL_FILE_METHOD_GET_SIZE = virtualFile.getMethod("getSize");
            VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED = virtualFile.getMethod("getLastModified");
            VIRTUAL_FILE_METHOD_TO_URI = virtualFile.getMethod("toURI");
            VIRTUAL_FILE_METHOD_TO_URL = virtualFile.getMethod("toURL");
            VIRTUAL_FILE_METHOD_GET_NAME = virtualFile.getMethod("getName");
            VIRTUAL_FILE_METHOD_GET_PATH_NAME = virtualFile.getMethod("getPathName");
            VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE = virtualFile.getMethod("getPhysicalFile");
            VIRTUAL_FILE_METHOD_GET_CHILD = virtualFile.getMethod("getChild", String.class);

            VIRTUAL_FILE_VISITOR_INTERFACE = loader.loadClass(VFS3_PKG + "VirtualFileVisitor");
            VIRTUAL_FILE_METHOD_VISIT = virtualFile.getMethod("visit", VIRTUAL_FILE_VISITOR_INTERFACE);

            Class<?> visitorAttributesClass = loader.loadClass(VFS3_PKG + "VisitorAttributes");
            VISITOR_ATTRIBUTES_FIELD_RECURSE = visitorAttributesClass.getField("RECURSE");
        } catch (Throwable ex) {
            throw new IllegalStateException("Could not detect JBoss VFS infrastructure", ex);
        }
    }

    /**
     * 调用Vfs方法
     *
     * @param method 方法
     * @param target Vfs对象
     * @param args   调用参数
     * @return 返回值
     * @throws IOException TODO
     */
    protected static Object invokeVfsMethod(Method method, Object target, Object... args) throws IOException {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            Throwable targetEx = ex.getTargetException();
            if (targetEx instanceof IOException) {
                throw (IOException) targetEx;
            }
            rethrowRuntimeException(ex.getTargetException());
        } catch (Exception ex) {
            handleReflectionException(ex);
        }

        throw new IllegalStateException("Invalid code path reached");
    }

    private static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    private static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            rethrowRuntimeException(((InvocationTargetException) ex).getTargetException());
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    static boolean exists(Object vfsResource) {
        try {
            return (Boolean) invokeVfsMethod(VIRTUAL_FILE_METHOD_EXISTS, vfsResource);
        } catch (IOException ex) {
            return false;
        }
    }

    static boolean isReadable(Object vfsResource) {
        try {
            return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource) > 0;
        } catch (IOException ex) {
            return false;
        }
    }

    static long getSize(Object vfsResource) throws IOException {
        return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource);
    }

    static long getLastModified(Object vfsResource) throws IOException {
        return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED, vfsResource);
    }

    static InputStream getInputStream(Object vfsResource) throws IOException {
        return (InputStream) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_INPUT_STREAM, vfsResource);
    }

    static URL getURL(Object vfsResource) throws IOException {
        return (URL) invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URL, vfsResource);
    }

    static URI getURI(Object vfsResource) throws IOException {
        return (URI) invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URI, vfsResource);
    }

    static String getName(Object vfsResource) {
        try {
            return (String) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_NAME, vfsResource);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot get resource name", ex);
        }
    }

    static Object getRelative(URL url) throws IOException {
        return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, null, url);
    }

    static Object getChild(Object vfsResource, String path) throws IOException {
        return invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_CHILD, vfsResource, path);
    }

    static File getFile(Object vfsResource) throws IOException {
        return (File) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE, vfsResource);
    }

    /**
     * 获取URL根
     *
     * @param url URL资源
     * @return 根资源
     * @throws IOException TODO
     */
    static Object getRoot(URI url) throws IOException {
        return invokeVfsMethod(VFS_METHOD_GET_ROOT_URI, null, url);
    }

    // protected methods used by the support sub-package

    /**
     * 获取URL根
     *
     * @param url URL资源
     * @return 根资源
     * @throws IOException TODO
     */
    protected static Object getRoot(URL url) throws IOException {
        return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, null, url);
    }
}
