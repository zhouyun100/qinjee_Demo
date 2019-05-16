/*
 * 文件名： ConsumerController.java
 * 
 * 工程名称: qinjee-ribbon
 *
 * Qinjee
 *
 * 创建日期： 2019年5月16日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.ribbon.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qinjee.ribbon.model.ResultJsonModel;
import com.qinjee.ribbon.model.UserInfoModel;

/**
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月16日
 */
@RestController
public class ConsumerController {

	public static final String USER_LOGIN_URL = "http://QINJEE-TSC/user/login/";
    public static final String USER_SENDMSG_URL = "http://QINJEE-TSC/user/sendMsg/";
    
    @Resource
    private RestTemplate restTemplate;
    
    @Resource
    private HttpHeaders headers;
    
    @RequestMapping(value = "/consumer/user/login")
    public ResultJsonModel getDept(UserInfoModel userInfo) {
    	ResultJsonModel resultJson = this.restTemplate.exchange(USER_LOGIN_URL, HttpMethod.GET,
                        new HttpEntity<Object>(userInfo,this.headers), ResultJsonModel.class)
                .getBody();
        return resultJson;
    }
    
    @RequestMapping(value = "/consumer/user/sendMsg")
    public String listDept() {
    	String result = this.restTemplate
                .exchange(USER_SENDMSG_URL, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), String.class)
                .getBody();
        return result;
    }
}
