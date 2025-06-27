package cn.bytengine.d.sa;

/**
 * 用户信息提供者
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface IdentityInfoProvider {
    /**
     * 根据指定SA唯一标识，获取用户身份信息
     *
     * @param saIdentification SA唯一标识
     * @return 身份信息，可能为null
     */
    IdentityInfo get(SaIdentification saIdentification);
}
