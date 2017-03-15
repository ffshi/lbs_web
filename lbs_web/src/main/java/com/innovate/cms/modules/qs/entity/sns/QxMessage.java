/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.sns;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 留言表Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxMessage extends DataEntity<QxMessage> {
	
	private static final long serialVersionUID = 1L;
	private String msid;		// 留言ID
	private String toId;		// 接收留言用户ID
	private String toName;		// 接收留言用户昵称
	private String fromId;		// 发起留言用户ID
	private String fromImg;		// 发起留言用户头像
	private String fromName;		// 发起留言用户昵称
	private String fromContent;		// 留言内容
	private String funType;      //功能来源，1，留言板留言 2，足迹留言
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public QxMessage() {
		super();
	}

	public QxMessage(String id){
		super(id);
	}

	@Length(min=1, max=64, message="留言ID长度必须介于 1 和 64 之间")
	public String getMsid() {
		return msid;
	}

	public void setMsid(String msid) {
		this.msid = msid;
	}
	
	@Length(min=1, max=64, message="接收留言用户ID长度必须介于 1 和 64 之间")
	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}
	
	@Length(min=1, max=50, message="接收留言用户昵称长度必须介于 1 和 50 之间")
	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
	
	@Length(min=1, max=64, message="发起留言用户ID长度必须介于 1 和 64 之间")
	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	@Length(min=1, max=300, message="发起留言用户头像长度必须介于 1 和 300 之间")
	public String getFromImg() {
		String imgUrl = this.fromImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFromImg(String fromImg) {
		this.fromImg = fromImg;
	}
	
	@Length(min=1, max=50, message="发起留言用户昵称长度必须介于 1 和 50 之间")
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	@Length(min=1, max=1000, message="留言内容长度必须介于 1 和 1000 之间")
	public String getFromContent() {
		return fromContent;
	}

	public void setFromContent(String fromContent) {
		this.fromContent = fromContent;
	}
	@Length(min=1, max=2, message="功能来源长度必须介于 1 和 2 之间")
	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}