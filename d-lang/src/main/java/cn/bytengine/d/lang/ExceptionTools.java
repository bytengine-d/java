package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ExceptionTools {
    private ExceptionTools() {
    }

    public static String getMessage(Throwable e) {
        if (null == e) {
            return CharSequenceTools.NULL;
        }
        return CharSequenceTools.format("{}: {}", e.getClass().getSimpleName(), e.getMessage());
    }
}
