package com.wzq.java.base.reference;

/**
 * @author wangzq
 * @date 2020/9/4 11:33
 */
public class R1_NormalReference {

    static class MyObject{
        @Override
        protected void finalize(){
            System.out.println("myObject gc..");
        }
    }
    public static void main(String[] args) {
        MyObject obj = new MyObject();
        // 如果不置null 不会gc，此为强
        // obj = null;
        System.gc();
        System.out.println(obj);
    }
}
