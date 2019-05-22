/*
 * 文件名： ArticleRepository.java
 * 
 * 工程名称: qinjee-tsc
 *
 * Qinjee
 *
 * 创建日期： 2019年5月22日
 *
 * Copyright(C) 2019, by zhouyun
 *
 * 原始作者: 周赟
 *
 */
package com.qinjee.tsc.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.qinjee.tsc.model.ArticleModel;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月22日
 */
@Component
public interface ArticleRepository extends ElasticsearchRepository<ArticleModel, String> {

}
