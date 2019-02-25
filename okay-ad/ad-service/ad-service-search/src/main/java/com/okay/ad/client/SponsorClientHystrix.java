package com.okay.ad.client;

import com.okay.ad.client.vo.AdPlanGetRequest;
import com.okay.ad.client.vo.AdPlanResponse;
import com.okay.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 断路器,需要和feign绑定
 * Created by OKali on 2019/1/20.
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlanResponse>> getAdPlansByFeign(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
