package cn.bytengine.d.assist;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 类信息，包括属性和指定函数信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassInfo {
    private Class<?> type;
    private String name;
    private String className;

    private final Map<String, PropertyInfo> propertyInfoMap = new HashMap<>(16);
    private final List<MethodInfo> methodInfos = new LinkedList<>();

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
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

    public boolean addMethodInfo(MethodInfo methodInfo) {
        return methodInfos.add(methodInfo);
    }

    public boolean removeMethodInfo(MethodInfo o) {
        return methodInfos.remove(o);
    }

    public boolean containsMethod(MethodInfo o) {
        return methodInfos.contains(o);
    }

    public void eachMethod(Consumer<? super MethodInfo> action) {
        methodInfos.forEach(action);
    }

    public MethodInfo findFirstMethod(String methodName) {
        return findFirstMethod(methodName, true);
    }

    public MethodInfo findFirstMethod(String methodName, boolean throwWhenNotFound) {
        Optional<MethodInfo> result = methodInfos.stream()
                .filter(mi -> CharSequenceUtil.equals(mi.getName(), methodName))
                .findFirst();
        if (throwWhenNotFound) {
            return result.orElseThrow(() -> new NoSuchElementException("the method name not existed in type"));
        }
        return result.orElse(null);
    }

    public List<MethodInfo> findAllMethods(String methodName) {
        return methodInfos.stream()
                .filter(mi -> CharSequenceUtil.equals(mi.getName(), methodName))
                .collect(Collectors.toList());
    }

    // region instance methods

    protected void checkPropertyName(String propertyName) {
        if (!containsProperty(propertyName)) {
            throw new NoSuchElementException("the property is not existed in type");
        }
    }

    public <T> T getPropertyValue(Object instance, String propertyName) {
        checkPropertyName(propertyName);
        PropertyInfo propertyInfo = getProperty(propertyName);
        return propertyInfo.getPropertyValue(instance);
    }

    public void setPropertyValue(Object instance, String propertyName, Object value) {
        PropertyInfo propertyInfo = getProperty(propertyName);
        propertyInfo.setPropertyValue(instance, value);
    }

    public Object invokeMethod(Object instance, String methodName, Object... args) {
        ClassAssists.checkInstance(getType(), instance);
        return findFirstMethod(methodName).invoke(instance, args);
    }
    // endregion

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

    public static ClassInfo of(Class<?> clazz, String... propertyNames) {
        ClassInfo info = new ClassInfo();
        info.type = clazz;
        info.name = clazz.getSimpleName();
        info.className = clazz.getCanonicalName();
        Collection<? extends PropertyDescriptor> properties = determineBasicProperties(clazz);
        if (!properties.isEmpty()) {
            boolean isAll = propertyNames.length == 0 || propertyNames.length == 1 && "*".equals(propertyNames[0]);
            Set<String> names = CollUtil.set(false, propertyNames);
            properties.stream()
                    .filter(p -> isAll || names.contains(p.getName()))
                    .collect(Collectors.toMap(FeatureDescriptor::getName, p -> PropertyInfo.of(clazz, p)))
                    .forEach(info::addProperty);
        }
        return info;
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
                String propertyName = CharSequenceUtil.lowerFirst(methodName.substring(nameIndex));
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
