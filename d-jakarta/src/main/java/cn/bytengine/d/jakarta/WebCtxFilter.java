package cn.bytengine.d.jakarta;

import cn.bytengine.d.ctx.Ctx;
import cn.bytengine.d.ctx.Ctxs;
import cn.bytengine.d.lang.CharSequenceTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * WebCtx请求过滤器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class WebCtxFilter extends HttpFilter {
    private static final String DEFAULT_SERVE_TRACE_ID_HEADER_KEY = "X-SERVE-TRACE-ID";
    private static final String DEFAULT_CLIENT_TRACE_ID_HEADER_KEY = "X-CLIENT-TRACE-ID";
    /**
     * 应用上下文
     */
    private final Ctx appCtx;
    /**
     * WebTrace配置策略
     */
    private final WebTraceConfig webTraceConfig;
    /**
     * Jakarta Servlet客户端TraceId查找器
     */
    private final JakartaServletClientTraceFinder clientTraceFinder;

    /**
     * 构造器
     *
     * @param appCtx         应用上下文
     * @param webTraceConfig WebTrace配置策略
     */
    public WebCtxFilter(Ctx appCtx, WebTraceConfig webTraceConfig) {
        this.appCtx = appCtx;
        this.webTraceConfig = webTraceConfig;
        this.clientTraceFinder = webTraceConfig.getClientTraceFinder();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        Ctx requestCtx = Ctxs.space(appCtx);
        WebCtx.setWebTraceConfig(webTraceConfig, requestCtx);
        WebCtx webCtx = Ctxs.proxy(WebCtx.class, requestCtx);
        String clientTraceId = webTraceConfig.isFindClientTraceId()
                ? clientTraceFinder.findClientTraceId(req)
                : CharSequenceTools.EMPTY;
        webCtx.generate(
                CharSequenceTools.nullToDefault(webTraceConfig.getTraceIdPrefix(), CharSequenceTools.EMPTY),
                CharSequenceTools.nullToDefault(clientTraceId, CharSequenceTools.EMPTY));
        req.setAttribute(WebCtx.WEB_CTX_REQUEST_ATTRIBUTE_KEY, webCtx);
        try {
            super.doFilter(req, res, chain);
        } finally {
            if (webTraceConfig.isReturnClientTraceId()) {
                String key = CharSequenceTools.nullToDefault(webTraceConfig.getClientTraceIdResponseHeaderKey(),
                        DEFAULT_CLIENT_TRACE_ID_HEADER_KEY);
                res.addHeader(key, clientTraceId);
            }
            if (webTraceConfig.isReturnTraceId()) {
                String key = CharSequenceTools.nullToDefault(webTraceConfig.getTraceIdResponseHeaderKey(),
                        DEFAULT_SERVE_TRACE_ID_HEADER_KEY);
                res.addHeader(key, webCtx.traceId());
            }
            req.removeAttribute(WebCtx.WEB_CTX_REQUEST_ATTRIBUTE_KEY);
        }
    }
}
