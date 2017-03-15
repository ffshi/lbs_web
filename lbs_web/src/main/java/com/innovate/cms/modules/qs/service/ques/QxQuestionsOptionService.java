/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ques;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.ques.QxQuestionsOptionDao;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsOption;

/**
 * 趣选问题答案Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxQuestionsOptionService extends CrudService<QxQuestionsOptionDao, QxQuestionsOption> {

	public QxQuestionsOption get(String id) {
		return super.get(id);
	}
	
	public List<QxQuestionsOption> findList(QxQuestionsOption qxQuestionsOption) {
		return super.findList(qxQuestionsOption);
	}

	@Transactional(readOnly = false)
	public void save(QxQuestionsOption qxQuestionsOption) {
		if (StringUtils.isBlank(qxQuestionsOption.getId()))	
		{
			qxQuestionsOption.setIsNewRecord(true);
		}else {
			qxQuestionsOption.setIsNewRecord(false);
		}
		super.save(qxQuestionsOption);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxQuestionsOption qxQuestionsOption) {
		super.delete(qxQuestionsOption);
	}
	
}