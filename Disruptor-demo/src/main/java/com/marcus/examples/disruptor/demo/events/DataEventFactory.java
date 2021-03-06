package com.marcus.examples.disruptor.demo.events;

import com.lmax.disruptor.EventFactory;
import com.marcus.examples.disruptor.demo.entity.DataEventEntity;

/**
 * @author: MarcusJiang1306
 */
public class DataEventFactory implements EventFactory<DataEventEntity> {
    public DataEventEntity newInstance() {
        return new DataEventEntity();
    }
}
