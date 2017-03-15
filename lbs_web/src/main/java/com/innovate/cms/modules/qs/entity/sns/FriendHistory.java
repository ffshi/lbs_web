package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

public class FriendHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String followUid;

	private String followImg;

	private String followName;

	private String results;

	public FriendHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FriendHistory(String followUid, String followImg, String followName, String results) {
		super();
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
		this.results = results;
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

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

}
