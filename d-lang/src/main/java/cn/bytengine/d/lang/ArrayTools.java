package cn.bytengine.d.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 数组工具类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ArrayTools {
    private ArrayTools() {
    }

    /**
     * 数组元素分割字符串
     */
    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";
    /**
     * 数组开头标识
     */
    private static final String ARRAY_START = "{";
    /**
     * 数组结束标识
     */
    private static final String ARRAY_END = "}";
    /**
     * 空数组标识
     */
    private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;

    /**
     * 空对象数组
     */
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * The index value when an element is not found in a list or array: {@code -1}. This value is returned by methods in
     * this class and can also be used in comparisons with values returned by various method from
     * {@link java.util.List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }

    /**
     * 数组或集合转String
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
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

    /**
     * 数组是否为空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空<br>
     * 此方法会匹配单一对象，如果此对象为{@code null}则返回true<br>
     * 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回false<br>
     * 如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(Object array) {
        if (array != null) {
            if (isArray(array)) {
                return 0 == Array.getLength(array);
            }
            return false;
        }
        return true;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为非空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return (null != array && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }

    /**
     * 获取数组长度<br>
     * 如果参数为{@code null}，返回0
     *
     * <pre>
     * ArrayUtil.length(null)            = 0
     * ArrayUtil.length([])              = 0
     * ArrayUtil.length([null])          = 1
     * ArrayUtil.length([true, false])   = 2
     * ArrayUtil.length([1, 2, 3])       = 3
     * ArrayUtil.length(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array 数组对象
     * @return 数组长度
     * @throws IllegalArgumentException 如果参数不为数组，抛出此异常
     * @see Array#getLength(Object)
     */
    public static int length(Object array) throws IllegalArgumentException {
        if (null == array) {
            return 0;
        }
        return Array.getLength(array);
    }

    /**
     * 将新元素插入到到已有数组中的某个位置<br>
     * 添加新元素会生成一个新的数组，不影响原数组<br>
     * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用null填充
     *
     * @param <T>         数组元素类型
     * @param buffer      已有数组
     * @param index       插入位置，此位置为对应此位置元素之前的空档
     * @param newElements 新元素
     * @return 新数组
     */
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

    /**
     * 返回数组中第一个匹配规则的值的位置
     *
     * @param <T>               数组元素类型
     * @param matcher           匹配接口，实现此接口自定义匹配规则
     * @param beginIndexInclude 检索开始的位置
     * @param array             数组
     * @return 匹配到元素的位置，-1表示未匹配到
     */
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

    /**
     * 返回数组中第一个匹配规则的值的位置
     *
     * @param <T>     数组元素类型
     * @param matcher 匹配接口，实现此接口自定义匹配规则
     * @param array   数组
     * @return 匹配到元素的位置，-1表示未匹配到
     */
    @SuppressWarnings("unchecked")
    public static <T> int matchIndex(Predicate<T> matcher, T... array) {
        return matchIndex(matcher, 0, array);
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param <T>   数组类型
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     */
    public static <T> int indexOf(T[] array, Object value) {
        return matchIndex((obj) -> ObjectTools.equal(value, obj), array);
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     */
    public static int indexOf(char[] array, char value) {
        if (isNotEmpty(array)) {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 获取子数组
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @param start 开始位置（包括）
     * @param end   结束位置（不包括）
     * @return 新的数组
     * @see Arrays#copyOfRange(Object[], int, int)
     */
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

    /**
     * 新建一个空数组
     *
     * @param <T>           数组元素类型
     * @param componentType 元素类型
     * @param newSize       大小
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 编辑数组<br>
     * 编辑过程通过传入的Editor实现来返回需要的元素内容，这个Editor实现可以实现以下功能：
     *
     * <pre>
     * 1、过滤出需要的对象，如果返回{@code null}表示这个元素对象抛弃
     * 2、修改元素对象，返回集合中为修改后的对象
     * </pre>
     *
     * @param <T>    数组元素类型
     * @param array  数组
     * @param editor 编辑器接口，{@code null}返回原集合
     * @return 编辑后的数组
     */
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

    /**
     * 过滤<br>
     * 过滤过程通过传入的Predicate实现来过滤返回需要的元素内容，这个Predicate实现可以实现以下功能：
     *
     * <pre>
     * 1、过滤出需要的对象，{@link Predicate#test(Object)}方法返回true的对象将被加入结果集合中
     * </pre>
     *
     * @param <T>    数组元素类型
     * @param array  数组
     * @param filter 过滤器接口，用于定义过滤规则，{@code null}返回原集合
     * @return 过滤后的数组
     */
    public static <T> T[] filter(T[] array, Predicate<T> filter) {
        if (null == array || null == filter) {
            return array;
        }
        return edit(array, t -> filter.test(t) ? t : null);
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static byte[] addAll(byte[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (byte[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final byte[] result = new byte[length];
        length = 0;
        for (byte[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static int[] addAll(int[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (int[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final int[] result = new int[length];
        length = 0;
        for (int[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static long[] addAll(long[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (long[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final long[] result = new long[length];
        length = 0;
        for (long[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static double[] addAll(double[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (double[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final double[] result = new double[length];
        length = 0;
        for (double[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static float[] addAll(float[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (float[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final float[] result = new float[length];
        length = 0;
        for (float[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static char[] addAll(char[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (char[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final char[] result = new char[length];
        length = 0;
        for (char[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static boolean[] addAll(boolean[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (boolean[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final boolean[] result = new boolean[length];
        length = 0;
        for (boolean[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    public static short[] addAll(short[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 计算总长度
        int length = 0;
        for (short[] array : arrays) {
            if (isNotEmpty(array)) {
                length += array.length;
            }
        }

        final short[] result = new short[length];
        length = 0;
        for (short[] array : arrays) {
            if (isNotEmpty(array)) {
                System.arraycopy(array, 0, result, length, array.length);
                length += array.length;
            }
        }
        return result;
    }

    /**
     * 数组对比
     *
     * @param o1 数组1
     * @param o2 数组2
     * @return 是否相等
     */
    public static boolean equals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            Object[] objects1 = (Object[]) o1;
            Object[] objects2 = (Object[]) o2;
            return Arrays.equals(objects1, objects2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            boolean[] booleans1 = (boolean[]) o1;
            boolean[] booleans2 = (boolean[]) o2;
            return Arrays.equals(booleans1, booleans2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            byte[] bytes1 = (byte[]) o1;
            byte[] bytes2 = (byte[]) o2;
            return Arrays.equals(bytes1, bytes2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            char[] chars1 = (char[]) o1;
            char[] chars2 = (char[]) o2;
            return Arrays.equals(chars1, chars2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            double[] doubles1 = (double[]) o1;
            double[] doubles2 = (double[]) o2;
            return Arrays.equals(doubles1, doubles2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            float[] floats1 = (float[]) o1;
            float[] floats2 = (float[]) o2;
            return Arrays.equals(floats1, floats2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            int[] ints1 = (int[]) o1;
            int[] ints2 = (int[]) o2;
            return Arrays.equals(ints1, ints2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            long[] longs1 = (long[]) o1;
            long[] longs2 = (long[]) o2;
            return Arrays.equals(longs1, longs2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            short[] shorts1 = (short[]) o1;
            short[] shorts2 = (short[]) o2;
            return Arrays.equals(shorts1, shorts2);
        }
        return false;
    }

    /**
     * Convert the given array (which may be a primitive array) to an object array (if
     * necessary, to an array of primitive wrapper objects).
     * <p>A {@code null} source value or empty primitive array will be converted to an
     * empty Object array.
     *
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never {@code null})
     * @throws IllegalArgumentException if the parameter is not an array
     */
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return EMPTY_OBJECT_ARRAY;
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        Class<?> wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }

    /**
     * Convert a {@code String} array into a delimited {@code String} (for example, CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param arr   the array to display (potentially {@code null} or empty)
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (isEmpty(arr)) {
            return "";
        }
        if (arr.length == 1) {
            return ObjectTools.nullSafeToString(arr[0]);
        }

        StringJoiner sj = new StringJoiner(delim);
        for (Object elem : arr) {
            sj.add(String.valueOf(elem));
        }
        return sj.toString();
    }

    /**
     * Convert a {@code String} array into a comma delimited {@code String}
     * (i.e., CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param arr the array to display (potentially {@code null} or empty)
     * @return the delimited {@code String}
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(Object[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (Object o : array) {
            stringJoiner.add(String.valueOf(o));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(boolean[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (boolean b : array) {
            stringJoiner.add(String.valueOf(b));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(byte[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (byte b : array) {
            stringJoiner.add(String.valueOf(b));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(char[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (char c : array) {
            stringJoiner.add('\'' + String.valueOf(c) + '\'');
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(double[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (double d : array) {
            stringJoiner.add(String.valueOf(d));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(float[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (float f : array) {
            stringJoiner.add(String.valueOf(f));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(int[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (int i : array) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(long[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (long l : array) {
            stringJoiner.add(String.valueOf(l));
        }
        return stringJoiner.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space).
     * Returns a {@code "null"} String if {@code array} is {@code null}.
     *
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString(short[] array) {
        if (array == null) {
            return CharSequenceTools.NULL;
        }
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (short s : array) {
            stringJoiner.add(String.valueOf(s));
        }
        return stringJoiner.toString();
    }
}
