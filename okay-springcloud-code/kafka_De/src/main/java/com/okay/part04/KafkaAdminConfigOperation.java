package com.okay.part04;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by OKali on 2019/4/6.
 */
public class KafkaAdminConfigOperation {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        describeTopicConfig();
    }

    public static void alterTopicConfig() throws ExecutionException, InterruptedException {
        String brokerList =  "192.168.1.105:9092";
        String topic = "topic-admin";

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        ConfigResource resource =
                new ConfigResource(ConfigResource.Type.TOPIC, topic);
        ConfigEntry entry = new ConfigEntry("cleanup.policy", "compact");
        Config config = new Config(Collections.singleton(entry));
        Map<ConfigResource, Config> configs = new HashMap<>();
        configs.put(resource, config);
        AlterConfigsResult result = client.alterConfigs(configs);
        result.all().get();

        client.close();
    }

    public static void describeTopicConfig() throws ExecutionException,
            InterruptedException {
        String brokerList =  "192.168.1.105:9092";
        String topic = "topic-admin";

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        ConfigResource resource =
                new ConfigResource(ConfigResource.Type.TOPIC, topic);

        DescribeConfigsResult result =
                client.describeConfigs(Collections.singleton(resource));
        Config config = result.all().get().get(resource);
        System.out.println("配置输出："+config);
        client.close();
    }
}
