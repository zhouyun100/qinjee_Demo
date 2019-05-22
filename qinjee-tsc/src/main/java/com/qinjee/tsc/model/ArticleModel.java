/*
 * 文件名： ArticleModel.java
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
package com.qinjee.tsc.model;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月22日
 */
@Document(indexName = "article",type = "articleDetails")
public class ArticleModel implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2068644510002497286L;

	private String id;
	
	private String title;
	
	private String description;

	/**
	 * @return 返回 id。
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id。
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 返回 title。
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 要设置的 title。
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 返回 description。
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description 要设置的 description。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArticleModel(String id,String title,String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
	public ArticleModel() {
		
	}
}
