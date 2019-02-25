package com.okay.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 将binlog打印的日志转换成java对象
 * Created by OKali on 2019/2/1.
 */
@Data
public class BinlogRowData {

    private TableTemplate table;

    private EventType eventType;

    private List<Map<String, String>> after;
    private List<Map<String, String>> before;

}
