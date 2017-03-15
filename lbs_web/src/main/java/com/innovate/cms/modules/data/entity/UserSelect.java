package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 用户态度
 * 
 * @author shifangfang
 * @date 2016年11月1日 下午4:18:47
 */
public class UserSelect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid; // 本地用户编号
	private String nickname; // 昵称
	private String headimgurl; // 用户头像,最后一个数值代表正方形头像大小
	private Integer seqId;

	public UserSelect() {
	}

	public UserSelect(String uid, String nickname, String headimgurl, Integer seqId) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.seqId = seqId;
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

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

}
