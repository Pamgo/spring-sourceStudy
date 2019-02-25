package com.okay.ad.index;

import com.alibaba.fastjson.JSON;
import com.okay.ad.dump.DConstant;
import com.okay.ad.dump.table.*;
import com.okay.ad.handler.AdLevelDataHandler;
import com.okay.ad.index.adunit.AdUnitIndex;
import com.okay.ad.mysql.constant.OpTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by OKali on 2019/1/29.
 */
@Component
@DependsOn("dataTable") // 保证dataTable先初始化完再初始化自己（由于dataTable优先级以及是最高了，这里可不加，只是为了再次约束）
public class IndexFileLoader {

    /**
     * 加载顺序不能变，必须第一层级到第二层级。。。
     */
    @PostConstruct
    public void init() {

        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpTypeEnum.ADD
        ));
        //
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdCreativeTable.class),
                OpTypeEnum.ADD
        ));
        //
        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(u, AdUnitTable.class),
                OpTypeEnum.ADD
        ));
        //
        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_CREATIVE)
        );
        adCreativeUnitStrings.forEach(cu -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(cu, AdCreativeUnitTable.class),
                OpTypeEnum.ADD
        ));
        //
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(ud -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(ud, AdUnitDistrictTable.class),
                OpTypeEnum.ADD
        ));
        //
        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        adUnitItStrings.forEach(ui -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(ui, AdUnitItTable.class),
                OpTypeEnum.ADD
        ));
        //
        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(uk -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(uk, AdUnitKeywordTable.class),
                OpTypeEnum.ADD
        ));
    }

    private List<String> loadDumpData(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )){
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
