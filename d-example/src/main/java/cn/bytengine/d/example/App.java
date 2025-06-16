package cn.bytengine.d.example;

import cn.bytengine.d.assist.ClassAccessor;
import cn.bytengine.d.assist.ClassAccessors;
import cn.bytengine.d.events.Events;
import cn.bytengine.d.events.InvokerEventBus;
import cn.bytengine.d.example.ctx.ServiceOne;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.fn.invoker.MetaInfoInvokerFactory;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.text.CharSequenceUtil;

public class App {
    public static void main(String[] args) throws Throwable {
        testInvokerEventBus();
    }

    private static void testInvokerEventBus() throws Throwable {
        ClassAccessor classAccessor = ClassAccessors.register(ServiceOne.class);
        classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(ServiceOne::handle));
        classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(ServiceOne::handleEvent));
        classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(ServiceOne::getUsername));
        classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(ServiceOne::getAge));
        classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(ServiceOne::setUsername));
        classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(ServiceOne::setAge));
        classAccessor.addPropertyAccessor("username");
        classAccessor.addPropertyAccessor("age");

        ServiceOne one = new ServiceOne();
        InvokerEventBus eventBus = Events.general().build();
        Invoker invoker = eventBus.register("TestEvent", ServiceOne::handleEvent);
        int times = 1000000;
        final TimeInterval timer = new TimeInterval();

        timer.start("eventbus");
        for (int i = 0; i < times; i++) {
            eventBus.pub("TestEvent", one, "Kaihan Sun", 38);
        }
        long eventBusTime = timer.intervalMs("eventbus");
        System.out.println("-------------------------eventbus----------------------------");

        timer.start("invoker");
        for (int i = 0; i < times; i++) {
            invoker.invoke(new Object[]{one, "Kaihan Sun", 38});
        }
        long invokerTime = timer.intervalMs("invoker");
        System.out.println("-------------------------invoker----------------------------");

        timer.start("accessor");
        Object[] args = new Object[]{"Kaihan Sun", 38};
        for (int i = 0; i < times; i++) {
//            classAccessor.invoke(one, "handle", args);
            classAccessor.set(one, "username", "sunkaihan");
        }
        long accessorTime = timer.intervalMs("accessor");

        timer.start("call");
        for (int i = 0; i < times; i++) {
//            one.handleEvent("Kaihan Sun", 38);
            one.setUsername("sunkaihan");
        }
        long callTime = timer.intervalMs("call");
        System.out.println("-------------------------call----------------------------");

        System.out.println(CharSequenceUtil.format("eventbus execute {} times took {} ms", times, eventBusTime));
        System.out.println(CharSequenceUtil.format("invoker execute {} times took {} ms", times, invokerTime));
        System.out.println(CharSequenceUtil.format("accessor execute {} times took {} ms", times, accessorTime));
        System.out.println(CharSequenceUtil.format("call execute {} times took {} ms", times, callTime));

        timer.clear();
    }
}
