package com.wzq.java.base.object;

import org.openjdk.jol.info.ClassLayout;

import java.util.stream.IntStream;

public class TestJOL {

    public static void main(String[] args) {
        System.out.println("-object-");
        IntStream.range(0,100).forEach(i->System.out.print("-"));

        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj){
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        System.out.println("-person-");
        IntStream.range(0,100).forEach(i->System.out.print("-"));

        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        synchronized (person){
            System.out.println(ClassLayout.parseInstance(person).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(person).toPrintable());

        System.out.println("-array-");
        IntStream.range(0,100).forEach(i->System.out.print("-"));

        int[] arr = new int[10];
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
        synchronized (arr){
            System.out.println(ClassLayout.parseInstance(arr).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
    }

    static class Person{
        private String name;
        public String id;
        private Integer age;
        public Person(){

        }
    }
}
