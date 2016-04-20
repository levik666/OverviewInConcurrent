package com.epam.jug.lock.bank;

public class AccountLockedException extends Exception {

    public AccountLockedException(String message) {
        super(message);
    }
}
