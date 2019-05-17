package com.qinjee.tsc.dao;

import org.springframework.stereotype.Service;

import com.qinjee.tsc.model.UserInfoModel;

@Service("userInfoDao")
public interface UserInfoDao {

	/**
	 * 
	 * 功能描述：根据主键 ID查询数据表信息
	 *
	 * @param id
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public UserInfoModel selectUserInfoById(Integer id);
	
	/**
	 * 
	 * 功能描述：根据账号密码查询用户信息
	 *
	 * @param username
	 * @param password
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public UserInfoModel selectByUsernameAndPassword(UserInfoModel userInfo);
	
	/**
	 * 
	 * 功能描述：添加用户信息
	 *
	 * @param userInfo
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public void addUserInfo(UserInfoModel userInfo);
	
}
