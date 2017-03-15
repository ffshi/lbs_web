package com.innovate.cms.modules.qs.entity.ads;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * app闪屏广告配置
 * 
 * @author shifangfang
 * @date 2016年9月26日 上午10:39:36
 */
public class QxScreenAd implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String pic;

	private String screenType;

	private String templateTag;

	private String destination;

	@JsonIgnore
	private Date createTime; // 数据创建时间

	public QxScreenAd() {
		super();
	}

	public QxScreenAd(String id, String pic, String screenType, String templateTag, String destination, Date createTime) {
		super();
		this.id = id;
		this.pic = pic;
		this.screenType = screenType;
		this.templateTag = templateTag;
		this.destination = destination;
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
