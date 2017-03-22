package com.innovate.cms.modules.qs.entity.msg;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * user comment
 * @author shifangfang
 * @date 2017年3月17日 下午2:54:48
 */
public class DynamicMsgComment extends DataEntity<DynamicMsgComment>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	  `cid` int(11) NOT NULL AUTO_INCREMENT,
//	  `mid` int(11) NOT NULL COMMENT '消息id',
//	  `uid` varchar(32) NOT NULL COMMENT '评论人/回复人',
//	  `headimgurl` varchar(512) DEFAULT '' COMMENT '用户头像',
//	  `content` varchar(255) DEFAULT '' COMMENT '评论/回复内容',
//	  `comment_type` tinyint(1) DEFAULT '0' COMMENT '评论类型0-评论1-回复',
//	  `reply_uid` varchar(32) NOT NULL COMMENT '被回复的用户id',
//	  `reply_name` varchar(255) DEFAULT '' COMMENT '被回复的用户名字',
//	  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	
	private int cid;
	private int mid;
	private String uid;
	private String cname;
	private String headimgurl;
	private String content;
	private int commentType;//评论类型0-评论1-回复
	private String replyUid="";
	private String replyName="";
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	public DynamicMsgComment() {
		super();
	}
	public DynamicMsgComment(String id) {
		super(id);
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCommentType() {
		return commentType;
	}
	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}
	public String getReplyUid() {
		return replyUid;
	}
	public void setReplyUid(String replyUid) {
		this.replyUid = replyUid;
	}
	
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
