package com.marcus.examples.disruptor.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.marcus.examples.disruptor.demo.entity.DataEvent;
import com.marcus.examples.disruptor.demo.entity.DataEventEntity;
import com.marcus.examples.disruptor.demo.events.DataEventFactory;
import com.marcus.examples.disruptor.demo.events.DataEventHandler;
import com.marcus.examples.disruptor.demo.events.DataEventProducer;
import com.marcus.examples.disruptor.demo.events.DataEventProducerWithTranslator;
import com.marcus.examples.disruptor.demo.workers.DisruptorManager;
import com.marcus.examples.disruptor.demo.workers.PrintConsumer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author: MarcusJiang1306
 */
public class DisruptorApplication {

    public static void main(String[] args) throws InterruptedException {
        EventHandler();
        PrintConsumer<DataEventEntity> consumer = new PrintConsumer<>();
        DisruptorManager<DataEventEntity> disruptorManager = new DisruptorManager<>(consumer);

        for (int i = 0; i < 10; i++) {
            disruptorManager.onData(new DataEventEntity(i, "Marcus"));
        }
        for (int i = 0; i < 10; i++) {
            disruptorManager.onData(new DataEventEntity(i, "Cuicui"));
        }

    }

    private static void EventHandler() throws InterruptedException {
        DataEventFactory dataEventFactory = new DataEventFactory();
        int bufferSize = 1024;
//        Disruptor<DataEvent> disruptor = new Disruptor<>(dataEventFactory, bufferSize, DaemonThreadFactory.INSTANCE);
        Disruptor<DataEvent> disruptor = new Disruptor<>(DataEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new DataEventHandler());
        disruptor.start();

        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer dataEventProducer = new DataEventProducer(ringBuffer);
        DataEventProducerWithTranslator dataEventProducerWithTranslator = new DataEventProducerWithTranslator(disruptor.getRingBuffer());


        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < 10; i++) {
            byteBuffer.putLong(0,i);
            dataEventProducer.onData(new DataEventEntity(i, "Marcus"));
            TimeUnit.MILLISECONDS.sleep(100);
        }
        System.out.println("=========");
        for (int i = 0; i < 10; i++) {
            byteBuffer.putLong(0,i);
            dataEventProducerWithTranslator.onData(new DataEventEntity(i, "Marcus"));
           TimeUnit.MILLISECONDS.sleep(100);
       }
    }
}
