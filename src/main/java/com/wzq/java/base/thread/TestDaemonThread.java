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

        // 场景1. 直接启动会在主线程退出时候，继续存活
        thread.start();
        sleep();

        // 场景2. 设置守护进程 在主线程退出时候，整体都退出。即：全部非守护线程都完毕时 程序退出
//        thread.setDaemon(true);
//        thread.start();
//        sleep();

        // 场景3. 通过 continueRun方式停掉子线程
//        thread.start();
//        sleep();
//        continueRun = false;

        // 场景4. 守护线程的子线程也是守护线程
//        Thread thread2 = new Thread(() -> {
//            new Thread(()->System.out.println(String.format("%s-%s-%s","son daemon",Thread.currentThread().getName()," running"))).start();
//            while (true){
//                System.out.println(Thread.currentThread().getName().concat(" running"));
//                try {
//                    Thread.sleep(100);
//                }catch (Exception e){
//                }
//            }
//        });
//        thread2.setDaemon(true);
//        thread2.start();
//        sleep();
    }

    static void sleep(){
        try {
            Thread.sleep(200);
        }catch (Exception e){
        }
    }

}
