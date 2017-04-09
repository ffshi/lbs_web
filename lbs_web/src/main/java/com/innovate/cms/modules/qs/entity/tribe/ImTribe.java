package com.innovate.cms.modules.qs.entity.tribe;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 用户群组
 * 
 * @author shifangfang
 * @date 2017年4月8日 上午10:52:03
 */
public class ImTribe extends DataEntity<ImTribe> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int tid;
	@JSONField(name = "tribe_id")
	private int tribeId;
	@JSONField(name = "tribe_img")
	private String tribeImg;
	@JSONField(name = "tribe_type")
	private String tribeType;
	@JSONField(name = "tribe_category")
	private String tribeCategory;
	@JSONField(name = "owner_uid")
	private String ownerUid;
	@JSONField(name = "owner_name")
	private String ownerName;
	@JSONField(name = "owner_headimg")
	private String ownerHeadimg;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "create_time")
	private Date createTime;
	private String name;
	private String introduction;
	private String location;
	@JSONField(name = "coord_type")
	private String coordType;
	@JSONField(name = "location_name")
	private String locationName;

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

	public String getTribeImg() {
		return tribeImg;
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

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getTribeId() {
		return tribeId;
	}

	public void setTribeId(int tribeId) {
		this.tribeId = tribeId;
	}

	public void setTribeImg(String tribeImg) {
		this.tribeImg = tribeImg;
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
