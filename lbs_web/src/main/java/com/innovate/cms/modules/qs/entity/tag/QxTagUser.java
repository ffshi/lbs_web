package com.innovate.cms.modules.qs.entity.tag;

import java.io.Serializable;

/**
 * 用户标签对应bean
 * 
 * @author shifangfang
 * @date 2016年8月24日 下午2:22:31
 */
public class QxTagUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private int tagId;
	private String tagName;

	public QxTagUser() {
		super();
	}

	public QxTagUser(String uid, int tagId, String tagName) {
		super();
		this.uid = uid;
		this.tagId = tagId;
		this.tagName = tagName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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
