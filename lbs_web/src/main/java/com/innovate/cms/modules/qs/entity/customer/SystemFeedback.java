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
 * 反馈表Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class SystemFeedback extends DataEntity<SystemFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String fbid;		// 反馈编号
	private String uid;		// 反馈人ID
	private String mobile;		// 反馈人手机
	private String content;		// 反馈内容
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public SystemFeedback() {
		super();
	}

	public SystemFeedback(String id){
		super(id);
	}

	@Length(min=1, max=11, message="反馈编号长度必须介于 1 和 11 之间")
	public String getFbid() {
		return fbid;
	}

	public void setFbid(String fbid) {
		this.fbid = fbid;
	}
	
	@Length(min=1, max=64, message="反馈人ID长度必须介于 1 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=11, message="反馈人手机长度必须介于 0 和 11 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=1, max=500, message="反馈内容长度必须介于 1 和 500 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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