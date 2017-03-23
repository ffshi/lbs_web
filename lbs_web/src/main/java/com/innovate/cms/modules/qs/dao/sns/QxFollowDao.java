/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.sns.QxFollow;
import com.innovate.cms.modules.qs.entity.sns.QxFriend;

/**
 * 关注表DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxFollowDao extends CrudDao<QxFollow> {

	/**
	 * 判断是否是好友
	 * 
	 * @param qxFollow
	 * @return
	 */
	public int isExist(QxFollow qxFollow);
	
	/**  
	 * 判断是否是好友, 添加delFlag = 0的条件限制
	 * 
	 * @param qxFollow
	 * @return  
	 */    
	
	public int isExistNew(QxFollow qxFollow);

	/**
	 * 存储好友关系
	 * 
	 * @param qxFollow
	 */
	public void saveFollow(QxFollow qxFollow);

	/**
	 * 更新好友关系
	 * 
	 * @param qxFollow
	 */
	public void updateFollow(QxFollow qxFollow);

	/**
	 * 查询用户好友列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<QxFriend> findFrindList(@Param("uid") String uid);


	/**
	 * 批量添加好友
	 * 
	 * @param flid
	 * @param addid
	 */
	public void saveFollows(@Param("flid") String flid, @Param("addid") String addid);

	/**
	 * 获取好友数量
	 * 
	 * @param uid
	 * @return
	 */
	public int getFriendsNum(String uid);


	/**  
	 * 根据uid更新好友的昵称和头像
	 * @param uid
	 * @param nickname
	 * @param headimgurl  
	 */    
	
	public void updateFollowInfo(@Param("uid")String uid, @Param("nickname")String nickname, @Param("headimgurl")String headimgurl);

	/**
	 * 存储用户当天的精选题答案
	 * @param uid
	 * @param result
	 * @param currentDay
	 */
	public void storeT0results(@Param("uid")String uid, @Param("result")String result, @Param("currentDay")int currentDay);


}