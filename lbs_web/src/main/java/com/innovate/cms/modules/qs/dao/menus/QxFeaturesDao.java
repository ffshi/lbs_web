/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.menus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.MenuFeaturesToJson;
import com.innovate.cms.modules.qs.entity.menus.FeatureMenu;
import com.innovate.cms.modules.qs.entity.menus.QxFeatures;

/**
 * 专题组DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxFeaturesDao extends CrudDao<QxFeatures> {

	List<MenuFeaturesToJson> getFeaturesByCid(@Param("cid") int cid);

	/**  
	 * 获取所有的专题组菜单
	 * @return  
	 */    
	
	List<FeatureMenu> getAllFeatures();
	
}