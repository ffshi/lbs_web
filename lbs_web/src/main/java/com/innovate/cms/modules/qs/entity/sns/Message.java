package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 新增留言接口 返回对象数据封装
 * 
 * @author hushasha
 * @date 2016年4月19日
 */
public class Message implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	// 留言ID
	private String msid;
	// 留言时间
	private Date createTime;

	public Message() {
		super();
	}

	public Message(String msid, Date createTime) {
		super();
		this.msid = msid;
		this.createTime = createTime;
	}

	public String getMsid() {
		return msid;
	}

	public void setMsid(String msid) {
		this.msid = msid;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
