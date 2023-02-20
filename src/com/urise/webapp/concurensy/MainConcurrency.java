package com.urise.webapp.concurensy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREAD_COUNT = 10000;
    private static int counter;
    private static final Object OBJECT = new Object();
    private static final Lock LOCK = new ReentrantLock();
    private static AtomicInteger atomic = new AtomicInteger(counter);

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();
        System.out.println(thread0.getState());

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            }).start();
        }
        System.out.println(counter);

        Thread.sleep(10);
        System.out.println(counter);

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.dec();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(counter);

//        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
//        ExecutorService service = Executors.newCachedThreadPool();
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            service.submit(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//        }
//        latch.await(10, TimeUnit.SECONDS);
//        service.shutdown();
//        System.out.println(counter);

        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < THREAD_COUNT; i++) {
            service.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    atomicInc();
                }
                latch.countDown();
            });
        }
        latch.await(10, TimeUnit.SECONDS);
        service.shutdown();
        System.out.println(atomic.get());


    }

    private synchronized void dec() {
        // == synchronized (this){counter--;}
        // == synchronized (MainConcurrency.class){counter--;}
        counter--;
    }

    private static void atomicInc(){
        atomic.incrementAndGet();
    }


    private static void inc() {
        double a = Math.sin(13.0);
        LOCK.lock();
        try{
            counter++;
        } finally {
            LOCK.unlock();
        }
//       == synchronized (OBJECT) {counter++;}

    }
}
