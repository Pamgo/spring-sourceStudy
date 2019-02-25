package com.okay.ad.index.interest;

import com.okay.ad.index.IndexAware;
import com.okay.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by Qinyi.
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    // <itTag, adUnitId set> 兴趣标签->推广单元
    private static Map<String, Set<Long>> itUnitMap;

    // <unitId, itTag set>  推广单元->兴趣标签
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitItIndex, before add: {}", unitItMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {

            Set<String> its = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            its.add(key);
        }

        log.info("UnitItIndex, after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitItIndex, before delete: {}", unitItMap);

        /**
         * 部分删除
         // 先根据key把itUnitMap对应的unitIds拿出来，
         // 然后对unitids进行removeAll，将传入进来的value给移除出去
         // 为什么不直接从itUnitMap直接移除这个key呢？
         // 这是因为key对应的推广单元unitIds不是全量的
         // 我们可以删除部分，就是删除一部分兴趣，比如有一个itTag对应到的推广单元对应是1,2,3，而我们key对应的是推广
         // 单元的1,2,而不删除3.所以我们需要先把key对应的unitIds取出来再进行
         // 删除1和2，保留3
         */
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> itTagSet = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.remove(key);
        }

        log.info("UnitItIndex, after delete: {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> itTags) {

        if (unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {

            Set<String> itTagSet = unitItMap.get(unitId);

            return CollectionUtils.isSubCollection(itTags, itTagSet);
        }

        return false;
    }
}
