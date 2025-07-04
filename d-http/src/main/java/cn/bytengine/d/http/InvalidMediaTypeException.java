package cn.bytengine.d.http;

/**
 * Exception thrown from {@link MediaType#parseMediaType(String)} in case of
 * encountering an invalid media type specification String.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class InvalidMediaTypeException extends IllegalArgumentException {
    /**
     * TODO
     */
    private final String mediaType;


    /**
     * Create a new InvalidMediaTypeException for the given media type.
     *
     * @param mediaType the offending media type
     * @param message   a detail message indicating the invalid part
     */
    public InvalidMediaTypeException(String mediaType, String message) {
        super(message != null ? "Invalid media type \"" + mediaType + "\": " + message : "Invalid media type \"" + mediaType);
        this.mediaType = mediaType;
    }

    /**
     * Constructor that allows wrapping {@link InvalidMimeTypeException}.
     */
    InvalidMediaTypeException(InvalidMimeTypeException ex) {
        super(ex.getMessage(), ex);
        this.mediaType = ex.getMimeType();
    }


    /**
     * Return the offending media type.
     * @return TODO
     */
    public String getMediaType() {
        return this.mediaType;
    }

}
