package com.epam.jug.singelton;

public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {}

    public static Singleton getInstance() {
        final Singleton localInstance = singleton;

        if (localInstance == null) {
            synchronized (singleton) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return  singleton;
    }

}
