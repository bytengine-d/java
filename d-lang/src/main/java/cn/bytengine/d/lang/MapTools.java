package cn.bytengine.d.lang;

import java.util.Map;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class MapTools {
    private MapTools() {
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && false == map.isEmpty();
    }

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
}
