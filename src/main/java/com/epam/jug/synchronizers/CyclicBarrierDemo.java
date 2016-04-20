package com.epam.jug.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier barrier1 = new CyclicBarrier(2, () -> System.out.println("BarrierAction 1 executed "));
        CyclicBarrier barrier2 = new CyclicBarrier(2, () -> System.out.println("BarrierAction 2 executed "));
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new CyclicBarrierRunnable(barrier1, barrier2));
        executorService.submit(new CyclicBarrierRunnable(barrier1, barrier2));

        executorService.shutdown();
    }

    public static class CyclicBarrierRunnable implements Runnable {
        private CyclicBarrier barrier1;
        private CyclicBarrier barrier2;

        public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2) {
            this.barrier1 = barrier1;
            this.barrier2 = barrier2;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
                barrier1.await();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
                barrier2.await();

                System.out.println(Thread.currentThread().getName() + " done!");
            } catch (InterruptedException | BrokenBarrierException exe) {
                exe.printStackTrace();
            }
        }
    }
}
