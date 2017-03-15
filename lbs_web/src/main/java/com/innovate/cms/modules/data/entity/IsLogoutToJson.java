package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.modules.common.entity.BaseBackInfo;

public class IsLogoutToJson extends BaseBackInfo implements Serializable
{
	 
	private static final long serialVersionUID = 1L;
	/**
	 * Logout 是否失效
	 */
	private int isLogout;
	

	public IsLogoutToJson()
	{
		super();
	}

	public IsLogoutToJson(int isLogout)
	{
		super();
		this.isLogout = isLogout;
	}

	public void setIsLogout(int isLogout)
	{
		this.isLogout = isLogout;
	}

	public int getIsLogout()
	{
		return this.isLogout;
	}
}