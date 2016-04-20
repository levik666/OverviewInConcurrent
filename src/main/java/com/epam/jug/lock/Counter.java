package com.epam.jug.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Counter {

    private ReadWriteLock lock =  new ReentrantReadWriteLock();
    private int value;

    public int increment() {
        lock.writeLock().lock();
        try {
            return ++value;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public int decrement() {
        lock.writeLock().lock();
        try {
            return --value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getValue() {
        lock.readLock().lock();
        try {
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }
}
