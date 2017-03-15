package com.innovate.cms.modules.common.entity;

public class MainPageBackInfo<T> extends DataBackInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private String nickname;
	private String headimgurl;
	// 用户上传题目数
	private int ugcCount;
	// 用户关注人数
	private int followsNum;
	// 用户粉丝人数
	private int followersNum;
	// 用户足迹数
	private int historyNum;

	public MainPageBackInfo() {
	}

	public MainPageBackInfo(String uid, String nickname, String headimgurl, int ugcCount, int followsNum, int followersNum) {
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.ugcCount = ugcCount;
		this.followsNum = followsNum;
		this.followersNum = followersNum;
	}

	public int getHistoryNum() {
		return historyNum;
	}

	public void setHistoryNum(int historyNum) {
		this.historyNum = historyNum;
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

	public int getUgcCount() {
		return ugcCount;
	}

	public void setUgcCount(int ugcCount) {
		this.ugcCount = ugcCount;
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
