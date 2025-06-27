package cn.bytengine.d.sa;

/**
 * 判断是否支持指定类型SA标识，获取用户信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class AbstractIdentityInfoProvider implements IdentityInfoProvider {

    @Override
    public IdentityInfo get(SaIdentification saIdentification) {
        if (supportedType(saIdentification)) {
            return getIdentityInfo(saIdentification);
        }
        return null;
    }

    /**
     * 是否支持SA标识
     *
     * @param saIdentification SA标识
     * @return 是否支持
     */
    abstract protected boolean supportedType(SaIdentification saIdentification);

    /**
     * 获取SA标识的用户信息
     *
     * @param saIdentification SA标识
     * @return 用户信息
     */
    abstract protected IdentityInfo getIdentityInfo(SaIdentification saIdentification);
}
