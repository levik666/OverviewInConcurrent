package com.epam.jug.synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountDownLatchDemo {

    private static final int EMULATE_TIME_TO_START = 1000;
    private static final String CACHE = "CacheService";
    private static final String ALERT = "AlertService";
    private static final String GSM = "GSMService";

    public static void main(String args[]) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        final ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Service(CACHE, EMULATE_TIME_TO_START, latch));
        executorService.submit(new Service(ALERT, EMULATE_TIME_TO_START, latch));
        executorService.submit(new Service(GSM, EMULATE_TIME_TO_START, latch));

        latch.await();  //main thread is waiting on CountDownLatch to finish
        Logger.getLogger(Service.class.getName()).log(Level.INFO, "All services are up, Application is starting now");
        executorService.shutdown();
    }

}

class Service implements Runnable {
    private final String name;
    private final int emulateServiceWork;
    private final CountDownLatch latch;

    public Service(final String name, final int emulateServiceWork, final CountDownLatch latch) {
        this.name = name;
        this.emulateServiceWork = emulateServiceWork;
        this.latch = latch;
    }

    @Override
    public void run() {
        sleep(emulateServiceWork);
        Logger.getLogger(Service.class.getName()).log(Level.INFO, name + " is Up");
        System.out.println();
        latch.countDown(); //reduce count of CountDownLatch by 1
    }

    private void sleep(int timeToStart) {
        try {
            Thread.sleep(timeToStart);
        } catch (InterruptedException exe) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, exe);
        }
    }
}