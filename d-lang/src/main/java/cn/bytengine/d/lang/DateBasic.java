package cn.bytengine.d.lang;

import java.util.Locale;
import java.util.TimeZone;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface DateBasic {
    String getPattern();

    TimeZone getTimeZone();

    Locale getLocale();
}
