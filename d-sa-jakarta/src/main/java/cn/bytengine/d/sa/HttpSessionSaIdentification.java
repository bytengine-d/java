package cn.bytengine.d.sa;

/**
 * Http会话SA标识
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class HttpSessionSaIdentification extends AbstractSessionSaIdentification {
    /**
     * 构造器
     *
     * @param session 会话标识
     */
    public HttpSessionSaIdentification(Object session) {
        super("http-session", session);
    }
}
