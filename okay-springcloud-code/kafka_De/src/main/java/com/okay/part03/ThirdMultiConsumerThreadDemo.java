package com.okay.part03;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 将处理消息模块改成多线程的实现方式
 * Created by OKali on 2019/3/31.
 */
public class ThirdMultiConsumerThreadDemo {
    public static final String brokerList = "192.168.1.105:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";
    public static Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        KafkaConsumerThread consumerThread = new KafkaConsumerThread(props, topic,
                Runtime.getRuntime().availableProcessors());
        consumerThread.start();
    }

    public static class KafkaConsumerThread extends Thread {
        private KafkaConsumer<String, String> kafkaConsumer;
        private ExecutorService executorService;
        private int threadNumber;

        public KafkaConsumerThread(Properties props, String topic, int threadNumber) {
            kafkaConsumer = new KafkaConsumer<String, String>(props);
            kafkaConsumer.subscribe(Collections.singletonList(topic));
            this.threadNumber = threadNumber;
            // 创建线程池
            executorService = new ThreadPoolExecutor(threadNumber, threadNumber, 0L,
                    TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(1000),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

                    if (!records.isEmpty()) {
                        // 处理消息
                        executorService.submit(new RecordsHandler(records));
                    }
                    // 提交偏移量
                    synchronized (offsets) {
                        if (!offsets.isEmpty()) {
                            kafkaConsumer.commitSync(offsets);
                            offsets.clear();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                kafkaConsumer.close();
            }
        }
    }

    public static class RecordsHandler extends Thread {
        public final ConsumerRecords<String, String> records;

        public RecordsHandler(ConsumerRecords<String, String> records) {
            this.records = records;
        }

        @Override
        public void run() {
            // 处理records
            for (TopicPartition tp : records.partitions()) {
                List<ConsumerRecord<String, String>> tpRecords = this.records.records(tp);
                // 处理tpRecords
                for (ConsumerRecord<String, String> record : records) {
                    // 处理消息模块
                    System.out.println("开启线程处理消息：" +Thread.currentThread().getName()
                            + ",分区："+ record.partition() + "，偏移量:" + record.offset()
                            +",消费记录：" + record.value());
                }

                // 设置处理消息后的位移
                long lastConsumerOffset = tpRecords.get(tpRecords.size() - 1).offset();
                synchronized (offsets) {
                    if (offsets.containsKey(tp)) {
                        offsets.put(tp, new OffsetAndMetadata(lastConsumerOffset + 1));
                    } else {
                        long position = offsets.get(tp).offset();
                        if (position < lastConsumerOffset + 1) {
                            offsets.put(tp, new OffsetAndMetadata(lastConsumerOffset + 1));
                        }
                    }
                }
            }
        }
    }
}
