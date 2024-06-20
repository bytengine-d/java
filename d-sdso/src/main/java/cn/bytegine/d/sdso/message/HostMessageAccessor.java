package cn.bytegine.d.sdso.message;

import java.time.Instant;
import java.util.Map;

/**
 * @author sunkaihan
 * @version 1.0
 */
public interface HostMessageAccessor<C extends HostMessageAccessor<C>> {
    C setMessageVersion(int messageVersion);

    C setSourceHostId(String sourceHostId);

    C setTargetHostId(String targetHostId);

    C setLocalHostId(String localHostId);

    C setRequestTime(Instant requestTime);

    C setRequestMessageId(String requestMessageId);

    C setReplyMessageId(String replyMessageId);

    C addHeader(String key, Object val);

    C addHeaders(Map<String, ?> headers);

    C removeHeader(String key);

    boolean hasHeader(String key);

    C setContent(byte[] content);
}
