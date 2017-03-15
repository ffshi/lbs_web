/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.List;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.modules.qs.entity.ques.QuestionAnswer;

/**
 * 模板五结果页随机推荐2人json样式
 * @author hushasha
 * @date 2016年7月7日
 */
public class RandomTwoUserToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String nickname;
	private String headimgurl;
	private List<QuestionAnswer> questions;
	
	public RandomTwoUserToJson() {
		super();
	}
	
	public RandomTwoUserToJson(String uid, String nickname, String headimgurl, List<QuestionAnswer> questions) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.questions = questions;
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
	public List<QuestionAnswer> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionAnswer> questions) {
		this.questions = questions;
	}
	
}
