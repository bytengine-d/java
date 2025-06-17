package cn.bytengine.d.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class NumberTools {
    private NumberTools() {
    }

    public static boolean equals(final Number number1, final Number number2) {
        if (number1 instanceof BigDecimal && number2 instanceof BigDecimal) {
            return equals((BigDecimal) number1, (BigDecimal) number2);
        }
        return Objects.equals(number1, number2);
    }

    public static boolean equals(BigDecimal bigNum1, BigDecimal bigNum2) {
        //noinspection NumberEquality
        if (bigNum1 == bigNum2) {
            return true;
        }
        if (bigNum1 == null || bigNum2 == null) {
            return false;
        }
        return 0 == bigNum1.compareTo(bigNum2);
    }

    public static Number parseNumber(String numberStr) throws NumberFormatException {
        if (CharSequenceTools.startWithIgnoreCase(numberStr, "0x")) {
            // 0x04表示16进制数
            return Long.parseLong(numberStr.substring(2), 16);
        } else if (CharSequenceTools.startWith(numberStr, '+')) {
            // issue#I79VS7
            numberStr = CharSequenceTools.subSuf(numberStr, 1);
        }

        try {
            final NumberFormat format = NumberFormat.getInstance();
            if (format instanceof DecimalFormat) {
                // issue#1818@Github
                // 当字符串数字超出double的长度时，会导致截断，此处使用BigDecimal接收
                ((DecimalFormat) format).setParseBigDecimal(true);
            }
            return format.parse(numberStr);
        } catch (ParseException e) {
            final NumberFormatException nfe = new NumberFormatException(e.getMessage());
            nfe.initCause(e);
            throw nfe;
        }
    }

    public static int parseInt(String number) throws NumberFormatException {
        if (CharSequenceTools.isBlank(number)) {
            return 0;
        }

        if (CharSequenceTools.startWithIgnoreCase(number, "0x")) {
            // 0x04表示16进制数
            return Integer.parseInt(number.substring(2), 16);
        }

        if (CharSequenceTools.containsIgnoreCase(number, "E")) {
            // 科学计数法忽略支持，科学计数法一般用于表示非常小和非常大的数字，这类数字转换为int后精度丢失，没有意义。
            throw new NumberFormatException(CharSequenceTools.format("Unsupported int format: [{}]", number));
        }

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).intValue();
        }
    }

    public static long parseLong(String number) {
        if (CharSequenceTools.isBlank(number)) {
            return 0L;
        }

        if (number.startsWith("0x")) {
            // 0x04表示16进制数
            return Long.parseLong(number.substring(2), 16);
        }

        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).longValue();
        }
    }

    public static float parseFloat(String number) {
        if (CharSequenceTools.isBlank(number)) {
            return 0f;
        }

        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).floatValue();
        }
    }

    public static double toDouble(Number value) {
        if (value instanceof Float) {
            return Double.parseDouble(value.toString());
        } else {
            return value.doubleValue();
        }
    }

    public static double parseDouble(String number) {
        if (CharSequenceTools.isBlank(number)) {
            return 0D;
        }

        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).doubleValue();
        }
    }

    public static boolean isValidNumber(Number number) {
        if (null == number) {
            return false;
        }
        if (number instanceof Double) {
            return (false == ((Double) number).isInfinite()) && (false == ((Double) number).isNaN());
        } else if (number instanceof Float) {
            return (false == ((Float) number).isInfinite()) && (false == ((Float) number).isNaN());
        }
        return true;
    }

    public static BigDecimal toBigDecimal(Number number) {
        if (null == number) {
            return BigDecimal.ZERO;
        }
        // issue#3423@Github of CVE-2023-51080
        AssertTools.isTrue(isValidNumber(number), "Number is invalid!");

        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Long) {
            return new BigDecimal((Long) number);
        } else if (number instanceof Integer) {
            return new BigDecimal((Integer) number);
        } else if (number instanceof BigInteger) {
            return new BigDecimal((BigInteger) number);
        }

        // Float、Double等有精度问题，转换为字符串后再转换
        return new BigDecimal(number.toString());
    }

    public static BigDecimal toBigDecimal(String numberStr) {
        if (CharSequenceTools.isBlank(numberStr)) {
            return BigDecimal.ZERO;
        }

        try {
            return new BigDecimal(numberStr);
        } catch (Exception ignore) {
            // 忽略解析错误
        }

        // 支持类似于 1,234.55 格式的数字
        final Number number = parseNumber(numberStr);
        return toBigDecimal(number);
    }

    public static BigInteger toBigInteger(String number) {
        return CharSequenceTools.isBlank(number) ? BigInteger.ZERO : new BigInteger(number);
    }
}
