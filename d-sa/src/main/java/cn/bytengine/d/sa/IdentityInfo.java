package cn.bytengine.d.sa;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * 身份信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class IdentityInfo {
    private final Object user;
    private final Map<String, Object> userAttribute = new LinkedHashMap<>();

    /**
     * 构造器
     *
     * @param user 用户数据
     */
    public IdentityInfo(Object user) {
        this.user = user;
    }

    /**
     * 获取当前用户数据
     *
     * @param <T> 用户数据类型
     * @return 当前用户数据
     */
    public <T> T getUser() {
        return (T) user;
    }

    /**
     * 遍历用户属性数据
     *
     * @param action 属性消费
     */
    public void forEachAttributes(BiConsumer<? super String, ? super Object> action) {
        userAttribute.forEach(action);
    }

    /**
     * 移除用户属性
     *
     * @param key 被移除属性Key
     * @return 被移除属性值
     */
    public Object removeAttribute(String key) {
        return userAttribute.remove(key);
    }

    /**
     * 设置用户属性
     *
     * @param key   属性名
     * @param value 属性值
     * @return 当前身份信息
     */
    public IdentityInfo setAttribute(String key, Object value) {
        userAttribute.put(key, value);
        return this;
    }

    /**
     * 获取用户属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    public Object getAttribute(String key) {
        return userAttribute.get(key);
    }

    /**
     * 获取用户属性
     *
     * @param key 属性名
     * @param <T> 属性值类型
     * @return 属性值
     */
    public <T> T getAttributeWith(String key) {
        return (T) getAttribute(key);
    }

    /**
     * 获取用户属性名集合
     *
     * @return 属性名集合
     */
    public Set<String> attributesKeySet() {
        return userAttribute.keySet();
    }

    /**
     * 用户是否包含指定属性
     *
     * @param key 属性名
     * @return 是否包含属性
     */
    public boolean containsAttribute(String key) {
        return userAttribute.containsKey(key);
    }

    /**
     * 用户包含属性是否为空
     *
     * @return 是否包含属性
     */
    public boolean attributeIsEmpty() {
        return userAttribute.isEmpty();
    }

    /**
     * 用户包含属性数量
     *
     * @return 包含属性数量
     */
    public int attributeSize() {
        return userAttribute.size();
    }
}
