package cn.bytengine.d.sa;

/**
 * SA Servlet服务类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class SaServlet {
    private SaServlet() {
    }

    private static final String SA_SERVLET_ATTRIBUTE_KEY_PREFIX = SaServlet.class.getName() + ".";
    /**
     * Sa会话上下文请求Attribute Key
     */
    public static final String SA_SERVLET_SESSION_CTX_ATTRIBUTE_KEY = SA_SERVLET_ATTRIBUTE_KEY_PREFIX + "attribute$session_ctx";
}
