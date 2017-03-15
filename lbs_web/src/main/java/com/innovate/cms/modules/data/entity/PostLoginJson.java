package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

public class PostLoginJson implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String unionid; // 获取用户个人信息（UnionID机制）
	private String openid; // 当前来源渠道唯一标识
	private String accessToken; // SDK接口调用凭证 微信 微博 QQ公用
	private String refreshToken; // SDK用户刷新access_token微信独享
	private int expiresIn; // SDKaccess_token的生命周期，单位是秒数
	private String nickname; // 昵称
	private String sex; // 用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知
	private String constellation; // 星座
	private Date birthday; // 生日
	private String province; // 归属省会s
	private String city; // 归属市级城市
	private String country; // 国家，如中国为CN
	private String headimgurl; // 用户头像,最后一个数值代表正方形头像大小
	private String lang; // 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	private String os; // 用户客户端ios或者android
	private String userType; // 用户类型0官方 1普通 2微信 3web微信 4QQ 5微博 6QQWeb 7小程序
	private String loginName; // 登录名-可为账号
	private String password; // 登录密码
	private String mobile; // 手机-可为账号
	private String version; // 临时参数
	


	public PostLoginJson()
	{
		super();
	}

	public PostLoginJson(String unionid, String openid, String accessToken, String refreshToken, Integer expiresIn,
			String nickname, String sex, String headimgurl, String os, String userType)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.nickname = nickname;
		this.sex = sex;
		this.headimgurl = headimgurl;
		this.os = os;
		this.userType = userType;
	}

	public PostLoginJson(String unionid, String openid, String accessToken, String refreshToken, Integer expiresIn,
			String nickname, String sex, String constellation, Date birthday, String province, String city, String country,
			String headimgurl, String lang, String os, String userType)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.nickname = nickname;
		this.sex = sex;
		this.constellation = constellation;
		this.birthday = birthday;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headimgurl = headimgurl;
		this.lang = lang;
		this.os = os;
		this.userType = userType;
	}

	
	public PostLoginJson(String unionid, String openid, String accessToken, String refreshToken, Integer expiresIn,
			String nickname, String sex, String constellation, Date birthday, String province, String city, String country,
			String headimgurl, String lang, String os, String userType, String loginName, String password, String mobile)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.nickname = nickname;
		this.sex = sex;
		this.constellation = constellation;
		this.birthday = birthday;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headimgurl = headimgurl;
		this.lang = lang;
		this.os = os;
		this.userType = userType;
		this.loginName = loginName;
		this.password = password;
		this.mobile = mobile;
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

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getRefreshToken()
	{
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}

	public int getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getConstellation()
	{
		return constellation;
	}

	public void setConstellation(String constellation)
	{
		this.constellation = constellation;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getHeadimgurl()
	{
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}

	public String getLang()
	{
		return lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	public String getOs()
	{
		return os;
	}

	public void setOs(String os)
	{
		this.os = os;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	

}
