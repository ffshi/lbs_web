/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.customer;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.customer.SystemReport;

/**
 * 举报表DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface SystemReportDao extends CrudDao<SystemReport> {
	
}