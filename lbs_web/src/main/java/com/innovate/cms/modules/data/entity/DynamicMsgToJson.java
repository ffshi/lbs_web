package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	// 价格收费
	private float price;
	// 报酬
	private float reward;
	private String requirements = "";
	private String geoId = "";
	private String pics = "";
	// 格式为：[经度,纬度]或[墨卡托坐标]
	private String location = "";
	// 1（GPS经纬度坐标）、2（国测局加密经纬度坐标）、3（百度加密经纬度坐标）、4（百度加密墨卡托坐标）
	private int coordType;

	// 三期新增属性
	// 活动时间/开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date msgDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date msgEndDate;
	// 二级消息类型
	private int msg2levelType;
	// 方便所有类型筛选
	private int msgSingleType;
	// 价格描述：比如一顿饭代表价格
	private String priceDes = "";
	// 消息状态0-未完成(默认) 1-完成
	private int msgState;
	// 允许报名人数/参与人数/拼单人数
	private int applyNum;
	// 座位数
	private int seatNum;
	// 出发地
	private String startPlace = "";
	// 目的地
	private String aimPlace = "";

	// 虚拟消息 0-不是 1-是
	private int isVirtual;

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

	public int getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(int isVirtual) {
		this.isVirtual = isVirtual;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getAimPlace() {
		return aimPlace;
	}

	public void setAimPlace(String aimPlace) {
		this.aimPlace = aimPlace;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}

	public int getMsgState() {
		return msgState;
	}

	public void setMsgState(int msgState) {
		this.msgState = msgState;
	}

	public Date getMsgEndDate() {
		return msgEndDate;
	}

	public void setMsgEndDate(Date msgEndDate) {
		this.msgEndDate = msgEndDate;
	}

	public String getPriceDes() {
		return priceDes;
	}

	public void setPriceDes(String priceDes) {
		this.priceDes = priceDes;
	}

	public int getMsg2levelType() {
		return msg2levelType;
	}

	public void setMsg2levelType(int msg2levelType) {
		this.msg2levelType = msg2levelType;
	}

	public int getMsgSingleType() {
		return msgSingleType;
	}

	public void setMsgSingleType(int msgSingleType) {
		this.msgSingleType = msgSingleType;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getCoordType() {
		return coordType;
	}

	public void setCoordType(int coordType) {
		this.coordType = coordType;
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
