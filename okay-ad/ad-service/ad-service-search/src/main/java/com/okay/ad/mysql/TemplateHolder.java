package com.okay.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.okay.ad.mysql.constant.OpTypeEnum;
import com.okay.ad.mysql.dto.ParseTemplate;
import com.okay.ad.mysql.dto.TableTemplate;
import com.okay.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by OKali on 2019/2/1.
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;

    private String SQL_SCHEMA = "select table_schema, table_name, " +
            "    column_name, ordinal_osition \n" +
            "    from information_schema.columns where table_schema = ? and \n" +
            "    table_name = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() { // 默认容器加载的时候执行
        loadJson("template.json");
    }

    /**
     * 对外服务方法
     * @param tableName 表名
     * @return
     */
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) { // 载入模版文件解析
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);

        try {
            Template template = JSON.parseObject(inStream, Charset.defaultCharset(),
                    Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error("获取模版文件失败" + e.getMessage(), e);
            throw new RuntimeException("fail to parse json file");
        }

    }

    private void loadMeta() {  // 索引到列名的映射关系，通过sql语句实现
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpTypeEnum.UPDATE);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpTypeEnum.DELETE);
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpTypeEnum.ADD);
            jdbcTemplate.query(SQL_SCHEMA, new Object[] {
                    template.getDatabase(), table.getTableName()
            },(rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if ((null != updateFields && updateFields.contains(colName))
                        || (null != insertFields && insertFields.contains(colName))
                        || (null != deleteFields && deleteFields.contains(colName))) {
                    table.getPosMap().put((pos - 1), colName);
                }

                return null;
            });
        }
    }

}
