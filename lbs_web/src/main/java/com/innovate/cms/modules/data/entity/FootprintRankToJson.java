package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

public class FootprintRankToJson implements Serializable {

	private static final long serialVersionUID = 1L;
	private String followUid; // 好友uid
	private String followImg; // 好友头像
	private String followName; // 好友昵称
	private double matchResult; // 好友与我的匹配度
	private String zoneContent; // 好友区间描述

	public FootprintRankToJson() {
		super();
	}

	public FootprintRankToJson(String followUid, String followImg, String followName, double matchResult, String zoneContent) {
		super();
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
		this.matchResult = matchResult;
		this.zoneContent = zoneContent;
	}

	public String getFollowUid() {
		return followUid;
	}

	public void setFollowUid(String followUid) {
		this.followUid = followUid;
	}

	public String getFollowImg() {
		String imgUrl = this.followImg;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
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

	public double getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(double matchResult) {
		this.matchResult = matchResult;
	}

	public String getZoneContent() {
		return zoneContent;
	}

	public void setZoneContent(String zoneContent) {
		this.zoneContent = zoneContent;
	}

}