package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * 
 * @ClassName: Template4Result
 * @Description: 用户已做题的情况，直接输出模板四对应的结果页
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年5月17日 下午6:38:30
 *
 */
public class Template4Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uid; // 用户ID
	private String text1; // 结果1文本任意内容，与产品约定 结果图片
	private String text2; // 结果2文本任意内容，与产品约定 结果标题
	private String text3; // 结果3文本任意内容，与产品约定 结果描述
	private String text4; // 结果4备用字段
	private String resultsTag; // 结果标志A、B、C、D、E

	private String groupName;
	private String groupDescription;

	public Template4Result() {
		super();
	}

	public Template4Result(String uid, String text1, String text2, String text3, String text4, String resultsTag) {
		super();
		this.uid = uid;
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
		this.resultsTag = resultsTag;
	}

	public Template4Result(String uid, String text1, String text2, String text3, String text4, String resultsTag, String groupName, String groupDescription) {
		super();
		this.uid = uid;
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
		this.resultsTag = resultsTag;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public String getText4() {
		return text4;
	}

	public void setText4(String text4) {
		this.text4 = text4;
	}

	public String getResultsTag() {
		return resultsTag;
	}

	public void setResultsTag(String resultsTag) {
		this.resultsTag = resultsTag == null ? null : resultsTag.trim();
	}

}
