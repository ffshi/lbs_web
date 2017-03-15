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
 * 回复表Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxMessagePost extends DataEntity<QxMessagePost> {
	
	private static final long serialVersionUID = 1L;
	private String poid;		// 回复ID
	private String msid;		// 留言表ID
	private String fromReplyUid;		// 回复人ID
	private String fromReplyName;		// 回复人昵称
	private String fromReplyImg;    //留言人头像
	private String toReplyUid;		// 被回复人ID
	private String toReplyName;		// 被回复人昵称
	private String replyContent;		// 回复内容
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public QxMessagePost() {
		super();
	}

	public QxMessagePost(String id){
		super(id);
	}

	@Length(min=1, max=64, message="回复ID长度必须介于 1 和 64 之间")
	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}
	
	@Length(min=1, max=64, message="留言表ID长度必须介于 1 和 64 之间")
	public String getMsid() {
		return msid;
	}

	public void setMsid(String msid) {
		this.msid = msid;
	}
	
	@Length(min=1, max=64, message="回复人ID长度必须介于 1 和 64 之间")
	public String getFromReplyUid() {
		return fromReplyUid;
	}

	public void setFromReplyUid(String fromReplyUid) {
		this.fromReplyUid = fromReplyUid;
	}
	
	@Length(min=1, max=50, message="回复人昵称长度必须介于 1 和 50 之间")
	public String getFromReplyName() {
		return fromReplyName;
	}

	public void setFromReplyName(String fromReplyName) {
		this.fromReplyName = fromReplyName;
	}
	
	@Length(min=1, max=300, message="留言人头像VARCHAR长度必须介于 1 和 300 之间")
	public String getFromReplyImg() {
		String imgUrl = this.fromReplyImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFromReplyImg(String fromReplyImg) {
		this.fromReplyImg = fromReplyImg;
	}

	@Length(min=1, max=64, message="被回复人ID长度必须介于 1 和 64 之间")
	public String getToReplyUid() {
		return toReplyUid;
	}

	public void setToReplyUid(String toReplyUid) {
		this.toReplyUid = toReplyUid;
	}
	
	@Length(min=1, max=50, message="被回复人昵称长度必须介于 1 和 50 之间")
	public String getToReplyName() {
		return toReplyName;
	}

	public void setToReplyName(String toReplyName) {
		this.toReplyName = toReplyName;
	}
	
	@Length(min=1, max=300, message="回复内容长度必须介于 1 和 300 之间")
	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
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