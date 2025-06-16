package cn.bytengine.d.lang;

import java.lang.reflect.Type;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ClassTools {
    private ClassTools() {
    }

    public static Class<?> getTypeArgument(Class<?> clazz) {
        return getTypeArgument(clazz, 0);
    }

    public static Class<?> getTypeArgument(Class<?> clazz, int index) {
        final Type argumentType = TypeTools.getTypeArgument(clazz, index);
        return TypeTools.getClass(argumentType);
    }
}
