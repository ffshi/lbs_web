package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;

/**
 * 
 * @ClassName: Menu
 * @Description: 更改为仅仅拉取channel即可
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年8月11日 下午8:46:45
 *
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer cid;

	private String channelName;

	private String icon;
	

	public Menu()
	{
		super();
	}

	public Menu(Integer cid, String channelName, String icon)
	{
		super();
		this.cid = cid;
		this.channelName = channelName;
		this.icon = icon;
	}

	public Integer getCid()
	{
		return cid;
	}

	public void setCid(Integer cid)
	{
		this.cid = cid;
	}

	public String getChannelName()
	{
		return channelName;
	}

	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}
}
