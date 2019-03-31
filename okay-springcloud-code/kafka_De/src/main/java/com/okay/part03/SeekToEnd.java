package com.okay.part03;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 使用seek()方法从分区末尾消费
 * Created by okali .
 */
public class SeekToEnd {
    public static final String brokerList = "192.168.1.104:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";

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
        Set<TopicPartition> assignment = new HashSet<>();
        while (assignment.size() == 0) { // 保存poll方法内部已经分配了分区
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }

        Map<TopicPartition, Long> offsets = consumer.endOffsets(assignment); // 获取所有分区的末尾
        for (TopicPartition tp : assignment) {
            System.out.println("设置到分区末尾-》");
            //consumer.seek(tp, offsets.get(tp));
            consumer.seek(tp, offsets.get(tp) + 1); // 位移越界，客户端或报越界
        }
        System.out.println("输出分区集合和偏移量");
        System.out.println(assignment);
        System.out.println(offsets);

        while (true) {
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));
            //consume the record.
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.offset() + ":" + record.value());
            }
        }

    }
}
