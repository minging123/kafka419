package com.kafka.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

public class KafkaStream{
    public static void main(String[] args) {
        //创建拓扑对象
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //创建配置文件
        Properties properties=new Properties();
        properties.put("bootstrap.servers","192.168.8.119:9092");
        properties.put("application.id","kafkaStream");
        //构建拓扑结构
        topologyBuilder.addSource("SOURCE","test").addProcessor("PROCESSOR", new ProcessorSupplier() {
            @Override
            public Processor get() {
                return new LogProcessor(){
                };
            }
        },"SOURCE")
                .addSink("SINK","test","PROCESSOR");
        KafkaStreams kafkaStreams = new KafkaStreams(topologyBuilder, properties);
        kafkaStreams.start();
    }
}
