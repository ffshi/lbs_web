/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;
import java.util.Date;

/**
 * 精华专题统计表(点赞和评论)
 * 
 * @author hushasha
 * @date 2016年10月24日
 */
public class QxGroupStatistic implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id; // 自增ID
	private String uid; // 专题创建人ID
	private Integer gid; // 专题ID
	private Integer praiseNum; // 专题点赞数量
	private Integer commentNum; // 专题评论数量
	private Date updateTime; // 数据更新时间

	public QxGroupStatistic() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}