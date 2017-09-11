package com.innovate.cms.modules.qs.entity.tribe;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户群组
 * 
 * @author shifangfang
 * @date 2017年4月8日 上午10:52:03
 */
public class ImTribeToJSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tid;
	@JSONField(name = "tribe_id")
	private long tribeId;
	@JSONField(name = "tribe_img")
	private String tribeImg;
	@JSONField(name = "tribe_type")
	private String tribeType;
	@JSONField(name = "tribe_category")
	private String tribeCategory = "";
	@JSONField(name = "owner_uid")
	private String ownerUid;
	private String name;
	private String introduction;
	private String location;
	@JSONField(name = "owner_name")
	private String ownerName;
	@JSONField(name = "owner_headimg")
	private String ownerHeadimg;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "create_time")
	private Date createTime;
	@JSONField(name = "coord_type")
	private String coordType;
	@JSONField(name = "location_name")
	private String locationName;
	//活动类消息id 为了报名而建立的群组
	private int mid;
	//建群后是否对报名人自动发出邀请 0-默认 1-不发 2-自动发
	@JSONField(name = "auto_invite")
	private int autoInvite;

	// `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '本平台群id',
	// `tribe_id` int(11) DEFAULT '0' COMMENT '群id,阿里的IM群id',
	// `tribe_img` varchar(255) DEFAULT '' COMMENT '群头像',
	// `tribe_type` tinyint(4) DEFAULT '0' COMMENT '群类型0-公开 1-半公开 2-秘密',
	// `tribe_category` varchar(64) DEFAULT '' COMMENT '群分类',
	// `owner_uid` varchar(32) DEFAULT '' COMMENT '创建者uid-群主',
	// `name` varchar(255) DEFAULT '' COMMENT '群名称',
	// `introduction` varchar(512) DEFAULT '' COMMENT '群介绍',
	// `location` varchar(255) DEFAULT NULL COMMENT '经纬度信息',
	// `coord_type` tinyint(4) DEFAULT '0' COMMENT
	// '经纬度类型1（GPS经纬度坐标）2（国测局加密经纬度坐标）3（百度加密经纬度坐标）4（百度加密墨卡托坐标）',
	// `location_name` varchar(255) DEFAULT '' COMMENT '位置名字,如融科创意中心',

	public ImTribeToJSON() {
		super();
	}

	public ImTribeToJSON(int tid, long tribeId, String tribeImg, String tribeType, String tribeCategory, String ownerUid, String name, String introduction, String location, String ownerName, String ownerHeadimg, Date createTime, String coordType, String locationName) {
		super();
		this.tid = tid;
		this.tribeId = tribeId;
		this.tribeImg = tribeImg;
		this.tribeType = tribeType;
		this.tribeCategory = tribeCategory;
		this.ownerUid = ownerUid;
		this.name = name;
		this.introduction = introduction;
		this.location = location;
		this.ownerName = ownerName;
		this.ownerHeadimg = ownerHeadimg;
		this.createTime = createTime;
		this.coordType = coordType;
		this.locationName = locationName;
	}

	public int getAutoInvite() {
		return autoInvite;
	}

	public void setAutoInvite(int autoInvite) {
		this.autoInvite = autoInvite;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public long getTribeId() {
		return tribeId;
	}

	public void setTribeId(long tribeId) {
		this.tribeId = tribeId;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerHeadimg() {
		return ownerHeadimg;
	}

	public void setOwnerHeadimg(String ownerHeadimg) {
		this.ownerHeadimg = ownerHeadimg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setTribeImg(String tribeImg) {
		this.tribeImg = tribeImg;
	}

	public String getTribeImg() {
		return tribeImg;
	}

	public String getTribeType() {
		return tribeType;
	}

	public void setTribeType(String tribeType) {
		this.tribeType = tribeType;
	}

	public String getTribeCategory() {
		return tribeCategory;
	}

	public void setTribeCategory(String tribeCategory) {
		this.tribeCategory = tribeCategory;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCoordType() {
		return coordType;
	}

	public void setCoordType(String coordType) {
		this.coordType = coordType;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
