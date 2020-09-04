package com.wzq.java.base.thread;

import io.reactivex.Observable;

import java.util.concurrent.Callable;

/**
 * @author wangzq
 * @date 2020/6/19 8:44
 */
public class ThreadLifeCycle {

    public static void main(String[] args) {
        Object lock = new Object();

        Thread thread = new Thread(() -> {
            System.out.println("try wait...");
            try {
                synchronized (lock) {
                    lock.wait(1000);
                }
                System.out.println("waiting release.");
                Thread.sleep(1000 * 3);
            } catch (Exception e) {

            }

        }, "testWaitThread");
        thread.start();
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception e) {

        }
        System.out.println(thread.getState());
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        System.out.println(thread.getState());
        synchronized (lock) {lock.notify();}
        System.out.println(thread.getState());


        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " running...");
            System.out.println(Thread.currentThread().getName() + " state:" + Thread.currentThread().getState());
            System.out.println(Thread.currentThread().getThreadGroup().getParent().getName());
            System.out.println(Thread.currentThread().getStackTrace()[0].getMethodName());

            try {
                Thread.sleep(1000 * 5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        System.out.println(t1.getName() + " state:" + t1.getState());
        System.out.println(Thread.currentThread().getName() + " state:" + Thread.currentThread().getState());
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " state:" + Thread.currentThread().getState());
    }
}
