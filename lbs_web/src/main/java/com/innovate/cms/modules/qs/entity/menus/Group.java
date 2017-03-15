package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;

/**
 * 根据专题组返回group菜单bean
 * 
 * @author shifangfang
 *
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer gid;// 专题编号
	private Integer fsid;// 专题组编号
	private String groupName;// 专题名称
	private String groupDescription;// 专题描述
	private String templateTag;// 模板标记模板1,2,3,4,5
	private String smallIcon;// 专题小图
	private String icon;// 专题大图
	private String rankTitle;// 排行榜自定义标题
	private String resDecription;// 专题做题结果页描述

	public Group() {
		super();
	}

	public Group(Integer gid, Integer fsid, String groupName, String groupDescription, String templateTag, String smallIcon, String icon) {
		super();
		this.gid = gid;
		this.fsid = fsid;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.templateTag = templateTag;
		this.smallIcon = smallIcon;
		this.icon = icon;
	}

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

	public String getResDecription() {
		return resDecription;
	}

	public void setResDecription(String resDecription) {
		this.resDecription = resDecription;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
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

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public String getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
