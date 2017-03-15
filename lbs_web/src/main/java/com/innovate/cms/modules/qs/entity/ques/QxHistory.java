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
 * 用户答题记录Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxHistory extends DataEntity<QxHistory> {
	
	private static final long serialVersionUID = 1L;
	private String htid;		// 答题记录编号
	private String uid;		// 所属用户ID
	private Integer fsid;		// 专题组ID
	private Integer gid;		// 专题ID
	private Integer qid;		//问题id
	private String results;		// 我的做题结果,问题结果或者专题结果
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	private String templateTag;
	private String resultsTag;
	
	public QxHistory() {
		super();
	}

	public QxHistory(String id){
		super(id);
	}

	

	@Length(min=1, max=64, message="答题记录编号长度必须介于 1 和 64 之间")
	public String getHtid() {
		return htid;
	}

	public void setHtid(String htid) {
		this.htid = htid;
	}
	
	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public String getResultsTag() {
		return resultsTag;
	}

	public void setResultsTag(String resultsTag) {
		this.resultsTag = resultsTag;
	}

	@Length(min=1, max=64, message="所属用户ID长度必须介于 1 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	

	
	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	@Length(min=1, max=100, message="我的做题结果长度必须介于 1 和 100 之间")
	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
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