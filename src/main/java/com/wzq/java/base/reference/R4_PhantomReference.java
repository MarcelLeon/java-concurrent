package com.wzq.java.base.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author wangzq
 * @date 2020/9/4 11:33
 */
public class R4_PhantomReference {
    public static void main(String[] args) {
        ReferenceQueue QUEUE = new ReferenceQueue();
        Object mainRole = new Object();
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(mainRole,QUEUE);

        Object mainRole2 = new Object();
        PhantomReference<Object> phantomReference2 = new PhantomReference<Object>(mainRole2,QUEUE);

        System.out.println(phantomReference.get());
        new Thread(()-> {
            Reference r;
            for (;;){
                if ((r = QUEUE.poll()) != null){
                    System.out.println("queue has element");
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        }catch (Exception e){}
        mainRole = null;
        System.gc();
        try {
            Thread.sleep(1000);
        }catch (Exception e){}
        mainRole2 = null;
        System.gc();
    }
}
