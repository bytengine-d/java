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

    public Class<?> getType() {
        return type;
    }

    public String getClassName() {
        return this.type.getName();
    }

    public String getDescriptorString() {
        return this.type.descriptorString();
    }

    public boolean isInterface() {
        return this.type.isInterface();
    }

    public boolean isAnnotation() {
        return this.type.isAnnotation();
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.type.getModifiers());
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.type.getModifiers());
    }

    public PropertyInfo addProperty(String key, PropertyInfo value) {
        return propertyInfoMap.put(key, value);
    }

    public PropertyInfo removeProperty(String key) {
        return propertyInfoMap.remove(key);
    }

    public Set<String> propertyNameSet() {
        return propertyInfoMap.keySet();
    }

    public Collection<PropertyInfo> propertyInfos() {
        return propertyInfoMap.values();
    }

    public boolean containsProperty(String o) {
        return propertyInfoMap.containsKey(o);
    }

    public boolean containsProperty(PropertyInfo o) {
        return propertyInfoMap.containsValue(o);
    }

    public PropertyInfo getProperty(String propertyName) {
        return propertyInfoMap.get(propertyName);
    }


    public void eachPropertyInfos(BiConsumer<? super String, ? super PropertyInfo> action) {
        propertyInfoMap.forEach(action);
    }

    public MethodInfo addMethod(Method method) {
        return addMethodInfo(MethodInfo.of(type, method));
    }

    public MethodInfo addMethodInfo(MethodInfo methodInfo) {
        methodInfos.put(methodInfo.getMethod(), methodInfo);
        return methodInfo;
    }

    public MethodInfo removeMethodInfo(Method o) {
        return methodInfos.remove(o);
    }

    public MethodInfo removeMethodInfo(MethodInfo o) {
        return removeMethodInfo(o.getMethod());
    }

    public boolean containsMethod(Method o) {
        return methodInfos.containsKey(o);
    }

    public boolean containsMethod(MethodInfo o) {
        return methodInfos.containsValue(o);
    }

    public MethodInfo getMethodInfo(Method o) {
        return methodInfos.get(o);
    }

    public MethodInfo findFirstMethod(String methodName) {
        return findFirstMethod(methodName, true);
    }

    public void eachMethod(BiConsumer<? super Method, ? super MethodInfo> action) {
        methodInfos.forEach(action);
    }

    public MethodInfo findFirstMethod(String methodName, boolean throwWhenNotFound) {
        Optional<MethodInfo> result = methodInfos.values().stream()
                .filter(mi -> CharSequenceTools.equals(mi.getMethodName(), methodName))
                .findFirst();
        if (throwWhenNotFound) {
            return result.orElseThrow(() -> new NoSuchElementException("the method name not existed in type"));
        }
        return result.orElse(null);
    }

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

    public static Collection<? extends PropertyDescriptor> determineBasicProperties(Class<?> beanClass) {

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
