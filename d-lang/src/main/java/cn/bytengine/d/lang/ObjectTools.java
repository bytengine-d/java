package cn.bytengine.d.lang;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 对象工具类，包括判空、克隆、序列化等操作
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ObjectTools {
    private ObjectTools() {
    }

    /**
     * 检查对象是否为null<br>
     * 判断标准为：
     *
     * <pre>
     * 1. == null
     * 2. equals(null)
     * </pre>
     *
     * @param obj 对象
     * @return 是否为null
     */
    public static boolean isNull(Object obj) {
        //noinspection ConstantConditions
        return null == obj || obj.equals(null);
    }

    /**
     * 检查对象是否不为null
     * <pre>
     * 1. != null
     * 2. not equals(null)
     * </pre>
     *
     * @param obj 对象
     * @return 是否为非null
     */
    public static boolean isNotNull(Object obj) {
        //noinspection ConstantConditions
        return null != obj && !obj.equals(null);
    }

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * ObjectTools.defaultIfNull(null, null)      = null
     * ObjectTools.defaultIfNull(null, "")        = ""
     * ObjectTools.defaultIfNull(null, "zz")      = "zz"
     * ObjectTools.defaultIfNull("abc", *)        = "abc"
     * ObjectTools.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param <T>          对象类型
     * @param object       被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return isNull(object) ? defaultValue : object;
    }

    /**
     * 如果被检查对象为 {@code null}， 返回默认值（由 defaultValueSupplier 提供）；否则直接返回
     *
     * @param source               被检查对象
     * @param defaultValueSupplier 默认值提供者
     * @param <T>                  对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
     * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
     */
    public static <T> T defaultIfNull(T source, Supplier<? extends T> defaultValueSupplier) {
        if (isNull(source)) {
            return defaultValueSupplier.get();
        }
        return source;
    }


    /**
     * 如果被检查对象为 {@code null}， 返回默认值（由 defaultValueSupplier 提供）；否则直接返回
     *
     * @param source               被检查对象
     * @param defaultValueSupplier 默认值提供者
     * @param <T>                  对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
     * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
     */
    public static <T> T defaultIfNull(T source, Function<T, ? extends T> defaultValueSupplier) {
        if (isNull(source)) {
            return defaultValueSupplier.apply(null);
        }
        return source;
    }

    /**
     * 比较两个对象是否相等，此方法是 {@link #equal(Object, Object)}的别名方法。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * <ol>
     * <li>obj1 == null &amp;&amp; obj2 == null</li>
     * <li>obj1.equals(obj2)</li>
     * <li>如果是BigDecimal比较，0 == obj1.compareTo(obj2)</li>
     * </ol>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     * @see #equal(Object, Object)
     */
    public static boolean equals(Object obj1, Object obj2) {
        return equal(obj1, obj2);
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * <ol>
     * <li>obj1 == null &amp;&amp; obj2 == null</li>
     * <li>obj1.equals(obj2)</li>
     * <li>如果是BigDecimal比较，0 == obj1.compareTo(obj2)</li>
     * </ol>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     * @see Objects#equals(Object, Object)
     */
    public static boolean equal(Object obj1, Object obj2) {
        if (obj1 instanceof Number && obj2 instanceof Number) {
            return NumberTools.equals((Number) obj1, (Number) obj2);
        }
        return Objects.equals(obj1, obj2);
    }

    /**
     * 支持null和数组判断是否相等
     *
     * @param o1 对比对象1
     * @param o2 对比对象2
     * @return 是否相等
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            return ArrayTools.equals(o1, o2);
        }
        return false;
    }

    /**
     * Return a String representation of the specified Object.
     * <p>Builds a String representation of the contents in case of an array.
     * Returns a {@code "null"} String if {@code obj} is {@code null}.
     *
     * @param obj the object to build a String representation for
     * @return a String representation of {@code obj}
     */
    public static String nullSafeToString(Object obj) {
        if (obj == null) {
            return CharSequenceTools.NULL;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Object[]) {
            return ArrayTools.nullSafeToString((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return ArrayTools.nullSafeToString((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return ArrayTools.nullSafeToString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return ArrayTools.nullSafeToString((char[]) obj);
        }
        if (obj instanceof double[]) {
            return ArrayTools.nullSafeToString((double[]) obj);
        }
        if (obj instanceof float[]) {
            return ArrayTools.nullSafeToString((float[]) obj);
        }
        if (obj instanceof int[]) {
            return ArrayTools.nullSafeToString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return ArrayTools.nullSafeToString((long[]) obj);
        }
        if (obj instanceof short[]) {
            return ArrayTools.nullSafeToString((short[]) obj);
        }
        String str = obj.toString();
        return (str != null ? str : CharSequenceTools.EMPTY);
    }

    /**
     * Return a hash code for the given elements, delegating to
     * {@link #nullSafeHashCode(Object)} for each element. Contrary
     * to {@link Objects#hash(Object...)}, this method can handle an
     * element that is an array.
     *
     * @param elements the elements to be hashed
     * @return a hash value of the elements
     */
    public static int nullSafeHash(Object... elements) {
        if (elements == null) {
            return 0;
        }
        int result = 1;
        for (Object element : elements) {
            result = 31 * result + nullSafeHashCode(element);
        }
        return result;
    }

    /**
     * Return a hash code for the given object, typically the value of
     * {@link Object#hashCode()}. If the object is an array, this method
     * will delegate to one of the {@code Arrays.hashCode} methods. If
     * the object is {@code null}, this method returns {@code 0}.
     *
     * @param obj TODO
     * @return TODO
     * @see Object#hashCode()
     * @see Arrays
     */
    public static int nullSafeHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                Object[] objects = (Object[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof boolean[]) {
                boolean[] objects = (boolean[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof byte[]) {
                byte[] objects = (byte[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof char[]) {
                char[] objects = (char[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof double[]) {
                double[] objects = (double[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof float[]) {
                float[] objects = (float[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof int[]) {
                int[] objects = (int[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof long[]) {
                long[] objects = (long[]) obj;
                return Arrays.hashCode(objects);
            }
            if (obj instanceof short[]) {
                short[] objects = (short[]) obj;
                return Arrays.hashCode(objects);
            }
        }
        return obj.hashCode();
    }

}
