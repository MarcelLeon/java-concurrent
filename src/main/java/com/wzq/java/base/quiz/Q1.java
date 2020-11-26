package com.wzq.java.base.quiz;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

/**
 * 实现一个容器，提供两个方法，add size，写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，
 * 线程2给出提示并结束。
 */
public class Q1 {

    static Thread t1,t2;

    public static void main(String[] args) {
        TmpArray tmpArray = new TmpArray();

        t2 = new Thread(() -> IntStream.range(0, 10).forEach(i -> {
            System.out.println("add one to array.");
            tmpArray.add(1);
            if (tmpArray.size() == 5){
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        }));
        t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("arrive 5 number.");
            LockSupport.unpark(t2);
        });
        t1.start();
        t2.start();
    }

    static class TmpArray {
        volatile int[] items = new int[0];

        public synchronized void add(int i) {
            if (items.length < 100) {
                int[] tmp = new int[items.length + 1];
                for (int a = 0; a < items.length; a++) {
                    tmp[a] = items[a];
                }
                tmp[items.length] = i;
                items = tmp;
            }
        }

        public synchronized int size() {
            return items.length;
        }
    }
}
