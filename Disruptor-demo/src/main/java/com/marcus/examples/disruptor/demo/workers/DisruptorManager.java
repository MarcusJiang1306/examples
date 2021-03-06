package com.marcus.examples.disruptor.demo.workers;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.marcus.examples.disruptor.demo.entity.DataEvent;

/**
 * @author: MarcusJiang1306
 */
public class DisruptorManager<T> {

    private WorkHandler<DataEvent<T>> workHandler;

    public static final Integer DEFAULT_SIZE = 4096 << 1 << 1;

    private static final Integer DEFAULT_CONSUMER_SIZE = Runtime.getRuntime().availableProcessors() << 1;

    private final Integer size;

    private Integer consumerSize;
    private Disruptor<DataEvent<T>> disruptor;

    public DisruptorManager(WorkHandler<DataEvent<T>> workHandler) {
        this(workHandler, DEFAULT_CONSUMER_SIZE, DEFAULT_SIZE);
    }

    public DisruptorManager(WorkHandler<DataEvent<T>> workHandler, Integer size, Integer consumerSize) {
        this.workHandler = workHandler;
        this.size = size;
        this.consumerSize = consumerSize;
        startup();
    }

    private void startup() {
        disruptor = new Disruptor<>(DataEvent::new,
                size,
                DaemonThreadFactory.INSTANCE,
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(workHandler);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
    }

    public void onData(final T t) {
        disruptor.getRingBuffer().publishEvent((event, sequence) -> event.setT(t));
    }
}
