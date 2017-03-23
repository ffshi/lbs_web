/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.BubbleInfoToJson;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;

/**
 * 系统用户信息DAO接口
 * @author gaoji
 * @version 2016-04-12
 */
@MyBatisDao
public interface SystemUserInfoDao extends CrudDao<SystemUserInfo> {
	/**
	 * 登陆时-新增用户关联表
	 * @Title: insertInfo
	 * @Description: toLogin-->saveUserAndInfo 登陆新增用户
	 * @param systemUserInfo    返回类型 void
	 *
	 */
	public void insertInfo(SystemUserInfo systemUserInfo);
	/**
	 * 登陆时-修改用户关联表
	 * @Title: updateInfo
	 * @Description: toLogin-->saveUserAndInfo 登陆修改用户
	 * @param systemUserInfo    返回类型 void
	 *
	 */
	public void updateInfo(SystemUserInfo systemUserInfo);

	/**
	 * 气泡推荐
	 * @param womanNum 女性数量
	 * @param manNum 男性数量
	 * @return 
	 */
	public List<BubbleInfoToJson> getBubbleNorminate(@Param("womanNum")int womanNum, @Param("manNum")int manNum);
	/**  
	 * 模板五结果页随机推荐2人接口
	 * @param uid
	 * @param gid
	 * @return  
	 */    
	

	
}