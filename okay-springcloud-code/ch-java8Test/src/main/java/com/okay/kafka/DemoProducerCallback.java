package com.okay.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Created by OKali on 2019/3/24.
 * 异步发送消息，实现kafka的callback
 */
public class DemoProducerCallback implements Callback {


    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            // TODO 可以在这里做一些事情
            e.printStackTrace();
        }
    }
}
