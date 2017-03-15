package com.innovate.cms.modules.common.entity;

/**
 * 排行榜标题自定义
 * 
 * @author shifangfang
 * @date 2016年8月25日 上午11:29:38
 */
public class RankDataBackInfo<T> extends DataBackInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rankTitle;// 排行榜自定义标题

	public RankDataBackInfo() {
		super();
	}

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

}
