/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 消息二级页面：关注消息列表
 * @author hushasha
 * @date 2016年8月4日
 */
public class FollowMsgToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String umid; //消息ID
	private String msgName; // 关注人昵称
	private String msgImg; // 关注人头像
	private String msgJumpId; // 消息跳转ID（关注人的ID)
	private String msgContent1; // 消息内容
	private Date createTime; // 关注时间
	private int isFriend; //是否已是好友, 0 否 1是
	public FollowMsgToJson() {
		super();
	}

	public FollowMsgToJson(String umid, String msgName, String msgImg, String msgJumpId, String msgContent1,
			Date createTime, int isFriend) {
		super();
		this.umid = umid;
		this.msgName = msgName;
		this.msgImg = msgImg;
		this.msgJumpId = msgJumpId;
		this.msgContent1 = msgContent1;
		this.createTime = createTime;
		this.isFriend = isFriend;
	}

	public String getUmid() {
		return umid;
	}

	public void setUmid(String umid) {
		this.umid = umid;
	}

	public String getMsgName() {
		return msgName;
	}
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	public String getMsgImg() {
		return msgImg;
	}
	public void setMsgImg(String msgImg) {
		this.msgImg = msgImg;
	}
	public String getMsgJumpId() {
		return msgJumpId;
	}
	public void setMsgJumpId(String msgJumpId) {
		this.msgJumpId = msgJumpId;
	}
	public String getMsgContent1() {
		return msgContent1;
	}
	public void setMsgContent1(String msgContent1) {
		this.msgContent1 = msgContent1;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getIsFriend() {
		return isFriend;
	}
	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}
	
}
