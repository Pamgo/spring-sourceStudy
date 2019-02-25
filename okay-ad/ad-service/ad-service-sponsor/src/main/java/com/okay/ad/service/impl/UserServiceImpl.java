package com.okay.ad.service.impl;

import com.okay.ad.constant.Constants;
import com.okay.ad.dao.AdUserRepository;
import com.okay.ad.entity.AdUser;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.IUserService;
import com.okay.ad.utils.CommonUtils;
import com.okay.ad.vo.CreateUserRequest;
import com.okay.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by OKali on 2019/1/19.
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()) { //校验参数
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) { // 同名
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
        ));  // 创建用户

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(),
                newUser.getToken(), newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
