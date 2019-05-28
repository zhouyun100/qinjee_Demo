/*
 * 文件名： UserInfoService.java
 * 
 * 工程名称: qinjee-tsc
 *
 * Qinjee
 *
 * 创建日期： 2019年5月27日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.tsc.service;

import com.qinjee.tsc.model.UserInfoModel;

/**
 * 
 *
 * @author 周赟
 *
 * @version
 *
 * @since 2019年5月27日
 */
public interface UserInfoService {
	
	public UserInfoModel selectByPrimaryKey(Integer id);

	public UserInfoModel selectByUsernameAndPassword(UserInfoModel userInfo);

	public UserInfoModel addUserInfo(UserInfoModel userInfo);

}
