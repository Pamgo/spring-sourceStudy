package com.okay.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by OKali on 2019/3/23.
 */
public class TestKafka01 {

    private Properties kafkaProp = new Properties();

    private KafkaProducer<String, String> producer;

    public void init() {
        kafkaProp.put("boostrap.servers","broker1:9092,broker2:9092");
        kafkaProp.put("key.serialier", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put("value.serialier", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(kafkaProp);
    }
    // 同步发送消息
    public void sendSyncMsg() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>("customTopic","productsKey","FranceValue");
        Future<RecordMetadata> send = producer.send(record);// 发送消息

        //producer.send(record).get(); // 同步发送，使用get等待结果返回
    }

    // 异步发送消息
    public void sendAsyncMsg() {
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>("customTopic","productsKey","FranceValue");
        producer.send(record, new DemoProducerCallback()); // 异步发送
    }

}
