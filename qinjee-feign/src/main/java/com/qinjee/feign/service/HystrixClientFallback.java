/*
 * 文件名： FeignClientServiceImpl.java
 * 
 * 工程名称: qinjee-feign
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
package com.qinjee.feign.service;

import org.springframework.stereotype.Component;

import com.qinjee.feign.model.ResultJsonModel;

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
public class HystrixClientFallback extends FallbackResult implements FeignClientService{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qinjee.feign.service.FeignClientService#get(java.lang.Integer)
	*/
	@Override
	public ResultJsonModel get(Integer id) {
		return getNetExceptionResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qinjee.feign.service.FeignClientService#login(java.lang.String, java.lang.String)
	*/
	@Override
	public ResultJsonModel login(String username, String password) {
		return getNetExceptionResult();
	}

	
}
