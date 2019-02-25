package com.okay.ad.index;

/**
 * V代表返回值，K代表键
 * Created by OKali on 2019/1/20.
 */
public interface IndexAware<K, V> {

    /**
     * 获取索引
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 添加索引
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 更新索引
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除索引
     * @param key
     * @param value
     */
    void delete(K key, V value);

}
