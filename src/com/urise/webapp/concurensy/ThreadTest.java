package com.urise.webapp.concurensy;

public class ThreadTest {
    static volatile int x = 10;

    public static void main(String[] args) throws InterruptedException {
        Resource resource = new Resource(100);
        System.out.println(resource.getNonStatic());
        System.out.println(Resource.getIsStatic());
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                resource.changeNonStatic();
                Resource.changeStatic();
            });
            thread.start();
            thread.join();
        }
        // Thread.sleep(10);
        System.out.println(resource.getNonStatic());
        System.out.println(Resource.getIsStatic());

        Thread thread1 = new Thread(() -> {
            while (x > 0) {
                System.out.println("change x = " + x--);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            int y = x;
            while (y > 0) {
              //  System.out.println("x = " + x);
                if (y != x) {
                    System.out.println("new value y = " + y);
                    y = x;
                }
            }
        });
        thread1.start();
        thread2.start();

    }

}

