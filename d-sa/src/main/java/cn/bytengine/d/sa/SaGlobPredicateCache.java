package cn.bytengine.d.sa;

import cn.bytengine.d.collection.ConcurrentLruCache;

/**
 * Glob表达式判断器缓存
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaGlobPredicateCache {
    /**
     * TODO
     */
    private final ConcurrentLruCache<String, SaGlobPredicate> saGlobPredicateLruCache;

    /**
     * 构建LRU Glob判断器缓存
     */
    public SaGlobPredicateCache() {
        this.saGlobPredicateLruCache = buildCache();
    }

    /**
     * 构建缓存
     *
     * @return 缓存
     * @see ConcurrentLruCache
     */
    protected ConcurrentLruCache<String, SaGlobPredicate> buildCache() {
        return new ConcurrentLruCache<>(128, SaGlobPredicate::new);
    }

    /**
     * 从缓存中获取指定表达式判断器
     *
     * @param globExpression Glob表达式
     * @return Glob判断器
     * @see ConcurrentLruCache#get(Object)
     */
    public SaGlobPredicate get(String globExpression) {
        return saGlobPredicateLruCache.get(globExpression);
    }
}
