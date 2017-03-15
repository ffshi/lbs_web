/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.user;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 系统用户信息Entity
 * @author gaoji
 * @version 2016-04-12
 */
public class SystemUserInfo extends DataEntity<SystemUserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// 本地用户编号
	private String unionid;		// 获取用户个人信息（UnionID机制）
	private String openid;		// 当前来源渠道唯一标识
	private String accessToken;		// SDK接口调用凭证 微信 微博 QQ公用
	private String refreshToken;		// SDK用户刷新access_token微信独享
	private Integer expiresIn;		// SDKaccess_token的生命周期，单位是秒数
	private String webAccessToken;		// 网页授权接口调用凭证,与基础支持的access_token不同
	private String webRefreshToken;		// 用户刷新access_token
	private Integer webExpiresIn;		// access_token接口调用凭证超时时间，单位（秒）
	private String loginIp;		// 最后登陆IP
	private Date loginDate;		// 最后登陆时间
	private Date updateTime;		// 数据更新时间
	
	public SystemUserInfo() {
		super();
	}

	public SystemUserInfo(String unionid, String openid, String accessToken, String refreshToken, Integer expiresIn,
			String loginIp, Date loginDate)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.loginIp = loginIp;
		this.loginDate = loginDate;
	}

	public SystemUserInfo(String unionid, String openid, String webAccessToken, String webRefreshToken, Integer webExpiresIn,
			String loginIp, Date loginDate, Date updateTime)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
		this.webAccessToken = webAccessToken;
		this.webRefreshToken = webRefreshToken;
		this.webExpiresIn = webExpiresIn;
		this.loginIp = loginIp;
		this.loginDate = loginDate;
		this.updateTime = updateTime;
	}

	public SystemUserInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="本地用户编号长度必须介于 1 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=1, max=64, message="获取用户个人信息（UnionID机制）长度必须介于 1 和 64 之间")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	@Length(min=1, max=64, message="当前来源渠道唯一标识长度必须介于 1 和 64 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=1, max=300, message="SDK接口调用凭证 微信 微博 QQ公用长度必须介于 1 和 300 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=0, max=100, message="SDK用户刷新access_token微信独享长度必须介于 0 和 100 之间")
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	@Length(min=0, max=300, message="网页授权接口调用凭证,与基础支持的access_token不同长度必须介于 0 和 300 之间")
	public String getWebAccessToken() {
		return webAccessToken;
	}

	public void setWebAccessToken(String webAccessToken) {
		this.webAccessToken = webAccessToken;
	}
	
	@Length(min=0, max=300, message="用户刷新access_token长度必须介于 0 和 300 之间")
	public String getWebRefreshToken() {
		return webRefreshToken;
	}

	public void setWebRefreshToken(String webRefreshToken) {
		this.webRefreshToken = webRefreshToken;
	}
	
	public Integer getWebExpiresIn() {
		return webExpiresIn;
	}

	public void setWebExpiresIn(Integer webExpiresIn) {
		this.webExpiresIn = webExpiresIn;
	}
	
	@Length(min=0, max=100, message="最后登陆IP长度必须介于 0 和 100 之间")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}