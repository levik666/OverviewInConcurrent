package com.epam.jug.accumulators;

import java.util.concurrent.atomic.LongAdder;

public class CounterJava8 implements Runnable{

    final LongAdder counter = new LongAdder();

    @Override
    public void run() {
        counter.increment();
    }

    public long getCount() {
        return counter.sum();
    }
}
