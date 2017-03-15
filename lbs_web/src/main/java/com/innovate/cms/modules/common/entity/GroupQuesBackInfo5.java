package com.innovate.cms.modules.common.entity;

import java.util.List;

import com.innovate.cms.modules.qs.entity.ques.Questions;
import com.innovate.cms.modules.qs.entity.ques.Result5;

/**
 * 模板5专题接口返回json包装 /api/v1/ques/getQuestions
 * 
 * @ClassName: DataBackInfo
 *
 */
public class GroupQuesBackInfo5 extends BaseBackInfo {

	private static final long serialVersionUID = 1L;
	// 专题id
	private Integer gid;
	// 专题图片
	private String icon;
	// 专题模板类型
	private String templateTag;

	// 专题组id
	private Integer fsid;
	// 专题名字
	private String groupName;
	// 专题描述
	private String groupDescription;
	// 用户是否做过该专题1:做过 0：没做过
	private int done;

	// 用户uid
	private String uid;
	private String rankTitle;// 排行榜自定义标题
	private String resDecription;// 专题做题结果页描述
	/**
	 * 集合数据
	 */
	private List<Result5> result;
	private List<Questions> questions;

	public GroupQuesBackInfo5() {
		super();
	}

	public GroupQuesBackInfo5(int gid, String icon, String templateTag, int fsid, String groupName, String groupDescription, int done, String uid, List<Result5> result, List<Questions> questions) {
		super();
		this.gid = gid;
		this.icon = icon;
		this.templateTag = templateTag;
		this.fsid = fsid;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.done = done;
		this.uid = uid;
		this.result = result;
		this.questions = questions;
	}

	public String getResDecription() {
		return resDecription;
	}

	public void setResDecription(String resDecription) {
		this.resDecription = resDecription;
	}

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getFsid() {
		return fsid;
	}

	public void setFsid(int fsid) {
		this.fsid = fsid;
	}

	public List<Result5> getResult() {
		return result;
	}

	public void setResult(List<Result5> result) {
		this.result = result;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}