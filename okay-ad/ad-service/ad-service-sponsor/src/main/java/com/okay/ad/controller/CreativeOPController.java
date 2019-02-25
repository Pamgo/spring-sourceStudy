package com.okay.ad.controller;

import com.alibaba.fastjson.JSON;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.ICreativeService;
import com.okay.ad.vo.CreativeRequest;
import com.okay.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by OKali on 2019/1/20.
 */
@Slf4j
@RestController
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(
           @RequestBody CreativeRequest request
    ) throws AdException {
        log.info("ad-sponsor: createCreative ->{}",
                JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
