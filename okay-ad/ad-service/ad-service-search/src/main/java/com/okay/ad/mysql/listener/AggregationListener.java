package com.okay.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.okay.ad.mysql.TemplateHolder;
import com.okay.ad.mysql.dto.BinlogRowData;
import com.okay.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by OKali on 2019/2/1.
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName; // 数据库
    private String tableName; // 表

    // 表/数据库 -->监听方法
    private Map<String, IListener> listenerMap = new HashMap<>();
    @Autowired
    private TemplateHolder templateHolder;

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 对外提供注册方法
     * @param _dbName 数据库名
     * @param _tableName 表名
     * @param listener 监听
     */
    public void register(String _dbName, String _tableName, IListener listener) {
        log.info("register : {}-{}", _dbName, _tableName);
        this.listenerMap.put(genKey(_dbName, _tableName), listener);
    }

    /**
     * 实现对binlog的监听，监听之后
     * 获取到对表有兴趣的监听器（对binlog的处理器）
     * 之后对event的getData转换为我们定义的java对象rowData，
     * 然后将rowData传递给感兴趣的处理器进行处理(对应我们的检索服务其实就是对于增量数据的更新)
     * @param event
     */
    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType(); // type类型
        log.debug("event type: {}", type);
        if (type == EventType.TABLE_MAP) { // TABLE_MAP 包括数据库和数据表的数据
            TableMapEventData data = event.getData(); //
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 表名和库名是否以及完成了填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        String key = genKey(this.dbName, this.tableName);
        IListener listener = this.listenerMap.get(key);
        if (null == listener) {
            log.debug("skip {}", key);
            return;
        }
        log.info("trigger event: {}", type.name());

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
        } finally { // 清空
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData)eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    /**
     * 将eventdata转换成binlogRowData
     * @param eventData
     * @return
     */
    private BinlogRowData buildRowData(EventData eventData) {

        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table) {
            log.warn("table {} not found", tableName);
        }
        List<Map<String, String>> afterMapList =  new ArrayList<>();

        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int collen = after.length;
            for (int ix = 0; ix < collen; ix ++) {
                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(ix);

                // 如果没有则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore position: {}", ix);
                    continue;
                }

                String colValue = after[ix].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
