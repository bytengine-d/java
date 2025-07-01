package cn.bytengine.d.jakarta;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Ban Tenio
 * @version 1.0
 */
public interface JakartaServletClientTraceFinder {
    /**
     * 从当前Http请求获取客户端TraceID
     *
     * @param req Http请求
     * @return 客户端TraceId
     */
    String findClientTraceId(HttpServletRequest req);
}
