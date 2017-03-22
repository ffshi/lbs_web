package com.innovate.cms.modules.qs.entity.msg;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 点赞列表bean
 * 
 * @author shifangfang
 * @date 2017年3月17日 下午4:07:04
 */
public class DynamicMsgPriseForService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int priseId;
	private String uid;
	private String cname;
	private String headimgurl;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public DynamicMsgPriseForService() {
		super();
	}

	public DynamicMsgPriseForService(int priseId, String uid, String headimgurl, Date createTime) {
		super();
		this.priseId = priseId;
		this.uid = uid;
		this.headimgurl = headimgurl;
		this.createTime = createTime;
	}

	public int getPriseId() {
		return priseId;
	}

	public void setPriseId(int priseId) {
		this.priseId = priseId;
	}

	public String getUid() {
		return uid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
