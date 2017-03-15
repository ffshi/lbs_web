package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

import com.google.common.base.Strings;
import com.innovate.cms.common.config.Global;

/**
 * 获取用户模板4做题结果
 * 
 * @author shifangfang
 * @date 2016年12月1日 下午4:04:10
 */
public class T4result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String nickname;
	private String headimgurl;
	private String groupName;
	private String rPic;// 结果图片
	private String rTtitle;// 结果标题
	private String rDescription;// 结果描述
	private int gid;
	private int templateTag=4;

	public T4result() {
		super();
	}

	public T4result(String uid, String nickname, String headimgurl, String groupName, String rPic, String rTtitle, String rDescription) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.groupName = groupName;
		this.rPic = rPic;
		this.rTtitle = rTtitle;
		this.rDescription = rDescription;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(int templateTag) {
		this.templateTag = templateTag;
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

//	public String getHeadimgurl() {
//		return headimgurl;
//	}
	
	public String getHeadimgurl() {
		String imgUrl = this.headimgurl;
		if (!Strings.isNullOrEmpty(imgUrl) && imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

//	public String getrPic() {
//		return rPic;
//	}
	
	public String getrPic() {
		String imgUrl = this.rPic;
		if (!Strings.isNullOrEmpty(imgUrl) && imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
		return imgUrl;
	}
	

	public void setrPic(String rPic) {
		this.rPic = rPic;
	}

	public String getrTtitle() {
		return rTtitle;
	}

	public void setrTtitle(String rTtitle) {
		this.rTtitle = rTtitle;
	}

	public String getrDescription() {
		return rDescription;
	}

	public void setrDescription(String rDescription) {
		this.rDescription = rDescription;
	}

	// u.uid AS uid,
	// u.nickname AS nickname,
	// u.headimgurl AS headimgurl,
	// g.group_name AS groupName,
	// r.text1 AS rPic,
	// r.text2 AS rTtitle,
	// r.text3 AS rDescription
}
