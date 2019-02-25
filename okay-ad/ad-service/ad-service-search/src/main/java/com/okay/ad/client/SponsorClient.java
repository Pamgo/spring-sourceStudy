package com.okay.ad.client;

import com.okay.ad.client.vo.AdPlanGetRequest;
import com.okay.ad.client.vo.AdPlanResponse;
import com.okay.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 使用feign调用微服务
 * Created by OKali on 2019/1/20.
 */
@FeignClient(value = "eureka-client-ad-sponsor",
            fallback = SponsorClientHystrix.class)  // 实现断路器，服务降级
public interface SponsorClient {

    /**
     * value表示需要调用的哪个微服务
     * @param request
     * @return
     */
    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = {RequestMethod.POST})
    CommonResponse<List<AdPlanResponse>> getAdPlansByFeign(
           @RequestBody AdPlanGetRequest request
    );
}
