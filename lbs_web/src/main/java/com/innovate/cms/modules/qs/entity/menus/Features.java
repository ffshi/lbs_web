package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: Features
 * @Description: 专题组实体
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年8月11日 下午7:57:16
 *
 */
public class Features implements Serializable
{

	private static final long serialVersionUID = 1L;

	private int fsid;//专题组编号
	private String featureName;//专题组名称
	private String featureIcon;//专题组图标
	private List<Groups> groups;//专题组集合
	
	public Features()
	{
		super();
	}

	public Features(int fsid, String featureName, String featureIcon, List<Groups> groups)
	{
		super();
		this.fsid = fsid;
		this.featureName = featureName;
		this.featureIcon = featureIcon;
		this.groups = groups;
	}

	public int getFsid()
	{
		return fsid;
	}

	public void setFsid(int fsid)
	{
		this.fsid = fsid;
	}

	public String getFeatureName()
	{
		return featureName;
	}

	public void setFeatureName(String featureName)
	{
		this.featureName = featureName;
	}

	public String getFeatureIcon()
	{
		return featureIcon;
	}

	public void setFeatureIcon(String featureIcon)
	{
		this.featureIcon = featureIcon;
	}

	public List<Groups> getGroups()
	{
		return groups;
	}

	public void setGroups(List<Groups> groups)
	{
		this.groups = groups;
	}
	
}
