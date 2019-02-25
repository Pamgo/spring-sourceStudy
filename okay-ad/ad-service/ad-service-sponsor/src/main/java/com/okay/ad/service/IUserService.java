package com.okay.ad.service;

import com.okay.ad.exception.AdException;
import com.okay.ad.vo.CreateUserRequest;
import com.okay.ad.vo.CreateUserResponse;

/**
 * Created by OKali on 2019/1/19.
 */
public interface IUserService {

    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
