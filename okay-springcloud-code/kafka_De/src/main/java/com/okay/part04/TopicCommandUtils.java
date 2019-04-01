package com.okay.part04;

/**
 * 使用topicCommand创建主题
 * Created by OKali on 2019/4/1.
 */
public class TopicCommandUtils {

    public static void main(String[] args) {
        //createTopic();
        //describeTopic();
        listTopic();
    }

    // 创建一个分区为1，副本因子为1的主题topic-create-api
    public static void createTopic(){
        String[] options = new String[]{
                "--zookeeper", "192.168.1.104:2181/kafka",
                "--create",
                "--replication-factor", "1",
                "--partitions", "1",
                "--topic", "topic-create-api"
        };
        kafka.admin.TopicCommand.main(options);
    }

    // 查看主题信息
    public static void describeTopic(){
        String[] options = new String[]{
                "--zookeeper", "192.168.1.104:2181/kafka",
                "--describe",
                "--topic", "topic-create"
        };
        kafka.admin.TopicCommand.main(options);
    }

    // 列出所有主题信息
    public static void listTopic(){
        String[] options = new String[]{
                "--zookeeper", "192.168.1.104:2181/kafka",
                "--list"
        };
        kafka.admin.TopicCommand.main(options);
    }
}
