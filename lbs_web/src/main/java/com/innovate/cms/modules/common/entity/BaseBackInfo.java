package com.innovate.cms.modules.common.entity;

import java.io.Serializable;

/**
 * backInfo 基类，所有backinfo继承该类
 * 
 * @author shifangfang
 *
 * @param <T>
 */
public class BaseBackInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 返回代码
	 */
	private int stateCode;
	/**
	 * 返回信息
	 */
	private String retMsg;


	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public int getStateCode() {
		return this.stateCode;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getRetMsg() {
		return this.retMsg;
	}


}
