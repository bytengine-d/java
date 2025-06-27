package cn.bytengine.d.sa;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于Servlet API的SaIdentification查找器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface JakartaServletSaIdentificationFinder {
    /**
     * 查找请求携带的SaIdentification信息
     *
     * @param saConfig Web SA配置
     * @param request  Http请求
     * @return SA唯一标识
     */
    SaIdentification find(WebSaConfig saConfig, HttpServletRequest request);
}
