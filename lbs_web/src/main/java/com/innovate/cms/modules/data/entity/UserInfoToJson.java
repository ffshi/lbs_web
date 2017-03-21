package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.config.Global;

public class UserInfoToJson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Mapping("uid")
	private String uid; // 本地用户编号
	private String unionid; // 获取用户个人信息（UnionID机制）
	private String nickname; // 昵称
	private String sex; // 用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知
	private String constellation; // 星座
	private Date birthday; // 生日
	private String province; // 归属省会
	private String city; // 归属市级城市
	private String country; // 国家，如中国为CN
	private String headimgurl = ""; // 用户头像,最后一个数值代表正方形头像大小
	private String userGroups; // 用户分组、预留
	private String userType; // 用户类型0、官方1、普通2、微信3、web微信4、QQ5、微博

	public UserInfoToJson() {
		super();
	}

	public UserInfoToJson(String uid, String unionid, String nickname, String sex, String constellation, Date birthday, String province, String city, String country, String headimgurl, String userGroups, String userType) {
		super();
		this.uid = uid;
		this.unionid = unionid;
		this.nickname = nickname;
		this.sex = sex;
		this.constellation = constellation;
		this.birthday = birthday;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headimgurl = headimgurl;
		this.userGroups = userGroups;
		this.userType = userType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @Title: getHeadimgurl
	 * @Description: 对系统上传头像不带http://的做特殊处理
	 * @return 返回类型 String
	 *
	 */
	public String getHeadimgurl() {
		String imgUrl = this.headimgurl;
		if (null != imgUrl && imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP) || imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(String userGroups) {
		this.userGroups = userGroups;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
