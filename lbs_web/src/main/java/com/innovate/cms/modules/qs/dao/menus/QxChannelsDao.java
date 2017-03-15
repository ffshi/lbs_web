/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.menus;

import java.util.List;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.menus.Menu;
import com.innovate.cms.modules.qs.entity.menus.QxChannels;

/**
 * 分类表DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxChannelsDao extends CrudDao<QxChannels> {

	/**
	 * 获取所有菜单信息
	 * @return
	 */
	public List<Menu> getAllMenus();

}