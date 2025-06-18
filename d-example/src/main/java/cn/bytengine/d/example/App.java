package cn.bytengine.d.example;

import cn.bytengine.d.assist.ClassAccessor;
import cn.bytengine.d.assist.ClassAccessors;
import cn.bytengine.d.events.Events;
import cn.bytengine.d.events.InvokerEventBus;
import cn.bytengine.d.example.ctx.ServiceOne;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.TimeInterval;

public class App {
    public static void main(String[] args) throws Throwable {
        testInvokerEventBus();
    }

    private static void testInvokerEventBus() throws Throwable {
        ClassAccessors.load();
        ClassAccessor classAccessor = ClassAccessors.get(ServiceOne.class);

        ServiceOne one = new ServiceOne();
        InvokerEventBus eventBus = Events.general().build();
        Invoker invoker = eventBus.register("TestEvent", ServiceOne::handleEvent);
        int times = 1000000;
        final TimeInterval timer = new TimeInterval();

        Object[] args = new Object[]{one, "Kaihan Sun", 38};

        timer.start("eventbus");
        for (int i = 0; i < times; i++) {
            eventBus.pub("TestEvent", one, "Kaihan Sun", 38);
        }
        long eventBusTime = timer.intervalMs("eventbus");
        System.out.println("-------------------------eventbus----------------------------");

        timer.start("invoker");
        for (int i = 0; i < times; i++) {
            invoker.invoke(args);
        }
        long invokerTime = timer.intervalMs("invoker");
        System.out.println("-------------------------invoker----------------------------");

        timer.start("accessor");
        for (int i = 0; i < times; i++) {
//            classAccessor.invoke(one, "handle", args);
            classAccessor.set(one, "username", "sunkaihan");
        }
        long accessorTime = timer.intervalMs("accessor");
        System.out.println("-------------------------accessor----------------------------");

        timer.start("call");
        for (int i = 0; i < times; i++) {
//            one.handleEvent("Kaihan Sun", 38);
            one.setUsername("sunkaihan");
        }
        long callTime = timer.intervalMs("call");
        System.out.println("-------------------------call----------------------------");

        System.out.println(CharSequenceTools.format("eventbus execute {} times took {} ms", times, eventBusTime));
        System.out.println(CharSequenceTools.format("invoker execute {} times took {} ms", times, invokerTime));
        System.out.println(CharSequenceTools.format("accessor execute {} times took {} ms", times, accessorTime));
        System.out.println(CharSequenceTools.format("call execute {} times took {} ms", times, callTime));

        timer.clear();
    }
}
