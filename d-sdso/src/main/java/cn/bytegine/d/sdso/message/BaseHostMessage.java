package cn.bytegine.d.sdso.message;

import cn.hutool.core.text.CharSequenceUtil;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunkaihan
 * @version 1.0
 */
public class BaseHostMessage<C extends BaseHostMessage<C>>
        implements HostMessage<C>, HostMessageAccessor<C> {
    private C me;
    private int messageVersion;
    private String sourceHostId;
    private String targetHostId;
    private String localHostId;
    private Instant requestTime;
    private String requestMessageId;
    private String replyMessageId;
    private Map<String, Object> headers;
    private byte[] content = new byte[0];

    public BaseHostMessage() {
        this.me = (C) this;
        headers = buildHeadersContainer();
    }

    private BaseHostMessage(Map<String, Object> headers) {
        this.me = (C) this;
        this.headers = headers;
    }

    protected Map<String, Object> buildHeadersContainer() {
        return new HashMap<>();
    }

    @Override
    public int getMessageVersion() {
        return messageVersion;
    }

    public C setMessageVersion(int messageVersion) {
        this.messageVersion = messageVersion;
        return me;
    }

    @Override
    public String getSourceHostId() {
        return this.sourceHostId;
    }

    public C setSourceHostId(String sourceHostId) {
        this.sourceHostId = sourceHostId;
        return this.me;
    }

    @Override
    public String getTargetHostId() {
        return targetHostId;
    }

    public C setTargetHostId(String targetHostId) {
        this.targetHostId = targetHostId;
        return me;
    }

    @Override
    public String getLocalHostId() {
        return localHostId;
    }

    public C setLocalHostId(String localHostId) {
        this.localHostId = localHostId;
        return me;
    }

    @Override
    public Instant getRequestTime() {
        return requestTime;
    }

    public C setRequestTime(Instant requestTime) {
        this.requestTime = requestTime;
        return me;
    }

    @Override
    public String getRequestMessageId() {
        return requestMessageId;
    }

    public C setRequestMessageId(String requestMessageId) {
        this.requestMessageId = requestMessageId;
        return me;
    }

    @Override
    public String getReplyMessageId() {
        return replyMessageId;
    }

    public C setReplyMessageId(String replyMessageId) {
        this.replyMessageId = replyMessageId;
        return me;
    }

    @Override
    public boolean isReply() {
        return CharSequenceUtil.isNotBlank(this.replyMessageId);
    }

    @Override
    public Map<String, Object> getHeaders() {
        return this.headers;
    }

    public C addHeader(String key, Object val) {
        this.headers.put(key, val);
        return me;
    }

    public C addHeaders(Map<String, ?> headers) {
        this.headers.putAll(headers);
        return me;
    }

    public C removeHeader(String key) {
        this.headers.remove(key);
        return me;
    }

    public boolean hasHeader(String key) {
        return this.headers.containsKey(key);
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    public C setContent(byte[] content) {
        this.content = content;
        return me;
    }

    @Override
    public HostMessage clone() {
        return new BaseHostMessage<>(this.headers)
                .setMessageVersion(this.getMessageVersion())
                .setSourceHostId(this.getSourceHostId())
                .setTargetHostId(this.getTargetHostId())
                .setLocalHostId(this.getLocalHostId())
                .setRequestTime(this.getRequestTime())
                .setRequestMessageId(this.getRequestMessageId())
                .setReplyMessageId(this.getReplyMessageId())
                .setContent(this.getContent());
    }

    @Override
    public String toString() {
        return "AbstractHostMessage{" +
                "messageVersion=" + messageVersion +
                ", sourceHostId='" + sourceHostId + '\'' +
                ", targetHostId='" + targetHostId + '\'' +
                ", localHostId='" + localHostId + '\'' +
                ", requestTime=" + requestTime +
                ", requestMessageId='" + requestMessageId + '\'' +
                ", replyMessageId='" + replyMessageId + '\'' +
                ", headers=" + headers +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
