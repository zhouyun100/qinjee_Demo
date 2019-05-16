/*
 * 文件名： UserServiceApi.java
 * 
 * 工程名称: qinjee-service-api
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
package com.qinjee.service.tsc.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qinjee.service.tsc.model.UserInfoModel;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月16日
 */
@RequestMapping("/user-service-remote")
public interface UserServiceApi {
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	UserInfoModel user(@RequestBody UserInfoModel user);
	
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	UserInfoModel user(@RequestParam("id") Integer id);
	
	@RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
	String user(@RequestParam("id") Integer id,@RequestParam("username") String username);
	
}
