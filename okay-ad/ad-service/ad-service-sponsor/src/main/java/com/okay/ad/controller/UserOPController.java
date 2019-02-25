package com.okay.ad.controller;

import com.alibaba.fastjson.JSON;
import com.okay.ad.exception.AdException;
import com.okay.ad.service.IUserService;
import com.okay.ad.vo.CreateUserRequest;
import com.okay.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by OKali on 2019/1/19.
 */
@Slf4j
@RestController
public class UserOPController {

    @Autowired
    private IUserService userService;

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException{
        log.info("ad-sponsor: createUser -> {}",
                JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
