/*
 * 文件名： LoginInterceptor.java
 * 
 * 工程名称: qinjee-tsc
 *
 * Shang De
 *
 * 创建日期： 2019年5月15日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.tsc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qinejee.consts.ResponseConsts;
import com.qinjee.tsc.redis.RedisService;

/**
 * session拦截
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月15日
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private RedisService redisService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	
	/**
	 * 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		String loginKey = String.valueOf(request.getSession().getAttribute(ResponseConsts.SESSION_LOGIN_KEY));
		
		if(loginKey != null) {
			if(redisService.exists(loginKey)) {
				/**
				 * 判断登录session失效时间，小于5分钟则重新更换session时长
				 */
				Long loginKeyValidTime = redisService.getExpire(loginKey);
				if(loginKeyValidTime < ResponseConsts.SESSION_CHECK_SECCOND) {
					redisService.expire(loginKey, ResponseConsts.SESSION_INVALID_SECCOND);
				}
				return true;
			}
		}
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.getWriter().write("{\"resultCode\":\""+ ResponseConsts.SESSION_INVALID_CODE +"\",\"resultStatus\":\""+ ResponseConsts.SESSION_INVALID_STATUS +"\",\"result\":\"session无效,请重新登陆！\"}");
		return false;
	}
	
}
