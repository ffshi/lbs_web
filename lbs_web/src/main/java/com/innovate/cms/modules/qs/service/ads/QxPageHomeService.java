/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ads;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.data.entity.HomePageToJson;
import com.innovate.cms.modules.qs.dao.ads.QxPageHomeDao;
import com.innovate.cms.modules.qs.entity.ads.QxPageHome;
import com.innovate.cms.modules.qs.entity.ads.QxScreenAd;
import com.innovate.cms.modules.qs.entity.ads.RotationGroup;

/**
 * 首页配置Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxPageHomeService extends CrudService<QxPageHomeDao, QxPageHome> {

	public QxPageHome get(String id) {
		return super.get(id);
	}
	
	public List<QxPageHome> findList(QxPageHome qxPageHome) {
		return super.findList(qxPageHome);
	}
	
	@Transactional(readOnly = false)
	public void save(QxPageHome qxPageHome) {
		if (StringUtils.isBlank(qxPageHome.getHid()))	
		{
			qxPageHome.setIsNewRecord(true);
		}else {
			qxPageHome.setIsNewRecord(false);
		}
		super.save(qxPageHome);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxPageHome qxPageHome) {
		super.delete(qxPageHome);
	}
	
	/**
	 * 查询所有首页配置信息
	 * @param pageHome
	 * @return
	 */
	public List<HomePageToJson> findAll() {
		return super.dao.findAll();
	}

	/**
	 * 获取闪屏广告
	 * @return
	 */
	public QxScreenAd getScreenAd() {
		return super.dao.getScreenAd();
	}

	
	/**
	 * 根据日期设置首页轮播专题
	 * @param gid
	 * @param showDate
	 */
	@Transactional(readOnly = false)
	public void setRotationGroup(int gid, int showDate) {
		super.dao.setRotationGroup(gid,showDate);
	}

	/**
	 * 获取当天轮播专题
	 * @param currentDay
	 * @return
	 */
	public List<RotationGroup> getRotationGroup(int currentDay) {
		return super.dao.getRotationGroup(currentDay);
	}
	
	
}