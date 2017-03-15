/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.ads;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 首页配置Entity
 * @author gaoji
 * @version 2016-04-01
 */
public class QxPageHome extends DataEntity<QxPageHome> {
	
	private static final long serialVersionUID = 1L;
	private String hid;		// hid
	private String rowType;		// 1、banner 2、热门专题 3、普通专题组
	private String title;		// 标题 文字/img
	private String refType;		// 1、专题 2、专题组 3、H5
	private String ref;		// 引用参数，受refType限制
	private String refTitle;    //引用资源标题
	private String img;		// 该 专题/组/H5 对应图片
	private String rowSort;		// 纵向排序字段0-100
	private String refSort;		// 横向排序字段0-100
	private Date updateTime;		// 数据更新时间
	private Date createTime;		// 数据创建时间
	
	public QxPageHome() {
		super();
	}

	public QxPageHome(String id){
		super(id);
	}

	@Length(min=1, max=11, message="hid长度必须介于 1 和 11 之间")
	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}
	
	@Length(min=1, max=2, message="1、banner 2、热门专题 3、普通专题组长度必须介于 1 和 2 之间")
	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	
	@Length(min=1, max=200, message="标题 文字/img长度必须介于 1 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=2, message="1、专题 2、专题组 3、H5长度必须介于 1 和 2 之间")
	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	@Length(min=1, max=1000, message="引用参数，受refType限制长度必须介于 1 和 1000 之间")
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Length(min=1, max=100, message="引用资源标题, 长度必须介于 1 和 100 之间")
	public String getRefTitle() {
		return refTitle;
	}

	public void setRefTitle(String refTitle) {
		this.refTitle = refTitle;
	}

	@Length(min=1, max=200, message="该 专题/组/H5 对应图片长度必须介于 1 和 200 之间")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getRowSort() {
		return rowSort;
	}

	public void setRowSort(String rowSort) {
		this.rowSort = rowSort;
	}
	
	public String getRefSort() {
		return refSort;
	}

	public void setRefSort(String refSort) {
		this.refSort = refSort;
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