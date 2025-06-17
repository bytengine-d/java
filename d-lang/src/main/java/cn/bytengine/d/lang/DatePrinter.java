package cn.bytengine.d.lang;

import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface DatePrinter extends DateBasic {
    String format(long millis);

    String format(Date date);

    String format(Calendar calendar);

    <B extends Appendable> B format(long millis, B buf);

    <B extends Appendable> B format(Date date, B buf);

    <B extends Appendable> B format(Calendar calendar, B buf);
}
