/**
 * 
 */
package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 留言板列表 部分json样式
 * 
 * @author hushasha
 * @date 2016年4月11日
 */
public class MessagePost implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	// 回复ID
	private String poid;
	// 回复人ID
	private String fromReplyUid;
	// 回复人昵称
	private String fromReplyName;
	// 留言人头像
	private String fromReplyImg;
	// 被回复人ID
	private String toReplyUid;
	// 被回复人昵称
	private String toReplyName;
	// 回复内容
	private String replyContent;
	// 数据创建时间
	private Date createTime;

	public MessagePost() {
		super();
	}

	public MessagePost(String poid, String fromReplyUid, String fromReplyName, String fromReplyImg, String toReplyUid, String toReplyName, String replyContent, Date createTime) {
		super();
		this.poid = poid;
		this.fromReplyUid = fromReplyUid;
		this.fromReplyName = fromReplyName;
		this.fromReplyImg = fromReplyImg;
		this.toReplyUid = toReplyUid;
		this.toReplyName = toReplyName;
		this.replyContent = replyContent;
		this.createTime = createTime;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	public String getFromReplyUid() {
		return fromReplyUid;
	}

	public void setFromReplyUid(String fromReplyUid) {
		this.fromReplyUid = fromReplyUid;
	}

	public String getFromReplyName() {
		return fromReplyName;
	}

	public void setFromReplyName(String fromReplyName) {
		this.fromReplyName = fromReplyName;
	}

	public String getFromReplyImg() {
		return fromReplyImg;
	}

	public void setFromReplyImg(String fromReplyImg) {
		this.fromReplyImg = fromReplyImg;
	}

	public String getToReplyUid() {
		return toReplyUid;
	}

	public void setToReplyUid(String toReplyUid) {
		this.toReplyUid = toReplyUid;
	}

	public String getToReplyName() {
		return toReplyName;
	}

	public void setToReplyName(String toReplyName) {
		this.toReplyName = toReplyName;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
