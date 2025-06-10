package cn.bytengine.d.example;

import cn.bytengine.d.assist.ClassAssists;
import cn.bytengine.d.assist.ClassInfo;
import cn.bytengine.d.events.Events;
import cn.bytengine.d.events.InvokerEventBus;
import cn.bytengine.d.example.ctx.ServiceOne;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.text.CharSequenceUtil;

import java.lang.reflect.Method;

public class App {
    public static void main(String[] args) throws Throwable {
        testInvokerEventBus();
    }

    /*private static void testDisruptor() {
        int ringBufferSize = 1024 * 1024;
        Disruptor<Object> disruptor =
                new Disruptor<>(factory, ringBufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new YieldingWaitStrategy());
    }*/

    private static void testInvokerEventBus() throws Throwable {
        ClassAssists classAssists = ClassAssists.of(ServiceOne.class);
        ClassInfo classInfo = classAssists.get(ServiceOne.class);
        Method method = ServiceOne.class.getDeclaredMethod("handleEvent", String.class, int.class);
        classInfo.addMethod(method);

        ServiceOne one = new ServiceOne();
        InvokerEventBus eventBus = Events.general().build();
        Invoker invoker = eventBus.register("TestEvent", ServiceOne::handleEvent);
        int times = 100000;
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

        timer.start("call");
        for (int i = 0; i < times; i++) {
            one.handleEvent("Kaihan Sun", 38);
        }
        long callTime = timer.intervalMs("call");
        System.out.println("-------------------------call----------------------------");

        timer.start("classInfo");
        for (int i = 0; i < times; i++) {
            classInfo.invokeMethod(one, "handleEvent", "Kaihan Sun", 38);
        }
        long classInfoTime = timer.intervalMs("classInfo");
        System.out.println("-------------------------classInfo----------------------------");

        System.out.println(CharSequenceUtil.format("eventbus execute {} times took {} ms", times, eventBusTime));
        System.out.println(CharSequenceUtil.format("invoker execute {} times took {} ms", times, invokerTime));
        System.out.println(CharSequenceUtil.format("call execute {} times took {} ms", times, callTime));
        System.out.println(CharSequenceUtil.format("classInfo execute {} times took {} ms", times, classInfoTime));

        timer.clear();
    }
}
