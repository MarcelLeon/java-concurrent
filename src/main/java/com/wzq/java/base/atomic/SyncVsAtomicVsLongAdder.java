package com.wzq.java.base.atomic;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class SyncVsAtomicVsLongAdder {
    static volatile Long long1 = 0L;
    static AtomicLong atomicLong = new AtomicLong(0L);
    static LongAdder longAdder = new LongAdder();

    final static int forTimes = 10000;
    final static int concurrent = 1000;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 4096, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4096));

        Phaser phaser = new Phaser();
        final Object lock = new Object();

        phaser.bulkRegister(concurrent);
        System.out.println("-----phase:".concat(String.valueOf(phaser.getPhase())));
        long currentTime1 = System.currentTimeMillis();
        IntStream.range(1, concurrent).forEach(i -> {
            executor.submit(() -> {
                IntStream.range(0, forTimes).forEach(a -> {
                            synchronized (lock) {
                                long1++;
                            }
                        }
                );
                phaser.arrive();
            });
        });
        phaser.arriveAndAwaitAdvance();
        System.out.println("-----phase:".concat(String.valueOf(phaser.getPhase())));
        System.out.println("sync cost:".concat(String.valueOf(System.currentTimeMillis() - currentTime1)));

        long currentTime2 = System.currentTimeMillis();
        IntStream.range(1, concurrent).forEach(i -> {
            executor.submit(() -> {
                IntStream.range(0, forTimes).forEach(a -> atomicLong.incrementAndGet());
                phaser.arrive();
            });
        });
        phaser.arriveAndAwaitAdvance();
        System.out.println("-----phase:".concat(String.valueOf(phaser.getPhase())));
        System.out.println("atomic cost:".concat(String.valueOf(System.currentTimeMillis() - currentTime2)));

        long currentTime3 = System.currentTimeMillis();
        IntStream.range(1, concurrent).forEach(i -> {
            executor.submit(() -> {
                IntStream.range(0, forTimes).forEach(a -> longAdder.increment());
                phaser.arrive();
            });
        });
        phaser.arriveAndAwaitAdvance();
        System.out.println("-----phase:".concat(String.valueOf(phaser.getPhase())));
        System.out.println("longAdder cost:".concat(String.valueOf(System.currentTimeMillis() - currentTime3)));

        System.out.println("long value:".concat(long1.toString()));
        System.out.println("atomic value:".concat(String.valueOf(atomicLong.get())));
        System.out.println("longAdder value:".concat(String.valueOf(longAdder.longValue())));
        executor.shutdown();
    }
}
