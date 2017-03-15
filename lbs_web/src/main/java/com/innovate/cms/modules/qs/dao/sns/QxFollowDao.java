/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.FriendsTopicToJson;
import com.innovate.cms.modules.qs.entity.sns.FriendHistory;
import com.innovate.cms.modules.qs.entity.sns.QxFollow;
import com.innovate.cms.modules.qs.entity.sns.QxFriend;
import com.innovate.cms.modules.qs.entity.sns.RecommendUser;
import com.innovate.cms.modules.qs.entity.sns.UserHistory;

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
	 * 分页获取足迹
	 * 
	 * @param uid
	 * @param start
	 * @param num
	 * @return
	 */
	public List<UserHistory> findUserHistory(@Param("uid") String uid, @Param("start") int start, @Param("num") int num);

	public List<FriendHistory> findHistorybyuidgidPage(@Param("uid") String uid, @Param("gid") String gid, @Param("start") int start, @Param("num") int num);

	public List<FriendHistory> findHistorybyuidgid(@Param("uid") String uid, @Param("gid") String gid);

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
	 * 获取好友参与的专题：最新的自己未做过的好友参与数排名前七位的专题
	 * 
	 * @param uid
	 * @return
	 */

	public List<FriendsTopicToJson> findFriendsTopic(String uid);

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

	/**
	 * 获取当天做完专题的用户
	 * @param currentDay
	 * @return
	 */
	public List<RecommendUser> getRecommendUser(@Param("currentDay")int currentDay);

}