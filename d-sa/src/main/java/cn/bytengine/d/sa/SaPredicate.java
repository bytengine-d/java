package cn.bytengine.d.sa;

import java.util.function.Predicate;

/**
 * SA条件判断
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface SaPredicate extends Predicate<String> {
    /**
     * 跟定条件判断是否符合验证规则
     *
     * @param condition 输入条件
     * @return 是否匹配
     */
    boolean test(String condition);
}
