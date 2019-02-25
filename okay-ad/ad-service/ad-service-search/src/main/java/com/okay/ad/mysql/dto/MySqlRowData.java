package com.okay.ad.mysql.dto;

import com.okay.ad.mysql.constant.OpTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by OKali on 2019/2/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;

    private String level; // 数据表层级

    private OpTypeEnum opType;

    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
