package com.okay.part02;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by OKali on 2019/3/24.
 */
@Slf4j
public class KafkaProducerAnalysis {

    public static final String brokerList = "192.168.1.104:9092";
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
        return kafkaProp;
    }

    public static void main(String[] args) {
        Properties kafkaProp = initPerferConfig();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProp);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello,kafka!");
        try {
            Future<RecordMetadata> send = producer.send(record);
            RecordMetadata metadata = send.get();
            log.info(metadata.topic()+"-"+metadata.partition()+":"+metadata.offset());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
