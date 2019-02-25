package com.okay.ad.service.impl;

import com.okay.ad.dao.CreativeRepository;
import com.okay.ad.entity.Creative;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.ICreativeService;
import com.okay.ad.vo.CreativeRequest;
import com.okay.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by OKali on 2019/1/19.
 */
@Slf4j
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    @Transactional
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        Creative creative = creativeRepository.save(
                request.concertToEntity()
        );

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
