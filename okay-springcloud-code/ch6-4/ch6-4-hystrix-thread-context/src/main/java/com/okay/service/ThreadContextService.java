package com.okay.service;
import com.okay.config.HystrixThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Component
public class ThreadContextService implements IThreadContextService{
	private static final Logger log = LoggerFactory.getLogger(ThreadContextService.class);

	@Autowired
	private RestTemplate restTemplate;

    @HystrixCommand
	public String getUser(Integer id) {
		log.info("==================================================");
		log.info("ThreadContextService, Current thread : " + Thread.currentThread().getId());
		log.info("ThreadContextService, ThreadContext object : " + HystrixThreadLocal.threadLocal.get());
		log.info("ThreadContextService, RequestContextHolder : " + RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST).toString());
		String json = restTemplate.getForObject("http://sc-provider-service/getUser/{1}", String.class, id);
		return "Success:" + json;
	}
   
   
	
}
