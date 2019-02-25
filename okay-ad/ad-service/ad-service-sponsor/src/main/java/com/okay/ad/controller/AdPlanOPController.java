package com.okay.ad.controller;

import com.alibaba.fastjson.JSON;
import com.okay.ad.entity.AdPlan;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.IAdPlanService;
import com.okay.ad.vo.AdPlanGetRequest;
import com.okay.ad.vo.AdPlanRequest;
import com.okay.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by OKali on 2019/1/19.
 */
@Slf4j
@RestController
public class AdPlanOPController {

    @Autowired
    private IAdPlanService adPlanService;

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}",
                JSON.toJSONString(request));

        return adPlanService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds ->{}",
                JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    @PostMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updateAdPlan ->{}",
                JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan->{}",
                JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
}
