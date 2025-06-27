package cn.bytengine.d.sa;

/**
 * 会话SaIdentification
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class AbstractSessionSaIdentification implements SaIdentification {
    /**
     * 会话类型
     */
    private final String type;
    /**
     * session标识
     */
    private final Object session;

    /**
     * 构造器
     *
     * @param type    回话类型
     * @param session 会话标识
     */
    public AbstractSessionSaIdentification(String type, Object session) {
        this.type = type;
        this.session = session;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object identify() {
        return session;
    }
}
