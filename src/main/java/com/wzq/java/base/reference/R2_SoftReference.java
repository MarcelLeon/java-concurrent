package com.wzq.java.base.reference;

import java.lang.ref.SoftReference;

/**
 * @author wangzq
 * @date 2020/9/4 11:33
 */
public class R2_SoftReference {
    static class MyObject{
        byte[] m = new byte[10*1024*1024];
    }
    public static void main(String[] args) {
        SoftReference<MyObject> softReference = new SoftReference<MyObject>(new MyObject());
        System.out.println(softReference.get());
        System.gc();
        System.out.println("after gc:"+softReference.get());
        // 配置启动参数 -Xmx15m
        new MyObject();
        System.out.println("after create 10m object:"+softReference.get());
    }
}
