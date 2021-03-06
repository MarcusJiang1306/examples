package com.marcus.examples.disruptor.demo.events;

import com.lmax.disruptor.RingBuffer;
import com.marcus.examples.disruptor.demo.entity.DataEvent;
import com.marcus.examples.disruptor.demo.entity.DataEventEntity;

/**
 * @author: MarcusJiang1306
 */
public class DataEventProducer {


    private final RingBuffer<DataEvent> ringBuffer;

    public DataEventProducer(RingBuffer<DataEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    public void onData(DataEventEntity dataEventEntity) {
        long sequence = ringBuffer.next();
        DataEvent dataEvent = ringBuffer.get(sequence);
        dataEvent.setT(dataEventEntity);
        ringBuffer.publish(sequence);
    }
}
