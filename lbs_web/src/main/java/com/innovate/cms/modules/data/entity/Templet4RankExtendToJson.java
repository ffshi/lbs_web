package com.innovate.cms.modules.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * h5推广用
 * 
 * @author shifangfang
 * @date 2016年8月1日 下午12:01:54
 */
public class Templet4RankExtendToJson extends Templet4RankToJson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Templet4RankExtendToJson() {
		super();
	}

	@JsonIgnore
	private String rankTitle;

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

}
