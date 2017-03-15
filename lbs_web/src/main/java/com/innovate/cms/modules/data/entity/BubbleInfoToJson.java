/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

/**
 * 气泡推荐Json样式
 * 
 * @author hushasha
 * @date 2016年5月9日
 */
public class BubbleInfoToJson implements Serializable {

	private static final long serialVersionUID = 1L;
	// 用户id
	private String uid;
	// 用户头像
	private String headImgUrl;
	// 用户昵称
	private String nickname;
	// 模板类型
	private String footType;
	// 专题id
	private Integer gid;
	// 专题名称
	private String groupName;
	// 0 随机推荐，1专题推荐
	private int type;

	public BubbleInfoToJson() {
		super();
	}

	public BubbleInfoToJson(String uid, String headImgUrl, String nickname, String footType, Integer gid, String groupName, int type) {
		super();
		this.uid = uid;
		this.headImgUrl = headImgUrl;
		this.nickname = nickname;
		this.footType = footType;
		this.gid = gid;
		this.groupName = groupName;
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getHeadImgUrl() {
		String imgUrl = this.headImgUrl;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getnickname() {
		return nickname;
	}

	public void setnickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFootType() {
		return footType;
	}

	public void setFootType(String footType) {
		this.footType = footType;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
