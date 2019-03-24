package com.okay.part01;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by OKali on 2019/3/24.
 */
public class ConsumerFastStart {

    public static final String brokerList = "192.168.1.104:9092";

    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties kafkaProp = new Properties();
        kafkaProp.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProp.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaProp.put("bootstrap.servers", brokerList);
        // 设置消费组的名称
        kafkaProp.put("group.id", groupId);

        // 创建一个消费者客户端实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProp);

        consumer.subscribe(Collections.singletonList(topic));
        // 订阅主题

        // 循环消费消息
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000).getSeconds());
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("====>" + record.value());
            }
        }

    }
}
