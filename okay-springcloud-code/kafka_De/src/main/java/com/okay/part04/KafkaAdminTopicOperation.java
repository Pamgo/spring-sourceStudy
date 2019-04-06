package com.okay.part04;

import org.apache.kafka.clients.admin.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 使用kafkaAdminClient创建主题
 * Created by OKali on 2019/4/6.
 */
public class KafkaAdminTopicOperation {

    public static final String brokerList = "192.168.1.105:9092";
    public static final String topic = "topic-admin";

    public static void describeTopic(){
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        DescribeTopicsResult result = client.describeTopics(Collections.singleton(topic));
        try {
            Map<String, TopicDescription> descriptionMap =  result.all().get();
            System.out.println(descriptionMap.get(topic));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        client.close();
    }

    public static void createTopic() {

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        Map<String, String> configs = new HashMap<>();
        configs.put("cleanup.policy", "compact"); // 指定需要覆盖的配置

        // 方式二
        Map<Integer, List<Integer>> replicasAssignments = new HashMap<>();
        replicasAssignments.put(0, Arrays.asList(0));
        replicasAssignments.put(1, Arrays.asList(0));
        replicasAssignments.put(2, Arrays.asList(0));
        replicasAssignments.put(3, Arrays.asList(0));
        NewTopic newTopic = new NewTopic(topic,replicasAssignments);
        newTopic.configs(configs);
        // 方式一
        //NewTopic newTopic = new NewTopic(topic, 4, (short) 1);

        CreateTopicsResult result = client.createTopics(Collections.singletonList(newTopic));

        try {
            result.all().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        client.close();
    }

    public static void deleteTopic(){

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        try {
            client.deleteTopics(Collections.singleton(topic)).all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        client.close();
    }

    public static void main(String[] args) {
        //createTopic();
        //describeTopic();
        deleteTopic();
    }
}
