package com.epam.jug.lock.bank;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Transfer implements Callable<Boolean>{

    private static final int WAIT_SEC = 10;

    private Account firstAccount;
    private Account secondAccount;
    private int amount;

    public Transfer(Account firstAccount, Account secondAccount, int amount) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.amount = amount;
    }


    @Override
    public Boolean call() throws Exception {
        System.out.println("Transfer start from " + firstAccount + " to " + secondAccount + ". Thread name " + Thread.currentThread().getName());
        synchronized(firstAccount) {
            if (firstAccount.getBalance() < amount) {
                throw new InsufficientFundsException();
            }
            Thread.sleep(WAIT_SEC);

            synchronized(secondAccount) {
                System.out.println("Looked " + secondAccount + ". Thread name " + Thread.currentThread().getId());
                Thread.sleep(WAIT_SEC);
                firstAccount.withdraw(amount);
                secondAccount.deposit(amount);
                System.out.println("Transfer from " + firstAccount + " to " + secondAccount + " completed" + ". Thread name " + Thread.currentThread().getId());
            }
        }
        return true;
    }




        /*@Override
    public Boolean call() throws Exception {
        System.out.println("Transfer start from " + firstAccount + " to " + secondAccount + ". Thread name " + Thread.currentThread().getName());
        if (firstAccount.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            try {
                if (firstAccount.getBalance() < amount) {
                    throw new InsufficientFundsException();
                }

                System.out.println("Looked " + firstAccount + ". Thread name " + Thread.currentThread().getName());
                Thread.sleep(100L);

                if (secondAccount.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Looked " + secondAccount + ". Thread name " + Thread.currentThread().getName());
                        Thread.sleep(100L);
                        firstAccount.withdraw(amount);
                        secondAccount.deposit(amount);
                        System.out.println("Transfer from " + firstAccount + " to " + secondAccount + " completed" + ". Thread name " + Thread.currentThread().getName());
                    } finally {
                        secondAccount.getLock().unlock();
                    }
                } else {
                    System.err.println("Transfer from " + firstAccount + " to " + secondAccount + " failed" + ". Thread name " + Thread.currentThread().getName());
                    return false;
                }
            } finally {
                firstAccount.getLock().unlock();
            }
        } else {
            System.err.println("Transfer from " + firstAccount + " to " + secondAccount + " failed" + ". Thread name " + Thread.currentThread().getName());
            return false;
        }

        return true;
    }*/
}
