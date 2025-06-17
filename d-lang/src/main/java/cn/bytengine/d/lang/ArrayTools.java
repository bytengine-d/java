package cn.bytengine.d.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ArrayTools {
    private ArrayTools() {
    }

    public static final int INDEX_NOT_FOUND = -1;

    public static boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }

    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {
                //ignore
            }
        }

        return obj.toString();
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Object array) {
        if (array != null) {
            if (isArray(array)) {
                return 0 == Array.getLength(array);
            }
            return false;
        }
        return true;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return (null != array && array.length != 0);
    }

    public static int length(Object array) throws IllegalArgumentException {
        if (null == array) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static <T> T[] insert(T[] buffer, int index, T... newElements) {
        final int newEleLen = length(newElements);
        final int len = length(buffer);
        if (index < 0) {
            index = (index % len) + len;
        }

        final Class<?> originComponentType = buffer.getClass().getComponentType();
        T[] result = newArray(originComponentType, len + newEleLen);
        System.arraycopy(buffer, 0, result, 0, Math.min(len, index));
        System.arraycopy(newElements, 0, result, index, newEleLen);
        if (index < len) {
            System.arraycopy(buffer, index, result, index + newEleLen, len - index);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> int matchIndex(Predicate<T> matcher, int beginIndexInclude, T... array) {
        AssertTools.notNull(matcher, "Matcher must be not null !");
        if (isNotEmpty(array)) {
            for (int i = beginIndexInclude; i < array.length; i++) {
                if (matcher.test(array[i])) {
                    return i;
                }
            }
        }

        return INDEX_NOT_FOUND;
    }

    @SuppressWarnings("unchecked")
    public static <T> int matchIndex(Predicate<T> matcher, T... array) {
        return matchIndex(matcher, 0, array);
    }

    public static <T> int indexOf(T[] array, Object value) {
        return matchIndex((obj) -> ObjectTools.equal(value, obj), array);
    }

    public static <T> boolean contains(T[] array, T value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    public static <T> T[] sub(T[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }
        if (end < 0) {
            end += length;
        }
        if (start == length) {
            return newArray(array.getClass().getComponentType(), 0);
        }
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }
        if (end > length) {
            if (start >= length) {
                return newArray(array.getClass().getComponentType(), 0);
            }
            end = length;
        }
        return Arrays.copyOfRange(array, start, end);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    public static <T> T[] edit(T[] array, Function<T, T> editor) {
        if (null == editor) {
            return array;
        }

        final ArrayList<T> list = new ArrayList<>(array.length);
        T modified;
        for (T t : array) {
            modified = editor.apply(t);
            if (null != modified) {
                list.add(modified);
            }
        }
        final T[] result = newArray(array.getClass().getComponentType(), list.size());
        return list.toArray(result);
    }

    public static <T> T[] filter(T[] array, Predicate<T> filter) {
        if (null == array || null == filter) {
            return array;
        }
        return edit(array, t -> filter.test(t) ? t : null);
    }
}
