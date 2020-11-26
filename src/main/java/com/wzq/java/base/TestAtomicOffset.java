package com.wzq.java.base;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: create by wangzq
 * @version: v1.0
 * @date:2020/8/20 2:12
 */
public class TestAtomicOffset {

    /**
     * 好奇unsafe.objectFieldOffset获取的是一个对象对应一个offset吗？
     * @param args
     */
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger atomicInteger2 = new AtomicInteger();
        AtomicInteger atomicInteger3 = new AtomicInteger();

        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe  = (Unsafe) unsafeField.get(null);
            int r1 = unsafe.getInt(atomicInteger,unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
            int r2 = unsafe.getInt(atomicInteger2,unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
            int r3 = unsafe.getInt(atomicInteger3,unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
            System.out.println("r1:"+r1+" integer:"+atomicInteger.hashCode()+" offset:"+unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
            System.out.println("r2:"+r2+" integer:"+atomicInteger2.hashCode()+" offset:"+unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
            System.out.println("r3:"+r3+" integer:"+atomicInteger3.hashCode()+" offset:"+unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
