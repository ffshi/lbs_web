/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.ads;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.HomePageToJson;
import com.innovate.cms.modules.qs.entity.ads.QxPageHome;
import com.innovate.cms.modules.qs.entity.ads.QxScreenAd;
import com.innovate.cms.modules.qs.entity.ads.RotationGroup;

/**
 * 首页配置DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxPageHomeDao extends CrudDao<QxPageHome> {

	/**
	 * 查询首页配置信息DAO层接口
	 * @return
	 */
	public List<HomePageToJson> findAll();

	/**
	 * 获取闪屏广告
	 * @return
	 */
	public QxScreenAd getScreenAd();

	/**
	 * 根据日期设置首页轮播专题
	 * @param gid
	 * @param showDate
	 */
	public void setRotationGroup(@Param("gid")int gid, @Param("showDate")int showDate);
	/**
	 * 获取当天轮播专题
	 * @param currentDay
	 * @return
	 */
	public List<RotationGroup> getRotationGroup(@Param("currentDay")int currentDay);
	
}