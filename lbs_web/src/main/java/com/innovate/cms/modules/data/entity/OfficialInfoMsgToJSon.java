package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 获取系统消息二级列表json
 * 
 * @author shifangfang
 * @date 2016年8月12日 下午7:49:41
 */
public class OfficialInfoMsgToJSon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msgName;

	private String msgImg;

	private String msgType;

	private String msgJumpId;

	private String createTime;

	private String umid;

	private String msgContent1;

	public OfficialInfoMsgToJSon() {
		super();
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

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgJumpId() {
		return msgJumpId;
	}

	public void setMsgJumpId(String msgJumpId) {
		this.msgJumpId = msgJumpId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUmid() {
		return umid;
	}

	public void setUmid(String umid) {
		this.umid = umid;
	}

	public String getMsgContent1() {
		return msgContent1;
	}

	public void setMsgContent1(String msgContent1) {
		this.msgContent1 = msgContent1;
	}

}
