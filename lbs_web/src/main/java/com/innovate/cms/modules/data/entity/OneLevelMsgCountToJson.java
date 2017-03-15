package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

/**
 * 一级消息列表按照msgGroupId汇总
 * 
 * @author shifangfang
 * @date 2016年8月11日 下午5:54:34
 */
public class OneLevelMsgCountToJson implements Serializable {

	private static final long serialVersionUID = 1L;

	private int msgGroupId;

	private int count;

	public OneLevelMsgCountToJson() {
		super();
	}

	public int getMsgGroupId() {
		return msgGroupId;
	}

	public void setMsgGroupId(int msgGroupId) {
		this.msgGroupId = msgGroupId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
