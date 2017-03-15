package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.List;

import com.innovate.cms.modules.qs.entity.ads.PageHome;

/**
 * 返回给前台的 首页json样式
 * @author hushasha
 * @date 2016年4月6日
 */
public class HomePageToJson implements Serializable {


	private static final long serialVersionUID = 1L;
	//1、banner 2、热门专题 3、普通专题组
	private String rowType;
	//标题 文字/img
	private String title;
	//数据集合
	private List<PageHome> lists;
	
	public HomePageToJson() {
		super();
	}

	public HomePageToJson(String rowType, String title, List<PageHome> lists) {
		super();
		this.rowType = rowType;
		this.title = title;
		this.lists = lists;
	}

	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PageHome> getLists() {
		return lists;
	}

	public void setLists(List<PageHome> lists) {
		this.lists = lists;
	}
	


}
