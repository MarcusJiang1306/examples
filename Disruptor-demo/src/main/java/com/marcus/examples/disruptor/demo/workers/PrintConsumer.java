package com.marcus.examples.disruptor.demo.workers;

import com.lmax.disruptor.WorkHandler;
import com.marcus.examples.disruptor.demo.entity.DataEvent;

/**
 * @author: MarcusJiang1306
 */
public class PrintConsumer<T> implements WorkHandler<DataEvent<T>> {

    @Override
    public void onEvent(DataEvent<T> event) throws Exception {
        System.out.println(event.getT().toString());
    }
}
