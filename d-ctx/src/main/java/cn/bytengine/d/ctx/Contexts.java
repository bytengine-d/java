package cn.bytengine.d.ctx;

import java.util.Map;

/**
 * 上下文对象操作函数集合
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Contexts {

    private Contexts() {
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
     * @param data 数据内容
     * @return 上下文视图扩展实现类
     */
    public static Ctx space(Ctx parent, Map<String, Object> data) {
        return new CtxImpl(parent, data);
    }
}
