package cn.bytengine.d.events.internal;

import cn.bytengine.d.events.RegisterOption;
import cn.bytengine.d.fn.invoker.Invoker;

import java.util.Arrays;
import java.util.Collection;

/**
 * 注册Invoker信息
 *
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.events.internal
 * <li>ClassName:      InvokerRegistration
 * <li>Date:    2024/5/7 13:30
 * </ul>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class InvokerRegistration {
    private final Invoker invoker;
    private final Collection<RegisterOption> registerOptions;

    public InvokerRegistration(Invoker invoker, RegisterOption... registerOptions) {
        this(invoker, Arrays.asList(registerOptions));
    }

    public InvokerRegistration(Invoker invoker, Collection<RegisterOption> registerOptions) {
        this.invoker = invoker;
        this.registerOptions = registerOptions;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public Collection<RegisterOption> getRegisterOptions() {
        return registerOptions;
    }

    @Override
    public String toString() {
        return "InvokerRegistration{" +
                "invoker=" + invoker +
                ", registerOptions=" + registerOptions +
                '}';
    }
}
