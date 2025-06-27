package cn.bytengine.d.sa;

/**
 * Web SA配置策略
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class WebSaConfig extends SaConfig {
    private String httpSessionQueryStringKey = SaServlet.SA_SERVLET_HTTP_REQUEST_SESSION_IDENTIFICATION_QUERY_STRING_KEY;
    private String httpSessionHeaderKey = SaServlet.SA_SERVLET_HTTP_REQUEST_SESSION_IDENTIFICATION_HEADER_KEY;
    private String httpSessionCookieKey = SaServlet.SA_SERVLET_HTTP_REQUEST_SESSION_IDENTIFICATION_COOKIE_KEY;
    private JakartaServletSaIdentificationFinder finder = null;

    /**
     * 获取JakartaServletSaIdentificationFinder
     *
     * @return SaIdentificationFinder
     */
    public JakartaServletSaIdentificationFinder getFinder() {
        return finder;
    }

    /**
     * 设置JakartaServletSaIdentificationFinder
     *
     * @param finder SaIdentificationFinder
     * @return 当前配置
     */
    public WebSaConfig setFinder(JakartaServletSaIdentificationFinder finder) {
        this.finder = finder;
        return this;
    }

    /**
     * 获取Http请求中HttpSession标识QueryString的Key
     *
     * @return HttpSession标识的Key
     */
    public String getHttpSessionQueryStringKey() {
        return httpSessionQueryStringKey;
    }

    /**
     * 设置Http请求中HttpSession标识QueryString的Key
     *
     * @param httpSessionQueryStringKey HttpSession标识的Key
     * @return 当前配置
     * @see SaServlet#SA_SERVLET_HTTP_REQUEST_SESSION_IDENTIFICATION_QUERY_STRING_KEY
     */
    public WebSaConfig setHttpSessionQueryStringKey(String httpSessionQueryStringKey) {
        this.httpSessionQueryStringKey = httpSessionQueryStringKey;
        return this;
    }

    /**
     * 获取Http请求中HttpSession标识Header的Key
     *
     * @return HttpSession标识的Key
     */
    public String getHttpSessionHeaderKey() {
        return httpSessionHeaderKey;
    }

    /**
     * 设置Http请求中HttpSession标识Header的Key
     *
     * @param httpSessionHeaderKey HttpSession标识的Key
     * @return 当前配置
     * @see SaServlet#SA_SERVLET_HTTP_REQUEST_SESSION_IDENTIFICATION_HEADER_KEY
     */
    public WebSaConfig setHttpSessionHeaderKey(String httpSessionHeaderKey) {
        this.httpSessionHeaderKey = httpSessionHeaderKey;
        return this;
    }

    /**
     * 获取Http请求中HttpSession标识Cookie的Key
     *
     * @return HttpSession标识的Key
     */
    public String getHttpSessionCookieKey() {
        return httpSessionCookieKey;
    }

    /**
     * 设置Http请求中HttpSession标识Cookie的Key
     *
     * @param httpSessionCookieKey HttpSession标识的Key
     * @return 当前配置
     * @see SaServlet#SA_SERVLET_HTTP_REQUEST_SESSION_IDENTIFICATION_COOKIE_KEY
     */
    public WebSaConfig setHttpSessionCookieKey(String httpSessionCookieKey) {
        this.httpSessionCookieKey = httpSessionCookieKey;
        return this;
    }
}
