package com.innovate.cms.modules.qs.entity.menus;

/**
 * 
 */


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 专题评论
 * 
 * @author hushasha
 * @date 2016年9月9日
 */
public class QxGroupComment extends DataEntity<QxGroupComment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer gcid; // 评论ID
	private Integer gid; // 专题ID
	private String fromCommentUid; // 评论人ID
	private String fromCommentName; // 评论人昵称
	private String fromCommentImg; // 评论人头像
	private String comment; // 评论内容
	private Date updateTime; // 数据更新时间
	private Date createTime; // 数据创建时间

	public QxGroupComment() {
		super();
	}

	public Integer getGcid() {
		return gcid;
	}

	public void setGcid(Integer gcid) {
		this.gcid = gcid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getFromCommentUid() {
		return fromCommentUid;
	}

	public void setFromCommentUid(String fromCommentUid) {
		this.fromCommentUid = fromCommentUid == null ? null : fromCommentUid.trim();
	}

	public String getFromCommentName() {
		return fromCommentName;
	}

	public void setFromCommentName(String fromCommentName) {
		this.fromCommentName = fromCommentName == null ? null : fromCommentName.trim();
	}

	public String getFromCommentImg() {
		String imgUrl = this.fromCommentImg;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFromCommentImg(String fromCommentImg) {
		this.fromCommentImg = fromCommentImg == null ? null : fromCommentImg.trim();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
