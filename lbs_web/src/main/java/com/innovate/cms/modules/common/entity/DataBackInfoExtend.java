package com.innovate.cms.modules.common.entity;

/**
 * h5推广用
 * @author shifangfang
 * @date 2016年8月1日 下午12:01:38
 * @param <T>
 */
public class DataBackInfoExtend<T> extends DataBackInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBackInfoExtend() {
		super();
	}

	private String rankTitle;

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

}
