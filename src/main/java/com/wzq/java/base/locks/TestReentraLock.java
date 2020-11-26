package com.wzq.java.base.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentraLock {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Condition condition = lock.newCondition();
        
        new Thread(()-> System.out.println("lock"));
        lock.lock();

        lock.unlock();
    }
}
