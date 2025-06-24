package cn.bytengine.d.utils;

import cn.bytengine.d.unique.UniqueGenerator;

/**
 * Trace配置策略
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class TraceConfig {
    private final UniqueGenerator uniqueGenerator;

    /**
     * 构造器
     *
     * @param uniqueGenerator TraceID生成器
     */
    public TraceConfig(UniqueGenerator uniqueGenerator) {
        this.uniqueGenerator = uniqueGenerator;
    }

    /**
     * 获取TraceID生成器
     *
     * @return TraceID生成器
     */
    public UniqueGenerator getUniqueGenerator() {
        return uniqueGenerator;
    }
}
