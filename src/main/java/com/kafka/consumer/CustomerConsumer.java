package com.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Properties;

public class CustomerConsumer {


    public static void main(String[] args) {

        Properties props = new Properties();
        //kafka集群
        props.put("bootstrap.servers", "192.168.8.120:9092");
        //消费者组id
        props.put("group.id", "test1");
        //换组重复消费topic
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //设置自动提交offset
        props.put("enable.auto.commit", "true");
        //提交延时
        props.put("auto.commit.interval.ms", "1000");
        //kv的反序列化
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//      consumer.subscribe(Arrays.asList("test"));
//        consumer.subscribe(Collections.singletonList("test"));
        //重复消费topic
        consumer.assign(Collections.singletonList(new TopicPartition("test1", 0)));
        consumer.seek(new TopicPartition("test1", 0), 2);
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
            for (ConsumerRecord<String, String> record : consumerRecords) {
                System.out.println(record.topic() + "--" + record.partition() + "--" + record.value());
            }
        }
    }
}
