package cn.bytengine.d.jakarta;

import cn.bytengine.d.ctx.Ctx;
import cn.bytengine.d.ctx.Ctxs;
import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.utils.TraceCtx;

/**
 * Web代理上下文
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class WebCtx extends TraceCtx {
    private static final String WEB_CTX_CONFIG_KEY_PREFIX = "cn.bytengine.d.WebCtx.config";
    /**
     * HttpServletRequest Attribute存储WebCtx上下文的Key
     */
    public static final String WEB_CTX_REQUEST_ATTRIBUTE_KEY = "cn.bytengine.d.WebCtx.$Attribute$Key";

    static {
        Ctxs.registerProxy(WebCtx.class, WebCtx::new);
    }

    /**
     * 构造器
     *
     * @param delegate 委托上下文
     */
    public WebCtx(Ctx delegate) {
        super(delegate);
    }

    @Override
    public WebTraceConfig getTraceConfig() {
        return this.getByTypeWithDefault(WEB_CTX_CONFIG_KEY_PREFIX, WebTraceConfig.class, null);
    }

    /**
     * 给指定Ctx上下文设置WebTraceConfig实例
     *
     * @param webTraceConfig WebTraceConfig实例
     * @param ctx            上下文
     */
    public static void setWebTraceConfig(WebTraceConfig webTraceConfig, Ctx ctx) {
        AssertTools.notNull(webTraceConfig, "the WebTraceConfig instance is null");
        ctx.set(WEB_CTX_CONFIG_KEY_PREFIX, webTraceConfig);
    }
}
