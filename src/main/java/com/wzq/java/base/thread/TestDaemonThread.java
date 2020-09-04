package com.wzq.java.base.thread;

/**
 * @author: create by wangzq
 * @version: v1.0
 * @date:2020/8/7 1:12
 */
public class TestDaemonThread {
    private static volatile boolean continueRun = true;
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            while (continueRun){
                System.out.println(Thread.currentThread().getName().concat(" running"));
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                }
            }
        });
        // 直接启动会在主线程退出时候，继续存活
        thread.start();
        // 设置守护进程 在主线程退出时候，整体都退出。即：全部非守护线程都完毕时 程序退出
//        thread.setDaemon(true);
//        thread.start();
        try {
            Thread.sleep(200);
        }catch (Exception e){
        }
        // 通过 continueRun 可以停掉
//        continueRun = false;
    }

}
