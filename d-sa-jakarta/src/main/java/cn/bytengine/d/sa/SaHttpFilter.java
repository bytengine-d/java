package cn.bytengine.d.sa;

import cn.bytengine.d.WebCtx;
import cn.bytengine.d.ctx.Ctxs;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SA Http请求过滤器，主要从请求获取SA信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaHttpFilter extends HttpFilter {
    private final SaConfig saConfig;

    /**
     * 构造器
     *
     * @param saConfig SA配置策略
     */
    public SaHttpFilter(SaConfig saConfig) {
        this.saConfig = saConfig;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        WebCtx webCtx = (WebCtx) req.getAttribute(WebCtx.WEB_CTX_REQUEST_ATTRIBUTE_KEY);
        SaSessionCtx.setSaConfig(saConfig, webCtx);
        SaSessionCtx saSessionCtx = Ctxs.proxy(SaSessionCtx.class, webCtx);
        req.setAttribute(SaServlet.SA_SERVLET_SESSION_CTX_ATTRIBUTE_KEY, saSessionCtx);
        try {
            super.doFilter(req, res, chain);
        } finally {
            req.removeAttribute(SaServlet.SA_SERVLET_SESSION_CTX_ATTRIBUTE_KEY);
        }
    }
}
