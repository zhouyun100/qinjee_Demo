package com.qinjee.tsc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qinjee.tsc.dao.UserInfoDao;
import com.qinjee.tsc.model.UserInfoModel;

@Service("userInfoService")
public class UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	
	public UserInfoModel selectByPrimaryKey(Integer id) {
		return userInfoDao.selectUserInfoById(id);
	}
	
	public UserInfoModel selectByUsernameAndPassword(String username,String password) {
		return userInfoDao.selectByUsernameAndPassword(username, password);
	}
	
	public UserInfoModel addUserInfo(UserInfoModel userInfo) {
		return userInfoDao.addUserInfo(userInfo);
	}
}
