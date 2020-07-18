package com.wzq.java.base;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author: create by wangzq
 * @version: v1.0
 * @date:2020/6/20 23:32
 */
@Slf4j
public class QuickSimpleDateFormat {

    public static void main(String[] args) {
        QuickSimpleDateFormat format = new QuickSimpleDateFormat();
        format.multiThreadSDF();
    }

    @SuppressWarnings({"AlibabaThreadPoolCreation"})
    private void multiThreadSDF() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = "2020-06-20";
        IntStream.range(1, 100).forEach(i -> {
            executorService.submit((Runnable) () -> {
                try {
                    Date afterParse = sdf.parse(dateStr);
                    String afterFormat = sdf.format(afterParse);
                    log.info("afterParse:{}, afterFormat:{}", afterParse, afterFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        });
        executorService.shutdown();
        // 出现非预期效果是因为，sdf中有calendar成员变量，parse format等操作都会使用。并发自然出问题。
        //解决方式 ： 1. 使用局部变量sdf  2. 共享时候加锁  3. ThreadLocal(当一个线程多次使用时，可以考虑加tl) 4. 使用DateTimeFormatter

        //sdf 是在创建时候，initializeCalendar方法初始化了calendar。 dateTimeFormatter是每次使用了
    }
}
