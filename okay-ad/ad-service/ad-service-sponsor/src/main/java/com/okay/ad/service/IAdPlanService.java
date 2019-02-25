package com.okay.ad.service;

import com.okay.ad.entity.AdPlan;
import com.okay.ad.exception.AdException;
import com.okay.ad.vo.AdPlanGetRequest;
import com.okay.ad.vo.AdPlanRequest;
import com.okay.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * Created by OKali on 2019/1/19.
 */
public interface IAdPlanService {

    /**
     * 创建推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
