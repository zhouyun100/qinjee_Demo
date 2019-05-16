/*
 * 文件名： FeignClientController.java
 * 
 * 工程名称: qinjee-feign
 *
 * Qinjee
 *
 * 创建日期： 2019年5月16日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qinjee.feign.model.ResultJsonModel;
import com.qinjee.feign.service.FeignClientService;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月16日
 */
@RestController
public class FeignClientController {

	@Autowired
	FeignClientService feignService;
	
	
	@RequestMapping("/get")
	public ResultJsonModel get(Integer id) {
		return feignService.get(id);
	}
}
