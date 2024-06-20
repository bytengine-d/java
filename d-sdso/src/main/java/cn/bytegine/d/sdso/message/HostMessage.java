package cn.bytegine.d.sdso.message;

import java.time.Instant;
import java.util.Map;

/**
 * @author sunkaihan
 * @version 1.0
 */
public interface HostMessage<C extends HostMessage<C>> extends HostMessageAccessor<C> {
    int getMessageVersion();

    String getSourceHostId();

    String getTargetHostId();

    String getLocalHostId();

    Instant getRequestTime();

    String getRequestMessageId();

    String getReplyMessageId();

    boolean isReply();

    Map<String, Object> getHeaders();

    byte[] getContent();

    HostMessage clone();
}
