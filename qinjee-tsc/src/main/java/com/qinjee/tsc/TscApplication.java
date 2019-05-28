/*
 * 文件名： TscApplication.java
 * 
 * 工程名称: qinjee-tsc
 *
 * Qinjee
 *
 * 创建日期： 2019年5月15日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.tsc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 人才供应链应用程序启动入口
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月15日
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.qinjee.tsc.dao")
public class TscApplication{
	
	public static void main(String [] args) {
		SpringApplication.run(TscApplication.class, args);
	}
	
}
