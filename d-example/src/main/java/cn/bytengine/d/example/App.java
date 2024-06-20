package cn.bytengine.d.example;

import cn.bytengine.d.events.Events;
import cn.bytengine.d.events.InvokerEventBus;
import cn.bytengine.d.example.ctx.ServiceOne;
import cn.bytengine.d.fn.invoker.Invoker;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.text.CharSequenceUtil;

public class App {
    public static void main(String[] args) throws Throwable {

    }

    /*private static void testDisruptor() {
        int ringBufferSize = 1024 * 1024;
        Disruptor<Object> disruptor =
                new Disruptor<>(factory, ringBufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new YieldingWaitStrategy());
    }*/

    private static void testInvokerEventBus() throws Throwable {
        ServiceOne one = new ServiceOne();
        InvokerEventBus eventBus = Events.general().build();
        Invoker invoker = eventBus.register("TestEvent", one::handleEvent);
        int times = 100000;
        final TimeInterval timer = new TimeInterval();
        timer.start("eventbus");
        for (int i = 0; i < times; i++) {
            eventBus.pub("TestEvent", "Kaihan Sun", 38);
        }
        long eventBusTime = timer.intervalMs("eventbus");
        timer.start("invoker");
        for (int i = 0; i < times; i++) {
            invoker.invoke(new Object[]{"Kaihan Sun", 38});
        }
        long invokerTime = timer.intervalMs("invoker");
        timer.start("call");
        for (int i = 0; i < times; i++) {
            one.handleEvent("Kaihan Sun", 38);
        }
        long callTime = timer.intervalMs("call");
        System.out.println(CharSequenceUtil.format("eventbus execute {} times took {} ms", times, eventBusTime));
        System.out.println(CharSequenceUtil.format("invoker execute {} times took {} ms", times, invokerTime));
        System.out.println(CharSequenceUtil.format("call execute {} times took {} ms", times, callTime));

        timer.clear();
    }
}
