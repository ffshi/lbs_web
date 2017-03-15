/**
 * 
 */
package com.innovate.cms.modules.qs.entity.label;

import java.io.Serializable;

/**
 * 选项标签
 * @author hushasha
 * @date 2016年8月31日
 */
public class OptionLabel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer qid; // 所属问题编号
	private String seqId; // 选项序号
	private Integer labelId; // 三级标签ID
	private String labelName; // 标签名称
	private Integer parentId; // 父级标签ID：同一父级标签下的三级标签 只能选择一个给用户贴上

	public OptionLabel() {
		super();
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
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
