package cn.bytengine.d.fn.invoker;

import cn.bytengine.d.lang.CharSequenceTools;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MethodHandle相关的辅助方法集合
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class MethodHandles {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING = new HashMap<>();
    private static final Map<Class<?>, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, MethodType> METHOD_SIGN_METHOD_TYPE_MAPPING = new HashMap<>();
    private static final Map<String, MethodHandle> METHOD_SIGN_METHOD_HANDLE_MAPPING = new HashMap<>();
    private static final java.lang.invoke.MethodHandles.Lookup LOOKUP = java.lang.invoke.MethodHandles.lookup();

    static {
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(boolean.class, Boolean.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(char.class, Character.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(byte.class, Byte.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(short.class, Short.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(int.class, Integer.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(long.class, Long.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(float.class, Float.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(double.class, Double.class);
    }

    private MethodHandles() {
    }

    /**
     * 获取Functional的SerializedLambda信息
     *
     * @param fn 标识Serializable的Functional对象
     * @return SerializedLambda信息
     */
    public static SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        // 先检查缓存中是否已存在
        if (lambda != null) {
            return lambda;
        }
        try {
            // 提取SerializedLambda并缓存
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            lambda = (SerializedLambda) method.invoke(fn);
            CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
        } catch (Exception e) {
            throw new ReflectionOperationException(e);
        }
        return lambda;
    }

    /**
     * 获取指定特征方法的MethodType实例
     * <p>
     *     根据指定方法特征缓存MethodType实例
     * </p>
     *
     * @param type             方法所属类型
     * @param methodName       方法名
     * @param parameterClasses 方法参数类型列表
     * @return MethodType实例
     */
    public static MethodType getMethodType(Class<?> type,
                                           String methodName,
                                           Class<?>[] parameterClasses) {
        String methodSign = CharSequenceTools.format("{}.{}({})", type.getName(), methodName, Arrays.toString(parameterClasses));
        return METHOD_SIGN_METHOD_TYPE_MAPPING.computeIfAbsent(methodSign, key -> {
            MethodHandle mh = getMethodHandler(type, methodName, parameterClasses);
            return mh == null ? null : mh.type();
        });
    }

    /**
     * 根据指定特征方法的MethodHandle实例
     *
     * @param type             方法所属类型
     * @param methodName       方法名
     * @param parameterClasses 方法参数类型列表
     * @return MethodHandle实例
     */
    public static MethodHandle getMethodHandler(Class<?> type,
                                                String methodName,
                                                Class<?>[] parameterClasses) {
        String methodSign = CharSequenceTools.format("{}.{}({})", type.getName(), methodName, Arrays.toString(parameterClasses));
        return METHOD_SIGN_METHOD_HANDLE_MAPPING.computeIfAbsent(methodSign, key -> {
            try {
                Method method = type.getDeclaredMethod(methodName, parameterClasses);
                return LOOKUP.unreflect(method);
            } catch (NoSuchMethodException | IllegalAccessException ex) {
                return null;
            }
        });
    }

    /**
     * 根据返回类型和参数类型，获取MethodType实例
     *
     * @param returnType     方法返回类型
     * @param parameterTypes 方法参数类型列表
     * @return MethodType实例
     */
    public static MethodType convertPrimitiveType(Class<?> returnType, Class<?>[] parameterTypes) {
        if (PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.containsKey(returnType)) {
            returnType = PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.get(returnType);
        }
        Class<?>[] targetParameterTypes = new Class[parameterTypes.length];
        for (int idx = 0; idx < parameterTypes.length; idx++) {
            Class<?> parameterType = parameterTypes[idx];
            if (PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.containsKey(returnType)) {
                parameterType = PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.get(returnType);
            }
            targetParameterTypes[idx] = parameterType;
        }
        return MethodType.methodType(returnType, targetParameterTypes);
    }
}
