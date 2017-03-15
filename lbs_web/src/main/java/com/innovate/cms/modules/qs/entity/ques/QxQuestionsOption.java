/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.ques;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 趣选问题选项Entity
 * 
 * @author gaoji
 * @version 2016-04-01
 */
public class QxQuestionsOption extends DataEntity<QxQuestionsOption> {

	private static final long serialVersionUID = 1L;
	private Integer qaid; // 选项编号ID
	private String createName; // 创建者姓名
	private Integer qid; // 所属问题编号
	private String seqId; // 选项序号
	private String optionType; // 选项类型
	private String option; // 选项内容
	private String ranksToResults; // 给某结果加分
	private String goTag; // 跳转标记
	private Date updateTime; // 数据更新时间
	private Date createTime; // 数据创建时间

	public QxQuestionsOption() {
		super();
	}

	public QxQuestionsOption(String id) {
		super(id);
	}

	@Length(min = 1, max = 64, message = "创建者姓名长度必须介于 1 和 64 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Integer getQaid() {
		return qaid;
	}

	public void setQaid(Integer qaid) {
		this.qaid = qaid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	@Length(min = 1, max = 2, message = "选项序号长度必须介于 1 和 2 之间")
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	@Length(min = 1, max = 2, message = "选项类型长度必须介于 1 和 2 之间")
	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	@Length(min = 1, max = 300, message = "选项内容长度必须介于 1 和 300 之间")
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	@Length(min = 1, max = 100, message = "给某结果加分长度必须介于 1 和 100 之间")
	public String getRanksToResults() {
		return ranksToResults;
	}

	public void setRanksToResults(String ranksToResults) {
		this.ranksToResults = ranksToResults;
	}

	@Length(min = 1, max = 11, message = "跳转标记长度必须介于 1 和 11 之间")
	public String getGoTag() {
		return goTag;
	}

	public void setGoTag(String goTag) {
		this.goTag = goTag;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "数据更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "数据创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}