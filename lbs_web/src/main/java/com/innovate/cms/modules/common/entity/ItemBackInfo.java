/**
 * 
 */
package com.innovate.cms.modules.common.entity;

/**
 * 返回对象数据Json样式
 * @author hushasha
 * @date 2016年4月20日
 */
public class ItemBackInfo extends BaseBackInfo  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对象数据
	 */
	private Object item;

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
	
	
}
