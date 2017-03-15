package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;
/**
 * 
 * @ClassName: Groups
 * @Description: 专题实体
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年8月11日 下午7:56:59
 *
 */
public class Groups implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int gid;//专题编号
	private String groupName;//专题名称
	private String groupDescription;//专题描述
	private String templateTag;//模板标记
	private String groupIcon;//专题图
	private String smallIcon;//专题小图
	
	public Groups()
	{
		super();
	}
	
	public Groups(int gid, String groupName, String groupDescription, String templateTag, String groupIcon, String smallIcon)
	{
		super();
		this.gid = gid;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.templateTag = templateTag;
		this.groupIcon = groupIcon;
		this.smallIcon = smallIcon;
	}

	public int getGid()
	{
		return gid;
	}
	public void setGid(int gid)
	{
		this.gid = gid;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	public String getGroupDescription()
	{
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription)
	{
		this.groupDescription = groupDescription;
	}
	public String getTemplateTag()
	{
		return templateTag;
	}
	public void setTemplateTag(String templateTag)
	{
		this.templateTag = templateTag;
	}
	public String getGroupIcon()
	{
		return groupIcon;
	}
	public void setGroupIcon(String groupIcon)
	{
		this.groupIcon = groupIcon;
	}
	public String getSmallIcon()
	{
		return smallIcon;
	}
	public void setSmallIcon(String smallIcon)
	{
		this.smallIcon = smallIcon;
	}
}
