package com.epam.jug.accumulators;

import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorJava8 {

    final LongAccumulator counter = new LongAccumulator((left, right) -> left * right, 2l);

    public void run() {
        counter.accumulate(2);
    }

    public long getCount() {
        return counter.get();
    }

    public static void main(String[] args) {
        LongAccumulatorJava8 java8 = new LongAccumulatorJava8();
        java8.run();
        System.out.println(java8.getCount());
    }


}
