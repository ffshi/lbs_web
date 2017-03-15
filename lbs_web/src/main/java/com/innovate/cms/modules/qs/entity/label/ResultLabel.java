/**
 * 
 */
package com.innovate.cms.modules.qs.entity.label;

import java.io.Serializable;

/**
 * 结果页标签
 * 
 * @author hushasha
 * @date 2016年8月31日
 */
public class ResultLabel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resultsTag; // 结果标志A、B、C、D、E
	private Integer labelId; // 三级标签ID
	private String labelName; // 标签名称
	private Integer parentId; // 父级标签ID：同一父级标签下的三级标签 只能选择一个给用户贴上

	public ResultLabel() {
		super();
	}

	public String getResultsTag() {
		return resultsTag;
	}

	public void setResultsTag(String resultsTag) {
		this.resultsTag = resultsTag == null ? null : resultsTag.trim();
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
		this.labelName = labelName == null ? null : labelName.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
