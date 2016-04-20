package com.epam.jug.threadhandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadHandlerDemo {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executor = Executors.newCachedThreadPool(new CaughtExceptionsThreadFactory("Worker"));

        System.out.println("Executing tasks:");
        for (int i = 0; i < 3; i++) {
            executor.execute(new Worker());
        }
    }

    public static class ThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable exe) {
            System.out.println(exe.getMessage());
            exe.printStackTrace();
        }
    }

    private static class CaughtExceptionsThreadFactory implements ThreadFactory {

        private String name;
        private ThreadUncaughtExceptionHandler handler = new ThreadUncaughtExceptionHandler();

        public CaughtExceptionsThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(final Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName(name + "_" + thread.getId());
            thread.setUncaughtExceptionHandler(handler);
            return thread;
        }
    }


    public static class Worker implements Runnable {
        private static int nTasks = 0;
        private int taskId = ++nTasks;

        @Override
        public void run() {
            System.out.println("\t Starting work in thread: " + taskId + " " + Thread.currentThread().getName());
            sleep(100);
            if ((taskId % 2) == 0) {
                throw new RuntimeException("Task " + taskId + " finished with Exception" + " " + Thread.currentThread().getName());
            } else {
                System.out.printf("\t Task %d finished normally.%n", taskId);
            }
        }

        private void sleep(long timeout) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }
}
