package com.epam.jug.lock.bank;

import java.util.concurrent.locks.*;

public class Account {

    private Lock lock;

    private int balance;
    private int id;

    public Account(int id, int balance) {
        this.balance = balance;
        this.id = id;

        lock = new ReentrantLock();
    }

    public void withdraw(int amount){
        balance -= amount;
    }

    public void deposit(int amount){
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance + ", id = " + id +
                '}';
    }
}
