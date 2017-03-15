package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

public class IsFriend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 0:不是 1：是
	private int isFriend;

	public IsFriend() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IsFriend(int isFriend) {
		super();
		this.isFriend = isFriend;
	}

	public int getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}

}
