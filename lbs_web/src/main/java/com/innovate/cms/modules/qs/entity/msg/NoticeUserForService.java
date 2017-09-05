package com.innovate.cms.modules.qs.entity.msg;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 参加报名后接收到通知的用户
 * 
 * @author shifangfang
 * @date 2017年9月5日 上午9:53:25
 */
public class NoticeUserForService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String nickname;
	private String headimgurl;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public NoticeUserForService() {
		super();
	}

	public NoticeUserForService(String uid, String nickname, String headimgurl) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
