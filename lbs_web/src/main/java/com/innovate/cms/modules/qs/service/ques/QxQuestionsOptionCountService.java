/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ques;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.ques.QxQuestionsOptionCountDao;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsOptionCount;

/**
 * 趣选选项答案统计表Service
 * 
 * @author shifangfang
 *
 */
@Service
@Transactional(readOnly = true)
public class QxQuestionsOptionCountService extends CrudService<QxQuestionsOptionCountDao, QxQuestionsOptionCount> {

	/**
	 * 统计问题选项被选择次数
	 * 
	 * @param qaid
	 */
	@Transactional(readOnly = false)
	public void increaseCount(@Param("qaid") int qaid) {
		// TODO Auto-generated method stub
		super.dao.increaseCount(qaid);
	}

}