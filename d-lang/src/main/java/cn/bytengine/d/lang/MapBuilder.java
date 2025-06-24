package cn.bytengine.d.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Map创建类
 *
 * @param <K> Key类型
 * @param <V> Value类型
 * @author Ban Tenio
 * @version 1.0
 */
public class MapBuilder<K, V> {
    /**
     * 被委托Map实例
     */
    private final Map<K, V> map;

    /**
     * 使用HashMap创建MapBuilder
     *
     * @param <KT> Key类型
     * @param <VT> Value类型
     * @return Map构建器
     * @see HashMap
     */
    public static <KT, VT> MapBuilder<KT, VT> map() {
        return new MapBuilder<>(new HashMap<>());
    }

    /**
     * 提供Map内容添加接口构建Map内容
     *
     * @param putter Map内容添加接口
     * @param <KT>   Key类型
     * @param <VT>   Value类型
     * @return Map实例
     */
    public static <KT, VT> Map<KT, VT> map(Consumer<BiConsumer<KT, VT>> putter) {
        MapBuilder<KT, VT> builder = new MapBuilder<>(new HashMap<>());
        BiConsumer<KT, VT> put = builder::put;
        putter.accept(put);

        return builder.build();
    }

    /**
     * 创建Builder，默认HashMap实现
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return MapBuilder
     */
    public static <K, V> MapBuilder<K, V> create() {
        return create(false);
    }


    /**
     * 创建Builder
     *
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @param isLinked true创建LinkedHashMap，false创建HashMap
     * @return MapBuilder
     */
    public static <K, V> MapBuilder<K, V> create(boolean isLinked) {
        return create(CollectionTools.newHashMap(isLinked));
    }

    /**
     * 创建Builder
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param map Map实体类
     * @return MapBuilder
     */
    public static <K, V> MapBuilder<K, V> create(Map<K, V> map) {
        return new MapBuilder<>(map);
    }

    /**
     * 链式Map创建类
     *
     * @param map 要使用的Map实现类
     */
    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    /**
     * 链式Map创建
     *
     * @param k Key类型
     * @param v Value类型
     * @return 当前类
     */
    public MapBuilder<K, V> put(K k, V v) {
        map.put(k, v);
        return this;
    }

    /**
     * 链式Map创建
     *
     * @param condition put条件
     * @param k         Key类型
     * @param v         Value类型
     * @return 当前类
     */
    public MapBuilder<K, V> put(boolean condition, K k, V v) {
        if (condition) {
            put(k, v);
        }
        return this;
    }

    /**
     * 链式Map创建
     *
     * @param condition put条件
     * @param k         Key类型
     * @param supplier  Value类型结果提供方
     * @return 当前类
     */
    public MapBuilder<K, V> put(boolean condition, K k, Supplier<V> supplier) {
        if (condition) {
            put(k, supplier.get());
        }
        return this;
    }

    /**
     * 链式Map创建
     *
     * @param map 合并map
     * @return 当前类
     */
    public MapBuilder<K, V> putAll(Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    /**
     * 清空Map
     *
     * @return this
     */
    public MapBuilder<K, V> clear() {
        this.map.clear();
        return this;
    }

    /**
     * 创建后的map
     *
     * @return 创建后的map
     */
    public Map<K, V> build() {
        return this.map;
    }
}
