package cn.bytengine.d.ctx;

import cn.bytengine.d.lang.AssertTools;

import java.time.Instant;
import java.util.function.Supplier;

/**
 * Ctx上下文代理，主要用于对CtxWrapper提供辅助
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class AbstractCtxProxy implements CtxProxy {
    private final Ctx delegate;
    private final boolean readOnly;

    /**
     * 指定委托Ctx实例创建只读Ctx代理
     *
     * @param delegate 委托Ctx实例
     * @see #AbstractCtxProxy(Ctx, boolean)
     */
    protected AbstractCtxProxy(Ctx delegate) {
        this(delegate, true);
    }

    /**
     * 指定委托Ctx实例创建Ctx代理
     *
     * @param delegate 委托Ctx实例
     * @param readOnly 是否只读
     */
    protected AbstractCtxProxy(Ctx delegate, boolean readOnly) {
        AssertTools.notNull(delegate, "delegate Ctx instance is null");
        while (delegate instanceof AbstractCtxProxy) {
            delegate = ((AbstractCtxProxy) delegate).delegate;
        }
        this.delegate = delegate;
        this.readOnly = readOnly;
    }

    /**
     * 获取委托Ctx实例
     *
     * @return 委托Ctx实例
     */
    final public Ctx delegate() {
        return this.delegate;
    }

    @Override
    public Object get(String key) {
        return delegate.get(key);
    }

    @Override
    public Ctx set(String key, Object value) {
        if (readOnly) {
            throw new UnsupportedOperationException("current Ctx is read only");
        }
        return delegate.set(key, value);
    }

    @Override
    public boolean has(String key) {
        return delegate.has(key);
    }

    @Override
    public Ctx remove(String key) {
        if (readOnly) {
            throw new UnsupportedOperationException("current Ctx is read only");
        }
        return delegate.remove(key);
    }

    @Override
    public <T> T getByType(String key, Class<T> type) {
        return delegate.getByType(key, type);
    }

    @Override
    public <T> T getByTypeWithDefault(String key, Class<T> type, T defaultVal) {
        return delegate.getByTypeWithDefault(key, type, defaultVal);
    }

    @Override
    public <T> T getByType(String key, Class<T> type, Supplier<T> defaultValSupplier) {
        return delegate.getByType(key, type, defaultValSupplier);
    }

    @Override
    public String getString(String key) {
        return delegate.getString(key);
    }

    @Override
    public String getString(String key, String defaultVal) {
        return delegate.getString(key, defaultVal);
    }

    @Override
    public String getString(String key, Supplier<String> defaultValSupplier) {
        return delegate.getString(key, defaultValSupplier);
    }

    @Override
    public Character getChar(String key) {
        return delegate.getChar(key);
    }

    @Override
    public Character getChar(String key, Character defaultVal) {
        return delegate.getChar(key, defaultVal);
    }

    @Override
    public Character getChar(String key, Supplier<Character> defaultValSupplier) {
        return delegate.getChar(key, defaultValSupplier);
    }

    @Override
    public Number getNumber(String key) {
        return delegate.getNumber(key);
    }

    @Override
    public Number getNumber(String key, Number defaultVal) {
        return delegate.getNumber(key, defaultVal);
    }

    @Override
    public Number getNumber(String key, Supplier<Number> defaultValSupplier) {
        return delegate.getNumber(key, defaultValSupplier);
    }

    @Override
    public Byte getByte(String key) {
        return delegate.getByte(key);
    }

    @Override
    public Byte getByte(String key, Byte defaultVal) {
        return delegate.getByte(key, defaultVal);
    }

    @Override
    public Byte getByte(String key, Supplier<Byte> defaultValSupplier) {
        return delegate.getByte(key, defaultValSupplier);
    }

    @Override
    public Short getShort(String key) {
        return delegate.getShort(key);
    }

    @Override
    public Short getShort(String key, Short defaultVal) {
        return delegate.getShort(key, defaultVal);
    }

    @Override
    public Short getShort(String key, Supplier<Short> defaultValSupplier) {
        return delegate.getShort(key, defaultValSupplier);
    }

    @Override
    public Integer getInteger(String key) {
        return delegate.getInteger(key);
    }

    @Override
    public Integer getInteger(String key, Integer defaultVal) {
        return delegate.getInteger(key, defaultVal);
    }

    @Override
    public Integer getInteger(String key, Supplier<Integer> defaultValSupplier) {
        return delegate.getInteger(key, defaultValSupplier);
    }

    @Override
    public Long getLong(String key) {
        return delegate.getLong(key);
    }

    @Override
    public Long getLong(String key, Long defaultVal) {
        return delegate.getLong(key, defaultVal);
    }

    @Override
    public Long getLong(String key, Supplier<Long> defaultValSupplier) {
        return delegate.getLong(key, defaultValSupplier);
    }

    @Override
    public Double getDouble(String key) {
        return delegate.getDouble(key);
    }

    @Override
    public Double getDouble(String key, Double defaultVal) {
        return delegate.getDouble(key, defaultVal);
    }

    @Override
    public Double getDouble(String key, Supplier<Double> defaultValSupplier) {
        return delegate.getDouble(key, defaultValSupplier);
    }

    @Override
    public Float getFloat(String key) {
        return delegate.getFloat(key);
    }

    @Override
    public Float getFloat(String key, Float defaultVal) {
        return delegate.getFloat(key, defaultVal);
    }

    @Override
    public Float getFloat(String key, Supplier<Float> defaultValSupplier) {
        return delegate.getFloat(key, defaultValSupplier);
    }

    @Override
    public Boolean getBoolean(String key) {
        return delegate.getBoolean(key);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultVal) {
        return delegate.getBoolean(key, defaultVal);
    }

    @Override
    public Boolean getBoolean(String key, Supplier<Boolean> defaultValSupplier) {
        return delegate.getBoolean(key, defaultValSupplier);
    }

    @Override
    public Instant getInstant(String key) {
        return delegate.getInstant(key);
    }

    @Override
    public Instant getInstant(String key, Instant defaultVal) {
        return delegate.getInstant(key, defaultVal);
    }

    @Override
    public Instant getInstant(String key, Supplier<Instant> defaultValSupplier) {
        return delegate.getInstant(key, defaultValSupplier);
    }

    @Override
    public Ctx getCtx(String key) {
        return delegate.getCtx(key);
    }

    @Override
    public Ctx getCtx(String key, Ctx defaultVal) {
        return delegate.getCtx(key, defaultVal);
    }

    @Override
    public Ctx getCtx(String key, Supplier<Ctx> defaultValSupplier) {
        return delegate.getCtx(key, defaultValSupplier);
    }

    @Override
    public Ctx getParent() {
        return delegate.getParent();
    }

    @Override
    public boolean hasParent() {
        return delegate.hasParent();
    }
}
