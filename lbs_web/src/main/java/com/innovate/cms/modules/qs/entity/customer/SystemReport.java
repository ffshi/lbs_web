/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.customer;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 举报表Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class SystemReport extends DataEntity<SystemReport> {
	
	private static final long serialVersionUID = 1L;
	private String rid;		// 举报编号
	private String uid;		// 举报人ID
	private String qid;		// 问题ID
	private String question;		// 问题内容
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public SystemReport() {
		super();
	}

	public SystemReport(String id){
		super(id);
	}

	@Length(min=1, max=11, message="举报编号长度必须介于 1 和 11 之间")
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
	
	@Length(min=1, max=64, message="举报人ID长度必须介于 1 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=1, max=11, message="问题ID长度必须介于 1 和 11 之间")
	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}
	
	@Length(min=0, max=500, message="问题内容长度必须介于 0 和 500 之间")
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}