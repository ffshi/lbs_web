/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.user;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 系统用户Entity
 * @author gaoji
 * @version 2016-04-12
 */
public class SystemUser extends DataEntity<SystemUser> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// 本地用户编号
	private String unionid;		// 获取用户个人信息（UnionID机制）
	private String openid;		// 当前来源渠道唯一标识
	private String nickname="";		// 昵称
	private String sex="-1";		// 用户的性别，值为1时是男性，值为2时是女性，默认值为-1时是未知
	private String tokenLocal;		// 本地系统登陆认证token，退出登录删除该token
	private String loginName;		// 登录名-可为账号
	private String mobile;		// 手机-可为账号
	private String password;		// 密码
	private String constellation;		// 星座
	private Date birthday;		// 生日
	private String province="-1";		// 归属省会
	private String city="-1";		// 归属市级城市
	private String country;		// 国家，如中国为CN
	private String headimgurl;		// 用户头像,最后一个数值代表正方形头像大小
	private String lang;		// 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	private String email;		// 邮箱
	private String os;		// 用户客户端ios或者android
	private String userGroups;		// 用户分组、预留
	private String userType;		// 用户类型0、官方1、普通2、微信3、web微信4、QQ5、微博
	private String loginFlag;		// 是否可登录0可登陆1不可登陆
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	@JSONField(name="u_num")
	private int uNum;//用户数字账号,类似qq号
	//个人签名
	@JSONField(name="personal_signature")
	private String personalSignature;
	//用户背景图
	@JSONField(name="background_image")
	private String backgroundImage="";
	
	
	public SystemUser() {
		super();
	}

	public SystemUser(String unionid, String openid, String nickname, String sex, String constellation, Date birthday,
			String province, String city, String country, String headimgurl, String lang, String os, String userType,
			Date createTime,String mobile,String password)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
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
		this.createTime = createTime;
		this.mobile = mobile;
		this.password = password;
	}
	public SystemUser(String unionid, String openid, String nickname, String sex, String constellation, Date birthday,
			String province, String city, String country, String headimgurl, String lang, String os, String userType,
			Date createTime)
	{
		super();
		this.unionid = unionid;
		this.openid = openid;
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
		this.createTime = createTime;
	}
	

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public SystemUser(String id){
		super(id);
	}
	
	
	public String getPersonalSignature() {
		return personalSignature;
	}

	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
	}

	public int getuNum() {
		return uNum;
	}

	public void setuNum(int uNum) {
		this.uNum = uNum;
	}

	@Length(min=1, max=64, message="本地用户编号长度必须介于 1 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	//@Length(min=1, max=64, message="获取用户个人信息（UnionID机制）长度必须介于 1 和 64 之间")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	//@Length(min=1, max=64, message="当前来源渠道唯一标识长度必须介于 1 和 64 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	//@Length(min=0, max=100, message="昵称长度必须介于 0 和 100 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=2, message="用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知长度必须介于 0 和 2 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=100, message="本地系统登陆认证token，退出登录删除该token长度必须介于 0 和 100 之间")
	public String getTokenLocal() {
		return tokenLocal;
	}

	public void setTokenLocal(String tokenLocal) {
		this.tokenLocal = tokenLocal;
	}
	
	@Length(min=0, max=100, message="登录名-可为账号长度必须介于 0 和 100 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=13, message="手机-可为账号长度必须介于 0 和 13 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=100, message="密码长度必须介于 0 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=100, message="星座长度必须介于 0 和 100 之间")
	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=30, message="归属省会长度必须介于 0 和 30 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=30, message="归属市级城市长度必须介于 0 和 30 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=30, message="国家，如中国为CN长度必须介于 0 和 30 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=1000, message="用户头像,最后一个数值代表正方形头像大小长度必须介于 0 和 1000 之间")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	@Length(min=0, max=10, message="返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语长度必须介于 0 和 10 之间")
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	@Length(min=0, max=200, message="邮箱长度必须介于 0 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=10, message="用户客户端ios或者android长度必须介于 0 和 10 之间")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	@Length(min=0, max=10, message="用户分组、预留长度必须介于 0 和 10 之间")
	public String getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(String userGroups) {
		this.userGroups = userGroups;
	}
	
	@Length(min=0, max=1, message="用户类型0、官方1、普通2、微信3、web微信4、QQ5、微博长度必须介于 0 和 1 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Length(min=0, max=64, message="是否可登录0可登陆1不可登陆长度必须介于 0 和 64 之间")
	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}