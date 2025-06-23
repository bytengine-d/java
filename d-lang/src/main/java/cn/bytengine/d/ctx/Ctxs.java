package cn.bytengine.d.ctx;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 上下文对象操作函数集合
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Ctxs {

    private Ctxs() {
    }

    private static final Ctx globalCtx = root();

    private static final Map<Class<?>, Function<? extends Ctx, ? extends CtxProxy>> REGISTER_CTX_PROXY_FACTORY_FUNCTIONS = new LinkedHashMap<>();

    /**
     * 创建一个新的根上下文视图
     *
     * @return 上下文视图扩展实现类
     */
    public static Ctx global() {
        return globalCtx;
    }

    /**
     * 创建一个新的根上下文视图
     *
     * @return 上下文视图扩展实现类
     */
    public static Ctx root() {
        return new CtxImpl();
    }

    /**
     * 根据指定数据创建一个新的根上下文视图
     *
     * @param data 数据内容
     * @return 上下文视图扩展实现类
     */
    public static Ctx root(Map<String, Object> data) {
        return new CtxImpl(data);
    }

    /**
     * 使用公共上下文创建新一级的上下文视图
     *
     * @return 上下文视图扩展实现类
     */
    public static Ctx space() {
        return space(global());
    }

    /**
     * 使用公共上下文创建新一级的上下文视图
     *
     * @param data 数据内容
     * @return 上下文视图扩展实现类
     */
    public static Ctx space(Map<String, Object> data) {
        return space(global(), data);
    }

    /**
     * 指定父级上下文创建新一级的上下文视图
     *
     * @param parent 父级上下文
     * @return 上下文视图扩展实现类
     */
    public static Ctx space(Ctx parent) {
        return new CtxImpl(parent);
    }

    /**
     * 指定父级上下文和数据，创建新一级的上下文视图
     *
     * @param parent 父级上下文
     * @param data   数据内容
     * @return 上下文视图扩展实现类
     */
    public static Ctx space(Ctx parent, Map<String, Object> data) {
        return new CtxImpl(parent, data);
    }

    /**
     * 根据指定类型，创建对应Ctx代理
     * <p>
     * Ctx代理实现类型必须已经注册，@link{}
     * </p>
     *
     * @param clazz    代理对应结构类型
     * @param delegate 委托Ctx
     * @param <T>      Ctx代理实现类
     * @return Ctx代理，如果没有
     * @see AbstractCtxProxy
     * @see cn.bytengine.d.ctx.annotations.CtxWrapper
     */
    public static <T extends CtxProxy> T proxy(Class<?> clazz, Ctx delegate) {
        return (T) REGISTER_CTX_PROXY_FACTORY_FUNCTIONS.get(clazz);
    }

    /**
     * 注册Ctx代理构造器，使用指定对应类型进行映射
     *
     * @param clazz           代理对应结构类型
     * @param factoryFunction Ctx代理构造器
     * @see #proxy(Class, Ctx)
     * @see cn.bytengine.d.ctx.annotations.CtxWrapper
     */
    public static void registerProxy(Class<?> clazz, Function<? extends Ctx, ? extends CtxProxy> factoryFunction) {
        REGISTER_CTX_PROXY_FACTORY_FUNCTIONS.put(clazz, factoryFunction);
    }
}
