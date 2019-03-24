package com.okay.kafka;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Created by OKali on 2019/3/24.
 * 不推荐使用自定义序列化器
 */
public class CustomerSerializer implements Serializer<Customer> {


    public void configure(Map map, boolean b) {
        // DO NOTHING
    }

    /**
     * Customer对象被序列化成：
     * 表示cutoemrid的4字节整数
     * 表示cutomername长度的4字节整数（如果cutomername为空，则长度为0）
     * 标识cutomername的N个字节
     * @param topic
     * @param data
     * @return
     */
    public byte[] serialize(String topic, Customer data) {

        try {
             byte[] serializedName;
             int stringSize;

             if (data == null) {
                 return null;
             } else {
                 if (data.getCustomerName() != null) {
                     serializedName = data.getCustomerName().getBytes("UTF-8");
                     stringSize = serializedName.length;
                 } else {
                     serializedName = new byte[0];
                     stringSize = 0;
                 }
             }

            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
            buffer.putInt(data.getCustomerId());
            buffer.putInt(stringSize);
            buffer.put(serializedName);

            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Customer to byte[]" + e);
        }
    }

    public void close() {
        // donothing
    }
}
