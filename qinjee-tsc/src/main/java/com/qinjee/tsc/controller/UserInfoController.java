package com.qinjee.tsc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

//import javax.servlet.http.HttpServletRequest;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qinjee.tsc.rabbitmq.SendMsgService;
import com.qinjee.tsc.redis.RedisService;
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
	private RedisService redisService;
	
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
				String loginKey = "LOGIN_" + userInfo.getUsername();
				// 记录登录的session
				request.getSession().setAttribute(ResponseConsts.SESSION_LOGIN_KEY, loginKey);
				// 设置redis登录缓存时间，30分钟过期，与前端保持一致
				redisService.setex(loginKey, ResponseConsts.SESSION_INVALID_SECCOND, userInfo.getUsername());

				logger.info("Login success！username={}", username);
				
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_SUCCESS);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_SUCCESS);
				resultJson.setResult("登录成功");
			}else {
				logger.info("Login faild,No this username found! username={};password={}", username,password);
				
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_FAILD);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_FAILD);
				resultJson.setResult("登录失败，无该用户信息");
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
	 * 功能描述：退出
	 *
	 * @param request
	 * @param id
	 * @return
	 * 
	 * @author 周赟
	 *
	 * @since 2019年5月15日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@RequestMapping("/logout")
	public ResultJsonModel logout(HttpServletRequest request, String id) {
		
		String loginKey = String.valueOf(request.getSession().getAttribute(ResponseConsts.SESSION_LOGIN_KEY));
		if(loginKey != null) {
			request.getSession().removeAttribute(ResponseConsts.SESSION_LOGIN_KEY);
			
			if(redisService.exists(loginKey)) {
				redisService.del(loginKey);
			}
		}
		
		ResultJsonModel resultJson = new ResultJsonModel();
		resultJson.setResultCode(ResponseConsts.SESSION_LOGOUT_CODE);
		resultJson.setResultStatus(ResponseConsts.SESSION_LOGOUT_STATUS);
		resultJson.setResult("退出成功");
		
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
	
	/**
	 * 处理文件上传
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
//	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
//	public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
//		System.out.println("调用文件上传方法");
//		String contentType = file.getContentType();
//		String fileName = file.getOriginalFilename();
//		return "SUCCESS";
//	}
}
