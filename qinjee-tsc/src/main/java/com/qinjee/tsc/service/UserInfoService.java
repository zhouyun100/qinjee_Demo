package com.qinjee.tsc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.qinjee.tsc.dao.UserInfoDao;
import com.qinjee.tsc.model.UserInfoModel;

@Service("userInfoService")
public class UserInfoService {

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
