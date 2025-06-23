package cn.bytengine.d.sa;

import cn.bytengine.d.ctx.AbstractCtxProxy;
import cn.bytengine.d.ctx.Ctx;
import cn.bytengine.d.ctx.Ctxs;
import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CollectionTools;
import cn.bytengine.d.lang.ObjectTools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * SA上下文
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaSessionCtx extends AbstractCtxProxy {
    private static final String SA_SESSION_CTX_KEY_PREFIX = SaSessionCtx.class.getName() + ".";
    private static final String SA_SESSION_CTX_USER_ID_KEY = SA_SESSION_CTX_KEY_PREFIX + "userId";
    private static final String SA_SESSION_CTX_ROLES_KEY = SA_SESSION_CTX_KEY_PREFIX + "roles";
    private static final String SA_SESSION_CTX_PERMISSIONS_KEY = SA_SESSION_CTX_KEY_PREFIX + "permissions";
    private static final String SA_SESSION_CTX_SA_CONFIG_KEY = SA_SESSION_CTX_KEY_PREFIX + "saConfig";

    static {
        Ctxs.registerProxy(SaSessionCtx.class, SaSessionCtx::new);
    }

    /**
     * 构造器，指定上下文
     * <p><b>上下文内容要包括{@link SaConfig}实例</b></p>
     */
    public SaSessionCtx(Ctx delegate) {
        super(delegate, false);
        AssertTools.notNull(getSaConfig(), "no SaConfig instance in current Ctx");
    }

    /**
     * 构造器，指定上下文
     * <p><b>上下文内容要包括{@link SaConfig}实例</b></p>
     */
    public SaSessionCtx(SaConfig saConfig, Ctx delegate) {
        super(delegate, false);
        setSaConfig(saConfig, delegate);
    }

    /**
     * 获取SA配置策略
     *
     * @return SA配置策略
     */
    public SaConfig getSaConfig() {
        return this.getByTypeWithDefault(SA_SESSION_CTX_SA_CONFIG_KEY, SaConfig.class, null);
    }

    /**
     * 上下文是否登录状态
     *
     * @return 是否登录
     */
    public boolean isLoggedIn() {
        return ObjectTools.isNotNull(getUserId());
    }

    /**
     * 上下文进入登录状态
     *
     * @param userId 用户标识
     * @return 当前上下文
     */
    public SaSessionCtx login(Object userId) {
        set(SA_SESSION_CTX_USER_ID_KEY, userId);
        return this;
    }

    /**
     * 当前用户状态登出
     *
     * @return 当前上下文
     */
    public SaSessionCtx logout() {
        remove(SA_SESSION_CTX_USER_ID_KEY);
        remove(SA_SESSION_CTX_ROLES_KEY);
        remove(SA_SESSION_CTX_PERMISSIONS_KEY);
        return this;
    }

    /**
     * 判断当前登陆用户是否指定用户
     *
     * @param userId 用户ID
     * @return 是否为指定用户
     */
    public boolean isUser(Object userId) {
        if (!isLoggedIn()) {
            return false;
        }
        return ObjectTools.equals(userId, getUserId());
    }

    /**
     * 判断当前登录用户是否在指定用户ID集合中
     *
     * @param userIds 用户ID集合
     * @return 是否在指定用户集合中
     */
    public boolean inUser(Collection<Object> userIds) {
        if (!isLoggedIn()) {
            return false;
        }
        return CollectionTools.has(userIds, getUserId());
    }

    /**
     * 获取当前用户唯一标识
     *
     * @param <T> 用户Id类型
     * @return 用户唯一标识
     */
    public <T> T getUserId() {
        return (T) this.getByTypeWithDefault(SA_SESSION_CTX_USER_ID_KEY, Object.class, null);
    }

    /**
     * 从上下文获取用户角色集合
     *
     * @return 用户角色集合
     */
    protected Set<String> getRoles() {
        return (Set<String>) this.getByType(SA_SESSION_CTX_ROLES_KEY, Set.class, CollectionTools::newHashSet);
    }

    /**
     * 用户添加角色
     *
     * @param roles 角色集合
     * @return 当前上下文
     */
    public SaSessionCtx addRole(String... roles) {
        return addRoles(Arrays.asList(roles));
    }

    /**
     * 用户添加角色
     *
     * @param roles 角色集合
     * @return 当前上下文
     */
    public SaSessionCtx addRoles(Collection<String> roles) {
        getRoles().addAll(roles);
        return this;
    }

    /**
     * 用户是否符合检查角色条件
     *
     * @param checkRole 检查角色条件
     * @return 是否负责角色条件
     */
    public boolean hasRole(String checkRole) {
        SaGlobPredicate tester = getSaConfig().getSaRoleGlobPredicateCache().get(checkRole);
        Optional<String> result = getRoles().stream().filter(tester).findAny();
        return result.isPresent();
    }

    /**
     * 用户是否符合检查角色条件
     *
     * @param checkRoles 检查角色条件集合
     * @return 是否符合角色条件
     */
    public boolean hasAnyRoles(String... checkRoles) {
        return hasAnyRoles(Arrays.asList(checkRoles));
    }

    /**
     * 用户是否符合检查角色条件
     *
     * @param checkRoles 检查角色条件
     * @return 是否符合角色条件
     */
    public boolean hasAnyRoles(Collection<String> checkRoles) {
        SaGlobPredicate tester;
        Optional<String> result;
        Set<String> roles = getRoles();
        SaGlobPredicateCache cache = getSaConfig().getSaRoleGlobPredicateCache();
        for (String checkRole : checkRoles) {
            tester = cache.get(checkRole);
            result = roles.stream().filter(tester).findAny();
            if (result.isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从上下文获取用户角色集合
     *
     * @return 用户角色集合
     */
    protected Set<String> getPermissions() {
        return (Set<String>) this.getByType(SA_SESSION_CTX_PERMISSIONS_KEY, Set.class, CollectionTools::newHashSet);
    }

    /**
     * 添加用户权限
     *
     * @param permissions 用户权限集合
     * @return 当前上下文
     */
    public SaSessionCtx addPermission(String... permissions) {
        return addPermissions(Arrays.asList(permissions));
    }

    /**
     * 添加用户权限
     *
     * @param permissions 用户权限集合
     * @return 当前上下文
     */
    public SaSessionCtx addPermissions(Collection<String> permissions) {
        getPermissions().addAll(permissions);
        return this;
    }

    /**
     * 用户是否符合检查权限条件
     *
     * @param checkPermission 检查权限条件
     * @return 是否负责权限条件
     */
    public boolean hasPermission(String checkPermission) {
        SaGlobPredicate tester = getSaConfig().getSaPermissionGlobPredicateCache().get(checkPermission);
        Optional<String> result = getPermissions().stream().filter(tester).findAny();
        return result.isPresent();
    }

    /**
     * 用户是否符合检查权限条件
     *
     * @param checkPermissions 检查角色权限集合
     * @return 是否符合权限条件
     */
    public boolean hasAnyPermissions(String... checkPermissions) {
        return hasAnyPermissions(Arrays.asList(checkPermissions));
    }

    /**
     * 用户是否符合检查权限条件
     *
     * @param checkPermissions 检查权限条件
     * @return 是否符合权限条件
     */
    public boolean hasAnyPermissions(Collection<String> checkPermissions) {
        SaGlobPredicate tester;
        Optional<String> result;
        Set<String> permissions = getPermissions();
        SaGlobPredicateCache cache = getSaConfig().getSaPermissionGlobPredicateCache();
        for (String checkPermission : checkPermissions) {
            tester = cache.get(checkPermission);
            result = permissions.stream().filter(tester).findAny();
            if (result.isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给指定Ctx上下文设置SaConfig实例
     *
     * @param saConfig SaConfig实例
     * @param ctx      上下文
     */
    public static void setSaConfig(SaConfig saConfig, Ctx ctx) {
        AssertTools.notNull(saConfig, "the SaConfig instance is null");
        ctx.set(SA_SESSION_CTX_SA_CONFIG_KEY, saConfig);
    }
}
