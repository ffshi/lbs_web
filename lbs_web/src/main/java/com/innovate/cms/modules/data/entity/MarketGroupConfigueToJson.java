package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * H5推广运营专题特殊配置
 * 
 * @author shifangfang
 * @date 2016年7月27日 下午4:17:32
 */
public class MarketGroupConfigueToJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String doButton;

	// private String redoButton;

	private String shareButton;

	private String quesBg;

	private String resBg;

	private String rateBg;

	private String ratedoneBg;

	private String rankBg;

	// private String frameColor;
	//
	// private String masterColor;
	//
	// private String strongColor;
	//
	// private String reserveColor;

	private String resSort;

	private String music;
	private String rankTitle;

	private String retestButton;
	private String moretestButton;
	private String pagehomeBg;

	private String noresBg; // varchar(255) DEFAULT '' COMMENT '结果页有排行榜背景图',
	private int showRank; // tinyint(4) DEFAULT '0' COMMENT '首页是否展示排行榜 0:展示
							// 1:不展示',
	private String percentOptionPic; // varchar(255) DEFAULT '' COMMENT
										// '百分比选项图片',
	private String rightIcon; // varchar(255) DEFAULT '' COMMENT '选项选中对勾图片',
	private int percentOptionMark; // tinyint(4) DEFAULT '0' COMMENT '选项展示形式
									// 0:百分比效果 1:选中对勾效果',
	private String percentRateColor; // varchar(16) DEFAULT '' COMMENT
										// '百分进度条字体颜色',
	private String percentRateBg; // varchar(16) DEFAULT '' COMMENT
									// '百分比进度条背景颜色',
	private String optionFrameColor; // varchar(16) DEFAULT '' COMMENT '选项边框颜色',
	private String optionFrameBg; // varchar(16) DEFAULT '' COMMENT '选项背景颜色',
	private String optionFontColor; // varchar(16) DEFAULT '' COMMENT
									// '选项文字内容颜色',
	private String rankTitleBg; // varchar(255) DEFAULT '' COMMENT '结果区域图片',
	private String redoMiniButton; // varchar(255) DEFAULT '' COMMENT '重做迷你按钮',
	private String leftHotPic; // varchar(255) DEFAULT '' COMMENT '按钮左hot标签',
	private String rightHotPic; // varchar(255) DEFAULT '' COMMENT '按钮右hot标签',
	private int rankHotPicPosition; // tinyint(4) DEFAULT '0' COMMENT
									// '排行榜hot按钮标签位置 0:排行榜左侧 1:排行榜右侧左上角
									// 2:排行榜右侧右上角',
	private int resHotPicPosition; // tinyint(4) DEFAULT '0' COMMENT
									// '结果页hot按钮标签位置 0:左侧 1:右侧按钮左上 2:右侧按钮右上
									// ',
	private String homepageFontColor; // varchar(16) DEFAULT '' COMMENT
										// '首页表述文字颜色',
	private String rankUsernameColor; // varchar(16) DEFAULT '' COMMENT
										// '排行榜用户名文字颜色',
	private String rankResFontColor; // varchar(16) DEFAULT '' COMMENT
										// '排行榜结果文字颜色',
	private String resFontColor; // varchar(16) DEFAULT '' COMMENT '结果文字颜色',
	private String resDescriptionFontColor; // varchar(16) DEFAULT '' COMMENT
											// '结果文字描述颜色',
	private String hotRecommendFontColor; // varchar(16) DEFAULT '' COMMENT
											// '热门专题推荐文字颜色',
	private String hotRecommendGroupColor; // varchar(16) DEFAULT '' COMMENT
											// '热门专题推荐题目标题颜色',
	private String hotRecommendGroupBg; // varchar(16) DEFAULT '' COMMENT
										// '热门专题推荐区域背景颜色',
	private String homepageBg; // varchar(16) DEFAULT '' COMMENT '首页背景颜色',
	private String questionBg; // varchar(16) DEFAULT '' COMMENT '做题页背景颜色',
	private String resBgColor; // varchar(255) DEFAULT '' COMMENT '结果页背景颜色',

	public MarketGroupConfigueToJson() {
		super();
	}

	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

	public void setDoButton(String doButton) {
		this.doButton = doButton;
	}

	public String getDoButton() {
		return this.doButton;
	}

	public String getRetestButton() {
		return retestButton;
	}

	public void setRetestButton(String retestButton) {
		this.retestButton = retestButton;
	}

	public String getMoretestButton() {
		return moretestButton;
	}

	public void setMoretestButton(String moretestButton) {
		this.moretestButton = moretestButton;
	}

	public String getPagehomeBg() {
		return pagehomeBg;
	}

	public void setPagehomeBg(String pagehomeBg) {
		this.pagehomeBg = pagehomeBg;
	}


	public void setShareButton(String shareButton) {
		this.shareButton = shareButton;
	}

	public String getShareButton() {
		return this.shareButton;
	}

	public void setQuesBg(String quesBg) {
		this.quesBg = quesBg;
	}

	public String getQuesBg() {
		return this.quesBg;
	}

	public void setResBg(String resBg) {
		this.resBg = resBg;
	}

	public String getResBg() {
		return this.resBg;
	}

	public void setRateBg(String rateBg) {
		this.rateBg = rateBg;
	}

	public String getRateBg() {
		return this.rateBg;
	}

	public void setRatedoneBg(String ratedoneBg) {
		this.ratedoneBg = ratedoneBg;
	}

	public String getRatedoneBg() {
		return this.ratedoneBg;
	}

	public void setRankBg(String rankBg) {
		this.rankBg = rankBg;
	}

	public String getRankBg() {
		return this.rankBg;
	}

	public void setResSort(String resSort) {
		this.resSort = resSort;
	}

	public String getResSort() {
		return this.resSort;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getMusic() {
		return this.music;
	}

	public String getNoresBg() {
		return noresBg;
	}

	public void setNoresBg(String noresBg) {
		this.noresBg = noresBg;
	}

	public int getShowRank() {
		return showRank;
	}

	public void setShowRank(int showRank) {
		this.showRank = showRank;
	}

	public String getPercentOptionPic() {
		return percentOptionPic;
	}

	public void setPercentOptionPic(String percentOptionPic) {
		this.percentOptionPic = percentOptionPic;
	}

	public String getRightIcon() {
		return rightIcon;
	}

	public void setRightIcon(String rightIcon) {
		this.rightIcon = rightIcon;
	}

	public int getPercentOptionMark() {
		return percentOptionMark;
	}

	public void setPercentOptionMark(int percentOptionMark) {
		this.percentOptionMark = percentOptionMark;
	}

	public String getPercentRateColor() {
		return percentRateColor;
	}

	public void setPercentRateColor(String percentRateColor) {
		this.percentRateColor = percentRateColor;
	}

	public String getPercentRateBg() {
		return percentRateBg;
	}

	public void setPercentRateBg(String percentRateBg) {
		this.percentRateBg = percentRateBg;
	}

	public String getOptionFrameColor() {
		return optionFrameColor;
	}

	public void setOptionFrameColor(String optionFrameColor) {
		this.optionFrameColor = optionFrameColor;
	}

	public String getOptionFrameBg() {
		return optionFrameBg;
	}

	public void setOptionFrameBg(String optionFrameBg) {
		this.optionFrameBg = optionFrameBg;
	}

	public String getOptionFontColor() {
		return optionFontColor;
	}

	public void setOptionFontColor(String optionFontColor) {
		this.optionFontColor = optionFontColor;
	}

	public String getRankTitleBg() {
		return rankTitleBg;
	}

	public void setRankTitleBg(String rankTitleBg) {
		this.rankTitleBg = rankTitleBg;
	}

	public String getRedoMiniButton() {
		return redoMiniButton;
	}

	public void setRedoMiniButton(String redoMiniButton) {
		this.redoMiniButton = redoMiniButton;
	}

	public String getLeftHotPic() {
		return leftHotPic;
	}

	public void setLeftHotPic(String leftHotPic) {
		this.leftHotPic = leftHotPic;
	}

	public String getRightHotPic() {
		return rightHotPic;
	}

	public void setRightHotPic(String rightHotPic) {
		this.rightHotPic = rightHotPic;
	}

	public int getRankHotPicPosition() {
		return rankHotPicPosition;
	}

	public void setRankHotPicPosition(int rankHotPicPosition) {
		this.rankHotPicPosition = rankHotPicPosition;
	}

	public int getResHotPicPosition() {
		return resHotPicPosition;
	}

	public void setResHotPicPosition(int resHotPicPosition) {
		this.resHotPicPosition = resHotPicPosition;
	}

	public String getHomepageFontColor() {
		return homepageFontColor;
	}

	public void setHomepageFontColor(String homepageFontColor) {
		this.homepageFontColor = homepageFontColor;
	}

	public String getRankUsernameColor() {
		return rankUsernameColor;
	}

	public void setRankUsernameColor(String rankUsernameColor) {
		this.rankUsernameColor = rankUsernameColor;
	}

	public String getRankResFontColor() {
		return rankResFontColor;
	}

	public void setRankResFontColor(String rankResFontColor) {
		this.rankResFontColor = rankResFontColor;
	}

	public String getResFontColor() {
		return resFontColor;
	}

	public void setResFontColor(String resFontColor) {
		this.resFontColor = resFontColor;
	}

	public String getResDescriptionFontColor() {
		return resDescriptionFontColor;
	}

	public void setResDescriptionFontColor(String resDescriptionFontColor) {
		this.resDescriptionFontColor = resDescriptionFontColor;
	}

	public String getHotRecommendFontColor() {
		return hotRecommendFontColor;
	}

	public void setHotRecommendFontColor(String hotRecommendFontColor) {
		this.hotRecommendFontColor = hotRecommendFontColor;
	}

	public String getHotRecommendGroupColor() {
		return hotRecommendGroupColor;
	}

	public void setHotRecommendGroupColor(String hotRecommendGroupColor) {
		this.hotRecommendGroupColor = hotRecommendGroupColor;
	}

	public String getHotRecommendGroupBg() {
		return hotRecommendGroupBg;
	}

	public void setHotRecommendGroupBg(String hotRecommendGroupBg) {
		this.hotRecommendGroupBg = hotRecommendGroupBg;
	}

	public String getHomepageBg() {
		return homepageBg;
	}

	public void setHomepageBg(String homepageBg) {
		this.homepageBg = homepageBg;
	}

	public String getQuestionBg() {
		return questionBg;
	}

	public void setQuestionBg(String questionBg) {
		this.questionBg = questionBg;
	}

	public String getResBgColor() {
		return resBgColor;
	}

	public void setResBgColor(String resBgColor) {
		this.resBgColor = resBgColor;
	}

}
