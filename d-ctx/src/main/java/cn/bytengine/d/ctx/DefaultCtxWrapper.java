package cn.bytengine.d.ctx;

/**
 * CtxWrapper默认实现
 * <p>
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.ctx
 * <li>ClassName:      DefaultCtxWrapper
 * <li>Date:    2024/6/3 16:13
 * </ul>
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class DefaultCtxWrapper extends AbstractCtxWrapper {
    /**
     * 指定委托对象，创建CtxWrapper
     *
     * @param delegate 委托上下文对象
     */
    public DefaultCtxWrapper(Ctx delegate) {
        super(delegate);
    }

    /**
     * 创建CtxWrapper
     */
    public DefaultCtxWrapper() {
        super(null);
    }
}
