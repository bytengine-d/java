package cn.bytengine.d.lang;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface DateParser extends DateBasic {
    Date parse(String source) throws ParseException;

    Date parse(String source, ParsePosition pos);

    boolean parse(String source, ParsePosition pos, Calendar calendar);

    default Object parseObject(String source) throws ParseException {
        return parse(source);
    }

    default Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }
}
