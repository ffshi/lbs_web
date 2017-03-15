/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.menus;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.data.entity.MenuFeaturesToJson;
import com.innovate.cms.modules.qs.dao.menus.QxFeaturesDao;
import com.innovate.cms.modules.qs.entity.menus.FeatureMenu;
import com.innovate.cms.modules.qs.entity.menus.QxFeatures;

/**
 * 专题组Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxFeaturesService extends CrudService<QxFeaturesDao, QxFeatures> {

	public QxFeatures get(String id) {
		return super.get(id);
	}
	
	public List<QxFeatures> findList(QxFeatures qxFeatures) {
		return super.findList(qxFeatures);
	}

	@Transactional(readOnly = false)
	public void save(QxFeatures qxFeatures) {
		if (StringUtils.isBlank(qxFeatures.getId()))	
		{
			qxFeatures.setIsNewRecord(true);
		}else {
			qxFeatures.setIsNewRecord(false);
		}
		super.save(qxFeatures);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxFeatures qxFeatures) {
		super.delete(qxFeatures);
	}
	/**
	 * 
	 * @Title: getFeaturesByCid
	 * @Description: 根据cid 查询出所有专题组
	 * @param cid
	 * @return    返回类型 List<MenuFeaturesToJson>
	 *
	 */
	public List<MenuFeaturesToJson> getFeaturesByCid(int cid)
	{
		return super.dao.getFeaturesByCid(cid);
	}

	/**  
	 * 获取所有的专题组菜单
	 * @return  
	 */    
	
	public List<FeatureMenu> getAllFeatures() {
		return super.dao.getAllFeatures();
	}
	
}