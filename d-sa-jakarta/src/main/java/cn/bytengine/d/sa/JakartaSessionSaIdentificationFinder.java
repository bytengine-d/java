package cn.bytengine.d.sa;

import cn.bytengine.d.lang.CharSequenceTools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 从HttpServletRequest获取SessionId
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class JakartaSessionSaIdentificationFinder implements JakartaServletSaIdentificationFinder {
    
    @Override
    public SaIdentification find(WebSaConfig saConfig, HttpServletRequest request) {
        String key = saConfig.getHttpSessionQueryStringKey();
        SaIdentification saIdentification = null;
        if (CharSequenceTools.isNotBlank(key)) {
            String value = request.getParameter(key);
            if (CharSequenceTools.isNotBlank(value)) {
                saIdentification = new HttpSessionSaIdentification(value);
            }
        }
        if (saIdentification == null) {
            key = saConfig.getHttpSessionHeaderKey();
            if (CharSequenceTools.isNotBlank(key)) {
                String value = request.getHeader(key);
                if (CharSequenceTools.isNotBlank(value)) {
                    saIdentification = new HttpSessionSaIdentification(value);
                }
            }
        }
        if (saIdentification == null) {
            key = saConfig.getHttpSessionCookieKey();
            if (CharSequenceTools.isNotBlank(key)) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (CharSequenceTools.equals(cookie.getName(), key)) {
                        String value = cookie.getValue();
                        if (CharSequenceTools.isNotBlank(value)) {
                            saIdentification = new HttpSessionSaIdentification(value);
                            break;
                        }
                    }
                }
            }
        }
        return saIdentification;
    }
}
