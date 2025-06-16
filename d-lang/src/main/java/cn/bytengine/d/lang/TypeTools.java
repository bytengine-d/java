package cn.bytengine.d.lang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class TypeTools {
    private TypeTools() {
    }

    public static Type getTypeArgument(Type type, int index) {
        final Type[] typeArguments = getTypeArguments(type);
        if (null != typeArguments && typeArguments.length > index) {
            return typeArguments[index];
        }
        return null;
    }

    public static Type[] getTypeArguments(Type type) {
        if (null == type) {
            return null;
        }

        final ParameterizedType parameterizedType = toParameterizedType(type);
        return (null == parameterizedType) ? null : parameterizedType.getActualTypeArguments();
    }

    public static ParameterizedType toParameterizedType(final Type type) {
        return toParameterizedType(type, 0);
    }

    public static ParameterizedType toParameterizedType(final Type type, final int interfaceIndex) {
        if (type instanceof ParameterizedType) {
            return (ParameterizedType) type;
        }

        if (type instanceof Class) {
            final ParameterizedType[] generics = getGenerics((Class<?>) type);
            if (generics.length > interfaceIndex) {
                return generics[interfaceIndex];
            }
        }

        return null;
    }

    public static ParameterizedType[] getGenerics(final Class<?> clazz) {
        final List<ParameterizedType> result = new ArrayList<>();
        // 泛型父类（父类及祖类优先级高）
        final Type genericSuper = clazz.getGenericSuperclass();
        if (null != genericSuper && !Object.class.equals(genericSuper)) {
            final ParameterizedType parameterizedType = toParameterizedType(genericSuper);
            if (null != parameterizedType) {
                result.add(parameterizedType);
            }
        }

        // 泛型接口
        final Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (ArrayTools.isNotEmpty(genericInterfaces)) {
            for (final Type genericInterface : genericInterfaces) {
                final ParameterizedType parameterizedType = toParameterizedType(genericInterface);
                if (null != parameterizedType) {
                    result.add(parameterizedType);
                }
            }
        }
        return result.toArray(new ParameterizedType[0]);
    }

    public static Class<?> getClass(Type type) {
        if (null != type) {
            if (type instanceof Class) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) type).getRawType();
            } else if (type instanceof TypeVariable) {
                Type[] bounds = ((TypeVariable<?>) type).getBounds();
                if (bounds.length == 1) {
                    return getClass(bounds[0]);
                }
            } else if (type instanceof WildcardType) {
                final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        }
        return null;
    }
}
