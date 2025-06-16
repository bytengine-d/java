package cn.bytengine.d.lang;

import cn.bytengine.d.utils.Convert;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ArrayTools {
    private ArrayTools() {
    }

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
        return (T[]) insert((Object) buffer, index, newElements);
    }

    public static <T> Object insert(Object array, int index, T... newElements) {
        if (isEmpty(newElements)) {
            return array;
        }
        if (isEmpty(array)) {
            return newElements;
        }

        final int len = length(array);
        if (index < 0) {
            index = (index % len) + len;
        }

        // 已有数组的元素类型
        final Class<?> originComponentType = array.getClass().getComponentType();
        Object newEleArr = newElements;
        // 如果 已有数组的元素类型是 原始类型，则需要转换 新元素数组 为该类型，避免ArrayStoreException
        if (originComponentType.isPrimitive()) {
            newEleArr = Convert.convert(array.getClass(), newElements);
        }
        final Object result = Array.newInstance(originComponentType, Math.max(len, index) + newElements.length);
        System.arraycopy(array, 0, result, 0, Math.min(len, index));
        System.arraycopy(newEleArr, 0, result, index, newElements.length);
        if (index < len) {
            System.arraycopy(array, index, result, index + newElements.length, len - index);
        }
        return result;
    }
}
