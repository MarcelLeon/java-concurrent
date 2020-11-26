package com.wzq.java.base.serializable;

import com.wzq.java.base.serializable.avro.User;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

public class AvroDemo {
    public static void main(String[] args) {
        // first create user1.avsc file and define user1 schema
        // second generate Pojo by avro-tools.  for-example:  java -jar  /Users/xiaoxu/Downloads/avro-tools-1.10.0.jar  compile schema user1.avsc .

        // serialized
        User user1 = new User();
        user1.setName("TestUser1");
        user1.setFavoriteNumber(111);

        User user2 = User.newBuilder().setName("TestUser").setFavoriteColor("BLUE").setFavoriteNumber(6).build();

        User user3 = new User("TestUser3",7,"YELLOW");

        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        try (DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter)){
            dataFileWriter.create(user1.getSchema(),new File("/Users/xiaoxu/IdeaProjects/java-concurrent/src/main/java/com/wzq/java/base/serializable/User.avro"));
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // deserializable
        User user = null;
        try {
            DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class); // 两种构造  一个传class一个传schema
            DataFileReader<User> dataFileReader = new DataFileReader<User>(new File("/Users/xiaoxu/IdeaProjects/java-concurrent/src/main/java/com/wzq/java/base/serializable/User.avro"),userDatumReader);

            while (dataFileReader.hasNext()){
                user = dataFileReader.next(user);
                System.out.println(user.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        IntStream.range(0,100).forEach(i-> System.out.print("-"));
        System.out.println();
        /**
         * 在没有对象代码的情况下 序列和反序列化
         * 用什么取替代对象？ Schema+GenericRecord  方式如下...
         */
        Schema schema = null;
        try {
            schema = new Schema.Parser().parse(new File("/Users/xiaoxu/IdeaProjects/java-concurrent/src/main/java/user.avsc"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GenericRecord record = new GenericData.Record(schema);  // 就俩构造函数，还有一个是通过已有record 深拷贝
        // 首先我们需要一个schema avsc文件，是前提，通过GenericData生成GenericRecord后如何执行序列、反序列化呢？
        // 把record当成map 操作即可
        record.put("name","schema");
        record.put("favorite_color","black");
        // 序列化方式一样
        DatumWriter<GenericRecord> userDatumWriter2 = new SpecificDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(userDatumWriter2)){
            dataFileWriter.create(schema, new File("/Users/xiaoxu/IdeaProjects/java-concurrent/src/main/java/com/wzq/java/base/serializable/User2.avro"));
            dataFileWriter.append(record);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 反序列化方式一样
        GenericRecord userMap = null;
        try {
            DatumReader<GenericRecord> userDatumReader2 = new GenericDatumReader<>(schema);
            DataFileReader<GenericRecord> dataFileReader2 = new DataFileReader<GenericRecord>(new File("/Users/xiaoxu/IdeaProjects/java-concurrent/src/main/java/com/wzq/java/base/serializable/User2.avro"),userDatumReader2);

            while (dataFileReader2.hasNext()){
                userMap = dataFileReader2.next(userMap);
                System.out.println("2rd...".concat(userMap.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
