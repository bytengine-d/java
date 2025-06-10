package cn.bytengine.d.assist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类辅助中将指定方法注册
 *
 * @author Ban Tenio
 * @version 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface MethodAccess {
}
