/*
 * 文件名： ArticleController.java
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
package com.qinjee.tsc.controller;

import java.util.List;
import java.util.UUID;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.qinjee.tsc.model.ArticleModel;
import com.qinjee.tsc.service.ArticleRepository;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月22日
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@RequestMapping("/save")
    public @ResponseBody String save(){
		String id = UUID.randomUUID().toString();
		String title = "帖子"+System.currentTimeMillis();
		String description = "这是一个测试帖子，时间戳："+System.currentTimeMillis();
		logger.info("article save content：id={};title={};description={}", id,title,description);
		
		ArticleModel article = new ArticleModel(id,title,description);
		articleRepository.save(article);
        return "save success";
    }

    //http://localhost:8888/article/delete?id=1525415333329
	@RequestMapping("/delete")
    public String delete(String id){
		articleRepository.delete(id);
        return "success";
    }

    //http://localhost:8888/article/update?id=1525417362754&name=修改&description=修改
	@RequestMapping("/update")
    public String update(String id,String name,String description){
		ArticleModel article = new ArticleModel(id,
                name,description);
		articleRepository.save(article);
        return "success";
    }

    //http://localhost:8888/article/getOne?id=1525417362754
	@RequestMapping("getOne")
    public ArticleModel getOne(String id){
		ArticleModel article = articleRepository.findOne(id);
		String articleStr = JSON.toJSONString(article);
		logger.info("getOne article={}", articleStr);
        return article;
    }


    //每页数量
    private Integer PAGESIZE=10;

    //根据关键字"帖子"去查询列表，name或者description包含的都查询
    @RequestMapping("getArticleList")
    public List<ArticleModel> getList(Integer pageNumber,String query){
		if (pageNumber == null) {
			pageNumber = 0;
		}
		
		// es搜索默认第一页页码是0
		SearchQuery searchQuery = getEntitySearchQuery(pageNumber, PAGESIZE, query);
        Page<ArticleModel> articlePage = articleRepository.search(searchQuery);
        
        return articlePage.getContent();
    }


    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        
    	/**
    	 * 	该 函数类似于如下sql逻辑，中文分词需要配置IK分词器
    	 * select * from articleDetails where title like '%searchContent%' or title like '%searchContent%';
    	 */
    	BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.matchQuery("title", searchContent))
				.should(QueryBuilders.matchQuery("description", searchContent));
    	
        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).build();
    }
	
}
