package com.okay.service.impl;


import com.okay.service.IUserService;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 */
@Component
public class UserService implements IUserService{
	
	@Override
	@HystrixCommand(fallbackMethod="defaultUser")
	public String getUser(String username) throws Exception {
		if(username.equals("spring")) {
			return "This is real user";
		}else {
			throw new Exception();
		}
	}
	
	 /**
	  * 出错则调用该方法返回友好错误
	  * @param username
	  * @return
	  */
	 public String defaultUser(String username) {
	    return "The user does not exist in this system->" + username;
	 }
	 
}
