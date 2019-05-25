package com.qinjee.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qinjee.entity.ResultJsonEntity;


//@FeignClient(name = "QINJEE-TSC", fallback = HystrixClientFallback.class)
@FeignClient(name = "QINJEE-TSC", fallbackFactory = HystrixClientFallbackFactory.class)
public interface FeignClientService{

	@RequestMapping("/user/get")
	public ResultJsonEntity get(@RequestParam("id") Integer id);
	
	@RequestMapping("/user/login")
	public ResultJsonEntity login(@RequestParam("username") String username, @RequestParam("password") String password);
	
}
