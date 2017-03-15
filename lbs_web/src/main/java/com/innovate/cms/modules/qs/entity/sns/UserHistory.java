package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.modules.qs.entity.ques.Questions;

/**
 * 用户做题记录返回的jsonbean格式
 * 
 * @author shifangfang
 *
 */
public class UserHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer gid;

	private String groupName;

	private String icon;

	private String results;

	private Date updateTime;

	private Questions question;

	public UserHistory() {
		super();
	}

	public UserHistory(Integer gid, String groupName, String icon, String results, Date updateTime, Questions question) {
		super();
		this.gid = gid;
		this.groupName = groupName;
		this.icon = icon;
		this.results = results;
		this.updateTime = updateTime;
		this.question = question;
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

}
