package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * 模板5匹配区间文案 接口返回样式
 * 
 * @author shifangfang
 *
 */
public class Result5 implements Serializable {

	private static final long serialVersionUID = 1L;

	private String zone;

	private String zoneContent;

	private String title;

	public Result5() {
		super();
	}

	public Result5(String zone, String zoneContent, String title) {
		super();
		this.zone = zone;
		this.zoneContent = zoneContent;
		this.title = title;
	}

	public Result5(String zone, String zoneContent) {
		super();
		this.zone = zone;
		this.zoneContent = zoneContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getZoneContent() {
		return zoneContent;
	}

	public void setZoneContent(String zoneContent) {
		this.zoneContent = zoneContent;
	}

}
