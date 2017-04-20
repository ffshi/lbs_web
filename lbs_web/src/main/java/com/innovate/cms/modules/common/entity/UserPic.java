package com.innovate.cms.modules.common.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户相册
 * 
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
	@JsonIgnore
	private String uid;

	public UserPic() {
		super();
	}

	public UserPic(String uid, String url) {
		super();
		this.url = url;
		this.uid = uid;
	}

	public UserPic(String url) {
		super();
		this.url = url;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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
