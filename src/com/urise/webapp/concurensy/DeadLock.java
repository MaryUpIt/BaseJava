package com.urise.webapp.concurensy;

public class DeadLock {
    public static void main(String[] args) {
        Resource lockA = new Resource("lockA");
        Resource lockB = new Resource("lockB");

        deadLock(lockA,lockB);
        deadLock(lockB,lockA);
    }

    private static void deadLock(Object lockA, Object lockB) {
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " start");
            synchronized (lockA) {
                System.out.println(threadName + " locked object " + lockA.toString());
                synchronized (lockB) {
                    System.out.println(threadName + " locked object " + lockB.toString());
                }
            }
            System.out.println(threadName + " unlocked object " + lockA.toString());
            System.out.println(threadName + " unlocked object " + lockB.toString());

        }).start();
    }


}
