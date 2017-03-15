/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.sns;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 用户消息表
 * 
 * @author shifangfang
 *
 */
public class QxUserMsg extends DataEntity<QxUserMsg> {

	private static final long serialVersionUID = 1L;

	private Integer umid; // 用户消息id
	private String uid; // 所属用户id
	private String msgName; // 消息发起名称
	private String msgImg; // 消息头像
	private String msgType; // 消息类型（1留言、2回复、3关注、4提醒、5推荐、6更新、7专题）
	private String msgTypeName; // 消息类型名称，（1留言 、2回复、3关注 、 4提醒 、 5推荐、6官方、7官方）
	private String msgJumpId; // 消息跳转ID（留言、回复、用户、专题、更新页）id
	private String msgContent1; // 消息内容1
	private String msgContent2; // 消息内容2
	private String msgContent3; // 消息内容3
	private Date updateTime; // 数据更新时间
	private Date createTime; // 消息时间
	private String delFlag; // 删除标记0正常1删除

	public QxUserMsg() {
		super();
	}

	public Integer getUmid() {
		return umid;
	}

	public void setUmid(Integer umid) {
		this.umid = umid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getMsgImg() {
		String imgUrl = this.msgImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
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

	public String getMsgTypeName() {
		return msgTypeName;
	}

	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
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

	public String getMsgContent2() {
		return msgContent2;
	}

	public void setMsgContent2(String msgContent2) {
		this.msgContent2 = msgContent2;
	}

	public String getMsgContent3() {
		return msgContent3;
	}

	public void setMsgContent3(String msgContent3) {
		this.msgContent3 = msgContent3;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}