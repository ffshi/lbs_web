package com.innovate.cms.modules.qs.entity.label;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户标签表--基于选项标签和模板4结果页标签生成的
 * 
 * @author hushasha
 * @date 2016年8月31日
 */
public class QxLabelUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String uid;

	private Integer parentId;

	private Integer labelId;

	private String labelName;

	private Boolean labelLevel;

	private Date createTime;

	private Date updateTime;

	private String delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Boolean getLabelLevel() {
		return labelLevel;
	}

	public void setLabelLevel(Boolean labelLevel) {
		this.labelLevel = labelLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}