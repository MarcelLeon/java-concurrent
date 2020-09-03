package com.wzq.java.base;

/**
 * @author wangzq
 * @date 2020/9/3 13:29
 */
public class TestCpuReordering {

    static int x = 0, y = 0;
    static int a = 0, b = 0;

    /**
     * 经常看到一个demo  描述指令重排序的，今天分享一下
     * 问题：
     *  什么是指令重排？指令重排得由来？有哪些种？java怎么解决这个问题（JMM as-if-serial 内存屏障）？
     * dcl 与 volatile问题
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        for (;;){
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread t1 = new Thread(() -> {
                x = 1;
                a = y;
            });
            Thread t2 = new Thread(() -> {
                y = 1;
                b = x;
            });
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            }catch (Exception e){}finally {
                if (a == 0 && b==0){
                    System.out.println("a=b=0");
                }
            }
        }
    }
}
