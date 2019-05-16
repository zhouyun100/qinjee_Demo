/*
 * 文件名： RestConfig.java
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
package com.qinjee.ribbon.config;

import java.nio.charset.Charset;
import java.util.Base64;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月16日
 */
@Configuration
public class RestConfig {

	@Bean
    public HttpHeaders getHeaders() { // 要进行一个Http头信息配置
        HttpHeaders headers = new HttpHeaders(); // 定义一个HTTP的头信息
        String auth = "qinjeejava:tsc"; // 认证的原始信息
        byte[] encodedAuth = Base64.getEncoder()
                .encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
        // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        return headers;
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
