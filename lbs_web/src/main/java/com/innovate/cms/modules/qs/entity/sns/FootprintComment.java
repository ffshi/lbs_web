package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

/**
 * 添加评论返回json样式
 * 
 * @author hushasha
 * @date 2016年8月18日
 */
public class FootprintComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer fcid; // 评论ID

	public FootprintComment() {
		super();
	}

	public Integer getFcid() {
		return fcid;
	}

	public void setFcid(Integer fcid) {
		this.fcid = fcid;
	}

}