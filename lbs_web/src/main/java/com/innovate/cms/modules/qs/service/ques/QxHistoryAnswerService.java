/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ques;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.modules.qs.dao.ques.QxHistoryAnswerDao;
import com.innovate.cms.modules.qs.entity.ques.QxHistoryAnswer;

/**
 * 用户答题记录Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxHistoryAnswerService extends CrudService<QxHistoryAnswerDao, QxHistoryAnswer> {

	/**
	 * 正则校验 [A-Z]|-1  如果不满足正则，qxHistoryAnswer.setAnswer("-1");
	 * @Title: addHistoryAnswer
	 * @Description: 新增选项记录
	 * @param qxHistoryAnswer
	 * @return    返回类型 int
	 * 
	 */
	@Transactional(readOnly = false)
	public int addHistoryAnswer(QxHistoryAnswer qxHistoryAnswer) {

		String regex = "[0-9]|-1";
		if (!ReUtil.isMatch(regex, qxHistoryAnswer.getAnswer()))
		{
			qxHistoryAnswer.setAnswer("-1");
		}
		return super.dao.addHistoryAnswer(qxHistoryAnswer);
	}

	/**
	 * 根据两个用户的做题答案计算匹配度
	 * @param uid
	 * @param fuid
	 * @param gid
	 * @return
	 */
	public String getMatchResult(@Param("uid")String uid, @Param("fuid")String fuid, @Param("gid")String gid) {
		// TODO Auto-generated method stub
		return super.dao.getMatchResult(uid,fuid,gid);
	}
}