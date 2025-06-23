package cn.bytengine.d;

import cn.bytengine.d.unique.UniqueGenerator;
import cn.bytengine.d.utils.TraceConfig;

/**
 * Web Trace配置策略
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class WebTraceConfig extends TraceConfig {
    private final JakartaServletClientTraceFinder clientTraceFinder;
    private String traceIdResponseHeaderKey;
    private boolean returnTraceId;
    private String clientTraceIdResponseHeaderKey;
    private boolean returnClientTraceId;
    private String traceIdPrefix = "web-";

    /**
     * 指定TraceId生成器和客户端TraceId查找器，创建WebTraceConfig配置
     *
     * @param uniqueGenerator   TraceId生成器
     * @param clientTraceFinder 客户端TraceId查找器
     */
    public WebTraceConfig(UniqueGenerator uniqueGenerator, JakartaServletClientTraceFinder clientTraceFinder) {
        super(uniqueGenerator);
        this.clientTraceFinder = clientTraceFinder;
    }

    /**
     * 获取客户端TraceId查找器
     *
     * @return 客户端TraceId查找器
     */
    public JakartaServletClientTraceFinder getClientTraceFinder() {
        return clientTraceFinder;
    }

    /**
     * 获取响应Header Key，用于返回服务端生成TraceId
     *
     * @return Header Key
     * @see #isReturnTraceId()
     */
    public String getTraceIdResponseHeaderKey() {
        return traceIdResponseHeaderKey;
    }

    /**
     * 设置响应Header Key，用于返回服务端生成TraceId
     *
     * @param traceIdResponseHeaderKey Header Key
     * @return 当前配置
     */
    public WebTraceConfig setTraceIdResponseHeaderKey(String traceIdResponseHeaderKey) {
        this.traceIdResponseHeaderKey = traceIdResponseHeaderKey;
        return this;
    }

    /**
     * 是否返回TraceId
     *
     * @return 是否返回
     * @see #setTraceIdResponseHeaderKey(String)
     * @see #getTraceIdResponseHeaderKey()
     */
    public boolean isReturnTraceId() {
        return returnTraceId;
    }

    /**
     * 设置是否返回服务端TraceId
     *
     * @param returnTraceId 是否启用
     * @return 当前配置
     */
    public WebTraceConfig setReturnTraceId(boolean returnTraceId) {
        this.returnTraceId = returnTraceId;
        return this;
    }

    /**
     * 获取响应Header Key，用于返回客户端TraceId
     *
     * @return Header Key
     */
    public String getClientTraceIdResponseHeaderKey() {
        return clientTraceIdResponseHeaderKey;
    }

    /**
     * 设置响应Header Key，用于返回客户端TraceId
     *
     * @param clientTraceIdResponseHeaderKey Header Key
     * @return 当前配置
     */
    public WebTraceConfig setClientTraceIdResponseHeaderKey(String clientTraceIdResponseHeaderKey) {
        this.clientTraceIdResponseHeaderKey = clientTraceIdResponseHeaderKey;
        return this;
    }

    /**
     * 是否返回客户端TraceId
     *
     * @return 是否返回
     * @see #setClientTraceIdResponseHeaderKey(String)
     * @see #getClientTraceIdResponseHeaderKey()
     */
    public boolean isReturnClientTraceId() {
        return returnClientTraceId;
    }

    /**
     * 设置是否返回客户端TraceId
     *
     * @param returnClientTraceId 是否启用
     * @return 当前配置
     */
    public WebTraceConfig setReturnClientTraceId(boolean returnClientTraceId) {
        this.returnClientTraceId = returnClientTraceId;
        return this;
    }

    /**
     * 获取TraceId前缀，默认"web-"
     *
     * @return TraceId前缀
     */
    public String getTraceIdPrefix() {
        return traceIdPrefix;
    }

    /**
     * 设置TraceId前缀，如果为null，则不添加前缀
     *
     * @param traceIdPrefix TraceId前缀
     * @return 当前配置
     */
    public WebTraceConfig setTraceIdPrefix(String traceIdPrefix) {
        this.traceIdPrefix = traceIdPrefix;
        return this;
    }

    @Override
    public String toString() {
        return "WebTraceConfig{" +
                "clientTraceFinder=" + clientTraceFinder +
                ", traceIdResponseHeaderKey='" + traceIdResponseHeaderKey + '\'' +
                ", returnTraceId=" + returnTraceId +
                ", clientTraceIdResponseHeaderKey='" + clientTraceIdResponseHeaderKey + '\'' +
                ", returnClientTraceId=" + returnClientTraceId +
                ", traceIdPrefix='" + traceIdPrefix + '\'' +
                "} " + super.toString();
    }
}
