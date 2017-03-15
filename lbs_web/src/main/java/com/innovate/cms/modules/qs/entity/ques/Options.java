package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * 问题选项接口返回样式
 * 
 * @author shifangfang
 *
 */
public class Options implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer qaid;

	private String option;

	private String optionType;

	private String ranksToResults;

	private String goTag;
	// 选项选择数量统计
	private int count;
	// 选项id :1-A 2-B 3-C 4-D 5-E
	private String seqId;

	public Options() {
		super();
	}

	public Options(Integer qaid, String option, String optionType, String ranksToResults, String goTag, int count, String seqId) {
		super();
		this.qaid = qaid;
		this.option = option;
		this.optionType = optionType;
		this.ranksToResults = ranksToResults;
		this.goTag = goTag;
		this.count = count;
		this.seqId = seqId;
	}

	public Options(int qaid, String option, String optionType, String ranksToResults, String goTag, int count) {
		super();
		this.qaid = qaid;
		this.option = option;
		this.optionType = optionType;
		this.ranksToResults = ranksToResults;
		this.goTag = goTag;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getQaid() {
		return qaid;
	}

	public void setQaid(int qaid) {
		this.qaid = qaid;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public void setQaid(Integer qaid) {
		this.qaid = qaid;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getRanksToResults() {
		return ranksToResults;
	}

	public void setRanksToResults(String ranksToResults) {
		this.ranksToResults = ranksToResults;
	}

	public String getGoTag() {
		return goTag;
	}

	public void setGoTag(String goTag) {
		this.goTag = goTag;
	}

}
