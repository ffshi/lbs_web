/**
 * 
 */
package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.List;

import com.innovate.cms.modules.qs.entity.sns.QxFriend;

/**
 *好友参与的话题Json样式
 * @author hushasha
 * @date 2016年5月9日
 */
public class FriendsTopicToJson implements Serializable {

	private static final long serialVersionUID = 1L;
	//专题编号
	private int gid; 
	//专题名称
	private String groupName;
	//专题图标
	private String icon;
	//模板标记：模板1,2,3,4,5
	private String templateTag;
	//参与此专题的好友人数
	private int num;
	//参与此专题的好友列表
	private List<QxFriend> lists;
	
	public FriendsTopicToJson() {
		super();
	}

	public FriendsTopicToJson(int gid, String groupName, String icon, String templateTag, int num,
			List<QxFriend> lists) {
		super();
		this.gid = gid;
		this.groupName = groupName;
		this.icon = icon;
		this.templateTag = templateTag;
		this.num = num;
		this.lists = lists;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<QxFriend> getLists() {
		return lists;
	}

	public void setLists(List<QxFriend> lists) {
		this.lists = lists;
	}

}
