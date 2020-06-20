package com.wzq.java.base;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Collections;

/**
 * @author wangzq
 * @date 2020/6/19 0:19
 */
@Slf4j
public class DeepCopy implements Serializable{

    public static void main(String[] args) {
        DeepCopy deepCopy = new DeepCopy();
        deepCopy.testShallow();
        deepCopy.testDeep();
        deepCopy.testSerialize();
    }

    /**
     * 初始化sun wukong
     * @return
     */
    MonkeySun initSun() {
        MonkeySun sun = new MonkeySun();
        sun.setName("wu kong");
        KeyStand keyStand = new KeyStand();
        keyStand.setWeight(1L);
        sun.setKeyStand(keyStand);
        return sun;
    }

    void testShallow() {
        log.info(String.join("-", Collections.nCopies(16,"-")));
        log.info("---- test :{} ----",Thread.currentThread().getStackTrace()[1].getMethodName());
        MonkeySun sun = initSun();
        log.info("sun info:{}", sun.toString());
        MonkeySun earsSix = sun;
        printMonkeysInfo(sun, earsSix);
        changeKeyStandAndPrint(sun, earsSix);
    }

    void testDeep() {
        log.info(String.join("-", Collections.nCopies(16,"-")));
        log.info("---- test :{} ----",Thread.currentThread().getStackTrace()[1].getMethodName());
        MonkeySun sun = initSun();
        MonkeySun earsSix = sun.clone();
        // properties object or field object clone
        earsSix.keyStand = sun.keyStand.clone();
        printMonkeysInfo(sun, earsSix);
        changeKeyStandAndPrint(sun, earsSix);
    }

    void testSerialize() {
        log.info(String.join("-", Collections.nCopies(16,"-")));
        log.info("------ test :{} ------",Thread.currentThread().getStackTrace()[1].getMethodName());
        MonkeySun sun = initSun();
        MonkeySun earsSix = null;
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // SerializationUtils.clone(sun);
            objectOutputStream.writeObject(sun);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            earsSix = (MonkeySun) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        printMonkeysInfo(sun, earsSix);
        changeKeyStandAndPrint(sun, earsSix);
    }

    void printMonkeysInfo(MonkeySun sun, MonkeySun earsSix) {
        log.info("earsSix copy from sun.\n");
        log.info("{} has key stand {} KG", sun.name, sun.keyStand.weight);
        log.info("{} has key stand {} KG", earsSix.name, earsSix.keyStand.weight);
        log.info("sun == earsSix:{}",sun == earsSix);
        log.info("sun eq earsSix:{}",sun.equals(earsSix));
    }

    void changeKeyStandAndPrint(MonkeySun sun, MonkeySun earsSix) {
        sun.setName("齐天大圣");
        log.info("sun's name set 齐天大圣");

        sun.keyStand.setWeight(10_0000L);
        log.info("sun's keyStand set weight 10_0000\n");

        log.info("{} has key stand {} KG", sun.name, sun.keyStand.weight);
        log.info("{} has key stand {} KG", earsSix.name, earsSix.keyStand.weight);
        log.info("sun == earsSix:{}",sun == earsSix);
        log.info("sun eq earsSix:{}\n",sun.equals(earsSix));
    }

    @Data
    @ToString
    class MonkeySun implements Cloneable,Serializable {
        String name;
        KeyStand keyStand;

        @Override
        protected MonkeySun clone() {
            MonkeySun sun = null;
            try {
                sun = (MonkeySun) super.clone();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return sun;
        }
    }

    @Data
    class KeyStand implements Cloneable,Serializable{
        private Long weight;

        @Override
        protected KeyStand clone(){
            KeyStand keyStand = null;
            try {
                keyStand = (KeyStand)super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return keyStand;
        }
    }
}
