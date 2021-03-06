package com.innovate.cms.modules.common.entity;

import java.util.List;

/**
 * 
 * @author shifangfang
 * @date 2017年4月12日 下午3:07:02
 * @param <T>
 */
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
	// 用户签名
	private String backgroundImage;
	// 用户关注人数
	private int followsNum;
	// 用户粉丝人数
	private int followersNum;
	// 用户动态总数
	private int msgNum;
	// 用户所属群组总数
	private int tribleNum;
	private String sex; // 用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知
	// 用户相册
	private List<UserPic> pics;

	public MainPageBackInfo() {
	}

	public int getTribleNum() {
		return tribleNum;
	}

	public void setTribleNum(int tribleNum) {
		this.tribleNum = tribleNum;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public int getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
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
