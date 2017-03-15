package com.innovate.cms.modules.qs.entity.tag;

import java.io.Serializable;

/**
 * 趣选用户tag
 * 
 * @author shifangfang
 * @date 2016年8月23日 下午3:04:36
 */
public class QxTagToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tagId;
	private String tagName;

	public QxTagToJson() {
		super();
		// TODO Auto-generated constructor stub
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
