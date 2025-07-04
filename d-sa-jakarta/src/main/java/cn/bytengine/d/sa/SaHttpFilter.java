package cn.bytengine.d.sa;

import cn.bytengine.d.ctx.Ctxs;
import cn.bytengine.d.jakarta.WebCtx;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SA Http请求过滤器，主要从请求获取SA信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaHttpFilter extends HttpFilter {
    /**
     * SA配置策略
     */
    private final WebSaConfig saConfig;

    /**
     * 构造器
     *
     * @param saConfig SA配置策略
     */
    public SaHttpFilter(WebSaConfig saConfig) {
        this.saConfig = saConfig;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        WebCtx webCtx = (WebCtx) req.getAttribute(WebCtx.WEB_CTX_REQUEST_ATTRIBUTE_KEY);
        SaSessionCtx.setSaConfig(saConfig, webCtx);
        SaSessionCtx saSessionCtx = Ctxs.proxy(SaSessionCtx.class, webCtx);
        req.setAttribute(SaServlet.SA_SERVLET_SESSION_CTX_ATTRIBUTE_KEY, saSessionCtx);
        IdentityInfoProvider identityInfoProvider = saConfig.getIdentityInfoProvider();
        if (identityInfoProvider != null) {
            JakartaServletSaIdentificationFinder finder = saConfig.getFinder();
            if (finder != null) {
                SaIdentification saIdentification = finder.find(saConfig, req);
                if (saIdentification != null) {
                    IdentityInfo identityInfo = identityInfoProvider.get(saIdentification);
                    if (identityInfo != null) {
                        saSessionCtx.login(identityInfo.getUser());
                    }
                }
            }
        }
        try {
            super.doFilter(req, res, chain);
        } finally {
            req.removeAttribute(SaServlet.SA_SERVLET_SESSION_CTX_ATTRIBUTE_KEY);
        }
    }
}
