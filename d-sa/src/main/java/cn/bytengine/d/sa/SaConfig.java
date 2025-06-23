package cn.bytengine.d.sa;

/**
 * SA认证配置
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaConfig {
    /**
     * 角色Glob判断器缓存
     */
    private final SaGlobPredicateCache saRoleGlobPredicateCache = new SaGlobPredicateCache();
    /**
     * 权限Glob判断器缓存
     */
    private final SaGlobPredicateCache saPermissionGlobPredicateCache = new SaGlobPredicateCache();

    /**
     * 获取角色Glob判断器缓存
     *
     * @return Glob判断器缓存
     */
    public SaGlobPredicateCache getSaRoleGlobPredicateCache() {
        return this.saRoleGlobPredicateCache;
    }

    /**
     * 获取权限Glob判断器缓存
     *
     * @return Glob判断器缓存
     */
    public SaGlobPredicateCache getSaPermissionGlobPredicateCache() {
        return saPermissionGlobPredicateCache;
    }
}
