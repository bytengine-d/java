package cn.bytengine.d.ctx;

/**
 * 上下文数据源操作接口
 *
 * @param <S> 数据源类型
 * @param <C> 数据源操作实现类型
 * @author Ban Tenio
 * @version 1.0
 */
public interface CtxSource<S, C extends CtxSource<S, C>> {
    /**
     * 清空数据源
     *
     * @return 当前数据源操作类对象
     */
    C clear();

    /**
     * 获取数据源
     *
     * @return 当前数据源对象
     */
    S getSource();

    /**
     * 变更数据源
     *
     * @param source 指定新数据源对象
     * @return 当前数据源操作类对象
     */
    C setSource(S source);
}
