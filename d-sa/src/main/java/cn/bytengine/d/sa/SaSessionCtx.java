package cn.bytengine.d.sa;

import cn.bytengine.d.ctx.CtxImpl;
import cn.bytengine.d.lang.CollectionTools;
import cn.bytengine.d.lang.ObjectTools;

import java.util.*;

/**
 * SA上下文
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaSessionCtx extends CtxImpl {
    /**
     * 用户唯一标识
     */
    private Object userId;
    /**
     * 用户角色集合
     */
    private final Set<String> roles = new LinkedHashSet<>();
    /**
     * 用户权限集合
     */
    private final Set<String> permissions = new LinkedHashSet<>();
    /**
     * SA配置对象
     */
    private final SaConfig saConfig;

    /**
     * 构造器，指定SA配置策略
     *
     * @param saConfig SA配置策略
     */
    public SaSessionCtx(SaConfig saConfig) {
        this.saConfig = saConfig;
    }

    /**
     * 获取SA配置策略
     *
     * @return SA配置策略
     */
    public SaConfig getSaConfig() {
        return saConfig;
    }

    /**
     * 上下文是否登录状态
     *
     * @return 是否登录
     */
    public boolean isLoggedIn() {
        return ObjectTools.isNotNull(userId);
    }

    /**
     * 上下文进入登录状态
     *
     * @param userId 用户标识
     * @return 当前上下文
     */
    public SaSessionCtx login(Object userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 当前用户状态登出
     *
     * @return 当前上下文
     */
    public SaSessionCtx logout() {
        this.userId = null;
        this.roles.clear();
        this.permissions.clear();
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
        return ObjectTools.equals(userId, this.userId);
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
        return CollectionTools.has(userIds, userId);
    }

    /**
     * 获取当前用户唯一标识
     *
     * @return 用户唯一标识
     * @param <T> 用户Id类型
     */
    public <T> T getUserId() {
        return (T) userId;
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
        this.roles.addAll(roles);
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
        Optional<String> result = roles.stream().filter(tester::test).findAny();
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
        this.permissions.addAll(permissions);
        return this;
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
}
