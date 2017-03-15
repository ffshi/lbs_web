/**
 * 
 */
package com.innovate.cms.modules.qs.entity.ques;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 专题评论回复
 * 
 * @author hushasha
 * @date 2016年9月9日
 */
public class QxGroupsCommentReply extends DataEntity<QxGroupsCommentReply> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer gcrid; // 评论回复ID
	private Integer gcid; // 评论ID
	private String fromReplyUid; // 回复人ID
	private String fromReplyName; // 回复人昵称
	private String fromReplyImg; // 回复人头像
	private String toReplyUid; // 被回复人ID
	private String toReplyName; // 被回复人昵称
	private String reply; // 回复内容
	private Date updateTime; // 数据更新时间
	private Date createTime; // 数据创建时间

	public QxGroupsCommentReply() {
		super();
	}

	public Integer getGcrid() {
		return gcrid;
	}

	public void setGcrid(Integer gcrid) {
		this.gcrid = gcrid;
	}

	public Integer getGcid() {
		return gcid;
	}

	public void setGcid(Integer gcid) {
		this.gcid = gcid;
	}

	public String getFromReplyUid() {
		return fromReplyUid;
	}

	public void setFromReplyUid(String fromReplyUid) {
		this.fromReplyUid = fromReplyUid == null ? null : fromReplyUid.trim();
	}

	public String getFromReplyName() {
		return fromReplyName;
	}

	public void setFromReplyName(String fromReplyName) {
		this.fromReplyName = fromReplyName == null ? null : fromReplyName.trim();
	}

	public String getFromReplyImg() {
		String imgUrl = this.fromReplyImg;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFromReplyImg(String fromReplyImg) {
		this.fromReplyImg = fromReplyImg == null ? null : fromReplyImg.trim();
	}

	public String getToReplyUid() {
		return toReplyUid;
	}

	public void setToReplyUid(String toReplyUid) {
		this.toReplyUid = toReplyUid == null ? null : toReplyUid.trim();
	}

	public String getToReplyName() {
		return toReplyName;
	}

	public void setToReplyName(String toReplyName) {
		this.toReplyName = toReplyName == null ? null : toReplyName.trim();
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply == null ? null : reply.trim();
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

}
