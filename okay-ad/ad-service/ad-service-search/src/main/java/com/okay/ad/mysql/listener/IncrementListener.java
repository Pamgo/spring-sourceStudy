package com.okay.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.okay.ad.mysql.constant.Constant;
import com.okay.ad.mysql.constant.OpTypeEnum;
import com.okay.ad.mysql.dto.BinlogRowData;
import com.okay.ad.mysql.dto.MySqlRowData;
import com.okay.ad.mysql.dto.TableTemplate;
import com.okay.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OKali on 2019/2/1.
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource(name = "indexSender")
    private ISender sender;

    @Autowired
    private AggregationListener aggregationListener;

    /**
     * 将表注册
     */
    @Override
    @PostConstruct
    public void register() {

        log.info("incrementListener register db and table info");
        Constant.table2Db.forEach((k, v) ->
                aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpTypeEnum opType = OpTypeEnum.to(eventType);
        rowData.setOpType(opType);

        // 取出模版中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }
        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry: afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
