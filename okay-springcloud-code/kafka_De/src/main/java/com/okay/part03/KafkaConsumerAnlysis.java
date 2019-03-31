package com.okay.part03;

import kafka.tools.ConsoleConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.glassfish.jersey.internal.util.collection.StringIgnoreCaseKeyComparator;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * 消费者客户端
 * Created by OKali on 2019/3/26.
 */
@Slf4j
public class KafkaConsumerAnlysis {

    public static final String brokerList = "192.168.1.104:9092";
    public static final String topic = "topic-.*"; //
    public static final String groupId = "group.demo";

    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initConfig() {
        Properties kafkaProp = new Properties();
        kafkaProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        kafkaProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        kafkaProp.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // 两个参数结合使用
        kafkaProp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true); // 自动提交，默认为true
        kafkaProp.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000); // 定期自动提交，默认5s

        kafkaProp.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer.client.id.demo");
        return kafkaProp;
    }

    public static void main(String[] args) {
        Properties kafkaProp = initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProp);
        TopicPartition tp = new TopicPartition("topic-demo",0);
        //consumer.subscribe(Pattern.compile(topic)); // 使用正则订阅主题
        consumer.assign(Arrays.asList(tp)); // 订阅主题特定分区
        // 使用partitionsFor()方法定义主题的所有分区
      /*  List<TopicPartition> partitions = new ArrayList<>();
        List<PartitionInfo> partitionInfos = consumer.partitionsFor("topic-demo");
        if (partitionInfos != null) {
            for (PartitionInfo tpInfo : partitionInfos) {
                partitions.add(new TopicPartition(tpInfo.topic(), tpInfo.partition()));
            }
        }
        consumer.assign(partitions);*/

        try {
            long lastConsumedOffset = -1; // 当前消费的位移
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                if (records.isEmpty()) {
                    break;
                }

                List<ConsumerRecord<String, String>> partitionRecords = records.records(tp);
                lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                consumer.commitSync(); // 同步提交消费位移
                System.out.println("consumed offset is " + lastConsumedOffset);
                OffsetAndMetadata offsetAndMetadata = consumer.committed(tp);
                System.out.println("commited offset is " + offsetAndMetadata.offset());
                long position = consumer.position(tp);
                System.out.println("the offset of the next record is " + position);
              /*  for (ConsumerRecord<String, String> record : records) {
                    System.out.println("topic="+record.topic() + ",partition =" + record.partition()
                    + ", offset="+record.offset());
                    System.out.println("key=" + record.key() +", value=" + record.value());
                }*/
              // 获取集合中指定分区的消息
               /* for (TopicPartition tp : records.partitions()) {
                    for (ConsumerRecord<String, String> record : records.records(tp)) {
                        System.out.println("topic="+record.topic() + ",partition =" + record.partition()
                                + ", offset="+record.offset());
                        System.out.println("key=" + record.key() +", value=" + record.value());
                    }
                }*/

            }
        } catch (Exception e) {
            log.error("occur exception", e);
            isRunning.set(false);
        } finally {
            consumer.close();
        }
    }
}
