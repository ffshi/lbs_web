package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

public class WBWebLoginToJson implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String uid ; 
	private String nickname ;
	private String headimgurl ;
	private String sex ;
	
	public WBWebLoginToJson()
	{
		super();
	}
	
	
	public WBWebLoginToJson(String uid, String nickname, String headimgurl, String sex)
	{
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.sex = sex;
	}

	public String getUid()
	{
		return uid;
	}
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public String getHeadimgurl()
	{
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
}
