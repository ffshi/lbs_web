/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.config;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.config.SystemDict;

/**
 * 字典DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface SystemDictDao extends CrudDao<SystemDict> {
	/**
	 * 
	 * @Title: findListByLabel
	 * @Description: 根据标签，查询字典集合
	 * @param label
	 * @return    返回类型 List<SystemDict>
	 *
	 */
	public List<SystemDict> findListByLabel(@Param("label") String label);
	/**
	 * 
	 * @param uid 
	 * @Title: openSwitch
	 * @Description: 打开模拟加好友开关
	 *
	 */
	public void openSwitch(@Param("uid") String uid);
	/**
	 * 
	 * @param uid 
	 * @Title: closeSwitch
	 * @Description: 关闭模拟加好友开关
	 *
	 */
	public void closeSwitch(@Param("uid") String uid);
}