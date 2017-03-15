/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.menus;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 专题组Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxFeatures extends DataEntity<QxFeatures> {
	
	private static final long serialVersionUID = 1L;
	private String fsid;		// 专题组编号
	private String cid;		// 频道编号
	private String featureName;		// 专题组名称
	private String icon;		// 专题组图标
	private String sort;		// 排序
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public QxFeatures() {
		super();
	}

	public QxFeatures(String id){
		super(id);
	}

	@Length(min=1, max=11, message="专题组编号长度必须介于 1 和 11 之间")
	public String getFsid() {
		return fsid;
	}

	public void setFsid(String fsid) {
		this.fsid = fsid;
	}
	
	@Length(min=1, max=11, message="频道编号长度必须介于 1 和 11 之间")
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@Length(min=1, max=100, message="专题组名称长度必须介于 1 和 100 之间")
	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	@Length(min=0, max=100, message="专题组图标长度必须介于 0 和 100 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
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
	
}