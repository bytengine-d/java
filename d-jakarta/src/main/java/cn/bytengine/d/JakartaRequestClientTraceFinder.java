package cn.bytengine.d;

import cn.bytengine.d.lang.CharSequenceTools;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

/**
 * 从请求寻找客户端TraceID信息，支持QueryString，Header和Cookie获取
 * <p>
 * 通过{@link WebTraceConfig#isFindClientTraceId()}控制是否进行找寻，通过{@link WebTraceConfig#getClientTraceIdKey()}查找。
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class JakartaRequestClientTraceFinder implements JakartaServletClientTraceFinder {
    private final WebTraceConfig webTraceConfig;

    /**
     * 构造器
     *
     * @param webTraceConfig Web Trace配置策略
     */
    public JakartaRequestClientTraceFinder(WebTraceConfig webTraceConfig) {
        this.webTraceConfig = webTraceConfig;
    }

    @Override
    public String findClientTraceId(HttpServletRequest req) {
        String clientTraceID = CharSequenceTools.EMPTY;
        if (!webTraceConfig.isFindClientTraceId()) {
            return clientTraceID;
        }
        String clientTraceIDKey = webTraceConfig.getClientTraceIdKey();
        String value = req.getParameter(clientTraceIDKey);
        if (CharSequenceTools.isNotBlank(value)) {
            clientTraceID = value;
        } else {
            value = req.getHeader(clientTraceIDKey);
            if (!CharSequenceTools.isNotBlank(value)) {
                value = Arrays.stream(req.getCookies())
                        .filter(cookie -> CharSequenceTools.equals(cookie.getName(), clientTraceIDKey))
                        .map(Cookie::getValue)
                        .findAny()
                        .orElse(CharSequenceTools.EMPTY);
            }
            clientTraceID = value;
        }
        return clientTraceID;
    }
}
