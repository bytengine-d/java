package cn.bytengine.d.lang;

import cn.bytengine.d.collection.MultiValueMap;
import cn.bytengine.d.collection.TransCollection;
import cn.bytengine.d.collection.UnmodifiableMultiValueMap;

import java.util.*;
import java.util.function.Function;

/**
 * 集合相关工具类
 * <p>
 * 此工具方法针对{@link Collection}及其实现类封装的工具。
 * <p>
 * 由于{@link Collection} 实现了{@link Iterable}接口，因此部分工具此类不提供，而是在{@link IteratorTools} 中提供
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class CollectionTools {
    private CollectionTools() {
    }

    /**
     * 默认初始大小
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    /**
     * 默认增长因子，当Map的size达到 容量*增长因子时，开始扩充Map
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyMap()}
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     * @since 4.6.3
     */
    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> set) {
        return (null == set) ? Collections.emptyMap() : set;
    }

    /**
     * 如果给定Map为空，返回默认Map
     *
     * @param <T>        集合类型
     * @param <K>        键类型
     * @param <V>        值类型
     * @param map        Map
     * @param defaultMap 默认Map
     * @return 非空（empty）的原Map或默认Map
     * @since 4.6.9
     */
    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T map, T defaultMap) {
        return isEmpty(map) ? defaultMap : map;
    }

    /**
     * 如果 key 对应的 value 不存在，则使用获取 mappingFunction 重新计算后的值，并保存为该 key 的 value，否则返回 value。<br>
     * 解决使用ConcurrentHashMap.computeIfAbsent导致的死循环问题。（issues#2349）<br>
     * A temporary workaround for Java 8 specific performance issue JDK-8161372 .<br>
     * This class should be removed once we drop Java 8 support.
     *
     * <p>
     * 注意此方法只能用于JDK8
     * </p>
     *
     * @param <K>             键类型
     * @param <V>             值类型
     * @param map             Map，一般用于线程安全的Map
     * @param key             键
     * @param mappingFunction 值计算函数
     * @return 值
     * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8161372">https://bugs.openjdk.java.net/browse/JDK-8161372</a>
     */
    public static <K, V> V computeIfAbsentForJdk8(final Map<K, V> map, final K key, final Function<? super K, ? extends V> mappingFunction) {
        V value = map.get(key);
        if (null == value) {
            value = mappingFunction.apply(key);
            final V res = map.putIfAbsent(key, value);
            if (null != res) {
                // issues#I6RVMY
                // 如果旧值存在，说明其他线程已经赋值成功，putIfAbsent没有执行，返回旧值
                return res;
            }
            // 如果旧值不存在，说明赋值成功，返回当前值

            // Dubbo的解决方式，判空后调用依旧无法解决死循环问题
            // 见：Issue2349Test
            //value = map.computeIfAbsent(key, mappingFunction);
        }
        return value;
    }

    /**
     * 将单一键值对转换为Map
     *
     * @param <K>   键类型
     * @param <V>   值类型
     * @param key   键
     * @param value 值
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> of(K key, V value) {
        return of(key, value, false);
    }

    /**
     * 将单一键值对转换为Map
     *
     * @param <K>     键类型
     * @param <V>     值类型
     * @param key     键
     * @param value   值
     * @param isOrder 是否有序
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> of(K key, V value, boolean isOrder) {
        final HashMap<K, V> map = CollectionTools.newHashMap(isOrder);
        map.put(key, value);
        return map;
    }

    /**
     * 使用给定的转换函数，转换源集合为新类型的集合
     *
     * @param <F>        源元素类型
     * @param <T>        目标元素类型
     * @param collection 集合
     * @param function   转换函数
     * @return 新类型的集合
     */
    public static <F, T> Collection<T> trans(Collection<F> collection, Function<? super F, ? extends T> function) {
        return new TransCollection<>(collection, function);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回 {@link HashSet}
     * @param ts       元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> set(boolean isSorted, T... ts) {
        if (null == ts) {
            return isSorted ? new LinkedHashSet<>() : new HashSet<>();
        }
        int initialCapacity = Math.max((int) (ts.length / .75f) + 1, 16);
        final HashSet<T> set = isSorted ? new LinkedHashSet<>(initialCapacity) : new HashSet<>(initialCapacity);
        Collections.addAll(set, ts);
        return set;
    }

    /**
     * 新建一个LinkedHashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> LinkedHashSet<T> newLinkedHashSet(T... ts) {
        return (LinkedHashSet<T>) set(true, ts);
    }

    /**
     * 新建一个LinkedList
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return List对象
     */
    @SafeVarargs
    public static <T> LinkedList<T> newLinkedList(T... ts) {
        return (LinkedList<T>) list(true, ts);
    }

    /**
     * 复制List创建新的List
     *
     * @param source 数据源
     * @param <T>    数据类型
     * @return 复制的新List
     */
    public static <T> List<T> copy(List<T> source) {
        return new ArrayList<>(source);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        return set(false, ts);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return newHashSet(false, collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param isSorted   是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param collection 集合，用于初始化Set
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Collection<T> collection) {
        return isSorted ? new LinkedHashSet<>(collection) : new HashSet<>(collection);
    }

    /**
     * 数组转为ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> toList(T... values) {
        return (ArrayList<T>) list(false, values);
    }

    /**
     * 新建一个List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     */
    @SafeVarargs
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayTools.isEmpty(values)) {
            return list(isLinked);
        }
        final List<T> arrayList = isLinked ? new LinkedList<>() : new ArrayList<>(values.length);
        Collections.addAll(arrayList, values);
        return arrayList;
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(boolean isLinked) {
        return newHashMap(DEFAULT_INITIAL_CAPACITY, isLinked);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @param size     初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isLinked) {
        final int initialCapacity = (int) (size / DEFAULT_LOAD_FACTOR) + 1;
        return isLinked ? new LinkedHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * 判断conditionItems元素是否出现在container中
     *
     * @param container      数据集合
     * @param conditionItems 查找目标集合
     * @param <T>            集合元素类型
     * @return 是否存在查询元素
     */
    public static <T> boolean hasAny(Collection<T> container, Collection<T> conditionItems) {
        return container.stream().filter(conditionItems::contains).findFirst().orElse(null) != null;
    }

    /**
     * 判断condition元素是否出现在container中
     *
     * @param container 数据集合
     * @param condition 查找目标
     * @param <T>       集合元素类型
     * @return 是否存在查询元素
     */
    public static <T> boolean has(Collection<T> container, T condition) {
        return container.contains(condition);
    }

    /**
     * Return an unmodifiable view of the specified multi-value map.
     *
     * @param targetMap the map for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified multi-value map
     * @param <K> Key类型
     * @param <V> Value类型
     */
    @SuppressWarnings("unchecked")
    public static <K, V> MultiValueMap<K, V> unmodifiableMultiValueMap(
            MultiValueMap<? extends K, ? extends V> targetMap) {

        AssertTools.notNull(targetMap, "'targetMap' must not be null");
        if (targetMap instanceof UnmodifiableMultiValueMap) {
            return (MultiValueMap<K, V>) targetMap;
        }
        return new UnmodifiableMultiValueMap<>(targetMap);
    }

    /**
     * Convert a {@link Collection} to a delimited {@code String} (for example, CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll   the {@code Collection} to convert (potentially {@code null} or empty)
     * @param delim  the delimiter to use (typically a ",")
     * @param prefix the {@code String} to start each element with
     * @param suffix the {@code String} to end each element with
     * @return the delimited {@code String}
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {

        if (isEmpty(coll)) {
            return "";
        }

        int totalLength = coll.size() * (prefix.length() + suffix.length()) + (coll.size() - 1) * delim.length();
        for (Object element : coll) {
            totalLength += String.valueOf(element).length();
        }

        StringBuilder sb = new StringBuilder(totalLength);
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (for example, CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll  the {@code Collection} to convert (potentially {@code null} or empty)
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }
}
