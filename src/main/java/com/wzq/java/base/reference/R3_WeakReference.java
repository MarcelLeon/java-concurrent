package com.wzq.java.base.reference;

import java.lang.ref.WeakReference;

/**
 * @author wangzq
 * @date 2020/9/4 11:33
 */
public class R3_WeakReference {
    public static void main(String[] args) {
        WeakReference<Object> weakReference = new WeakReference<>(new Object());
        System.out.println(weakReference.get());
        System.gc();
        // weak reference 会在gc 后清除
        System.out.println("after gc:" + weakReference.get());
    }
}
