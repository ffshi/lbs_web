package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * 专辑答案 接口返回样式
 * 
 * @author shifangfang
 *
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer qrid;

	private String resultsTag;

	private String text1;

	private String text2;

	private String text3;

	private String text4;

	public Result() {
		super();
	}

	public Result(Integer qrid, String resultsTag, String text1, String text2, String text3, String text4) {
		super();
		this.qrid = qrid;
		this.resultsTag = resultsTag;
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
	}

	public Integer getQrid() {
		return qrid;
	}

	public void setQrid(Integer qrid) {
		this.qrid = qrid;
	}

	public String getResultsTag() {
		return resultsTag;
	}

	public void setResultsTag(String resultsTag) {
		this.resultsTag = resultsTag == null ? null : resultsTag.trim();
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

}
