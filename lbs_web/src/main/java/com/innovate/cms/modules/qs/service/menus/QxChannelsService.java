/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.menus;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.menus.QxChannelsDao;
import com.innovate.cms.modules.qs.entity.menus.Menu;
import com.innovate.cms.modules.qs.entity.menus.QxChannels;

/**
 * 分类表Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxChannelsService extends CrudService<QxChannelsDao, QxChannels> {

	public QxChannels get(String id) {
		return super.get(id);
	}
	
	public List<QxChannels> findList(QxChannels qxChannels) {
		return super.findList(qxChannels);
	}
	
	@Transactional(readOnly = false)
	public void save(QxChannels qxChannels) {
		if (StringUtils.isBlank(qxChannels.getId()))	
		{
			qxChannels.setIsNewRecord(true);
		}else {
			qxChannels.setIsNewRecord(false);
		}
		super.save(qxChannels);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxChannels qxChannels) {
		super.delete(qxChannels);
	}

	/**
	 * 获取所有菜单信息
	 * @return
	 */
	public List<Menu> getAllMenus() {
		// TODO Auto-generated method stub
		return super.dao.getAllMenus();
	}
	
}