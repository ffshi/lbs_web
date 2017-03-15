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
 * 趣选问题结果表Entity
 * 
 * @author gaoji
 * @version 2016-04-01
 */
public class QxQuestionsResults extends DataEntity<QxQuestionsResults> {

	private static final long serialVersionUID = 1L;
	private Integer qrid; // 结果集编号ID
	private String createName; // 创建者姓名
	private Integer gid; // 所属问题编号
	private String resultsTag; // 结果标志A、B、C、D、E
	private String text1; // 结果1文本任意内容，与产品约定
	private String text2; // 结果2文本任意内容，与产品约定
	private String text3; // 结果3文本任意内容，与产品约定
	private String text4; // 结果4备用字段
	private Date updateTime; // 数据更新时间
	private Date createTime; // 数据创建时间

	public QxQuestionsResults() {
		super();
	}

	public QxQuestionsResults(String id) {
		super(id);
	}

	@Length(min = 1, max = 64, message = "创建者姓名长度必须介于 1 和 64 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Integer getQrid() {
		return qrid;
	}

	public void setQrid(Integer qrid) {
		this.qrid = qrid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	@Length(min = 1, max = 10, message = "结果标志A、B、C、D、E长度必须介于 1 和 10 之间")
	public String getResultsTag() {
		return resultsTag;
	}

	public void setResultsTag(String resultsTag) {
		this.resultsTag = resultsTag;
	}

	@Length(min = 1, max = 300, message = "结果1文本任意内容，与产品约定长度必须介于 1 和 300 之间")
	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	@Length(min = 1, max = 300, message = "结果2文本任意内容，与产品约定长度必须介于 1 和 300 之间")
	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	@Length(min = 1, max = 300, message = "结果3文本任意内容，与产品约定长度必须介于 1 和 300 之间")
	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	@Length(min = 1, max = 300, message = "结果4备用字段长度必须介于 1 和 300 之间")
	public String getText4() {
		return text4;
	}

	public void setText4(String text4) {
		this.text4 = text4;
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