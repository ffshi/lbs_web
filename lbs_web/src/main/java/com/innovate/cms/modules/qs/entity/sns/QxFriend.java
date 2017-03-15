package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

/**
 * 好友列表返回jsonbean格式
 * 
 * @author shifangfang
 *
 */
public class QxFriend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String followUid;

	private String followImg;

	private String followName;
	

	public QxFriend() {
		super();
	}

	public QxFriend(String followUid, String followImg, String followName) {
		super();
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
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

	
}
