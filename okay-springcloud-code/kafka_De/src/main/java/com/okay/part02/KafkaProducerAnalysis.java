package com.okay.part02;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by OKali on 2019/3/24.
 */
@Slf4j
public class KafkaProducerAnalysis {

    public static final String brokerList = "192.168.1.105:9092";
    public static final String topic = "topic-demo";

    public static Properties initConfig() {
        Properties kafkaProp = new Properties();
        kafkaProp.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put("bootstrap.servers", brokerList);
        kafkaProp.put("client.id", "producer.client.id.demo");

        return kafkaProp;
    }

    public static Properties initNewConfig() {
        Properties kafkaProp = new Properties();
        kafkaProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        kafkaProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put(ProducerConfig.CLIENT_ID_CONFIG,"producer.client.id.demo");
        return kafkaProp;
    }

    public static Properties initPerferConfig() {
        Properties kafkaProp = new Properties();
        kafkaProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        kafkaProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProp.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        kafkaProp.put(ProducerConfig.RETRIES_CONFIG, 10); // 重试次数
        // 两个拦截器，拦截器链使用英文逗号隔开
        kafkaProp.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                ProducerInterceptorPrefix.class.getName() + "," + ProducerInterceptorPrefixPlus.class.getName());

        // 消息累加器的大小
        kafkaProp.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024L);
        // 分区中的双端队列大小，ByteBuffer，使用BufferPool进行缓存的服用
        kafkaProp.put(ProducerConfig.BATCH_SIZE_CONFIG, 16 * 1024);
        // 客户端域集群node之间的链接最多缓存请求数。（每个请求最多缓存的请求数），
        // 即最多只能缓存5个为响应的请求，超过之后就不能在向这个连接发送更多的请求了，除非有缓存的请求收到了响应
        kafkaProp.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

        return kafkaProp;
    }

    public static void main(String[] args) {
        Properties kafkaProp = initPerferConfig();

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProp);
        try {
            int i = 0;
            while (i < 100) {
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello,kafka!" + i++);
                Future<RecordMetadata> send = producer.send(record);
                RecordMetadata metadata = send.get();
            }
            // 异步发送
           /* producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        exception.printStackTrace();
                    } else {
                        log.info("==>"+metadata.topic()+"-"+metadata.partition()+":"+metadata.offset());
                        System.out.println(metadata.topic()+"-" + metadata.partition()+":" + metadata.offset());
                    }
                }
            });*/

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        producer.close();
    }

}
