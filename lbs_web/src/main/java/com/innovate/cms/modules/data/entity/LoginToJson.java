package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

public class LoginToJson extends UserInfoToJson implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String token ;
	public LoginToJson(String token)
	{
		super();
		this.token = token;
	}

	public LoginToJson()
	{
		super();
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	} 
}