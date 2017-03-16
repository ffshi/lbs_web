package com.innovate.cms.modules.qs.entity.msg;

import java.sql.Date;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 用户动态消息
 * 
 * @author shifangfang
 * @date 2017年3月16日 上午11:30:36
 */
public class DynamicMsg extends DataEntity<DynamicMsg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// `mid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户发布消息id',
	// `uid` varchar(32) NOT NULL COMMENT '消息发布者id',
	// `user_name` varchar(128) NOT NULL COMMENT '消息发布者名字',
	// `publish_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
	// CURRENT_TIMESTAMP COMMENT '消息发布时间',
	// `msg_type` tinyint(4) DEFAULT NULL COMMENT
	// '消息类型0-个人动态1-求帮助2-个人活动3-公告4-出租售5-技能服务6-商家活动',
	// `anonymity` tinyint(4) DEFAULT '0' COMMENT '是否匿名发布0-非匿名1-匿名',
	// `title` varchar(255) DEFAULT '' COMMENT '消息标题',
	// `description` varchar(2048) DEFAULT '' COMMENT '消息描述',
	// `business_address` varchar(255) DEFAULT '' COMMENT '商家地址',
	// `business_name` varchar(128) DEFAULT '' COMMENT '商家名',
	// `price` decimal(10,2) DEFAULT NULL,
	// `reward` decimal(10,2) DEFAULT NULL COMMENT '报酬',
	// `requirements` varchar(1024) DEFAULT '' COMMENT '要求,条件',
	// `pics` varchar(2048) DEFAULT '' COMMENT '消息图片地址/尺寸客户端计算',
	// `geo_id` int(64) DEFAULT NULL COMMENT '消息发布位置数据id',
	private int mid;
	private String uid;
	private String userName = "";
	private int msgType;
	private int anonymity;
	private String title;
	private String description = "";
	private String businessAddress = "";
	private String businessName = "";
	private float price;
	private float reward;
	private String requirements = "";
	private String geoId = "";
	private String pics = "";
	private Date publishTime;

	public DynamicMsg() {
		super();
	}

	public DynamicMsg(String id) {
		super(id);
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(int anonymity) {
		this.anonymity = anonymity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getReward() {
		return reward;
	}

	public void setReward(float reward) {
		this.reward = reward;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getGeoId() {
		return geoId;
	}

	public void setGeoId(String geoId) {
		this.geoId = geoId;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

}
