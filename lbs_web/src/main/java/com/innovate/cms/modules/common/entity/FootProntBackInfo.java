package com.innovate.cms.modules.common.entity;

/**
 * 用户足迹返回json
 * 
 * @author shifangfang
 *
 * @param <T>
 */
public class FootProntBackInfo<T> extends DataBackInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;// 用户uid
	private String footName;// 用户昵称
	private String footImg;// 用户头像

	public FootProntBackInfo() {
		super();
	}

	public FootProntBackInfo(String uid, String footName, String footImg) {
		super();
		this.uid = uid;
		this.footName = footName;
		this.footImg = footImg;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFootName() {
		return footName;
	}

	public void setFootName(String footName) {
		this.footName = footName;
	}

	public String getFootImg() {
		return footImg;
	}

	public void setFootImg(String footImg) {
		this.footImg = footImg;
	}

}
