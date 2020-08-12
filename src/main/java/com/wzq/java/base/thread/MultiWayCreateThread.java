package com.wzq.java.base.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author wangzq
 * @date 2020/6/19 8:45
 */
@Slf4j
public class MultiWayCreateThread {

    public static void main(String[] args) {
        MultiWayCreateThread multiWayCreateThread = new MultiWayCreateThread();
//        multiWayCreateThread.firstWay();
//        multiWayCreateThread.secondWay();
        multiWayCreateThread.thridWay();
        // thread pool
        multiWayCreateThread.fourthWay();
        multiWayCreateThread.fifthWay();
        multiWayCreateThread.sixthWay();
    }

    CountDownLatch countDownLatch = new CountDownLatch(10);

    void firstWay() {

        IntStream.range(0, 10).forEach(i -> {
            Thread thread = new MyThread();
            thread.setName("thread_" + i);
            thread.start();
            log.info("count : " + countDownLatch.getCount());
            countDownLatch.countDown();
        });
        while (countDownLatch.getCount() != 0) {
            log.info("while count : " + countDownLatch.getCount());
        }
        Thread.interrupted();
    }

    void secondWay() {
        Runnable runnable = new MyRunnable();
        Runnable runnable2 = runnable;
        new Thread(runnable).start();
        new Thread(runnable2).start();
    }

    void thridWay() {
        FutureTask<String> task = new FutureTask<String>((Callable<String>) () -> {
            String logStr = "create thread " + Thread.currentThread().getName() + " by implements Callable";
            log.info(logStr);
            return logStr;
        });
        new Thread(task).start();

        while (!task.isDone()) {
            log.info("wait for call done");
        }
        try {
            log.info("execute success. result : {}", task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    void fourthWay() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new MyThread());
        executorService.shutdown();
    }

    void fifthWay() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        ExecutorService executorService = new ScheduledThreadPoolExecutor(100,
                new BasicThreadFactory.Builder()
                        .namingPattern("wzq-test-thread-%d").daemon(true).build());
        IntStream.range(0, 100).forEach(i -> {
            executorService.submit((Runnable) () -> {
                System.out.println(Thread.currentThread().getName() + " is running.");
            });
        });
        executorService.shutdown();
    }

    void sixthWay() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        ExecutorService executorService = new ThreadPoolExecutor(
                10, 100,
                5000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("six-way-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        IntStream.range(0, 100).forEach(i -> {
            executorService.submit((Runnable) () -> {
                System.out.println(Thread.currentThread().getName() + " is running.");
            });
        });
        executorService.shutdown();
    }


    class MyThread extends Thread {

        @Override
        public void run() {
            print();
        }

        public void print() {
            log.info("create thread {}, by extends Thread", this.getName());
        }
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            print();
        }

        public void print() {
            log.info("create thread {} by implements runnable", Thread.currentThread().getName());
        }
    }

    class MyCallable implements Callable {

        @Override
        public Object call() throws Exception {
            String logStr = print();
            log.info(logStr);
            return logStr;
        }

        public String print() {
            return "create thread " + Thread.currentThread().getName() + " by implements Callable";
        }
    }
}
