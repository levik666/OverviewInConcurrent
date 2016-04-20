package com.epam.jug.lock.bank;

import java.util.concurrent.*;

public class ApplicationExecutor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final Account firstAccount = new Account(1, 1000);
        final Account secondAccount = new Account(2, 2000);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Transfer(firstAccount, secondAccount, 50));
        executorService.submit(new Transfer(secondAccount, firstAccount, 20));
        executorService.awaitTermination(100, TimeUnit.MINUTES);
    }
}
