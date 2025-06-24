package cn.bytengine.d.utils;

import cn.bytengine.d.ctx.AbstractCtxProxy;
import cn.bytengine.d.ctx.Ctx;
import cn.bytengine.d.ctx.Ctxs;
import cn.bytengine.d.lang.AssertTools;

/**
 * Trace上下文代理
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class TraceCtx extends AbstractCtxProxy {
    private static final String TRACE_CTX_KEY_PREFIX = TraceCtx.class.getName() + ".";
    /**
     * TraceId上下文Key
     */
    public static final String TRACE_CTX_TRACE_ID_KEY_PREFIX = TRACE_CTX_KEY_PREFIX + "traceId";
    /**
     * TraceConfig上下文Key
     */
    public static final String TRACE_CTX_CONFIG_KEY_PREFIX = TRACE_CTX_KEY_PREFIX + "config";

    static {
        Ctxs.registerProxy(TraceCtx.class, TraceCtx::new);
    }

    /**
     * 构造器
     *
     * @param delegate 委托上下文
     */
    public TraceCtx(Ctx delegate) {
        super(delegate, false);
        AssertTools.notNull(getTraceConfig(), "no TraceConfig instance in current Ctx");
    }

    /**
     * 获取Trace配置策略
     *
     * @return Trace配置策略
     */
    public TraceConfig getTraceConfig() {
        return this.getByTypeWithDefault(TRACE_CTX_CONFIG_KEY_PREFIX, TraceConfig.class, null);
    }

    /**
     * 获取上下文TraceId
     *
     * @return TraceId
     */
    public String traceId() {
        return getString(TRACE_CTX_TRACE_ID_KEY_PREFIX);
    }

    /**
     * 上下文设置指定TraceID
     *
     * @param traceId 追踪Id
     * @return 当前上下文代理
     */
    public TraceCtx traceId(String traceId) {
        set(TRACE_CTX_TRACE_ID_KEY_PREFIX, traceId);
        return this;
    }

    /**
     * 上下文生成新的TraceId
     *
     * @return 当前上下文代理
     */
    public TraceCtx generate() {
        return generate("", "");
    }

    /**
     * 生成带有指定前缀的TraceID
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 当前上下文代理
     */
    public TraceCtx generate(String prefix, String suffix) {
        String traceId = getTraceConfig().getUniqueGenerator().nextUnique();
        traceId(prefix + traceId + suffix);
        return this;
    }

    /**
     * 给指定Ctx上下文设置TraceConfig实例
     *
     * @param traceConfig TraceConfig实例
     * @param ctx         上下文
     */
    public static void setTraceConfig(TraceConfig traceConfig, Ctx ctx) {
        AssertTools.notNull(traceConfig, "the TraceConfig instance is null");
        ctx.set(TRACE_CTX_CONFIG_KEY_PREFIX, traceConfig);
    }
}
