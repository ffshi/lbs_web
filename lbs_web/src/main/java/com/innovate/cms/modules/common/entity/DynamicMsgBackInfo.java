package com.innovate.cms.modules.common.entity;

import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;

/**
 * 消息详情页
 * 
 * @author shifangfang
 * @date 2017年9月11日 下午3:21:39
 */
public class DynamicMsgBackInfo extends ItemBackInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DynamicMsgForService msg;

	public DynamicMsgForService getMsg() {
		return msg;
	}

	public void setMsg(DynamicMsgForService msg) {
		this.msg = msg;
	}

}
