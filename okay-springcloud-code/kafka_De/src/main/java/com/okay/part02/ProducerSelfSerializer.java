package com.okay.part02;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by OKali on 2019/3/25.
 */
public class ProducerSelfSerializer {

    public static final String brokerList = "192.168.1.104:9092";
    public static final String topic = "topic-demo";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties kafkaProp = new Properties();
        kafkaProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());

        kafkaProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        KafkaProducer<String, Company> producer = new KafkaProducer<String, Company>(kafkaProp);

        Company company = Company.builder().name("byteRun").address("广州").build();

        ProducerRecord<String, Company> record = new ProducerRecord<String, Company>(topic, company);

        producer.send(record).get();
    }
}
