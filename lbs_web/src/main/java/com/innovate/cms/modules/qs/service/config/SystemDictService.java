/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.config;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.config.SystemDictDao;
import com.innovate.cms.modules.qs.entity.config.SystemDict;

/**
 * 字典Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class SystemDictService extends CrudService<SystemDictDao, SystemDict> {

	public SystemDict get(String id) {
		return super.get(id);
	}
	
	public List<SystemDict> findList(SystemDict systemDict) {
		return super.findList(systemDict);
	} 
	/**
	 * 
	 * @Title: findListByLabel
	 * @Description: 根据标签，查询字典集合
	 * @param label
	 * @return    返回类型 List<SystemDict>
	 *
	 */
	public List<SystemDict> findListByLabel(String label) {
		return super.dao.findListByLabel(label);
	} 
	
	@Transactional(readOnly = false)
	public void save(SystemDict systemDict) {
		if (StringUtils.isBlank(systemDict.getId()))	
		{
			systemDict.setIsNewRecord(true);
		}else {
			systemDict.setIsNewRecord(false);
		}
		super.save(systemDict);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemDict systemDict) {
		super.delete(systemDict);
	}
	/**
	 * 
	 * @Title: doSwitch
	 * @Description: 模拟好友开关
	 * @param state    返回类型 void
	 * @param uid 
	 *
	 */
	@Transactional(readOnly = false)
	public void doSwitch(String state, String uid)
	{
		if ("1".equals(state))	
		{
			super.dao.openSwitch(uid);
		}else {
			super.dao.closeSwitch(uid);
		}
		
	}
	
}