/**
 * 
 */
package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;

/**
 * 结果页 --热门专题推荐接口 (随机专题推荐接口) 返回jsonbean样式
 * 
 * @author hushasha
 * @date 2016年4月22日
 */
public class GroupRecommend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 专题id
	private Integer gid;
	// 专题名称
	private String groupName;
	// 专题小图标（对应数据库的small_icon字段）
	private String icon;
	// 做过此专题的人数
	private Integer num;
	// 模板标记
	private String templateTag;

	public GroupRecommend() {
		super();
	}

	public GroupRecommend(Integer gid, String groupName, String icon, Integer num, String templateTag) {
		super();
		this.gid = gid;
		this.groupName = groupName;
		this.icon = icon;
		this.num = num;
		this.templateTag = templateTag;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

}
