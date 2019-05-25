/*
 * 文件名： HystrixClientFallbackFactory.java
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

import feign.hystrix.FallbackFactory;

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
public class HystrixClientFallbackFactory extends FallbackResult  implements FallbackFactory<FeignClientService>{

	@Override
	public FeignClientService create(Throwable arg0) {
		
		return new FeignClientService() {
			
			@Override
			public ResultJsonModel get(Integer id) {
				return getNetExceptionResult();
			}

			@Override
			public ResultJsonModel login(String username, String password) {
				return getNetExceptionResult();
			}

		};
		
	}

	
}
