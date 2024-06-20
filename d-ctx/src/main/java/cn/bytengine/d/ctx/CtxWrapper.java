package cn.bytengine.d.ctx;

/**
 * 上下文包装器
 * <p>
 * 应用于上下文视图代理关系
 * </p>
 *
 * @param <C> 上下文包装器实现类型
 * @author Ban Tenio
 * @version 1.0
 */
public interface CtxWrapper<C extends CtxWrapper<C>> extends Ctx {
    /**
     * 获取代理上下文
     *
     * @return 上下文对象
     */
    Ctx getDelegate();

    /**
     * 指定新的代理上下文
     *
     * @param delegate 新代理上下文对象
     * @return 当前包装器对象类型
     */
    C setDelegate(Ctx delegate);

    /**
     * 是否包含代理上下文
     *
     * @return true表示包含，反之返回false
     */
    default boolean hasDelegate() {
        return getDelegate() != null;
    }

    default Object get(String key) {
        return getDelegate().get(key);
    }

    default Ctx set(String key, Object value) {
        getDelegate().set(key, value);
        return this;
    }

    default boolean has(String key) {
        return getDelegate().has(key);
    }

    default C remove(String key) {
        getDelegate().remove(key);
        return (C) this;
    }

    default Ctx getParent() {
        return getDelegate().getParent();
    }
}
