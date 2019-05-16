package com.qinjee.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qinjee.feign.model.ResultJsonModel;


@FeignClient(value = "QINJEE-TSC")
public interface FeignClientService{

	@RequestMapping("/user/get")
	public ResultJsonModel get(Integer id);
	
}
