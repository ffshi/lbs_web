package com.innovate.cms.modules.qs.entity.msg;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 首页动态消息接口输出格式
 * 
 * @author shifangfang
 * @date 2017年3月16日 下午4:46:39
 */
public class DynamicMsgForService implements Serializable {

	/**
	 * 自动把数据中user_name的数据包装到javaBean的userName中
	 * 
	 * @JSONField(name="user_name")对应数据库属性字段名 private String userName = "";
	 */
	private static final long serialVersionUID = 1L;
	private int mid;
	private String uid;
	@JSONField(name = "user_name")
	private String userName = "";
	private String headimgurl = "";
	@JSONField(name = "msg_type")
	private int msgType;
	private int anonymity;
	private String title;
	private String description = "";
	@JSONField(name = "business_address")
	private String businessAddress = "";
	@JSONField(name = "business_name")
	private String businessName = "";
	private float price;
	private float reward;
	private String requirements = "";
	@JsonIgnore
	private String geoId = "";
	private String pics = "";
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "publish_time")
	private Date publishTime;
	private String location = "";
	@JSONField(name = "coord_type")
	private int coordType;
	@JsonIgnore
	private int isPrise;

	@JSONField(name = "prise_num")
	private int priseNum;// 点赞次数
	@JSONField(name = "comment_num")
	private int commentNum;// 评论次数
	@JSONField(name = "share_num")
	private int shareNum;// 分享次数
	@JSONField(name = "apply_for_num")
	private int applyForNum;// 申请报名人数

	// @JSONField(name = "is_shield")
	// private int isShield;// 是否屏蔽
	// @JSONField(name = "address_list")
	// private String addressList = "";

	// 三期新增属性
	// 活动时间/开始时间
	@JSONField(name = "msg_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date msgDate;
	@JSONField(name = "msg_end_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date msgEndDate;
	// 二级消息类型
	@JSONField(name = "msg_2level_type")
	private int msg2levelType;
	// 方便所有类型筛选
	@JSONField(name = "msg_single_ype")
	private int msgSingleType;
	// 价格描述：比如一顿饭代表价格
	@JSONField(name = "price_des")
	private String priceDes = "";
	// 消息状态0-未完成(默认) 1-完成
	@JSONField(name = "msg_state")
	private int msgState;
	// 允许报名人数/参与人数/拼单人数
	@JSONField(name = "apply_num")
	private int applyNum;
	// 座位数
	@JSONField(name = "seat_num")
	private int seatNum;
	// 出发地
	@JSONField(name = "start_place")
	private String startPlace = "";
	// 目的地
	@JSONField(name = "aim_place")
	private String aimPlace = "";
	// 点赞列表
	private List<DynamicMsgPriseForService> priseList;
	// 评论列表
	private List<DynamicMsgComment> commentList;
	// 座位数
	@JSONField(name = "tribe_id")
	private int tribeId;
	// 报名审核状态
	@JSONField(name = "check_state")
	@JsonInclude(Include.NON_EMPTY)
	private Integer checkState;

	public DynamicMsgForService() {
		super();
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public int getTribeId() {
		return tribeId;
	}

	public void setTribeId(int tribeId) {
		this.tribeId = tribeId;
	}

	public List<DynamicMsgPriseForService> getPriseList() {
		return priseList;
	}

	public void setPriseList(List<DynamicMsgPriseForService> priseList) {
		this.priseList = priseList;
	}

	public List<DynamicMsgComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<DynamicMsgComment> commentList) {
		this.commentList = commentList;
	}

	public int getApplyForNum() {
		return applyForNum;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public Date getMsgEndDate() {
		return msgEndDate;
	}

	public void setMsgEndDate(Date msgEndDate) {
		this.msgEndDate = msgEndDate;
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

	public String getPriceDes() {
		return priceDes;
	}

	public void setPriceDes(String priceDes) {
		this.priceDes = priceDes;
	}

	public int getMsgState() {
		return msgState;
	}

	public void setMsgState(int msgState) {
		this.msgState = msgState;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
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

	public void setApplyForNum(int applyForNum) {
		this.applyForNum = applyForNum;
	}

	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
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
