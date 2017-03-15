/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.ques;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.ques.QxHistoryAnswer;

/**
 * 用户问题选项DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxHistoryAnswerDao extends CrudDao<QxHistoryAnswer> {
	/**
	 * 
	 * @Title: addHistoryAnswer
	 * @Description: 新增选项记录
	 * @param qxHistoryAnswer
	 * @return    返回类型 int
	 *
	 */
	public int addHistoryAnswer(QxHistoryAnswer qxHistoryAnswer);

	/**
	 * H5需要
	 * 
	 * 根据两个用户做的同一个专题的答案计算匹配度
	 * 
	 * @param uid
	 * @param fuid
	 * @param gid
	 * @return
	 */
	public String getMatchResult(@Param("uid")String uid, @Param("fuid")String fuid, @Param("gid")String gid);
}