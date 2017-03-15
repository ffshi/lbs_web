package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

public class Templet4RankToJson implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String followUid;    // 好友uid
	private String followImg;	 // 好友头像
	private String followName;   // 好友昵称
	private String result;	 	 // 好友做题结果
	
	
	public Templet4RankToJson()
	{
		super();
	}
	public Templet4RankToJson(String followUid, String followImg, String followName, String result)
	{
		super();
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
		this.result = result;
	}
	public String getFollowUid()
	{
		return followUid;
	}
	public void setFollowUid(String followUid)
	{
		this.followUid = followUid;
	}
	public String getFollowImg()
	{
		String imgUrl = this.followImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}
	public void setFollowImg(String followImg)
	{
		this.followImg = followImg;
	}
	public String getFollowName()
	{
		return followName;
	}
	public void setFollowName(String followName)
	{
		this.followName = followName;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	
	
}
