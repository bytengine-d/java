package cn.bytengine.d.lang;

import java.lang.reflect.*;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ModifierTools {

    public enum ModifierType {
        PUBLIC(Modifier.PUBLIC),
        PRIVATE(Modifier.PRIVATE),
        PROTECTED(Modifier.PROTECTED),
        STATIC(Modifier.STATIC),
        FINAL(Modifier.FINAL),
        SYNCHRONIZED(Modifier.SYNCHRONIZED),
        VOLATILE(Modifier.VOLATILE),
        TRANSIENT(Modifier.TRANSIENT),
        NATIVE(Modifier.NATIVE),

        ABSTRACT(Modifier.ABSTRACT),
        STRICT(Modifier.STRICT);

        private final int value;

        ModifierType(int modifier) {
            this.value = modifier;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static boolean hasModifier(Class<?> clazz, ModifierTools.ModifierType... modifierTypes) {
        if (null == clazz || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (clazz.getModifiers() & modifiersToInt(modifierTypes));
    }

    public static boolean hasModifier(Constructor<?> constructor, ModifierTools.ModifierType... modifierTypes) {
        if (null == constructor || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (constructor.getModifiers() & modifiersToInt(modifierTypes));
    }

    public static boolean hasModifier(Method method, ModifierTools.ModifierType... modifierTypes) {
        if (null == method || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (method.getModifiers() & modifiersToInt(modifierTypes));
    }

    public static boolean hasModifier(Field field, ModifierTools.ModifierType... modifierTypes) {
        if (null == field || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        return 0 != (field.getModifiers() & modifiersToInt(modifierTypes));
    }

    public static boolean hasAllModifiers(final Class<?> clazz, final ModifierTools.ModifierType... modifierTypes) {
        if (null == clazz || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        final int checkedModifiersInt = modifiersToInt(modifierTypes);
        return checkedModifiersInt == (clazz.getModifiers() & checkedModifiersInt);
    }

    public static boolean hasAllModifiers(final Member member, final ModifierTools.ModifierType... modifierTypes) {
        if (null == member || ArrayTools.isEmpty(modifierTypes)) {
            return false;
        }
        final int checkedModifiersInt = modifiersToInt(modifierTypes);
        return checkedModifiersInt == (member.getModifiers() & checkedModifiersInt);
    }

    public static boolean isPublic(Field field) {
        return hasModifier(field, ModifierTools.ModifierType.PUBLIC);
    }

    public static boolean isPublic(Method method) {
        return hasModifier(method, ModifierTools.ModifierType.PUBLIC);
    }

    public static boolean isPublic(Class<?> clazz) {
        return hasModifier(clazz, ModifierTools.ModifierType.PUBLIC);
    }

    public static boolean isPublic(Constructor<?> constructor) {
        return hasModifier(constructor, ModifierTools.ModifierType.PUBLIC);
    }

    public static boolean isStatic(Field field) {
        return hasModifier(field, ModifierTools.ModifierType.STATIC);
    }

    public static boolean isStatic(Method method) {
        return hasModifier(method, ModifierTools.ModifierType.STATIC);
    }

    public static boolean isStatic(Class<?> clazz) {
        return hasModifier(clazz, ModifierTools.ModifierType.STATIC);
    }

    public static boolean isSynthetic(Field field) {
        return field.isSynthetic();
    }

    public static boolean isSynthetic(Method method) {
        return method.isSynthetic();
    }

    public static boolean isSynthetic(Class<?> clazz) {
        return clazz.isSynthetic();
    }

    public static boolean isAbstract(Method method) {
        return hasModifier(method, ModifierTools.ModifierType.ABSTRACT);
    }

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
