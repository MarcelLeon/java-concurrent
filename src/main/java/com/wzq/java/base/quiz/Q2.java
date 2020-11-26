package com.wzq.java.base.quiz;

import java.util.concurrent.Semaphore;

/**
 * 写一个固定容量同步容器，拥有put和get方法，已经getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用。
 */
public class Q2 {
    public static void main(String[] args) {

    }

    class FixedSynchronizer {
        final int MAX = 10;
        int[] items = new int[0];
        final Object lock = new Object();
        Semaphore producers = new Semaphore(2);
        Semaphore consumers = new Semaphore(10);

        public void put(int i) {
            synchronized (lock) {
            while (items.length == MAX){
                try {
                    this.wait();
                }catch (Exception e){}
            }

                int[] tmp = new int[items.length + 1];
                for (int a = 0; a < items.length; a++) {
                    tmp[a] = items[a];
                }
                tmp[items.length] = i;
                items = tmp;
            }
        }

        public int get(int index) {
            synchronized (lock) {
                return items[index];
            }
        }

        public int getCount() {
            synchronized (lock) {
                return items.length;
            }
        }
    }
}
