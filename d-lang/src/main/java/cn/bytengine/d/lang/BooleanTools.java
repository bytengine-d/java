package cn.bytengine.d.lang;

import cn.hutool.core.convert.Convert;

import java.util.Set;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class BooleanTools {
    private BooleanTools() {
    }

    private static final Set<String> TRUE_SET = CollectionTools.newHashSet("true", "yes", "y", "t", "ok", "1", "on", "是", "对", "真", "對", "√");
    private static final Set<String> FALSE_SET = CollectionTools.newHashSet("false", "no", "n", "f", "0", "off", "否", "错", "假", "錯", "×");

    public static Boolean negate(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean negate(boolean bool) {
        return !bool;
    }

    public static boolean toBoolean(String valueStr) {
        if (CharSequenceTools.isNotBlank(valueStr)) {
            valueStr = valueStr.trim().toLowerCase();
            return TRUE_SET.contains(valueStr);
        }
        return false;
    }

    public static Boolean toBooleanObject(String valueStr) {
        if (CharSequenceTools.isNotBlank(valueStr)) {
            valueStr = valueStr.trim().toLowerCase();
            if (TRUE_SET.contains(valueStr)) {
                return true;
            } else if (FALSE_SET.contains(valueStr)) {
                return false;
            }
        }
        return null;
    }

    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }

    public static Integer toInteger(boolean value) {
        return toInt(value);
    }

    public static char toChar(boolean value) {
        return (char) toInt(value);
    }

    public static Character toCharacter(boolean value) {
        return toChar(value);
    }

    public static byte toByte(boolean value) {
        return (byte) toInt(value);
    }

    public static Byte toByteObj(boolean value) {
        return toByte(value);
    }

    public static long toLong(boolean value) {
        return toInt(value);
    }

    public static Long toLongObj(boolean value) {
        return toLong(value);
    }

    public static short toShort(boolean value) {
        return (short) toInt(value);
    }

    public static Short toShortObj(boolean value) {
        return toShort(value);
    }

    public static float toFloat(boolean value) {
        return (float) toInt(value);
    }

    public static Float toFloatObj(boolean value) {
        return toFloat(value);
    }

    public static double toDouble(boolean value) {
        return toInt(value);
    }

    public static Double toDoubleObj(boolean value) {
        return toDouble(value);
    }

    public static String toStringTrueFalse(boolean bool) {
        return toString(bool, "true", "false");
    }

    public static String toStringOnOff(boolean bool) {
        return toString(bool, "on", "off");
    }

    public static String toStringYesNo(boolean bool) {
        return toString(bool, "yes", "no");
    }

    public static String toString(boolean bool, String trueString, String falseString) {
        return bool ? trueString : falseString;
    }

    public static String toString(Boolean bool, String trueString, String falseString, String nullString) {
        if (bool == null) {
            return nullString;
        }
        return bool ? trueString : falseString;
    }

    public static boolean and(boolean... array) {
        if (ArrayTools.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (final boolean element : array) {
            if (false == element) {
                return false;
            }
        }
        return true;
    }

    public static Boolean andOfWrap(Boolean... array) {
        if (ArrayTools.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }

        for (final Boolean b : array) {
            if (!isTrue(b)) {
                return false;
            }
        }
        return true;
    }

    public static boolean or(boolean... array) {
        if (ArrayTools.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (final boolean element : array) {
            if (element) {
                return true;
            }
        }
        return false;
    }

    public static Boolean orOfWrap(Boolean... array) {
        if (ArrayTools.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }

        for (final Boolean b : array) {
            if (isTrue(b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean xor(boolean... array) {
        if (ArrayTools.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty");
        }

        boolean result = false;
        for (final boolean element : array) {
            result ^= element;
        }

        return result;
    }

    public static Boolean xorOfWrap(Boolean... array) {
        if (ArrayTools.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        final boolean[] primitive = Convert.convert(boolean[].class, array);
        return xor(primitive);
    }

    public static boolean isBoolean(Class<?> clazz) {
        return (clazz == Boolean.class || clazz == boolean.class);
    }
}
