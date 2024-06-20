package cn.bytengine.d.ctx;

/**
 * CtxWrapper实现基类
 * <p>
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.ctx
 * <li>ClassName:      AbstractCtxWrapper
 * <li>Date:    2024/6/3 16:05
 * </ul>
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class AbstractCtxWrapper implements CtxWrapper<AbstractCtxWrapper> {
    private Ctx delegate = null;

    /**
     * 指定委托对象，创建CtxWrapper
     *
     * @param delegate 委托上下文对象
     */
    protected AbstractCtxWrapper(Ctx delegate) {
        this.delegate = delegate;
    }

    /**
     * 创建CtxWrapper
     */
    protected AbstractCtxWrapper() {
        this(null);
    }

    @Override
    public Ctx getDelegate() {
        return delegate;
    }

    @Override
    public AbstractCtxWrapper setDelegate(Ctx delegate) {
        this.delegate = delegate;
        return this;
    }
}
