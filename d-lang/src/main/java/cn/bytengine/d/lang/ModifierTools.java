package cn.bytengine.d.lang;

import java.lang.reflect.*;

/**
 * 修饰符工具类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ModifierTools {
    /**
     * 修饰符枚举
     *
     * @author Ban Tenio
     * @version 1.0
     */
    public enum ModifierType {
        /**
         * public修饰符，所有类都能访问
         */
        PUBLIC(Modifier.PUBLIC),
        /**
         * private修饰符，只能被自己访问和修改
         */
        PRIVATE(Modifier.PRIVATE),
        /**
         * protected修饰符，自身、子类及同一个包中类可以访问
         */
        PROTECTED(Modifier.PROTECTED),
        /**
         * static修饰符，（静态修饰符）指定变量被所有对象共享，即所有实例都可以使用该变量。变量属于这个类
         */
        STATIC(Modifier.STATIC),
        /**
         * final修饰符，最终修饰符，指定此变量的值不能变，使用在方法上表示不能被重载
         */
        FINAL(Modifier.FINAL),
        /**
         * synchronized，同步修饰符，在多个线程中，该修饰符用于在运行前，对他所属的方法加锁，以防止其他线程的访问，运行结束后解锁。
         */
        SYNCHRONIZED(Modifier.SYNCHRONIZED),
        /**
         * （易失修饰符）指定该变量可以同时被几个线程控制和修改
         */
        VOLATILE(Modifier.VOLATILE),
        /**
         * （过度修饰符）指定该变量是系统保留，暂无特别作用的临时性变量，序列化时忽略
         */
        TRANSIENT(Modifier.TRANSIENT),
        /**
         * native，本地修饰符。指定此方法的方法体是用其他语言在程序外部编写的。
         */
        NATIVE(Modifier.NATIVE),
        /**
         * abstract，将一个类声明为抽象类，没有实现的方法，需要子类提供方法实现。
         */
        ABSTRACT(Modifier.ABSTRACT),
        /**
         * strictfp，一旦使用了关键字strictfp来声明某个类、接口或者方法时，那么在这个关键字所声明的范围内所有浮点运算都是精确的，符合IEEE-754规范的。
         */
        STRICT(Modifier.STRICT);

        private final int value;

        /**
         * 构造
         *
         * @param modifier 修饰符int表示，见{@link Modifier}
         */
        ModifierType(int modifier) {
            this.value = modifier;
        }

        /**
         * 获取修饰符枚举对应的int修饰符值，值见{@link Modifier}
         *
         * @return 修饰符枚举对应的int修饰符值
         */
        public int getValue() {
            return this.value;
        }
    }

    /**
     * 类是否存在给定修饰符中的<b>任意一个</b><br>
     * 如定义修饰符为：{@code public static final}，那么如果传入的modifierTypes为：
     * <ul>
     *     <li>public、static  返回{@code true}</li>
     *     <li>public、abstract返回{@code true}</li>
     *     <li>private、abstract返回{@code false}</li>
     * </ul>
     *
     * @param clazz         类，如果为{@code null}返回{@code false}
     * @param modifierTypes 修饰符枚举，如果为空返回{@code false}
     * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
     */
    public static boolean hasModifier(Class<?> clazz, ModifierTools.ModifierType... modifierTypes) {
        if (null == clazz || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (clazz.getModifiers() & modifiersToInt(modifierTypes));
    }

    /**
     * 构造是否存在给定修饰符中的<b>任意一个</b><br>
     * 如定义修饰符为：{@code public static final}，那么如果传入的modifierTypes为：
     * <ul>
     *     <li>public、static  返回{@code true}</li>
     *     <li>public、abstract返回{@code true}</li>
     *     <li>private、abstract返回{@code false}</li>
     * </ul>
     *
     * @param constructor        构造，如果为{@code null}返回{@code false}
     * @param modifierTypes 修饰符枚举，如果为空返回{@code false}
     * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
     */
    public static boolean hasModifier(Constructor<?> constructor, ModifierTools.ModifierType... modifierTypes) {
        if (null == constructor || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (constructor.getModifiers() & modifiersToInt(modifierTypes));
    }

    /**
     * 方法是否存在给定修饰符中的<b>任意一个</b><br>
     * 如定义修饰符为：{@code public static final}，那么如果传入的modifierTypes为：
     * <ul>
     *     <li>public、static  返回{@code true}</li>
     *     <li>public、abstract返回{@code true}</li>
     *     <li>private、abstract返回{@code false}</li>
     * </ul>
     *
     * @param method        方法，如果为{@code null}返回{@code false}
     * @param modifierTypes 修饰符枚举，如果为空返回{@code false}
     * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
     */
    public static boolean hasModifier(Method method, ModifierTools.ModifierType... modifierTypes) {
        if (null == method || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (method.getModifiers() & modifiersToInt(modifierTypes));
    }

    /**
     * 字段是否存在给定修饰符中的<b>任意一个</b><br>
     * 如定义修饰符为：{@code public static final}，那么如果传入的modifierTypes为：
     * <ul>
     *     <li>public、static  返回{@code true}</li>
     *     <li>public、abstract返回{@code true}</li>
     *     <li>private、abstract返回{@code false}</li>
     * </ul>
     *
     * @param field        构造、字段或方法，如果为{@code null}返回{@code false}
     * @param modifierTypes 修饰符枚举，如果为空返回{@code false}
     * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
     */
    public static boolean hasModifier(Field field, ModifierTools.ModifierType... modifierTypes) {
        if (null == field || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (field.getModifiers() & modifiersToInt(modifierTypes));
    }

    /**
     * 类中是否同时存在<b>所有</b>给定修饰符<br>
     * 如定义修饰符为：{@code public static final}，那么如果传入的modifierTypes为：
     * <ul>
     *     <li>public、static  返回{@code true}</li>
     *     <li>public、abstract返回{@code false}</li>
     *     <li>private、abstract返回{@code false}</li>
     * </ul>
     *
     * @param clazz         类，如果为{@code null}返回{@code false}
     * @param modifierTypes 修饰符枚举，如果为空返回{@code false}
     * @return 是否同时存在所有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
     */
    public static boolean hasAllModifiers(final Class<?> clazz, final ModifierTools.ModifierType... modifierTypes) {
        if (null == clazz || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        final int checkedModifiersInt = modifiersToInt(modifierTypes);
        return checkedModifiersInt == (clazz.getModifiers() & checkedModifiersInt);
    }

    /**
     * 成员中是否同时存在<b>所有</b>给定修饰符<br>
     * 如定义修饰符为：{@code public static final}，那么如果传入的modifierTypes为：
     * <ul>
     *     <li>public、static  返回{@code true}</li>
     *     <li>public、abstract返回{@code false}</li>
     *     <li>private、abstract返回{@code false}</li>
     * </ul>
     *
     * @param member        构造、字段或方法，如果为{@code null}返回{@code false}
     * @param modifierTypes 修饰符枚举，如果为空返回{@code false}
     * @return 是否同时存在所有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
     */
    public static boolean hasAllModifiers(final Member member, final ModifierTools.ModifierType... modifierTypes) {
        if (null == member || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        final int checkedModifiersInt = modifiersToInt(modifierTypes);
        return checkedModifiersInt == (member.getModifiers() & checkedModifiersInt);
    }

    /**
     * 是否是Public字段
     *
     * @param field 字段
     * @return 是否是Public
     */
    public static boolean isPublic(Field field) {
        return hasModifier(field, ModifierTools.ModifierType.PUBLIC);
    }

    /**
     * 是否是Public方法
     *
     * @param method 方法
     * @return 是否是Public
     */
    public static boolean isPublic(Method method) {
        return hasModifier(method, ModifierTools.ModifierType.PUBLIC);
    }

    /**
     * 是否是Public类
     *
     * @param clazz 类
     * @return 是否是Public
     */
    public static boolean isPublic(Class<?> clazz) {
        return hasModifier(clazz, ModifierTools.ModifierType.PUBLIC);
    }

    /**
     * 是否是Public构造
     *
     * @param constructor 构造
     * @return 是否是Public
     */
    public static boolean isPublic(Constructor<?> constructor) {
        return hasModifier(constructor, ModifierTools.ModifierType.PUBLIC);
    }

    /**
     * 是否是static字段
     *
     * @param field 字段
     * @return 是否是static
     */
    public static boolean isStatic(Field field) {
        return hasModifier(field, ModifierTools.ModifierType.STATIC);
    }

    /**
     * 是否是static方法
     *
     * @param method 方法
     * @return 是否是static
     */
    public static boolean isStatic(Method method) {
        return hasModifier(method, ModifierTools.ModifierType.STATIC);
    }

    /**
     * 是否是static类
     *
     * @param clazz 类
     * @return 是否是static
     */
    public static boolean isStatic(Class<?> clazz) {
        return hasModifier(clazz, ModifierTools.ModifierType.STATIC);
    }

    /**
     * 是否是合成字段（由java编译器生成的）
     *
     * @param field 字段
     * @return 是否是合成字段
     */
    public static boolean isSynthetic(Field field) {
        return field.isSynthetic();
    }

    /**
     * 是否是合成方法（由java编译器生成的）
     *
     * @param method 方法
     * @return 是否是合成方法
     */
    public static boolean isSynthetic(Method method) {
        return method.isSynthetic();
    }

    /**
     * 是否是合成类（由java编译器生成的）
     *
     * @param clazz 类
     * @return 是否是合成
     */
    public static boolean isSynthetic(Class<?> clazz) {
        return clazz.isSynthetic();
    }

    /**
     * 是否抽象方法
     *
     * @param method 方法
     * @return 是否抽象方法
     */
    public static boolean isAbstract(Method method) {
        return hasModifier(method, ModifierTools.ModifierType.ABSTRACT);
    }

    /**
     * 设置final的field字段可以被修改
     * 只要不会被编译器内联优化的 final 属性就可以通过反射有效的进行修改 --  修改后代码中可使用到新的值;
     * <p>以下属性，编译器会内联优化，无法通过反射修改：</p>
     * <ul>
     *     <li> 基本类型 byte, char, short, int, long, float, double, boolean</li>
     *     <li> Literal String 类型(直接双引号字符串)</li>
     * </ul>
     * <p>以下属性，可以通过反射修改：</p>
     * <ul>
     *     <li>基本类型的包装类 Byte、Character、Short、Long、Float、Double、Boolean</li>
     *     <li>字符串，通过 new String("")实例化</li>
     *     <li>自定义java类</li>
     * </ul>
     * <pre class="code">
     * {@code
     *      //示例，移除final修饰符
     *      class JdbcDialects {private static final List<Number> dialects = new ArrayList<>();}
     *      Field field = ReflectTools.getField(JdbcDialects.class, fieldName);
     * 		ReflectTools.removeFinalModify(field);
     * 		ReflectTools.setFieldValue(JdbcDialects.class, fieldName, dialects);
     *    }
     * </pre>
     *
     * @param field 被修改的field，不可以为空
     */
    public static void removeFinalModify(Field field) {
        if (field != null) {
            if (hasModifier(field, ModifierTools.ModifierType.FINAL)) {
                //将字段的访问权限设为true：即去除private修饰符的影响
                if (false == field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    //去除final修饰符的影响，将字段设为可修改的
                    final Field modifiersField = Field.class.getDeclaredField("modifiers");
                    //Field 的 modifiers 是私有的
                    modifiersField.setAccessible(true);
                    //& ：位与运算符，按位与；  运算规则：两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
                    //~ ：位非运算符，按位取反；运算规则：转成二进制，如果位为0，结果是1，如果位为1，结果是0.
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                } catch (final NoSuchFieldException | IllegalAccessException e) {
                    //内部，工具类，基本不抛出异常
                    throw new RuntimeException(CharSequenceTools.format("IllegalAccess for {}.{}", field.getDeclaringClass(), field.getName()), e);
                }
            }
        }
    }

    private static int modifiersToInt(ModifierTools.ModifierType... modifierTypes) {
        int modifier = modifierTypes[0].getValue();
        for (int i = 1; i < modifierTypes.length; i++) {
            modifier |= modifierTypes[i].getValue();
        }
        return modifier;
    }
}
