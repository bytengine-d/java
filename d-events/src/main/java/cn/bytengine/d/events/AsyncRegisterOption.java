package cn.bytengine.d.events;

import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.fn.invoker.InvokerRunnable;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * 异步调用调用器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class AsyncRegisterOption implements RegisterOption {
    private final Supplier<ExecutorService> executorServiceSupplier;

    /**
     * 构造器
     *
     * @param executorServiceSupplier ExecutorService提供函数
     */
    public AsyncRegisterOption(Supplier<ExecutorService> executorServiceSupplier) {
        this.executorServiceSupplier = executorServiceSupplier;
    }

    @Override
    public Invoker option(String eventName, Invoker invoker) {
        return args -> {
            executorServiceSupplier.get().execute(new InvokerRunnable(invoker, args));
            return null;
        };
    }
}
