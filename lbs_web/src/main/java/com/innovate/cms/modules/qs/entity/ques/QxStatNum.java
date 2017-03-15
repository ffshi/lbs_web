/**
 * 
 */
package com.innovate.cms.modules.qs.entity.ques;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * 用户每个频道做题数 实体类
 * 
 * @author hushasha
 * @date 2016年8月23日
 */
public class QxStatNum extends DataEntity<QxStatNum> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid; // 用户ID
	private Integer cid; // 分类编号，即频道ID
	private Integer num; // 做题数量
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间

	public QxStatNum() {
		super();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
