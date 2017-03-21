package com.innovate.cms.modules.qs.entity.msg;

import java.io.Serializable;
import java.util.List;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgComment;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPriseForService;

/**
 * 动态消息详情页
 * @author shifangfang
 * @date 2017年3月18日 上午10:03:03
 */
public class DynamicMsgInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DynamicMsgComment> msgComments;
	private List<DynamicMsgPriseForService> msgPrises;

	public DynamicMsgInfo() {
		super();
	}

	public List<DynamicMsgComment> getMsgComments() {
		return msgComments;
	}

	public void setMsgComments(List<DynamicMsgComment> msgComments) {
		this.msgComments = msgComments;
	}

	public List<DynamicMsgPriseForService> getMsgPrises() {
		return msgPrises;
	}

	public void setMsgPrises(List<DynamicMsgPriseForService> msgPrises) {
		this.msgPrises = msgPrises;
	}

}
