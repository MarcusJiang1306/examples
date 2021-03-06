package com.marcus.examples.disruptor.demo.events;

import com.lmax.disruptor.EventHandler;
import com.marcus.examples.disruptor.demo.entity.DataEvent;

/**
 * @author: MarcusJiang1306
 */
public class DataEventHandler<T> implements EventHandler<DataEvent<T>> {
    public void onEvent(DataEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + event.getT().toString());
        System.out.println("Sequence: " + sequence+" and endOfBatch:" + endOfBatch);
    }
}
