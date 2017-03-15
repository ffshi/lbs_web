package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 一级消息列表
 * 
 * @author shifangfang
 * @date 2016年8月11日 下午5:54:34
 */
public class OneLevelMsgToJson implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msgGroupImg;

	private String msgGroupName;

	private int count;
	private int umid;
	private int msgGroupId;// msg_group_id;
	private String msgContent1;
	private String msgType;
	private String msgName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	// private String msgType;

	public OneLevelMsgToJson() {
		super();
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public void setMsgGroupImg(String msgGroupImg) {
		this.msgGroupImg = msgGroupImg;
	}

	public String getMsgGroupImg() {
		return this.msgGroupImg;
	}

	public int getMsgGroupId() {
		return msgGroupId;
	}

	public void setMsgGroupId(int msgGroupId) {
		this.msgGroupId = msgGroupId;
	}

	public int getUmid() {
		return umid;
	}

	public void setUmid(int umid) {
		this.umid = umid;
	}

	public void setMsgGroupName(String msgGroupName) {
		this.msgGroupName = msgGroupName;
	}

	public String getMsgGroupName() {
		return this.msgGroupName;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return this.count;
	}

	public void setMsgContent1(String msgContent1) {
		this.msgContent1 = msgContent1;
	}

	public String getMsgContent1() {
		return this.msgContent1;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
