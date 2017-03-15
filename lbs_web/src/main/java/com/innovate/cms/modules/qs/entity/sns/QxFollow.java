/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.sns;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 关注表Entity
 * 
 * @author gaoji
 * @version 2016-04-01
 */
public class QxFollow extends DataEntity<QxFollow> {

	private static final long serialVersionUID = 1L;
	private String flid; // 关注ID
	private String uid; // 用户id
	private String followUid; // 好友id
	private String followImg; // 好友头像
	private String followName; // 好友昵称
	private Date updateTime; // 数据更新时间
	private Date createTime; // 数据创建时间
	private String unionid;// varchar(64) NOT NULL COMMENT '关注人用户全局唯一ID',
	private String img;// varchar(300) DEFAULT '' COMMENT '关注人头像',
	private String name;// varchar(50) DEFAULT '' COMMENT '关注人昵称',
	private String followUnionid;// varchar(64) NOT NULL COMMENT '被关注人全局唯一ID',

	public QxFollow() {
		super();
	}

	public QxFollow(String id) {
		super(id);
	}

	@Length(min = 1, max = 64, message = "关注ID长度必须介于 1 和 64 之间")
	public String getFlid() {
		return flid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	@Length(min = 1, max = 64, message = "用户id长度必须介于 1 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Length(min = 1, max = 64, message = "好友id长度必须介于 1 和 64 之间")
	public String getFollowUid() {
		return followUid;
	}

	public void setFollowUid(String followUid) {
		this.followUid = followUid;
	}

	@Length(min = 1, max = 300, message = "好友头像长度必须介于 1 和 300 之间")
	public String getFollowImg() {
		return followImg;
	}

	public void setFollowImg(String followImg) {
		this.followImg = followImg;
	}

	@Length(min = 1, max = 50, message = "好友昵称长度必须介于 1 和 50 之间")
	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getImg() {
		String imgUrl = this.img;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFollowUnionid() {
		return followUnionid;
	}

	public void setFollowUnionid(String followUnionid) {
		this.followUnionid = followUnionid;
	}


}