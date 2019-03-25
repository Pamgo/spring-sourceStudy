package com.okay.part02;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 生产者拦截器
 *
 * 通过onSend方法为每一条消息添加一个前缀“prefix1-",并通过onAcknowledgement方法来统计发送消息的成功率
 *
 * Created by OKali on 2019/3/25.
 */
public class ProducerInterceptorPrefixPlus implements ProducerInterceptor<String, String> {

    private volatile long sendSuccess = 0;
    private volatile long sendFailure = 0;

    private static final String prefixV = "prefix2-";

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        // 修改消息前缀
        String modifiedValue = prefixV + record.value();
        return new ProducerRecord<String, String>(record.topic(),
                record.partition(),
                record.timestamp(),
                record.key(),
                modifiedValue,
                record.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            sendSuccess ++;
        } else {
            sendFailure ++;
        }

    }

    @Override
    public void close() {
        double successRate = (double)sendSuccess / (sendFailure + sendSuccess);
        System.out.println("[INFO] 发送成功率：" + String.format("%f", successRate * 100) + "%");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
