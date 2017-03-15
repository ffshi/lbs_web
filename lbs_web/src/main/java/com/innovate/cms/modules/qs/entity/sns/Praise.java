/**
 * 
 */
package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;

/**
 * 点赞列表json
 * @author hushasha
 * @date 2016年8月10日
 */
public class Praise implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fpid; //点赞ID
	
    private String praiseUid; //点赞人UID

    private String praiseName; //点赞人昵称

	public Praise() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Praise(Integer fpid, String praiseUid, String praiseName) {
		super();
		this.fpid = fpid;
		this.praiseUid = praiseUid;
		this.praiseName = praiseName;
	}

	public Integer getFpid() {
		return fpid;
	}

	public void setFpid(Integer fpid) {
		this.fpid = fpid;
	}

	public String getPraiseUid() {
		return praiseUid;
	}

	public void setPraiseUid(String praiseUid) {
		this.praiseUid = praiseUid;
	}

	public String getPraiseName() {
		return praiseName;
	}

	public void setPraiseName(String praiseName) {
		this.praiseName = praiseName;
	}
    
}
