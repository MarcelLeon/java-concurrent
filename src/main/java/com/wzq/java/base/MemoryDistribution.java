package com.wzq.java.base;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author wangzq
 * @date 2020/9/1 13:41
 */
public class MemoryDistribution {
    static volatile Object object1 = new Object();
    public static void main(String[] args) {

        Object object = new Object();
        System.out.println("new:"+ClassLayout.parseInstance(object).toPrintable());

        synchronized (object){
            System.out.println("in sync:"+ClassLayout.parseInstance(object).toPrintable());
        }
        System.out.println("end sync:"+ClassLayout.parseInstance(object).toPrintable());

        System.out.println("volatile:"+ClassLayout.parseInstance(object1).toPrintable());
        System.gc();
        System.out.println("after gc:"+ClassLayout.parseInstance(object).toPrintable());
    }
}
