/**
 * 
 */
package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

/**
 * 删除回复接口 返回对象数据封装
 * 
 * @author hushasha
 * @date 2016年4月21日
 */
public class DelReply implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	//留言ID
	private String msid;

	public DelReply() {
		super();
	}

	public DelReply(String msid) {
		super();
		this.msid = msid;
	}

	public String getMsid() {
		return msid;
	}

	public void setMsid(String msid) {
		this.msid = msid;
	}

}
