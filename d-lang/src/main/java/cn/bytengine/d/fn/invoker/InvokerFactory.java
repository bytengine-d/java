package cn.bytengine.d.fn.invoker;

import java.io.Serializable;

/**
 * 调用器工厂
 */
public interface InvokerFactory {
    /**
     * 根据指定的标记Serializable的Functional对象，创建调用器对象
     *
     * @param serializable 标记Serializable的Functional对象
     * @return 封装Functional调用器
     */
    Invoker create(Serializable serializable);
}
