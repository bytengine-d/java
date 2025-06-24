package cn.bytengine.d.http;

import cn.bytengine.d.collection.MultiValueMap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * {@code HttpHeaders} object that can only be read, not written to.
 * <p>This caches the parsed representations of the "Accept" and "Content-Type" headers
 * and will get out of sync with the backing map it is mutated at runtime.
 *
 * @author Ban Tenio
 * @version 1.0
 */
class ReadOnlyHttpHeader extends HttpHeader {

    private static final long serialVersionUID = -8578554704772377436L;

    private MediaType cachedContentType;

    @SuppressWarnings("serial")
    private List<MediaType> cachedAccept;


    ReadOnlyHttpHeader(MultiValueMap<String, String> headers) {
        super(headers);
    }


    @Override
    public MediaType getContentType() {
        if (this.cachedContentType != null) {
            return this.cachedContentType;
        } else {
            MediaType contentType = super.getContentType();
            this.cachedContentType = contentType;
            return contentType;
        }
    }

    @Override
    public List<MediaType> getAccept() {
        if (this.cachedAccept != null) {
            return this.cachedAccept;
        } else {
            List<MediaType> accept = super.getAccept();
            this.cachedAccept = accept;
            return accept;
        }
    }

    @Override
    public void clearContentHeaders() {
        // No-op.
    }

    @Override
    public List<String> get(Object key) {
        List<String> values = this.headers.get(key);
        return (values != null ? Collections.unmodifiableList(values) : null);
    }

    @Override
    public void add(String headerName, String headerValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(String key, List<? extends String> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(MultiValueMap<String, String> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(String headerName, String headerValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAll(Map<String, String> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, String> toSingleValueMap() {
        return Collections.unmodifiableMap(this.headers.toSingleValueMap());
    }

    @Override
    public Map<String, String> asSingleValueMap() {
        return Collections.unmodifiableMap(this.headers.asSingleValueMap());
    }

    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.headers.keySet());
    }

    @Override
    public List<String> put(String key, List<String> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<List<String>> values() {
        return Collections.unmodifiableCollection(this.headers.values());
    }

    @Override
    public Set<Map.Entry<String, List<String>>> entrySet() {
        return this.headers.entrySet().stream().map(AbstractMap.SimpleImmutableEntry::new)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(LinkedHashSet::new), // Retain original ordering of entries
                        Collections::unmodifiableSet));
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super List<String>> action) {
        this.headers.forEach((k, vs) -> action.accept(k, Collections.unmodifiableList(vs)));
    }

}
