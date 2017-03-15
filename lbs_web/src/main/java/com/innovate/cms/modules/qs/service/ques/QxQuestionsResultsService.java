/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ques;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.ques.QxQuestionsResultsDao;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsResults;
import com.innovate.cms.modules.qs.entity.ques.Result;
import com.innovate.cms.modules.qs.entity.ques.Result5;

/**
 * 趣选问题结果表Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxQuestionsResultsService extends CrudService<QxQuestionsResultsDao, QxQuestionsResults> {

	public QxQuestionsResults get(String id) {
		return super.get(id);
	}
	
	public List<QxQuestionsResults> findList(QxQuestionsResults qxQuestionsResults) {
		return super.findList(qxQuestionsResults);
	}

	@Transactional(readOnly = false)
	public void save(QxQuestionsResults qxQuestionsResults) {
		if (StringUtils.isBlank(qxQuestionsResults.getId()))	
		{
			qxQuestionsResults.setIsNewRecord(true);
		}else {
			qxQuestionsResults.setIsNewRecord(false);
		}
		super.save(qxQuestionsResults);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxQuestionsResults qxQuestionsResults) {
		super.delete(qxQuestionsResults);
	}

	public List<Result> findResultsBygid(int gid) {
		return super.dao.findResultsBygid(gid);
	}

	/**
	 * 获取模板5专题结果匹配度文案
	 * @param parseInt
	 * @return
	 */
	public List<Result5> findResultsBygid5(int gid) {
		// TODO Auto-generated method stub
		return super.dao.findResultsBygid5(gid);
	}
	
}