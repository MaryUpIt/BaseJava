package com.urise.webapp.concurensy;

public class Singleton {

    int i;
    volatile private static Singleton INSTANCE;
    double sin = Math.sin(13.);

    private Singleton() {
    }

    //double check locking
    public static Singleton getInstance() {
        if (INSTANCE == null)
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    int i = 13;
                    INSTANCE = new Singleton();
                }
            }
        return INSTANCE;
    }

    //Initialization-on-demand holder idiom
    public static Singleton getInstanceHolder() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}

