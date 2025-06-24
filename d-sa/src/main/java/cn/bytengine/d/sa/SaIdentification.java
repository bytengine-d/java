package cn.bytengine.d.sa;

/**
 * SA唯一标识，可以是不同类型的
 * <ul>
 *    <li>手机号</li>
 *    <li>SessionId</li>
 *    <li>API Key/Token</li>
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface SaIdentification {
    /**
     * 获取SA唯一标识类型
     *
     * @return 唯一标识类型
     */
    String getType();

    /**
     * 获取唯一标识数据
     *
     * @return 唯一标识数据
     */
    Object identify();
}
