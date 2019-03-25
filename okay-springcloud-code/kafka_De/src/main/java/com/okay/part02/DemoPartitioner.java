package com.okay.part02;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义分区器实现
 * 默认分区器在key为null时不会选择非可用分区，但是可以用自定义分区器来打破这一局限
 * Created by OKali on 2019/3/25.
 */
public class DemoPartitioner implements Partitioner {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);

        int partitionsNum = partitionInfos.size();
        if (null == keyBytes) {
            return counter.getAndIncrement() % partitionsNum;
        } else {
            return Utils.toPositive(Utils.murmur2(keyBytes)) % partitionsNum;
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
