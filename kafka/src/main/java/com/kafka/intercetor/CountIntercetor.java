package com.kafka.intercetor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CountIntercetor implements ProducerInterceptor<String, String> {
    private int sccessCount = 0;
    private int errorCount = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            sccessCount++;
        } else {
            errorCount++;
        }
    }

    @Override
    public void close() {
        System.out.println("发送成功" + ":" + sccessCount + "条数据");
        System.out.println("发送失败" + ":" + errorCount + "条数据");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
