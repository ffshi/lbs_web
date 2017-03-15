package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

public class WXWebLoginToJson implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String uid; 		//系统唯一标识
	private String unionid; 		//UnionID机制 全局授权唯一标示
	private String openid; 		//	用户的唯一标识 
	private String nickname; 		// 用户昵称
	private String headimgurl; 		//用户头像
	private String sex; 		//用户性别
	private String province; 		//用户个人资料填写的省份
	private String country; 		//国家，如中国为CN 
	private String city; 		//普通用户个人资料填写的城市 
	
	public WXWebLoginToJson()
	{
		super();
	}
	
	public WXWebLoginToJson(String uid, String unionid, String openid, String nickname, String headimgurl, String sex,
			String province, String country, String city)
	{
		super();
		this.uid = uid;
		this.unionid = unionid;
		this.openid = openid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.sex = sex;
		this.province = province;
		this.country = country;
		this.city = city;
	}
	public String getUid()
	{
		return uid;
	}
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	public String getUnionid()
	{
		return unionid;
	}
	public void setUnionid(String unionid)
	{
		this.unionid = unionid;
	}
	public String getOpenid()
	{
		return openid;
	}
	public void setOpenid(String openid)
	{
		this.openid = openid;
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
		String imgUrl = this.headimgurl;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
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
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public String toString()
	{
		return "WXWebLoginToJson [uid=" + uid + ", unionid=" + unionid + ", openid=" + openid + ", nickname=" + nickname
				+ ", headimgurl=" + headimgurl + ", sex=" + sex + ", province=" + province + ", country=" + country + ", city="
				+ city + "]";
	}
	
}
