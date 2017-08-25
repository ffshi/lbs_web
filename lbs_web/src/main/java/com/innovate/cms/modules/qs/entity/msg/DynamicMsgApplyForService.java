package com.innovate.cms.modules.qs.entity.msg;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 活动报名用户列表bean
 * 
 * @author shifangfang
 * @date 2017年3月17日 下午4:07:04
 */
public class DynamicMsgApplyForService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int aid;
	private int mid;
	//审核状态-1-未报名 0-未审核 1-通过 2-拒绝 
	@JSONField(name = "check_state")
	private int checkState;
	private String uid;
	private String nickname;
	private String headimgurl;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "create_time")
	private Date createTime;

	public DynamicMsgApplyForService() {
		super();
	}

	public DynamicMsgApplyForService(int aid, int mid, int checkState, String uid, String nickname, String headimgurl, Date createTime) {
		super();
		this.aid = aid;
		this.mid = mid;
		this.checkState = checkState;
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.createTime = createTime;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
