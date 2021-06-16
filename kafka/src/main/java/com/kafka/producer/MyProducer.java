package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;


public class MyProducer  {

    private static final String TOPIC = "test";

    private final String BROKER_LIST = "192.168.8.119:9092,192.168.8.120:9092,192.168.8.121:9092";
    private final String SERIALIZER_CLASS = "kafka.serializer.StringEncoder";
    private final String ZK_CONNECT = "192.168.8.119:2181,192.168.8.120:2181,192.168.8.121:2181";
    Properties props;
    Producer<String, String> producer;

    public MyProducer() {
        props = new Properties();
        props.put("zk.connect", ZK_CONNECT);
        props.put("serializer.class", SERIALIZER_CLASS);
        props.put("bootstrap.servers", BROKER_LIST);
        props.put("request.required.acks", "1");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);
    }

    public void publishMessage(String topic) {
            File file = new File("E:\\111.txt");
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                //一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    //显示行号
                    String msg = "line " + line + ": " + tempString;
                    ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, msg);
                    producer.send(data);
                    System.out.println("msg = " + msg);
                    line++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        producer.close();
    }
    public static void main(String[] args) {
        MyProducer mp = new MyProducer();
        mp.publishMessage("test");
    }
}
