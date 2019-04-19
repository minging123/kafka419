package com.kafka.consumer;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.cluster.BrokerEndPoint;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 根据制定的Topic,Partition,offset来获取数据
 */
public class LowerConsumer {

    public static void main(String[] args) {
        //定义参数
        ArrayList<String> brokers = new ArrayList<>();
        brokers.add("192.168.8.119");
        brokers.add("192.168.8.120");
        brokers.add("192.168.8.121");

        //端口号
        int port = 9092;

        //主题
        String topic = "test";

        //分区
        int partition = 0;

        //offset
        long offset = 2;
        LowerConsumer lowerConsumer = new LowerConsumer();
        lowerConsumer.getData(brokers, port, topic, partition, offset);
    }

    //find分区leader
    private BrokerEndPoint findLeader(List<String> brokers, int port, String topic, int partition) {
        for (String broker : brokers) {
            //获取分区leader的消费者对象
            SimpleConsumer getLeader = new SimpleConsumer(broker, port, 1000, 1024 * 4, "getLeader");
            //创建一个主题元数据请求
            TopicMetadataRequest topicMetadataRequest = new TopicMetadataRequest(Collections.singletonList(topic));
            //获取主题元数据返回值
            TopicMetadataResponse topicMetadataResponse = getLeader.send(topicMetadataRequest);
            //解析元数据返回值
            List<TopicMetadata> topicMetadata = topicMetadataResponse.topicsMetadata();
            //遍历主题元数据
            for (TopicMetadata topicMetadatum : topicMetadata) {
                //获取多个分区的元数据信息
                List<PartitionMetadata> partitionMetadata = topicMetadatum.partitionsMetadata();
                //遍历分区元数据
                for (PartitionMetadata partitionMetadatum : partitionMetadata) {
                    if (partition == partitionMetadatum.partitionId()) {
                        return partitionMetadatum.leader();
                    }
                }
            }
        }
        return null;
    }

    //获取数据
    private void getData(List<String> brokers, int port, String topic, int partition, long offset) {
        //获取分区leader
        BrokerEndPoint leader = findLeader(brokers, port, topic, partition);
        if (leader == null) {
            return;
        }
        //获取数据的消费者对象
        SimpleConsumer getData = new SimpleConsumer(leader.host(), port, 1000, 1024 * 4, "getData");
        //创建获取数据的对象
        FetchRequest fetchRequest = new FetchRequestBuilder().addFetch(topic, partition, offset, 5000).build();
        //获取返回值
        FetchResponse fetchResponse = getData.fetch(fetchRequest);
        //解析返回值
        ByteBufferMessageSet messageAndOffsets = fetchResponse.messageSet(topic, partition);
        for (MessageAndOffset messageAndOffset : messageAndOffsets) {
            long offset1 = messageAndOffset.offset();
            ByteBuffer payload = messageAndOffset.message().payload();
            byte[] bytes = new byte[payload.limit()];
            payload.get(bytes);
            System.out.println(offset1 + "--" + new String(bytes));
        }
    }
}
