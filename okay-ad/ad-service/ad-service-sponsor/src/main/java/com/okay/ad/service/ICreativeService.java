package com.okay.ad.service;

import com.okay.ad.exception.AdException;
import com.okay.ad.vo.CreativeRequest;
import com.okay.ad.vo.CreativeResponse;

/**
 * Created by OKali on 2019/1/19.
 */
public interface ICreativeService {

    /**
     * 添加创意
     * @param request
     * @return
     * @throws AdException
     */
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
