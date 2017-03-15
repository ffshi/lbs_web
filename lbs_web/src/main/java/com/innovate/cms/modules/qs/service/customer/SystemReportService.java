/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.customer.SystemReportDao;
import com.innovate.cms.modules.qs.entity.customer.SystemReport;

/**
 * 举报表Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class SystemReportService extends CrudService<SystemReportDao, SystemReport> {

	public SystemReport get(String id) {
		return super.get(id);
	}
	
	public List<SystemReport> findList(SystemReport systemReport) {
		return super.findList(systemReport);
	}

	@Transactional(readOnly = false)
	public void save(SystemReport systemReport) {
		if (StringUtils.isBlank(systemReport.getId()))	
		{
			systemReport.setIsNewRecord(true);
		}else {
			systemReport.setIsNewRecord(false);
		}
		super.save(systemReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemReport systemReport) {
		super.delete(systemReport);
	}
	
}