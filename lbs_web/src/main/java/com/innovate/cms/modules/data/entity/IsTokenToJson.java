package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.modules.common.entity.BaseBackInfo;

public class IsTokenToJson extends BaseBackInfo implements Serializable
{
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */ 
	private static final long serialVersionUID = 1L;
	/**
	 * token 是否失效
	 */
	private int isToken;

	public IsTokenToJson()
	{
		super();
	}

	public IsTokenToJson(int isToken)
	{
		super();
		this.isToken = isToken;
	}

	public void setIsToken(int isToken)
	{
		this.isToken = isToken;
	}

	public int getIsToken()
	{
		return this.isToken;
	}
}
