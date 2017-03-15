package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: ConfigTimeToJson
 * @Description: v1.0 获取系统日期
 * @author gaoji gaoji_cyou-inc_com
 * @date 2015年12月28日 下午2:10:41
 *
 */
public class ConfigTimeToJson implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String timestamp;

	public ConfigTimeToJson()
	{
		super();
	}

	public ConfigTimeToJson(String timestamp)
	{
		super();
		this.timestamp = timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getTimestamp()
	{
		return this.timestamp;
	}
}
