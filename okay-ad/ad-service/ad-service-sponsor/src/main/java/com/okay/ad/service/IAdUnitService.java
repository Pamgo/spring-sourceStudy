package com.okay.ad.service;

import com.okay.ad.exception.AdException;
import com.okay.ad.vo.*;

/**
 * Created by OKali on 2019/1/19.
 */
public interface IAdUnitService {

    /**
     * 添加推广单元
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 添加推广单元关键字
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws  AdException;

    /**
     * 添加推广单元兴趣
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 添加推广单元限制
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    /**
     * 创意与推广单元关联
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
