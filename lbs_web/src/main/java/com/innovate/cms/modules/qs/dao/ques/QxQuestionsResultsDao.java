/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.ques;

import java.util.List;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsResults;
import com.innovate.cms.modules.qs.entity.ques.Result;
import com.innovate.cms.modules.qs.entity.ques.Result5;

/**
 * 趣选问题结果表DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxQuestionsResultsDao extends CrudDao<QxQuestionsResults> {

	/**
	 * 获取模板4结果
	 * @param gid
	 * @return
	 */
	public List<Result> findResultsBygid(int gid);

	/**
	 * /**
	 * 获取模板5专题结果匹配度文案
	 * @param parseInt
	 * @return
	 */
	public List<Result5> findResultsBygid5(int gid);
	
}