package com.qinjee.tsc.model;

import java.io.Serializable;

public class UserInfoModel implements Serializable{
	
	/**
	 * MQ寮体消息要序列化
	 */
	private static final long serialVersionUID = -3013073118953388034L;

	private Integer id;
	
	private String username;
	
	private String password;
	
	private String nickname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
