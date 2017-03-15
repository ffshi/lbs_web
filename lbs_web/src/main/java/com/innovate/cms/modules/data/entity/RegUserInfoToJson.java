/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.config.Global;

/**
 * 新注册用户列表---返回json样式
 * 
 * @author hushasha
 * @date 2016年9月6日
 */
public class RegUserInfoToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid; // 本地用户编号
	private String nickname; // 用户昵称
	private String headimgurl; // 用户头像
	private Date createTime; // 注册时间

	public RegUserInfoToJson() {
		super();
	}

	public RegUserInfoToJson(String uid, String nickname, String headimgurl, Date createTime) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.createTime = createTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		String imgUrl = this.headimgurl;
		if (imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
