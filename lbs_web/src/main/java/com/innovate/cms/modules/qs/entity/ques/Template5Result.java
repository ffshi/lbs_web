package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

/**
 * 
 * @ClassName: Template5Result
 * @Description: 用户已做题的情况，直接输出模板5对应的结果页
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年5月17日 下午6:38:30
 *
 */
public class Template5Result implements Serializable {

	private static final long serialVersionUID = 1L;
	private int gid; // 专题ID
	private String myUid; // 我的UID
	private String myImg; // 我的头像
	private String myName; // 我的昵称
	private String followUid; // 好友的UID
	private String followImg; // 好友的头像
	private String followName; // 好友的昵称
	private Double matchResult; // 我与好友的匹配百分比
	private String zoneContent; // 匹配对应的文案
	private String title;// 匹配对应的文案标题

	private String groupName;// 专题名

	public Template5Result() {
		super();
	}

	public Template5Result(int gid, String myUid, String myImg, String myName, String followUid, String followImg, String followName, Double matchResult, String zoneContent, String title, String groupName) {
		super();
		this.gid = gid;
		this.myUid = myUid;
		this.myImg = myImg;
		this.myName = myName;
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
		this.matchResult = matchResult;
		this.zoneContent = zoneContent;
		this.title = title;
		this.groupName = groupName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMyUid() {
		return myUid;
	}

	public void setMyUid(String myUid) {
		this.myUid = myUid;
	}

	public String getMyImg() {
		return myImg;
	}

	public void setMyImg(String myImg) {
		this.myImg = myImg;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
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

	public Double getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(Double matchResult) {
		this.matchResult = matchResult;
	}

	public String getZoneContent() {
		return zoneContent;
	}

	public void setZoneContent(String zoneContent) {
		this.zoneContent = zoneContent;
	}

}
