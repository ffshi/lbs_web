package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 留言回复接口 返回对象数据封装
 * 
 * @author hushasha
 * @date 2016年4月19日
 */
public class Reply implements Serializable {
 
	private static final long serialVersionUID = 1L;
	// 回复ID
	private String poid;
	// 回复时间
	private Date createTime;

	public Reply() {
		super();
	}

	public Reply(String poid, Date createTime) {
		super();
		this.poid = poid;
		this.createTime = createTime;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
