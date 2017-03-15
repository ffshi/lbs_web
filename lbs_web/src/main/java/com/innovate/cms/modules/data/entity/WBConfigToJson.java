package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

public class WBConfigToJson implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String url = "";
	private String noncestr = "";
	private String timestamp = "";
	private String signature = "";
	
	public WBConfigToJson()
	{
		super();
	}

	public WBConfigToJson(String url, String noncestr, String timestamp, String signature)
	{
		super();
		this.url = url;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.signature = signature;
	}
	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getNoncestr()
	{
		return noncestr;
	}
	public void setNoncestr(String noncestr)
	{
		this.noncestr = noncestr;
	}
	public String getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
	public String getSignature()
	{
		return signature;
	}
	public void setSignature(String signature)
	{
		this.signature = signature;
	}
}
