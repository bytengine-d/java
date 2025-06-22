package cn.bytengine.d.utils;

/**
 * Thrown when a URL string cannot be parsed.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class InvalidUrlException extends IllegalArgumentException {

    /**
     * Construct a {@code InvalidUrlException} with the specified detail message.
     *
     * @param msg the detail message
     */
    public InvalidUrlException(String msg) {
        super(msg);
    }

    /**
     * Construct a {@code InvalidUrlException} with the specified detail message
     * and nested exception.
     *
     * @param msg   the detail message
     * @param cause the nested exception
     */
    public InvalidUrlException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
