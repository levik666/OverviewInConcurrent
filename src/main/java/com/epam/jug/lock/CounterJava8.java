package com.epam.jug.lock;

import java.util.concurrent.locks.StampedLock;

public class CounterJava8 {

    private StampedLock lock = new StampedLock();
    private int value;

    public int increment() {
        long stamp = lock.writeLock();
        try {
            return ++value;
        } finally {
            this.lock.unlockWrite(stamp);
        }
    }

    public int decrement() {
        long stamp = lock.writeLock();
        try {
            return --value;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int getValue() {
        long stamp;
        if ((stamp = lock.tryOptimisticRead()) != 0l) { // non blocking path - super fast
            if (lock.validate(stamp)) {
                System.out.println("use non blocking path");
                //success! no contention with a writer thread
                return value;
            }
        }
        //another thread must have acquired a write lock in the mean while, changing the stamp.
        stamp = lock.readLock(); //this is a traditional blocking read lock
        try {
            System.out.println("use readLock");
            return value;
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public static void main(String[] args) {
        final CounterJava8 counter = new CounterJava8();
        for(int i = 0; i <= 10; i++) {
            counter.increment();

            if (i%2 == 0) {
                counter.decrement();
            }

            System.out.println("Iteration " + i + " value " + counter.getValue());
        }

        System.out.println("End counterValue " + counter.getValue());
    }
}
