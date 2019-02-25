package com.okay.ad.service.impl;

import com.okay.ad.constant.CommonStatus;
import com.okay.ad.constant.Constants;
import com.okay.ad.dao.AdPlanRepository;
import com.okay.ad.dao.AdUserRepository;
import com.okay.ad.entity.AdPlan;
import com.okay.ad.entity.AdUser;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.IAdPlanService;
import com.okay.ad.utils.CommonUtils;
import com.okay.ad.vo.AdPlanGetRequest;
import com.okay.ad.vo.AdPlanRequest;
import com.okay.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by OKali on 2019/1/19.
 */
@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository userRepository;

    private final AdPlanRepository planRepository;

    @Autowired
    public AdPlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {

        if (!request.createValidator()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //确保关联的 User 是存在的
        Optional<AdUser> adUser =
                userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw  new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName()
        );
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newAdPlan = planRepository.save(
                new AdPlan(request.getUserId(), request.getPlanName(),
                        CommonUtils.parseStrDate(request.getStartDate()),
                        CommonUtils.parseStrDate(request.getEndDate()))
        );

        return new AdPlanResponse(newAdPlan.getId(),
                newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {

        if (!request.validator()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdInAndUserId(request.getIds(),request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {

        if (!request.updateValidator()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(CommonUtils.parseStrDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(CommonUtils.parseStrDate(request.getEndDate()));
        }
        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {

        if (!request.deleteValidator()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(
                request.getId(),request.getUserId()
        );
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }
}
