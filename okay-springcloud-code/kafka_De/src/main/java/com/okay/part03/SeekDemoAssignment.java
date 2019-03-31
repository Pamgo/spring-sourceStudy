package com.okay.part03;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 对于第一个poll方法要设置多少时间比较合适，用下面方式可以解决，分区分配不及时问题
 * Created by 朱小厮 on 2018/8/19.
 */
public class SeekDemoAssignment {
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
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, ConsumerInterceptorTTL.class.getName());
        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        long start = System.currentTimeMillis();
        Set<TopicPartition> assignment = new HashSet<>();

        while (assignment.size() == 0) {  // 判断分区集合是否为空
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }

        long end = System.currentTimeMillis();
        System.out.println("第一次poll拉取消息内部分配分区耗费时间：" + (end - start));
        System.out.println("分区集合：" + assignment);
        for (TopicPartition tp : assignment) { // 为分区集合设置分区偏移量
            System.out.println("开始设置分区偏移量为10--》》》");
            consumer.seek(tp, 10);
        }
        while (true) {
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));
            //consume the record.
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("消费记录：" + record.offset() + ":" + record.value());
            }
        }
    }
}
