package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 好友可见
 * 
 * @author shifangfang
 * @date 2016年8月19日 上午8:48:11
 */
public class VisibleToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int visible;

	public VisibleToJson() {
		super();
	}

	public VisibleToJson(int visible) {
		super();
		this.visible = visible;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

}
