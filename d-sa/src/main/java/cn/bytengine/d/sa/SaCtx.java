package cn.bytengine.d.sa;

import cn.bytengine.d.ctx.CtxImpl;
import cn.bytengine.d.lang.CollectionTools;
import cn.bytengine.d.lang.ObjectTools;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * SA上下文
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaCtx extends CtxImpl {
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
     * @param userId 上下文
     */
    public SaCtx login(Object userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 获取当前用户唯一标识
     *
     * @return 用户唯一标识
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
    public SaCtx addRole(String... roles) {
        return addRoles(Arrays.asList(roles));
    }

    public SaCtx addRoles(Collection<String> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public boolean hasRole(String checkRole) {
        return roles.contains(checkRole);
    }

    public boolean hasAnyRoles(String... checkRoles) {
        return hasAnyRoles(Arrays.asList(checkRoles));
    }

    public boolean hasAnyRoles(Collection<String> checkRoles) {
        return CollectionTools.hasAny(this.roles, checkRoles);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.arch"));
    }
}
