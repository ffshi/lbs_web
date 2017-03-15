package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAtrribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<UserSelect> userShare =new ArrayList<>();
	private List<UserSelect> userSelectsA =new ArrayList<>();
	private List<UserSelect> userSelectsB =new ArrayList<>();
	public List<UserSelect> getUserShare() {
		return userShare;
	}
	public void setUserShare(List<UserSelect> userShare) {
		this.userShare = userShare;
	}
	public List<UserSelect> getUserSelectsA() {
		return userSelectsA;
	}
	public void setUserSelectsA(List<UserSelect> userSelectsA) {
		this.userSelectsA = userSelectsA;
	}
	public List<UserSelect> getUserSelectsB() {
		return userSelectsB;
	}
	public void setUserSelectsB(List<UserSelect> userSelectsB) {
		this.userSelectsB = userSelectsB;
	}
	public UserAtrribute(List<UserSelect> userShare, List<UserSelect> userSelectsA, List<UserSelect> userSelectsB) {
		super();
		this.userShare = userShare;
		this.userSelectsA = userSelectsA;
		this.userSelectsB = userSelectsB;
	}
	public UserAtrribute() {
	}
}
