/**
 * 
 */
package com.innovate.cms.modules.data.entity;


/**
 * wishbone 用户首页接口
 * 
 * @author shifangfang
 * @date 2016年10月26日 下午2:52:57
 */
public class UgcHistoryGroupToJson extends UgcGroupToJson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//用户 模板0选择结果 1-A 2-B
	private int selected;
	//好友动态 模板0好友选择结果 1-A 2-B
	//此处Integer类型不要修改, 因为足迹专题详情是不需要这个字段的
	private Integer followSelected;
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	public Integer getFollowSelected() {
		return followSelected;
	}
	public void setFollowSelected(Integer followSelected) {
		this.followSelected = followSelected;
	}

}
