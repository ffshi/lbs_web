package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * 我的作品接口
 * @author shifangfang
 * @date 2016年12月1日 下午3:39:16
 */
public class Group0 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int gid;
	private String groupName;
	private int templateTag;
	
	public Group0() {
		super();
	}
	public Group0(int gid, String groupName) {
		super();
		this.gid = gid;
		this.groupName = groupName;
	}
	
	
	public Group0(int gid, String groupName, int templateTag) {
		super();
		this.gid = gid;
		this.groupName = groupName;
		this.templateTag = templateTag;
	}
	public int getTemplateTag() {
		return templateTag;
	}
	public void setTemplateTag(int templateTag) {
		this.templateTag = templateTag;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
