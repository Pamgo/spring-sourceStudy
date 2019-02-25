package com.okay.ad.service.impl;

import com.okay.ad.constant.Constants;
import com.okay.ad.dao.AdPlanRepository;
import com.okay.ad.dao.AdUnitRepository;
import com.okay.ad.dao.CreativeRepository;
import com.okay.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.okay.ad.dao.unit_condition.AdUnitItRepository;
import com.okay.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.okay.ad.dao.unit_condition.CreativeUnitRepository;
import com.okay.ad.entity.AdPlan;
import com.okay.ad.entity.AdUnit;
import com.okay.ad.entity.unit_condition.AdUnitDistrict;
import com.okay.ad.entity.unit_condition.AdUnitIt;
import com.okay.ad.entity.unit_condition.AdUnitKeyword;
import com.okay.ad.entity.unit_condition.CreativeUnit;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.IAdUnitService;
import com.okay.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by OKali on 2019/1/19.
 */
@Slf4j
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository, AdUnitKeywordRepository unitKeywordRepository, AdUnitItRepository unitItRepository, AdUnitDistrictRepository unitDistrictRepository, CreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }


    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        if (!request.createValidator()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldAdUnit = unitRepository.findByPlanIdAndUnitName(
                request.getPlanId(), request.getUnitName()
        );
        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newAdUnit = unitRepository.save(
                new AdUnit(request.getPlanId(), request.getUnitName(),
                        request.getPositionType(),request.getBudget())
        );

        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {

        // 使用java8语法获取传入进来的所有id
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                .map(AdUnitKeyword::getId)
                .collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {

        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));

        List<Long> ids = unitItRepository.saveAll(unitIts).stream()
                         .map(AdUnitIt::getId)
                         .collect(Collectors.toList());

        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {

        // 判断是否有效
        List<Long> unitIds = request.getUnitDistricts().stream()
                    .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                    .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 组装成推广单元限制实体结合
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new AdUnitDistrict(d.getUnitId(), d.getProvince(),
                        d.getCity())
        ));
        // 保持后返回Ids
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts)
                .stream().map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {

        List<Long> unitIds = request.getCreativeUnitItems().stream()
                    .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                    .collect(Collectors.toList());

        List<Long> creativeIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        if (!(isRelatedUnitExist(unitIds) && isRelatedCreativeExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getCreativeUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits)
                        .stream().map(CreativeUnit::getId)
                        .collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    /**
     * 判断创意是否存在
     * @param ids
     * @return
     */
    private boolean isRelatedCreativeExist(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return creativeRepository.findAllById(ids).size() ==
                new HashSet<>(ids).size();
    }

    /**
     * 判断
     * @param unitIds
     * @return
     */
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitRepository.findAllById(unitIds).size() ==
                new HashSet<>(unitIds).size();
    }

}
