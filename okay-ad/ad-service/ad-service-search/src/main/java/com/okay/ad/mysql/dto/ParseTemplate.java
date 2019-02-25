package com.okay.ad.mysql.dto;

import com.okay.ad.mysql.constant.OpTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by OKali on 2019/2/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParseTemplate {

    private String database;

    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());

        for (JsonTable table : _template.getTableList()) {

            String name = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name, tableTemplate);

            // 遍历操作类型对应的列
            Map<OpTypeEnum, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column: table.getInsert()) {
                getAndCreateIfNeed(
                        OpTypeEnum.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

            for (JsonTable.Column column: table.getUpdate()) {
                getAndCreateIfNeed(
                        OpTypeEnum.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

            for (JsonTable.Column column: table.getDelete()) {
                getAndCreateIfNeed(
                        OpTypeEnum.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return template;
    }

    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map,
                                               Supplier<R> factory) {
        // 如果key获取map的值不存在，通过factory去创建一个对象
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
