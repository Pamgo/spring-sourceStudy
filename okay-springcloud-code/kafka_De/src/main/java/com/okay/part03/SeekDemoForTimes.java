package com.okay.part03;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 指定时间点消费
 * Created by 朱小厮 on 2018/8/19.
 */
public class SeekDemoForTimes {
    public static final String brokerList = "192.168.1.104:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo1";

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        Set<TopicPartition> assignment = consumer.assignment();
        while (assignment.size() == 0) {
            consumer.poll(200);
            assignment = consumer.assignment();
        }

        Map<TopicPartition, Long> timestampToSearch = new HashMap<>();
        for (TopicPartition tp : assignment) {
            timestampToSearch.put(tp, System.currentTimeMillis() - 1* 24*3600*1000);
        }

        // 获取每个分区的时间点偏移
        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(timestampToSearch);

        for (TopicPartition partition : assignment) { //设置分区的偏移量
            // 设置分区偏移量
            OffsetAndTimestamp offsetAndTimestamp = offsets.get(partition); // 获取分区时间点的偏移俩
            if (offsetAndTimestamp != null) {
                consumer.seek(partition, offsetAndTimestamp.offset());
            }
        }

        while (true) { // 开始消费
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));
            //consume the record.
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.offset() + ":" + record.value());
            }
        }
    }
}
