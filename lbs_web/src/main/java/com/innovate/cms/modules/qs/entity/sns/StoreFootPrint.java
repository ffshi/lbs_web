package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

/**
 * 存储足迹专用，由于推送需要用footid,为了mybatis存储能自动返回id，参数必须是一个对象或者map
 * 
 * 
 * @author shifangfang
 * @date 2016年6月21日 下午5:41:07
 */
public class StoreFootPrint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3362038024603235888L;

	private int footid;
	private String uid;
	private String gid;
	private String fuid;
	private String results;
	private String footType;

	public StoreFootPrint() {
		super();
	}

	public StoreFootPrint(int footid, String uid, String gid, String fuid, String results, String footType) {
		super();
		this.footid = footid;
		this.uid = uid;
		this.gid = gid;
		this.fuid = fuid;
		this.results = results;
		this.footType = footType;
	}

	public String getFootType() {
		return footType;
	}

	public void setFootType(String footType) {
		this.footType = footType;
	}

	public int getFootid() {
		return footid;
	}

	public void setFootid(int footid) {
		this.footid = footid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	

}
