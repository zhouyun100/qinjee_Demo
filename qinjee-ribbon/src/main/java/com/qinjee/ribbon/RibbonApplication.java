/*
 * 文件名： RibbonApplication.java
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
package com.qinjee.ribbon;

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
 * @since 2019年5月16日
 */
@SpringBootApplication
@EnableEurekaClient
public class RibbonApplication {
	public static void main(String [] args) {
		SpringApplication.run(RibbonApplication.class, args);
	}
}
