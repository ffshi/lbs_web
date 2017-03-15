package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * 模板0做题历史
 * 
 * @author shifangfang
 * @date 2016年11月15日 上午10:27:11
 */
public class T0History implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gid;
	private int selected;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public T0History() {
	}

	public T0History(int gid, int selected) {
		super();
		this.gid = gid;
		this.selected = selected;
	}

}
