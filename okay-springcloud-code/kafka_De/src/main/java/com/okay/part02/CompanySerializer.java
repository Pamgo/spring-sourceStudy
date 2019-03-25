package com.okay.part02;

import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * 自定义序列号器
 * Created by OKali on 2019/3/25.
 */
public class CompanySerializer implements Serializer<Company> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Company data) {
        if (data == null) {
            return null;
        }
        byte[] name, address;
        try {
            if (data.getName() != null) {
                name = data.getName().getBytes("UTF-8");
            } else {
                name = new byte[0];
            }
            if (data.getAddress() != null) {
                address = data.getAddress().getBytes("UTF-8");
            } else {
                address = new byte[0];
            }

            ByteBuffer bf = ByteBuffer.allocate(4+4+name.length + address.length);
            bf.putInt(name.length);
            bf.put(name);

            bf.putInt(address.length);
            bf.put(address);
            return bf.array();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
