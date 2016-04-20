package com.epam.jug.accumulators;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Counter implements Runnable{

    final AtomicLong counter = new AtomicLong(0);


    @Override
    public void run() {
        counter.updateAndGet(counter->counter-1);
        counter.incrementAndGet();
    }

    public long getCount() {
        return counter.get();
    }
}
