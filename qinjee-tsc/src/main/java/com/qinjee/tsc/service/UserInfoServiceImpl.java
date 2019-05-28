/*
 * 文件名： UserInfoServiceImpl.java
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.qinjee.tsc.dao.UserInfoDao;
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
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

	private static Logger logger = LogManager.getLogger(UserInfoService.class);
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	public UserInfoModel selectByPrimaryKey(Integer id) {
		logger.info("请求参数：id={}", id);
		return userInfoDao.selectUserInfoById(id);
	}
	
	public UserInfoModel selectByUsernameAndPassword(UserInfoModel userInfo) {
		logger.info("请求参数：userInfo={}", JSON.toJSONString(userInfo));
		return userInfoDao.selectByUsernameAndPassword(userInfo);
	}
	
	public UserInfoModel addUserInfo(UserInfoModel userInfo) {
		logger.info("请求参数：userInfo={}", JSON.toJSONString(userInfo));
		userInfoDao.addUserInfo(userInfo);
		return userInfo;
	}
}
