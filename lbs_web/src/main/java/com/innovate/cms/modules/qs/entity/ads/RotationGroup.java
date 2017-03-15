package com.innovate.cms.modules.qs.entity.ads;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * wishbone版本首页轮播专题配置
 * 
 * @author shifangfang
 * @date 2016年11月30日 下午2:56:52
 */
public class RotationGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private int id;
	private int gid;
	private String groupName;
	private String groupDescription;
	private String smallIcon;
	private String templateTag;

	public RotationGroup() {
		super();
	}

	public RotationGroup(int id, int gid, String groupName, String groupDescription, String smallIcon, String templateTag) {
		super();
		this.id = id;
		this.gid = gid;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.smallIcon = smallIcon;
		this.templateTag = templateTag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
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
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	// `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	// `gid` int(11) NOT NULL COMMENT '专题ID',
	// `group_name` varchar(100) NOT NULL DEFAULT '' COMMENT '专题名称',
	// `group_description` varchar(255) NOT NULL DEFAULT '' COMMENT '专题描述',
	// `small_icon` varchar(255) NOT NULL DEFAULT '' COMMENT '专题图片',
	// `template_tag` tinyint(1) NOT NULL DEFAULT '-1' COMMENT '模板标识',
	// `show_date` int(8) NOT NULL COMMENT '专题展示时间如20161129',

}
