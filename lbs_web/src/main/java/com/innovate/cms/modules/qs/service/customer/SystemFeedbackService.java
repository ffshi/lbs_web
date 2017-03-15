/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.customer.SystemFeedbackDao;
import com.innovate.cms.modules.qs.entity.customer.SystemFeedback;

/**
 * 反馈表Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class SystemFeedbackService extends CrudService<SystemFeedbackDao, SystemFeedback> {

	public SystemFeedback get(String id) {
		return super.get(id);
	}
	
	public List<SystemFeedback> findList(SystemFeedback systemFeedback) {
		return super.findList(systemFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(SystemFeedback systemFeedback) {
		if (StringUtils.isBlank(systemFeedback.getId()))	
		{
			systemFeedback.setIsNewRecord(true);
		}else {
			systemFeedback.setIsNewRecord(false);
		}
		super.save(systemFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemFeedback systemFeedback) {
		super.delete(systemFeedback);
	}
	
}