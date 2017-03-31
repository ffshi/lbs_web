/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

/**
 * 用户基本属性---性别、年龄、星座、地区 返回json样式
 * 
 * @author hushasha
 * @date 2016年7月29日
 */
public class BaseUserPropertyToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid; // 本地用户编号
	private String nickname; // 用户昵称
	private String headimgurl; // 用户头像
	private String sex; // 用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知
	private Integer age; // 年龄
	private String constellation; // 星座
	private String province; // 归属省会
	private String city; // 归属市级城市

	private int uNum;//本平台用户号,类似QQ
	public BaseUserPropertyToJson() {
		super();
	}

	public BaseUserPropertyToJson(String uid, String nickname, String headimgurl, String sex, Integer age, String constellation, String province, String city) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.sex = sex;
		this.age = age;
		this.constellation = constellation;
		this.province = province;
		this.city = city;
	}

	public int getuNum() {
		return uNum;
	}

	public void setuNum(int uNum) {
		this.uNum = uNum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		String imgUrl = this.headimgurl;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
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

}
