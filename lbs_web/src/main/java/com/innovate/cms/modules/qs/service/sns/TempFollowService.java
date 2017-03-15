/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.sns.TempFollowDao;
import com.innovate.cms.modules.qs.entity.sns.TempFollow;

/**
 * 好友临时表sevice
 * 
 * @author shifangfang
 *
 */
@Service
@Transactional(readOnly = true)
public class TempFollowService extends CrudService<TempFollowDao, TempFollow> {

	/**
	 * 根据uid删除 好友表与临时好友表的共同好友
	 * 
	 * 
	 * @param uid
	 */
	@Transactional(readOnly = false)
	public void delDuplicateFriends(@Param("uid") String uid) {
		super.dao.delDuplicateFriends(uid);
	}

	/**
	 * 根据uid，获取指定num个推荐好友
	 * @param uid
	 * @param num
	 * @return
	 */
	public List<TempFollow> recommendFriends(@Param("uid") String uid, @Param("num") int num) {
		return super.dao.recommendFriends(uid, num);
	}

	/**
	 * 从临时表中删除推荐过的好友
	 * 
	 * @param flids
	 */
	@Transactional(readOnly = false)
	public void delRecommendedFriends(List<String> flids) {
		super.dao.delRecommendedFriends(flids);

	}
	/**
	 * 保存临时好友
	 * @Title: saveTempFollow
	 * @Description: H5端保存临时好友
	 * @param entity    返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public void saveTempFollow(TempFollow entity)
	{
		super.dao.insert(entity);
		
	}

	/**
	 * 
	 * @Title: findIsFollow
	 * @Description: 查询好友表/临时好友表中 有没有重复数据
	 * @param uid
	 * @param followUid
	 * @return    返回类型 List<TempFollow>
	 *
	 */
	public List<TempFollow> findIsFollow(String uid,String followUid)
	{
		return super.dao.findIsFollow(uid, followUid);
	}
	
}