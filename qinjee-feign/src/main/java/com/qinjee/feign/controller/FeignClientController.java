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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qinejee.consts.ResponseConsts;
import com.qinjee.entity.ResultJsonEntity;
import com.qinjee.feign.redis.RedisService;
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
@RequestMapping("/user")
public class FeignClientController {

	@Autowired
	private FeignClientService feignService;

	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public ResultJsonEntity get(Integer id) {
		return feignService.get(id);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ResultJsonEntity login(HttpServletRequest request,  HttpServletResponse response, String username, String password) {
		
		ResultJsonEntity resultJson = feignService.login(username, password);
		
		if(ResponseConsts.RESULT_CODE_SUCCESS.equals(resultJson.getResultCode())) {
			String loginKey = ResponseConsts.SESSION_KEY + username;
			// 设置redis登录缓存时间，30分钟过期，与前端保持一致
			redisService.setex(loginKey, ResponseConsts.SESSION_INVALID_SECCOND, username);
			Cookie cookie = new Cookie(ResponseConsts.SESSION_KEY, loginKey);
			cookie.setMaxAge(ResponseConsts.SESSION_INVALID_SECCOND); 
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		return resultJson;
	}
	
	/**
	 * 功能描述：退出
	 *
	 * @param request
	 * @param id
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@RequestMapping("/logout")
	public ResultJsonEntity logout(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (ResponseConsts.SESSION_KEY.equals(cookies[i].getName())) {
					String loginKey = cookies[i].getValue();
					if (loginKey != null) {
						if(redisService.exists(loginKey)) {
							redisService.del(loginKey);
						}
						cookies[i].setValue(null);  
						cookies[i].setMaxAge(0);// 立即销毁cookie  
						cookies[i].setPath("/");  
                        response.addCookie(cookies[i]); 
					}
				}
			}
		}
		
		ResultJsonEntity resultJson = new ResultJsonEntity();
		resultJson.setResultCode(ResponseConsts.SESSION_LOGOUT_CODE);
		resultJson.setResultStatus(ResponseConsts.SESSION_LOGOUT_STATUS);
		resultJson.setResult("退出成功");
		
		return resultJson;
	}
}
