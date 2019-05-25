/*
 * 文件名： AuthFilter.java
 * 
 * 工程名称: qinjee-zuul
 *
 * Qinjee
 *
 * 创建日期： 2019年5月25日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.api.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.qinejee.consts.ResponseConsts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.qinjee.entity.ResultJsonEntity;
import com.qinjee.zuul.redis.RedisService;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月25日
 */
@Component
public class AuthFilter extends ZuulFilter{

	@Autowired
	private RedisService redisService;
	
	//排除过滤的 uri 地址
    private static final String LOGIN_URI = "/api/qinjee-tsc/user/login";
    private static final String REGISTER_URI = "/api/qinjee-tsc/user/register";

	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String requestUri = request.getRequestURI();
        //注册和登录接口不拦截，其他接口都要拦截校验 token
        if (LOGIN_URI.equals(requestUri) || REGISTER_URI.equals(requestUri)) {
            return false;
        }
        return true;
	}

	
	@Override
	public Object run(){
		RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //先从 cookie 中取 SESSION_KEY
        Cookie sessionKey = getCookie(request, ResponseConsts.SESSION_KEY);
        if (sessionKey == null || StringUtils.isEmpty(sessionKey.getValue())) {
        	setUnauthorizedResponse(requestContext);
        }else {
			if (redisService.exists(sessionKey.getValue())) {
				/**
				 * 判断登录session失效时间，小于5分钟则重新更换session时长
				 */
				Long loginKeyValidTime = redisService.getExpire(sessionKey.getValue());
				if (loginKeyValidTime < ResponseConsts.SESSION_CHECK_SECCOND) {
					redisService.expire(sessionKey.getValue(), ResponseConsts.SESSION_INVALID_SECCOND);
				}
			}else {
				setUnauthorizedResponse(requestContext);
			}
        }
        
		return null;
	}

	
	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	
	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER - 1;
	}
	
	/**
	 * 
	 * 功能描述：设置 401 无权限状态
	 *
	 * @param requestContext
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月25日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
    private void setUnauthorizedResponse(RequestContext requestContext){
    	requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ResultJsonEntity resultJson = new ResultJsonEntity();
		resultJson.setResultCode(ResponseConsts.SESSION_INVALID_CODE);
		resultJson.setResultStatus(ResponseConsts.SESSION_INVALID_STATUS);
		resultJson.setResult(ResponseConsts.SESSION_INVALID_MES);
        String result = JSON.toJSONString(resultJson);
        requestContext.setResponseBody(result);
    }
	
	public Cookie getCookie(HttpServletRequest request,String cookieName) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookieName.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}
}
