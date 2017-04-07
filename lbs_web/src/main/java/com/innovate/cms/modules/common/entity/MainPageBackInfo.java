package com.innovate.cms.modules.common.entity;

import java.util.List;

public class MainPageBackInfo<T> extends DataBackInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private String nickname;
	private String headimgurl;
	// 用户号 类似qq
	private int uNum;
	// 用户签名
	private String personalSignature;
	// 用户关注人数
	private int followsNum;
	// 用户粉丝人数
	private int followersNum;

	// 用户相册
	private List<UserPic> pics;

	public MainPageBackInfo() {
	}

	public List<UserPic> getPics() {
		return pics;
	}

	public void setPics(List<UserPic> pics) {
		this.pics = pics;
	}

	public int getuNum() {
		return uNum;
	}

	public void setuNum(int uNum) {
		this.uNum = uNum;
	}

	public String getPersonalSignature() {
		return personalSignature;
	}

	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
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
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getFollowsNum() {
		return followsNum;
	}

	public void setFollowsNum(int followsNum) {
		this.followsNum = followsNum;
	}

	public int getFollowersNum() {
		return followersNum;
	}

	public void setFollowersNum(int followersNum) {
		this.followersNum = followersNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
