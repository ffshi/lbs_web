/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Strings;
import com.innovate.cms.common.config.Global;

/**
 * 好友动态Json格式
 * @author hushasha
 * @date 2016年12月2日
 */
public class FollowDynamicStateToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer dsid; //动态标识ID
	private String uid; //用户ID
	private String nickname; //用户昵称
	private String headimgurl; //用户头像
	private Integer gid; //专题ID
	private String groupName; //专题名称
	private String groupDescription; //专题描述
	private String smallIcon; //专题图片
	private Integer templateTag; // 模板标识：0, 4
	private Integer state; //状态：1、创建站边专题，2、站边题做题记录，3、模板4做题记录
	private Date createTime; //创建时间
	
	public FollowDynamicStateToJson() {
		super();
	}
	
	public Integer getDsid() {
		return dsid;
	}

	public void setDsid(Integer dsid) {
		this.dsid = dsid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		String imgUrl = this.headimgurl;
		if (!Strings.isNullOrEmpty(imgUrl) && imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getSmallIcon() {
		String imgUrl = this.smallIcon;
		if (!Strings.isNullOrEmpty(imgUrl) && imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public Integer getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(Integer templateTag) {
		this.templateTag = templateTag;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
