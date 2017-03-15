/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.ques;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsOptionCount;

/**
 * 趣选答案选项统计表Dao
 * 
 * @author shifangfang
 *
 */
@MyBatisDao
public interface QxQuestionsOptionCountDao extends CrudDao<QxQuestionsOptionCount> {

	/**
	 * 统计问题选项被选择次数
	 * 
	 * @param qaid
	 */
	public void increaseCount(@Param("qaid") int qaid);

}