package cn.bytengine.d.sa;

import cn.bytengine.d.utils.GlobPathMatcher;

/**
 * Glob表达式条件判断
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SaGlobPredicate implements SaPredicate {
    /**
     * TODO
     */
    private final static GlobPathMatcher globPathMatcher = new GlobPathMatcher();
    /**
     * TODO
     */
    private final static String ALL_GLOB_EXPRESSION = "**";
    /**
     * TODO
     */
    private String globExpression = ALL_GLOB_EXPRESSION;

    /**
     * 无表达式
     */
    public SaGlobPredicate() {
    }

    /**
     * 指定条件表达式
     *
     * @param globExpression 条件表达式
     * @see #setGlobExpression(String)
     */
    public SaGlobPredicate(String globExpression) {
        setGlobExpression(globExpression);
    }

    /**
     * 修改条件表达式
     *
     * @param globExpression 条件表达式
     * @return 当前对象
     */
    public SaGlobPredicate setGlobExpression(String globExpression) {
        this.globExpression = globExpression;
        return this;
    }

    /**
     * 判断条件是否符合Glob表达式规则
     *
     * @param condition 输入条件
     * @return 是否符合规则
     * @see GlobPathMatcher#match(String, String)
     */
    @Override
    public boolean test(String condition) {
        return globPathMatcher.match(globExpression, condition);
    }
}
