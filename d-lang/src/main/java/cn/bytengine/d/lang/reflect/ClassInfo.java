package cn.bytengine.d.lang.reflect;

import cn.bytengine.d.lang.CharSequenceTools;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 类信息，包括属性和指定函数信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassInfo {
    private Class<?> type;

    private final Map<String, PropertyInfo> propertyInfoMap = new HashMap<>(16);
    private final Map<Method, MethodInfo> methodInfos = new HashMap<>(16);

    /**
     * 获取类信息类型
     *
     * @return 类信息实例
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * 获取类信息实例名称
     *
     * @return 类信息实例名称
     */
    public String getClassName() {
        return this.type.getName();
    }

    /**
     * 获取类信息实例描述符
     *
     * @return 类信息实例描述符
     */
    public String getDescriptorString() {
        return this.type.descriptorString();
    }

    /**
     * 类信息是否是接口类型
     *
     * @return 是否接口类型
     */
    public boolean isInterface() {
        return this.type.isInterface();
    }

    /**
     * 类信息是否是Annotation类型
     *
     * @return 是否Annotation类型
     */
    public boolean isAnnotation() {
        return this.type.isAnnotation();
    }

    /**
     * 类信息是否是抽象类
     *
     * @return 是否抽象类
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(this.type.getModifiers());
    }

    /**
     * 类信息是否不可扩展
     *
     * @return 是否不可扩展
     */
    public boolean isFinal() {
        return Modifier.isFinal(this.type.getModifiers());
    }

    /**
     * 类信息包含的属性信息
     *
     * @param key   属性名
     * @param value 属性信息
     * @return 添加的属性信息
     */
    public PropertyInfo addProperty(String key, PropertyInfo value) {
        return propertyInfoMap.put(key, value);
    }

    /**
     * 类信息移除属性信息
     *
     * @param key 属性名
     * @return 移除的属性信息
     */
    public PropertyInfo removeProperty(String key) {
        return propertyInfoMap.remove(key);
    }

    /**
     * 类信息属性名集合
     *
     * @return 属性名集合
     */
    public Set<String> propertyNameSet() {
        return propertyInfoMap.keySet();
    }

    /**
     * 获取类信息属性信息集合
     *
     * @return 属性信息集合
     */
    public Collection<PropertyInfo> propertyInfos() {
        return propertyInfoMap.values();
    }

    /**
     * 类信息是否包括指定属性
     *
     * @param o 属性名
     * @return 是否存在属性
     */
    public boolean containsProperty(String o) {
        return propertyInfoMap.containsKey(o);
    }

    /**
     * 类信息是否包含指定属性信息
     *
     * @param o 属性信息
     * @return 是否存在属性
     */
    public boolean containsProperty(PropertyInfo o) {
        return propertyInfoMap.containsValue(o);
    }

    /**
     * 获取指定属性信息
     *
     * @param propertyName 属性名
     * @return 属性信息
     */
    public PropertyInfo getProperty(String propertyName) {
        return propertyInfoMap.get(propertyName);
    }

    /**
     * 遍历类信息包含所有属性信息
     *
     * @param consumer 属性信息访问器
     */
    public void eachPropertyInfos(BiConsumer<? super String, ? super PropertyInfo> consumer) {
        propertyInfoMap.forEach(consumer);
    }

    /**
     * 类信息添加根据指定Method实例的方法信息
     *
     * @param method Method实例
     * @return 方法信息
     */
    public MethodInfo addMethod(Method method) {
        return addMethodInfo(MethodInfo.of(type, method));
    }

    /**
     * 类信息添加方法信息
     *
     * @param methodInfo 方法信息
     * @return 方法信息
     */
    public MethodInfo addMethodInfo(MethodInfo methodInfo) {
        methodInfos.put(methodInfo.getMethod(), methodInfo);
        return methodInfo;
    }

    /**
     * 类信息移除指定Method实例对应的方法信息
     *
     * @param o Method实例
     * @return 被移除方法信息
     */
    public MethodInfo removeMethodInfo(Method o) {
        return methodInfos.remove(o);
    }

    /**
     * 类信息移除指定方法信息
     *
     * @param o 方法信息
     * @return 被移除方法信息
     */
    public MethodInfo removeMethodInfo(MethodInfo o) {
        return removeMethodInfo(o.getMethod());
    }

    /**
     * 是否包含指定方法信息
     *
     * @param o Method实例
     * @return 是否包含方法信息
     */
    public boolean containsMethod(Method o) {
        return methodInfos.containsKey(o);
    }

    /**
     * 是否包含指定方法信息
     *
     * @param o 方法信息
     * @return 是否包含方法信息
     */
    public boolean containsMethod(MethodInfo o) {
        return methodInfos.containsValue(o);
    }

    /**
     * 获取指定方法信息
     *
     * @param o Method实例
     * @return 方法信息
     */
    public MethodInfo getMethodInfo(Method o) {
        return methodInfos.get(o);
    }

    /**
     * 指定方法名获取第一个匹配的方法信息
     *
     * @param methodName 方法名称
     * @return 方法信息
     */
    public MethodInfo findFirstMethod(String methodName) {
        return findFirstMethod(methodName, true);
    }

    /**
     * 遍历类信息包含的所有方法信息
     *
     * @param consumer 方法信息访问器
     */
    public void eachMethod(BiConsumer<? super Method, ? super MethodInfo> consumer) {
        methodInfos.forEach(consumer);
    }

    /**
     * 指定方法名获取第一个匹配的方法信息，是否在未找到是抛出异常
     *
     * @param methodName 方法名称
     * @param throwWhenNotFound 是否抛出未找到异常
     * @return 方法信息
     */
    public MethodInfo findFirstMethod(String methodName, boolean throwWhenNotFound) {
        Optional<MethodInfo> result = methodInfos.values().stream()
                .filter(mi -> CharSequenceTools.equals(mi.getMethodName(), methodName))
                .findFirst();
        if (throwWhenNotFound) {
            return result.orElseThrow(() -> new NoSuchElementException("the method name not existed in type"));
        }
        return result.orElse(null);
    }

    /**
     * 获取指定方法名的方法信息集合
     *
     * @param methodName 方法名称
     * @return 方法信息集合
     */
    public List<MethodInfo> findAllMethods(String methodName) {
        return methodInfos.values().stream()
                .filter(mi -> CharSequenceTools.equals(mi.getMethodName(), methodName))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return Objects.equals(type, classInfo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    /**
     * 根据类信息实例创建类信息对象
     *
     * @param clazz 类信息实例
     * @return 类信息
     */
    public static ClassInfo of(Class<?> clazz) {
        ClassInfo info = new ClassInfo();
        info.type = clazz;
        buildMethodInfos(info, clazz);
        buildPropertyInfos(info, clazz);
        return info;
    }

    private static void buildMethodInfos(ClassInfo info, Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            info.addMethodInfo(MethodInfo.of(clazz, declaredMethod));
        }
        List<Method> defaultMethods = findDefaultMethodsOnInterfaces(clazz);
        if (defaultMethods != null) {
            for (Method defaultMethod : defaultMethods) {
                info.addMethodInfo(MethodInfo.of(clazz, defaultMethod));
            }
        }
    }

    private static List<Method> findDefaultMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method method : ifc.getMethods()) {
                if (method.isDefault()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(method);
                }
            }
        }
        return result;
    }

    private static void buildPropertyInfos(ClassInfo info, Class<?> clazz) {
        Collection<? extends PropertyDescriptor> properties = determineBasicProperties(clazz);
        if (!properties.isEmpty()) {
            properties.stream()
                    .collect(Collectors.toMap(FeatureDescriptor::getName, p -> buildPropertyInfo(info, clazz, p)))
                    .forEach(info::addProperty);
        }
    }

    private static PropertyInfo buildPropertyInfo(ClassInfo classInfo, Class<?> clazz, PropertyDescriptor p) {
        MethodInfo reader = p.getReadMethod() == null ? null : classInfo.getMethodInfo(p.getReadMethod());
        MethodInfo writer = p.getWriteMethod() == null ? null : classInfo.getMethodInfo(p.getWriteMethod());
        return PropertyInfo.of(clazz, p.getName(), p.getPropertyType(), reader, writer);
    }

    private static Collection<? extends PropertyDescriptor> determineBasicProperties(Class<?> beanClass) {

        Map<String, PropertyDescriptor> pdMap = new TreeMap<>();
        try {
            for (Method method : beanClass.getMethods()) {
                String methodName = method.getName();

                boolean setter;
                int nameIndex;
                if (methodName.startsWith("set") && method.getParameterCount() == 1) {
                    setter = true;
                    nameIndex = 3;
                } else if (methodName.startsWith("get") && method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                    setter = false;
                    nameIndex = 3;
                } else if (methodName.startsWith("is") && method.getParameterCount() == 0 && method.getReturnType() == boolean.class) {
                    setter = false;
                    nameIndex = 2;
                } else {
                    continue;
                }
                String propertyName = CharSequenceTools.lowerFirst(methodName.substring(nameIndex));
                if (propertyName.isEmpty()) {
                    continue;
                }

                PropertyDescriptor pd = pdMap.get(propertyName);
                if (pd != null) {
                    if (setter) {
                        Method writeMethod = pd.getWriteMethod();
                        if (writeMethod == null ||
                                writeMethod.getParameterTypes()[0].isAssignableFrom(method.getParameterTypes()[0])) {
                            pd.setWriteMethod(method);
                        } else {
                            pd.setWriteMethod(method);
                        }
                    } else {
                        Method readMethod = pd.getReadMethod();
                        if (readMethod == null ||
                                (readMethod.getReturnType() == method.getReturnType() && method.getName().startsWith("is"))) {
                            pd.setReadMethod(method);
                        }
                    }
                } else {
                    pd = new PropertyDescriptor(propertyName, (!setter ? method : null), (setter ? method : null));
                    pdMap.put(propertyName, pd);
                }
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        return pdMap.values();
    }
}
