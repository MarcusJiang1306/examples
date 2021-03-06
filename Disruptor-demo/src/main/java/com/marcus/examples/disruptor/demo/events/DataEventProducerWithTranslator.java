package com.marcus.examples.disruptor.demo.events;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.marcus.examples.disruptor.demo.entity.DataEvent;
import com.marcus.examples.disruptor.demo.entity.DataEventEntity;

/**
 * @author: MarcusJiang1306
 */
public class DataEventProducerWithTranslator {

    private final RingBuffer<DataEvent> ringBuffer;

    public DataEventProducerWithTranslator(RingBuffer<DataEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<DataEvent, DataEventEntity> TRANSLATOR = (event, sequence, arg0) -> event.setT(arg0);


    public void onData(DataEventEntity dataEventEntity) {
        ringBuffer.publishEvent(TRANSLATOR,dataEventEntity);
    }
}
