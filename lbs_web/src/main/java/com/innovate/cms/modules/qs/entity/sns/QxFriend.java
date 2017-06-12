package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innovate.cms.common.config.Global;

/**
 * 好友列表返回jsonbean格式
 * 
 * @author shifangfang
 *
 */
public class QxFriend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String followUid;

	private String followImg = "-1";

	private String followName = "-1";
	private String sex; // 用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知
	// 个人签名
	@JsonInclude(Include.NON_EMPTY)
	@JSONField(name = "personal_signature")
	private String personalSignature;

	public QxFriend() {
		super();
	}

	public QxFriend(String followUid, String followImg, String followName) {
		super();
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPersonalSignature() {
		return personalSignature;
	}

	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
	}

	public String getFollowUid() {
		return followUid;
	}

	public void setFollowUid(String followUid) {
		this.followUid = followUid;
	}

	public String getFollowImg() {
		String imgUrl = this.followImg;
		if (imgUrl.length() > 0 && !imgUrl.equals("-1")) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP) || imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
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

}
