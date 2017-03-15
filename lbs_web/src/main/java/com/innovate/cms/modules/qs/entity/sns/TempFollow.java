/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.sns;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 好友临时表
 * 
 * @author shifangfang
 *
 */
public class TempFollow extends DataEntity<TempFollow> {

	private static final long serialVersionUID = 1L;

	
	private Integer tfid; // AUTO_INCREMENT // '关注ID',
	@JsonIgnore
	private String uid; // DEFAULT '-1' // '分享人ID',
	private String followUid; // DEFAULT '-1' // '临时好友ID',
	private String followImg; // DEFAULT '-1' // '临时好友头像',
	private String followName; // DEFAULT '-1' // '临时好友昵称',
	@JsonIgnore
	private Date createTime; // DEFAULT CURRENT_TIMESTAMP // '数据创建时间',

	

	public TempFollow(String uid, String followUid, String followImg, String followName)
	{
		super();
		this.uid = uid;
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
	}

	public TempFollow() {
		super();
	}

	public int getTfid() {
		return tfid;
	}

	public void setTfid(int tfid) {
		this.tfid = tfid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFollowUid() {
		return followUid;
	}

	public void setFollowUid(String followUid) {
		this.followUid = followUid;
	}

	public String getFollowImg() {
		String imgUrl = this.followImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFollowImg(String followImg) {
		this.followImg = followImg;
	}

	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}