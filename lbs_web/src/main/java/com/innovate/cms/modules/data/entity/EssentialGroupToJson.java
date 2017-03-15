/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Strings;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.modules.qs.entity.ques.OptionsAndNum;

/**
 * 精华专题、社区 返回json样式
 * 
 * @author hushasha
 * @date 2016年10月21日
 */
public class EssentialGroupToJson implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid; // 创建人ID
	private String nickname; // 创建人昵称
	private String headimgurl; // 创建人头像
	private Integer fsid; // 专题组ID,专题分类ID
	private Integer gid; // 专题ID
	private String groupName; // 专题名字
	private String templateTag; // 模板标识 0,1,2,3,4,5
	private Integer recommended; // 0:不被推荐 1:被推荐
	private List<OptionsAndNum> optionsAndNums; // 选项
	private Integer praiseNum; // 点赞人数
	private Integer commentNum; // 评论数量
	private Integer participateNum; // 参与人数

	public EssentialGroupToJson() {
		super();
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

	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
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

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public Integer getRecommended() {
		return recommended;
	}

	public void setRecommended(Integer recommended) {
		this.recommended = recommended;
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

	public Integer getParticipateNum() {
		return participateNum;
	}

	public void setParticipateNum(Integer participateNum) {
		this.participateNum = participateNum;
	}

}
