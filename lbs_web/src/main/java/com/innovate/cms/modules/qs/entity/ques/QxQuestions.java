/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.ques;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 趣选题库Entity
 * 
 * @author gaoji
 * @version 2016-04-01
 */
public class QxQuestions extends DataEntity<QxQuestions> {

	private static final long serialVersionUID = 1L;
	private Integer qid; // 题目编号ID
	private String createName; // 创建者姓名
	private Integer gid; // 所属专题编号
	private String templateTag; // 模板标记模板1,2,3,4 三期做1,4
	private String subject1Type; // 标题1类型（1文字2图片3声音4视频）
	private String subject2Type; // 标题2类型（1文字2图片3声音4视频）
	private String subject1; // 标题1
	private String subject2; // 标题2
	private String tag; // 当前标记
	private Date updateTime; // 数据更新时间
	private Date createTime; // 数据创建时间

	public QxQuestions() {
		super();
	}

	public QxQuestions(String id) {
		super(id);
	}

	@Length(min = 1, max = 64, message = "创建者姓名长度必须介于 1 和 64 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	@Length(min = 0, max = 2, message = "模板标记模板1,2,3,4 三期做1,4长度必须介于 0 和 2 之间")
	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	@Length(min = 1, max = 2, message = "标题1类型（1文字2图片3声音4视频）长度必须介于 1 和 2 之间")
	public String getSubject1Type() {
		return subject1Type;
	}

	public void setSubject1Type(String subject1Type) {
		this.subject1Type = subject1Type;
	}

	@Length(min = 1, max = 2, message = "标题2类型（1文字2图片3声音4视频）长度必须介于 1 和 2 之间")
	public String getSubject2Type() {
		return subject2Type;
	}

	public void setSubject2Type(String subject2Type) {
		this.subject2Type = subject2Type;
	}

	@Length(min = 1, max = 300, message = "标题1长度必须介于 1 和 300 之间")
	public String getSubject1() {
		return subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	@Length(min = 1, max = 300, message = "标题2长度必须介于 1 和 300 之间")
	public String getSubject2() {
		return subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	@Length(min = 1, max = 11, message = "当前标记长度必须介于 1 和 11 之间")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}