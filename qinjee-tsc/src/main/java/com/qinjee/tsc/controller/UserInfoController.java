package com.qinjee.tsc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qinjee.tsc.rabbitmq.SendMsgService;
import com.qinejee.consts.ResponseConsts;
import com.qinjee.tsc.model.ResultJsonModel;
import com.qinjee.tsc.model.UserInfoModel;
import com.qinjee.tsc.service.UserInfoService;

/**
 * 
 * 控制类
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月15日
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

	private static Logger logger = LogManager.getLogger(UserInfoController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SendMsgService sendMsgService;

	/**
	 * 功能描述：设置redis缓存，并返回value
	 *
	 * @param id
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
	@RequestMapping("/login")
	public ResultJsonModel login(HttpServletRequest request, String username, String password) {
		
        logger.info("Login request parameter：username={};password={}", username,password);
        
		ResultJsonModel resultJson = new ResultJsonModel();
		try {
			UserInfoModel userInfo = new UserInfoModel();
			userInfo.setUsername(username);
			userInfo.setPassword(password);
			userInfo = userInfoService.selectByUsernameAndPassword(userInfo);
			if (userInfo != null) {
				logger.info("Login success！username={}", username);
				
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_SUCCESS);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_SUCCESS);
				resultJson.setResult(userInfo);
			}else {
				logger.info("Login faild,No this username found! username={};password={}", username,password);
				
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_FAILD);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_FAILD);
				resultJson.setResult("登录失败，无该用户信息");
			}
			
		}catch(Exception e) {
			logger.info("Login exception! username={};password={}", username,password);
			e.printStackTrace();
			resultJson.setResultCode(ResponseConsts.RESULT_CODE_EXCEPTION);
			resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_EXCEPTION);
			resultJson.setResult(ResponseConsts.RESULT_RESULT_MESSAGE);
		}
		
		return resultJson;
	}
	
	/**
	 * 
	 * 功能描述：注册
	 *
	 * @param username
	 * @param password
	 * @param nickname
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@RequestMapping("/register")
	public ResultJsonModel register(String username, String password) {
		ResultJsonModel resultJson = new ResultJsonModel();
		try {
			UserInfoModel userInfo = new UserInfoModel();
			userInfo.setUsername(username);
			userInfo.setPassword(password);
			UserInfoModel userInfoModel = userInfoService.selectByUsernameAndPassword(userInfo);
			if(null == userInfoModel) {
				userInfoModel = userInfoService.addUserInfo(userInfo);
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_SUCCESS);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_SUCCESS);
				resultJson.setResult(userInfoModel);
			}else {
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_FAILD);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_FAILD);
				resultJson.setResult("用户已存在");
			}
		}catch(Exception e) {
			e.printStackTrace();
			resultJson.setResultCode(ResponseConsts.RESULT_CODE_EXCEPTION);
			resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_EXCEPTION);
			resultJson.setResult(ResponseConsts.RESULT_RESULT_MESSAGE);
		}
		
		return resultJson;
	}
	
	/**
	 * 功能描述：mq消息发送与接收
	 *
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@RequestMapping("/sendMsg")
	public String sendMsg(Integer id,String username) {
		try {
			UserInfoModel userInfo = new UserInfoModel();
			userInfo.setId(id);;
			userInfo.setUsername(username);
			sendMsgService.send(userInfo);
			return "send success";
		}catch(Exception e) {
			e.printStackTrace();
			return "send faild";
		}
	}
	

	
	/**
	 * 
	 * 功能描述：根据ID获取数据库数据信息
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
	@RequestMapping("/get")
	public ResultJsonModel get(Integer id) {
		ResultJsonModel resultJson = new ResultJsonModel();
		try {
			UserInfoModel userInfo = userInfoService.selectByPrimaryKey(id);
			if(null != userInfo) {
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_SUCCESS);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_SUCCESS);
				resultJson.setResult(userInfo);
			}else {
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_FAILD);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_FAILD);
				resultJson.setResult("信息不存在");
			}
		}catch(Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			resultJson.setResultCode(ResponseConsts.RESULT_CODE_EXCEPTION);
			resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_EXCEPTION);
			resultJson.setResult(ResponseConsts.RESULT_RESULT_MESSAGE);
		}
		return resultJson;
	}
	
}
