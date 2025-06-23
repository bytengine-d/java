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

    public TraceConfig(UniqueGenerator uniqueGenerator) {
        this.uniqueGenerator = uniqueGenerator;
    }

    public UniqueGenerator getUniqueGenerator() {
        return uniqueGenerator;
    }
}
