package com.innovate.cms.modules.common.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户相册
 * @author shifangfang
 * @date 2017年4月7日 下午4:41:51
 */
public class UserPic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(name = "pic_id")
	private String picId;
	private String url;

	public UserPic() {
		super();
	}

	public UserPic(String picId, String url) {
		super();
		this.picId = picId;
		this.url = url;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
