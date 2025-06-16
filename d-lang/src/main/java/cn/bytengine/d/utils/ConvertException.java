package cn.bytengine.d.utils;

import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.ExceptionTools;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ConvertException extends RuntimeException {

    public ConvertException(Throwable e) {
        super(ExceptionTools.getMessage(e), e);
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String messageTemplate, Object... params) {
        super(CharSequenceTools.format(messageTemplate, params));
    }

    public ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConvertException(Throwable throwable, String messageTemplate, Object... params) {
        super(CharSequenceTools.format(messageTemplate, params), throwable);
    }
}
