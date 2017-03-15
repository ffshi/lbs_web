/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.push;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 推送管理Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxPushInfo extends DataEntity<QxPushInfo> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// 用户UserId
	private String account;		// 用户别名
	private String deviceId;		// 设备唯一ID
	private Integer deviceType;		// 设备类型0:iOS设备,1:Andriod设备
	private String deviceIp;		// 用户登录IP
	private String tagType;		// 用户分类标签
	private String tagAddress;		// 用户地区标签
	private String tagAppVersion;		// 设备版本标签
	private String tagChurnUser;		// 流失用户标签
	private String tagPotentialPay;		// 潜在付费用户标签
	private String tagPotentialLoss;		// 潜在流失用户标签
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public QxPushInfo() {
		super();
	}

	public QxPushInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户UserId长度必须介于 0 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=100, message="用户别名长度必须介于 0 和 100 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=1, max=100, message="设备唯一ID长度必须介于 1 和 100 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=1, max=3, message="设备类型0:iOS设备,1:Andriod设备长度必须介于 1 和 3 之间")
	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	
	@Length(min=0, max=50, message="用户登录IP长度必须介于 0 和 50 之间")
	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	
	@Length(min=0, max=50, message="用户分类标签长度必须介于 0 和 50 之间")
	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	@Length(min=0, max=50, message="用户地区标签长度必须介于 0 和 50 之间")
	public String getTagAddress() {
		return tagAddress;
	}

	public void setTagAddress(String tagAddress) {
		this.tagAddress = tagAddress;
	}
	
	@Length(min=1, max=50, message="设备版本标签长度必须介于 1 和 50 之间")
	public String getTagAppVersion() {
		return tagAppVersion;
	}

	public void setTagAppVersion(String tagAppVersion) {
		this.tagAppVersion = tagAppVersion;
	}
	
	@Length(min=0, max=50, message="流失用户标签长度必须介于 0 和 50 之间")
	public String getTagChurnUser() {
		return tagChurnUser;
	}

	public void setTagChurnUser(String tagChurnUser) {
		this.tagChurnUser = tagChurnUser;
	}
	
	@Length(min=0, max=50, message="潜在付费用户标签长度必须介于 0 和 50 之间")
	public String getTagPotentialPay() {
		return tagPotentialPay;
	}

	public void setTagPotentialPay(String tagPotentialPay) {
		this.tagPotentialPay = tagPotentialPay;
	}
	
	@Length(min=0, max=50, message="潜在流失用户标签长度必须介于 0 和 50 之间")
	public String getTagPotentialLoss() {
		return tagPotentialLoss;
	}

	public void setTagPotentialLoss(String tagPotentialLoss) {
		this.tagPotentialLoss = tagPotentialLoss;
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