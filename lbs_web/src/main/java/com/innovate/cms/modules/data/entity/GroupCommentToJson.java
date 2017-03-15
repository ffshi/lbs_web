/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 专题评论 返回json样式
 * 
 * @author hushasha
 * @date 2016年9月12日
 */
public class GroupCommentToJson implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer gcid; //评论ID
	private String fromCommentUid; // 评论人ID
	private String fromCommentName; // 评论人昵称
	private String fromCommentImg; // 评论人头像
	private String comment; // 评论内容
	private String createTime; // 数据创建时间

	public GroupCommentToJson() {
		super();
	}
	
	public Integer getGcid() {
		return gcid;
	}

	public void setGcid(Integer gcid) {
		this.gcid = gcid;
	}

	public String getFromCommentUid() {
		return fromCommentUid;
	}

	public void setFromCommentUid(String fromCommentUid) {
		this.fromCommentUid = fromCommentUid;
	}

	public String getFromCommentName() {
		return fromCommentName;
	}

	public void setFromCommentName(String fromCommentName) {
		this.fromCommentName = fromCommentName;
	}

	public String getFromCommentImg() {
		return fromCommentImg;
	}

	public void setFromCommentImg(String fromCommentImg) {
		this.fromCommentImg = fromCommentImg;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
