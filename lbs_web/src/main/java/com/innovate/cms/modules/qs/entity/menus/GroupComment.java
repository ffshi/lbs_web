package com.innovate.cms.modules.qs.entity.menus;

/**
 * 
 */


import java.io.Serializable;

/**
 * 专题评论 返回gcid json样式
 * 
 * @author hushasha
 * @date 2016年9月9日
 */
public class GroupComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer gcid; // 评论ID

	public GroupComment() {
		super();
	}

	public Integer getGcid() {
		return gcid;
	}

	public void setGcid(Integer gcid) {
		this.gcid = gcid;
	}
}
