package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 问题 接口返回样式
 * 
 * @author shifangfang
 *
 */
public class Questions implements Serializable {

	private static final long serialVersionUID = 1L;
	// 专题图片
	private String icon;
	@JsonIgnore
	private String groupName;
	@JsonIgnore
	private String groupDescription;

	@JsonIgnore
	private String rankTitle;// 排行榜自定义标题

	@JsonIgnore
	private String resDecription;// 专题做题结果页描述

	private Integer qid;

	@JsonIgnore
	private Integer fsid;

	@JsonIgnore
	private String templateTag;

	private String subject1Type;

	private String subject1;

	private String subject2Type;

	private String subject2;

	private String tag;

	private Date createTime;

	private String field;

	// 问题答案
	private String answer;

	private List<Options> options;

	public Questions() {
		super();
	}

	public Questions(String icon, String groupName, String groupDescription, Integer qid, Integer fsid, String templateTag, String subject1Type, String subject1, String subject2Type, String subject2, String tag, Date createTime, String field, String answer, List<Options> options) {
		super();
		this.icon = icon;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.qid = qid;
		this.fsid = fsid;
		this.templateTag = templateTag;
		this.subject1Type = subject1Type;
		this.subject1 = subject1;
		this.subject2Type = subject2Type;
		this.subject2 = subject2;
		this.tag = tag;
		this.createTime = createTime;
		this.field = field;
		this.answer = answer;
		this.options = options;
	}

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

	public String getResDecription() {
		return resDecription;
	}

	public void setResDecription(String resDecription) {
		this.resDecription = resDecription;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@JsonIgnore
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public String getSubject1Type() {
		return subject1Type;
	}

	public void setSubject1Type(String subject1Type) {
		this.subject1Type = subject1Type;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getSubject1() {
		return subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	public String getSubject2Type() {
		return subject2Type;
	}

	public void setSubject2Type(String subject2Type) {
		this.subject2Type = subject2Type;
	}

	public String getSubject2() {
		return subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getField() {
		return field;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
