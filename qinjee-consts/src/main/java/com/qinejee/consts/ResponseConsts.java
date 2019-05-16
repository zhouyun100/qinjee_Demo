package com.qinejee.consts;

/**
 * REST请求、返回code常量
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月15日
 */
public class ResponseConsts {

	/**
	 * SESSION
	 */
	public static final String SESSION_LOGIN_KEY = "LOGIN_KEY";
	public static final String SESSION_INVALID_CODE = "10101";
	public static final String SESSION_INVALID_STATUS = "FAILD";
	public static final Integer SESSION_INVALID_SECCOND = 1800;
	public static final Integer SESSION_CHECK_SECCOND = 300;
	public static final String SESSION_LOGOUT_CODE = "10102";
	public static final String SESSION_LOGOUT_STATUS = "SUCCESS";
	
	/**
	 * 请求成功
	 */
	public static final String RESULT_CODE_SUCCESS = "10000";
	public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
	
	/**
	 * 业务异常
	 */
	public static final String RESULT_CODE_FAILD = "10100";
	public static final String RESULT_STATUS_FAILD = "FAILD";
	
	/**
	 * 程序异常
	 */
	public static final String RESULT_CODE_EXCEPTION = "10200";
	public static final String RESULT_STATUS_EXCEPTION = "SYSTEM_EXCEPTION";
	public static final String RESULT_RESULT_MESSAGE = "请求异常，请联系开发人员！";
	
}
