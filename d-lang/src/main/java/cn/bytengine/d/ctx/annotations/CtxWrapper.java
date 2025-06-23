package cn.bytengine.d.ctx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 根据指定类型生成上下文数据结构访问接口
 *
 * @author Ban Tenio
 * @version 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface CtxWrapper {
    /**
     * include field names, default is all.
     *
     * @return include field names;
     */
    String[] value() default {"*"};

    /**
     * exclude field names, default is empty.
     *
     * @return exclude field names;
     */
    String[] excludes() default {};
}
