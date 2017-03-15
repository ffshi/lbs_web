package com.innovate.cms.modules.qs.entity.tag;

import java.io.Serializable;

/**
 * 专题标签对应bean
 * 
 * @author shifangfang
 * @date 2016年8月24日 下午2:22:31
 */
public class QxTagGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int gid;
	private int tagId;
	private String tagName;

	public QxTagGroup() {
		super();
	}

	public QxTagGroup(int gid, int tagId, String tagName) {
		super();
		this.gid = gid;
		this.tagId = tagId;
		this.tagName = tagName;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
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
