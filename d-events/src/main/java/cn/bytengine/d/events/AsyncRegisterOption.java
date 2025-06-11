package cn.bytengine.d.events;

import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.fn.invoker.InvokerRunnable;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class AsyncRegisterOption implements RegisterOption {
    private final Supplier<ExecutorService> executorServiceSupplier;

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
