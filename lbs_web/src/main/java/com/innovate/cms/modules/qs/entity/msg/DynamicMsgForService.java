package com.innovate.cms.modules.qs.entity.msg;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 首页动态消息接口输出格式
 * 
 * @author shifangfang
 * @date 2017年3月16日 下午4:46:39
 */
public class DynamicMsgForService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mid;
	private String uid;
	private String userName = "";
	private String headimgurl = "";
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date publishTime;
	private String location = "";
	private int coordType;
	@JsonIgnore
	private int isPrise;

	private int priseNum;// 点赞次数
	private int commentNum;// 评论次数

	public DynamicMsgForService() {
		super();
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCoordType() {
		return coordType;
	}

	public void setCoordType(int coordType) {
		this.coordType = coordType;
	}

	public int getIsPrise() {
		return isPrise;
	}

	public void setIsPrise(int isPrise) {
		this.isPrise = isPrise;
	}

	public int getPriseNum() {
		return priseNum;
	}

	public void setPriseNum(int priseNum) {
		this.priseNum = priseNum;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishTime() {
		return publishTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

}
