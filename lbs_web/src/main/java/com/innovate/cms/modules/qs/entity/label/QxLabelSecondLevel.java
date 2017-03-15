package com.innovate.cms.modules.qs.entity.label;

import java.util.Date;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 二级标签
 * 
 * @author hushasha
 * @date 2016年8月31日
 */
public class QxLabelSecondLevel extends DataEntity<QxLabelSecondLevel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer labelId; // 标签ID

	private String labelName; // 二级标签名称

	private Integer parentId; // 父级标签ID

	private String editor; // 编辑者姓名

	private Date createTime; // 创建时间

	private Date updateTime; // 更新时间

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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor == null ? null : editor.trim();
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

}