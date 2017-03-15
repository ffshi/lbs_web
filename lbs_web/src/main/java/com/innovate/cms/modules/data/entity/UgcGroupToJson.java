/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.modules.qs.entity.ques.OptionsAndNum;

/**
 * wishbone 用户首页接口
 * 
 * @author shifangfang
 * @date 2016年10月26日 下午2:52:57
 */
public class UgcGroupToJson implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid; // 创建人ID
	private String nickname; // 创建人昵称
	private String headimgurl; // 创建人头像
	private Integer gid; // 专题ID
	private Integer fsid; //专题组ID,专题分类ID
	private String groupName; // 专题名字
	private int templateTag;
	@JsonIgnore
	private Integer done = 0;// 是否做过

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public int getTemplateTag() {
		return templateTag;
	}

	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public void setTemplateTag(int templateTag) {
		this.templateTag = templateTag;
	}

	private List<OptionsAndNum> optionsAndNums; // 选项
	private Integer praiseNum; // 点赞人数
	private Integer commentNum; // 评论数量

	public UgcGroupToJson() {
		super();
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
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

	public List<OptionsAndNum> getOptionsAndNums() {
		return optionsAndNums;
	}

	public void setOptionsAndNums(List<OptionsAndNum> optionsAndNums) {
		this.optionsAndNums = optionsAndNums;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

}
