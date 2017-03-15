package com.innovate.cms.modules.qs.entity.tag;

import java.util.Date;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 趣选标签
 * 趣选标签系统 qx_tag
 * @author shifangfang
 * @date 2016年8月23日 下午2:21:14
 */
public class QxTag extends DataEntity<QxTag> {

	private static final long serialVersionUID = 1L;

	private String name; // tag name
	private String editor;

	private Date createTime; // 创建时间

	public QxTag() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
