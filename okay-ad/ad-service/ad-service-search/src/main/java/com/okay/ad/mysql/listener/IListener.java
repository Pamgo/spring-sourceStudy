package com.okay.ad.mysql.listener;

import com.okay.ad.mysql.dto.BinlogRowData;

/**
 * Created by OKali on 2019/2/1.
 */
public interface IListener {

    /**
     * 不同的表定义不同的更新方法
     */
    void register();

    /**
     * 增量数据的索引的更新
     * @param eventData
     */
    void onEvent(BinlogRowData eventData);
}
