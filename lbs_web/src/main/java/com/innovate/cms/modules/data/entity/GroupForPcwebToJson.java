package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 
 * 为pcweb端提供专题数据
 * 
 * @author shifangfang
 * @date 2016年8月19日 下午2:24:36
 */
public class GroupForPcwebToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int gid;
	private int fsid;
	private String groupName;
	private String groupDescription;
	private String templateTag;
	private String smallIcon;
	private String icon;

	public GroupForPcwebToJson() {
		super();
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getFsid() {
		return fsid;
	}

	public void setFsid(int fsid) {
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
