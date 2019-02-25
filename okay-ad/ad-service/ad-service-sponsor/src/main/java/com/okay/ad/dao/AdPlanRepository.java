package com.okay.ad.dao;

import com.okay.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by OKali on 2019/1/19.
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * 根据id和userId查询
     * @param id
     * @param userId
     * @return
     */
    AdPlan findByIdAndUserId(Long id, Long userId);

    /**
     * 根据id集合和用户id
     * @param ids
     * @return
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 根据用户id和计划名称
     * @param userId
     * @param planName
     * @return
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    List<AdPlan> findAllByPlanStatus(Integer status);
}
