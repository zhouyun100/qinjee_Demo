package com.qinjee.tsc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.qinjee.tsc.rabbitmq.SendMsgService;
import com.qinjee.tsc.redis.RedisService;
import com.qinejee.consts.ResponseConsts;
import com.qinjee.entity.ResultJsonEntity;
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
@Api(tags = "用户管理API")
@RestController
@RequestMapping("/user")
public class UserInfoController {

	private static Logger logger = LogManager.getLogger(UserInfoController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SendMsgService sendMsgService;
	
	@Autowired
	private RedisService redisService;

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
	@ApiOperation(value="用户登录", notes="根据用户名、密码来登录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
		@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ResponseEntity<ResultJsonEntity> login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		
        logger.info("Login request parameter：username={};password={}", username,password);
        
        ResultJsonEntity resultJson = new ResultJsonEntity();
		try {
			UserInfoModel userInfo = new UserInfoModel(username,password);
			userInfo = userInfoService.selectByUsernameAndPassword(userInfo);
			if (userInfo != null) {
				String loginKey = ResponseConsts.SESSION_KEY + username;
				// 设置redis登录缓存时间，30分钟过期，与前端保持一致
				redisService.setex(loginKey, ResponseConsts.SESSION_INVALID_SECCOND, username);
				Cookie cookie = new Cookie(ResponseConsts.SESSION_KEY, loginKey);
				cookie.setMaxAge(ResponseConsts.SESSION_INVALID_SECCOND); 
				cookie.setPath("/");
				response.addCookie(cookie);
				
				resultJson.setResultCode(ResponseConsts.RESULT_CODE_SUCCESS);
				resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_SUCCESS);
				resultJson.setResult(userInfo);
				logger.info("Login success！username={}", username);
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
		
		return ResponseEntity.ok(resultJson);
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
	@ApiOperation(value="用户注册", notes="根据用户名、密码注册，返回注册成功后的用户实体信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
		@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResultJsonEntity> register(String username, String password) {
		ResultJsonEntity resultJson = new ResultJsonEntity();
		
		try {
			UserInfoModel userInfo = new UserInfoModel(username,password);
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
		
		return ResponseEntity.ok(resultJson);
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
	@ApiOperation(value="退出", notes="用户注销")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public  ResponseEntity<ResultJsonEntity> logout(HttpServletRequest request, HttpServletResponse response) {
		ResultJsonEntity resultJson = new ResultJsonEntity();
		resultJson.setResultCode(ResponseConsts.RESULT_CODE_SUCCESS);
		resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_SUCCESS);
		
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (ResponseConsts.SESSION_KEY.equals(cookies[i].getName())) {
					String loginKey = cookies[i].getValue();
					if (loginKey != null) {
						if(redisService.exists(loginKey)) {
							redisService.del(loginKey);
						}
						cookies[i].setValue(null);  
						cookies[i].setMaxAge(0);// 立即销毁cookie  
						cookies[i].setPath("/");  
                        response.addCookie(cookies[i]); 
					}
				}
			}
		}
		
		resultJson.setResult("退出成功");
		
		return ResponseEntity.ok(resultJson);
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
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	public String sendMsg(Integer id,String username) {
		try {
			UserInfoModel userInfo = new UserInfoModel(id,username);
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
	@ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<ResultJsonEntity> get(Integer id) {
		ResultJsonEntity resultJson = new ResultJsonEntity();
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
			resultJson.setResultCode(ResponseConsts.RESULT_CODE_EXCEPTION);
			resultJson.setResultStatus(ResponseConsts.RESULT_STATUS_EXCEPTION);
			resultJson.setResult(ResponseConsts.RESULT_RESULT_MESSAGE);
		}
		return ResponseEntity.ok(resultJson);
	}
	
}
