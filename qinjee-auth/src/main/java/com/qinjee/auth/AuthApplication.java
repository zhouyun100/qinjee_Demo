/*
 * 文件名： AuthApplication.java
 * 
 * 工程名称: qinjee-auth
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
package com.qinjee.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 *
 * @author 周赟
 *
 * @version
 *
 * @since 2019年5月27日
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
