package com.innovate.cms.modules.data.entity;

/**
 * H5推广运营-热门专题推荐配置 运营根据专题自定义推荐
 * 
 * @author shifangfang
 * @date 2016年8月10日 下午4:25:25
 */
public class MarketGroupRecommendToJson {

	private String recommendType;
	private String pic;
	private String title;
	private String templateTag;

	private String url;

	public MarketGroupRecommendToJson() {
		super();
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(String recommendType) {
		this.recommendType = recommendType;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
