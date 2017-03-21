package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 用户动态消息存储bean
 * 
 * @author shifangfang
 * @date 2017年3月15日 下午5:54:56
 */
public class DynamicMsgToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int mid;
	private String uid;
	private String userName = "";
	private int msgType;
	private int anonymity;
	private String title = "";
	private String description = "";
	private String businessAddress = "";
	private String businessName = "";
	private float price;
	private float reward;
	private String requirements = "";
	private String geoId = "";
	private String pics = "";
	//格式为：[经度,纬度]或[墨卡托坐标]
	private String location = "";
	//1（GPS经纬度坐标）、2（国测局加密经纬度坐标）、3（百度加密经纬度坐标）、4（百度加密墨卡托坐标）
	private int coord_type;

	public DynamicMsgToJson() {
		super();
	}

	public DynamicMsgToJson(String uid, String userName, int msgType, int anonymity, String title, String description, String businessAddress, String businessName, float price, float reward, String requirements, String geoId, String pics) {
		super();
		this.uid = uid;
		this.userName = userName;
		this.msgType = msgType;
		this.anonymity = anonymity;
		this.title = title;
		this.description = description;
		this.businessAddress = businessAddress;
		this.businessName = businessName;
		this.price = price;
		this.reward = reward;
		this.requirements = requirements;
		this.geoId = geoId;
		this.pics = pics;
	}
	

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getCoord_type() {
		return coord_type;
	}

	public void setCoord_type(int coord_type) {
		this.coord_type = coord_type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

}
