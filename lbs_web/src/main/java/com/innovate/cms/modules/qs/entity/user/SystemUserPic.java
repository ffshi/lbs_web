package com.innovate.cms.modules.qs.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 用户相册的照片entity
 * 
 * @author shifangfang
 * @date 2017年4月6日 下午4:53:38
 */
public class SystemUserPic extends DataEntity<SystemUserPic> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// `pic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户相册图片id',
	// `url` varchar(255) DEFAULT '' COMMENT '图片url',
	// `uid` varchar(32) DEFAULT '' COMMENT '用户id',
	// `u_num` int(11) DEFAULT NULL COMMENT '本平台用户号-类似qq号',

	@JSONField(name = "pic_id")
	private String picId;
	private String url;
	private String uid;
	@JSONField(name = "u_num")
	private int uNum;

	public SystemUserPic() {
		super();
	}

	public SystemUserPic(String picId, String url, String uid, int uNum) {
		super();
		this.picId = picId;
		this.url = url;
		this.uid = uid;
		this.uNum = uNum;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getuNum() {
		return uNum;
	}

	public void setuNum(int uNum) {
		this.uNum = uNum;
	}

}
