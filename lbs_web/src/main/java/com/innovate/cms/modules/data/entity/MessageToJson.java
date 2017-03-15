/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.modules.qs.entity.sns.MessagePost;

/**
 * 留言板列表 返回给前台的json样式
 * 
 * @author hushasha
 * @date 2016年4月8日
 */
public class MessageToJson implements Serializable {

	private static final long serialVersionUID = 1L;
	// 留言ID
	private String msid;
	// 发起留言用户ID
	private String fromId;
	// 发起留言用户头像
	private String fromImg;
	// 发起留言用户昵称
	private String fromName;
	// 留言内容
	private String fromContent;
	// 功能来源，1，留言板留言 2，足迹留言
	private String funType;
	// 数据创建时间
	private Date createTime;
	// 留言回复列表
	private List<MessagePost> replyLists;

	public MessageToJson() {
		super();
	}

	public MessageToJson(String msid, String fromId, String fromImg, String fromName, String fromContent, String funType, Date createTime, List<MessagePost> replyLists) {
		super();
		this.msid = msid;
		this.fromId = fromId;
		this.fromImg = fromImg;
		this.fromName = fromName;
		this.fromContent = fromContent;
		this.funType = funType;
		this.createTime = createTime;
		this.replyLists = replyLists;
	}

	public String getMsid() {
		return msid;
	}

	public void setMsid(String msid) {
		this.msid = msid;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getFromImg() {
		String imgUrl = this.fromImg;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFromImg(String fromImg) {
		this.fromImg = fromImg;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromContent() {
		return fromContent;
	}

	public void setFromContent(String fromContent) {
		this.fromContent = fromContent;
	}

	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonInclude(Include.ALWAYS)
	public List<MessagePost> getReplyLists() {
		return replyLists;
	}

	public void setReplyLists(List<MessagePost> replyLists) {
		this.replyLists = replyLists;
	}

}
