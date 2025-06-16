package cn.bytengine.d.lang;

import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Type;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class TypeReference<T> implements Type {

    private final Type type;

    public TypeReference() {
        this.type = TypeUtil.getTypeArgument(getClass());
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
