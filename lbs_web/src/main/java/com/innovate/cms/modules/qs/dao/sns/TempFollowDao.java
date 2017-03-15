/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.sns.TempFollow;

/**
 * 好友临时表 Dao
 * 
 * @author shifangfang
 *
 */
@MyBatisDao
public interface TempFollowDao extends CrudDao<TempFollow> {

	/**
	 * 根据uid删除 好友表与临时好友表的共同好友
	 * 
	 * @param uid
	 */
	public void delDuplicateFriends(@Param("uid") String uid);

	/**
	 * 根据uid，获取指定num个推荐好友
	 * 
	 * @param uid
	 * @param num
	 * @return
	 */
	public List<TempFollow> recommendFriends(@Param("uid") String uid, @Param("num") int num);

	/**
	 * 从临时表中删除推荐过的好友
	 * @param flids
	 */
	public void delRecommendedFriends(List<String> flids);
	/**
	 * 
	 * @Title: findIsFollow
	 * @Description: 查询好友表/临时好友表中 有没有重复数据
	 * @param uid
	 * @param followUid
	 * @return    返回类型 List<TempFollow>
	 *
	 */
	public List<TempFollow> findIsFollow(@Param("uid") String uid, @Param("followUid") String followUid);
}