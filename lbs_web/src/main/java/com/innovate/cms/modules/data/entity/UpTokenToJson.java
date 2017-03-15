package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

public class UpTokenToJson implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String token;
	
	public UpTokenToJson()
	{
		super();
	}
	public UpTokenToJson(String token)
	{
		super();
		this.token = token;
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
