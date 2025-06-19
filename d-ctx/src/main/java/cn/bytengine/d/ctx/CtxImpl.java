package cn.bytengine.d.ctx;

import cn.bytengine.d.lang.AssertTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上下文实现类
 * <p>
 * 它是基于Map为存储结构的实现。
 * 并且它实现了父子关系
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CtxImpl implements Ctx {
    private Ctx parentCtx;
    private Map<String, Object> source;

    /**
     * 上下文构造器
     */
    public CtxImpl() {
        this((Ctx) null);
    }

    /**
     * 上下文构造器，指定父级上下文
     *
     * @param parentCtx 父级上下文
     */
    public CtxImpl(final Ctx parentCtx) {
        this.source = new ConcurrentHashMap<>();
        this.parentCtx = parentCtx;
    }

    /**
     * 上下文构造器，指定数据源
     *
     * @param source 数据源
     */
    public CtxImpl(final Map<String, ?> source) {
        Set<String> strings = source.keySet();
        List<String> keys = new ArrayList<>(strings);
        for (String key : keys) {
            if (source.get(key) == null) {
                source.remove(key);
            }
        }
        this.source = new ConcurrentHashMap<>(source);
        this.parentCtx = null;
    }

    /**
     * 上下文构造器，指定父级上下文和数据源
     *
     * @param parentCtx 父级上下文
     * @param source    数据源
     */
    public CtxImpl(final Ctx parentCtx, final Map<String, ?> source) {
        this.source = source == null ? null : new ConcurrentHashMap<>(source);
        this.parentCtx = parentCtx;
    }

    @Override
    public Object get(String key) {
        AssertTools.notNull(key, "The key is null.");
        Object obj = this.source.get(key);
        if (obj == null && this.parentCtx != null) {
            obj = this.parentCtx.get(key);
        }
        return obj;
    }

    @Override
    public Ctx set(String key, Object value) {
        AssertTools.notNull(key, "The key is null.");
        if (value != null) {
            this.source.put(key, value);
        } else {
            this.source.remove(key);
        }
        return this;
    }

    @Override
    public boolean has(String key) {
        AssertTools.notNull(key, "The key is null.");
        if (this.source.containsKey(key)) {
            return true;
        } else {
            return parentCtx != null && parentCtx.has(key);
        }
    }

    @Override
    public CtxImpl remove(String key) {
        AssertTools.notNull(key, "The key is null.");
        return this;
    }

    @Override
    public Ctx getParent() {
        return parentCtx;
    }

    /**
     * 清理上下文内容
     *
     * @return 上下文
     */
    public CtxImpl clear() {
        this.source.clear();
        return this;
    }

    /**
     * 获取上下文源
     *
     * @return 上下文源
     */
    public Map<String, Object> getSource() {
        return this.source;
    }

    /**
     * 设置上下文源
     *
     * @param source 上下文源
     * @return 上下文
     */
    public CtxImpl setSource(Map<String, Object> source) {
        this.source = source;
        return this;
    }

    @Override
    public String toString() {
        return "CtxImpl{" +
                "parent=" + parentCtx +
                ", source=" + source +
                '}';
    }
}
