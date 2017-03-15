/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.menus;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 专题表Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxGroups extends DataEntity<QxGroups> {
	
	private static final long serialVersionUID = 1L;
	private Integer gid;		// 专题编号
	private Integer fsid;		// 专题组编号
	private String groupName;		// 专题名称
	private String icon;		// 专题图标
	private BigDecimal sort;		// sort
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	private String templateTag;		// 专题名称模板标记模板0,1,2,3,4,5
	private String uid;		// 创建者ID
	private Integer isHomePage;		// 是否是首页精华0-不是 1-是
	private Integer recommended;		// 0:不被推荐 1:被推荐
	
	public QxGroups() {
		super();
	}

	public QxGroups(String id){
		super(id);
	}

	@Length(min=1, max=11, message="专题编号长度必须介于 1 和 11 之间")
	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	@Length(min=1, max=11, message="专题组编号长度必须介于 1 和 11 之间")
	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}
	
	@Length(min=1, max=100, message="专题名称长度必须介于 1 和 100 之间")
	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Length(min=0, max=100, message="专题图标长度必须介于 0 和 100 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getIsHomePage() {
		return isHomePage;
	}

	public void setIsHomePage(Integer isHomePage) {
		this.isHomePage = isHomePage;
	}

	public Integer getRecommended() {
		return recommended;
	}

	public void setRecommended(Integer recommended) {
		this.recommended = recommended;
	}
	
}