/*
 * 文件名： FallbackResult.java
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

import com.qinejee.consts.ResponseConsts;
import com.qinjee.entity.ResultJsonEntity;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月25日
 */
public class FallbackResult {

	protected ResultJsonEntity getNetExceptionResult() {
		ResultJsonEntity resultJson = new ResultJsonEntity();
		resultJson.setResultCode(ResponseConsts.RESULT_CODE_NET_EXCEPTION);
		resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_NET_EXCEPTION);
		resultJson.setResult(ResponseConsts.RESULT_RESULT_NET_MESSAGE);
		return resultJson;
	}
}
