package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

import com.innovate.cms.common.config.Global;

/**
 * * whishbone2.0 我的关注内的推荐 推荐一个当天做题结果匹配度最高的人
 * 
 * @author shifangfang
 * @date 2016年11月30日 下午4:30:21
 */
public class RecommendUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int match;// 匹配度

	private String uid;
	private String nickname;
	private String headimgurl;
	private String result;

	public RecommendUser() {
		super();
	}

	public RecommendUser(String uid, String result) {
		super();
		this.uid = uid;
		this.result = result;
	}

	public RecommendUser(int match, String uid, String result) {
		super();
		this.match = match;
		this.uid = uid;
		this.result = result;
	}

	public RecommendUser(int match, String uid, String nickname, String headimgurl, String result) {
		super();
		this.match = match;
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.result = result;
	}

	public int getMatch() {
		return match;
	}

	public void setMatch(int match) {
		this.match = match;
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
		if (null==imgUrl) {
			imgUrl="";
		}
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	// `uid` varchar(64) NOT NULL COMMENT '用户ID',
	// `nickname` varchar(100) NOT NULL DEFAULT '' COMMENT '用户昵称',
	// `headimgurl` varchar(500) NOT NULL DEFAULT '' COMMENT '用户头像',
	// `result` varchar(15) NOT NULL DEFAULT '' COMMENT
	// '当天首页精华做题结果集：1111111111',
	// `done_date` int(8) NOT NULL COMMENT '完成日期如20161129',

}
