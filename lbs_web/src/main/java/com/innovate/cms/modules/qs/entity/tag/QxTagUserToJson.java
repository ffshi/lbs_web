package com.innovate.cms.modules.qs.entity.tag;

import java.io.Serializable;

/**
 * 趣选用户tag
 * 
 * @author shifangfang
 * @date 2016年8月23日 下午3:04:36
 */
public class QxTagUserToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tagId;
	private String tagName;
	private int nums;

	public QxTagUserToJson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
