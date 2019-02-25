package com.okay.ad.index.keyword;

import com.okay.ad.index.IndexAware;
import com.okay.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by OKali on 2019/1/24.
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>>{

    // keyword->unit的，关键词到推广单元的映射
    private static Map<String, Set<Long>> keywordUnitMap;
    // unit->keyword的，正向索引，推广单元id到keyword的映射
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    /**
     * 根据关键次获取推广单元Id集合
     * @param key 关键词
     * @return
     */
    @Override
    public Set<Long> get(String key) {

        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = keywordUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }

        return result;
    }

    /**
     *  添加推广单元和关键词的正反向索引
     * @param key  关键词
     * @param value 推广单元集合
     */
    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitKeywordIndex, before add: {}", unitKeywordMap);

        Set<Long> unitIdSet = CommonUtils.getorCreate(
                key, keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId, unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.add(key);
        }

        log.info("UnitKeywordIndex, after add: {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("keyword index can not support update");
    }

    /**
     * 根据关键词删除对应的推广单元集合
     * @param key  关键字
     * @param value  推广单元id集合
     */
    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitKeywordIndex, before delete: {}", unitKeywordMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);  // 删除关键词对应的推广单元id集合包含的推广单元id

        for (Long unitId : value) {

            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId, unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.remove(key); // 删除推广单元对应的关键词里的关键词
        }

        log.info("UnitKeywordIndex, after delete: {}", unitKeywordMap);
    }

    /**
     *  根据推广单元id去匹配该推广单元对应的关键词里的传入关键词是否匹配成功
     * @param unitId  推广单元
     * @param keywords 关键词
     * @return
     */
    public boolean match(Long unitId, List<String> keywords) {

        if (unitKeywordMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {

            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            // 当keywords是unitKeywords的子集的时候返回true
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }

        return false;
    }
}
