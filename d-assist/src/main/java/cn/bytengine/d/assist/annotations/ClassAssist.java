package cn.bytengine.d.assist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类辅助注解，用于生成类型信息和属性、方法访问器
 *
 * @author Ban Tenio
 * @version 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ClassAssist {
}
