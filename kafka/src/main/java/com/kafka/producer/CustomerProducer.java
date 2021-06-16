package com.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.Properties;

public class CustomerProducer {
    public static void main(String[] args) {
        //配置信息
        Properties props = new Properties();
        //kafka集群
        props.put("bootstrap.servers", "192.168.8.119:9092");
        //应答级别
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        //重试次数
        props.put("retries", 0);
        //批量大小
        props.put("batch.size", 16384);
        //提交延迟
        props.put("linger.ms", 1);
        //缓存
        props.put("buffer.memory", 33554432);
        //kv的序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //自定义分区
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.kafka.producer.CustomerPatitioner");
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("com.kafka.intercetor.TimeIntercetor");
        arrayList.add("com.kafka.intercetor.CountIntercetor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, arrayList);
        //创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++)
            producer.send(new ProducerRecord<String, String>("test1", String.valueOf(i)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println(metadata.partition() + "--" + metadata.offset());
                    } else {
                        System.out.println("发送失败");
                    }
                }
            });
        producer.close();
    }
}
