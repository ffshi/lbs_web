/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.sns.QxFollowDao;
import com.innovate.cms.modules.qs.entity.sns.QxFollow;
import com.innovate.cms.modules.qs.entity.sns.QxFriend;

/**
 * 关注表Service
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxFollowService extends CrudService<QxFollowDao, QxFollow> {

	public QxFollow get(String id) {
		return super.get(id);
	}

	public List<QxFollow> findList(QxFollow qxFollow) {
		return super.findList(qxFollow);
	}

	@Transactional(readOnly = false)
	public void save(QxFollow qxFollow) {
		if (StringUtils.isBlank(qxFollow.getId())) {
			qxFollow.setIsNewRecord(true);
		} else {
			qxFollow.setIsNewRecord(false);
		}
		super.save(qxFollow);
	}

	@Transactional(readOnly = false)
	public void delete(QxFollow qxFollow) {
		super.delete(qxFollow);
	}

	/**
	 * 判断是否是好友
	 * @param qxFollow
	 * @return
	 */
	public int isExist(QxFollow qxFollow) {
		return super.dao.isExist(qxFollow);
	}
	
	/**  
	 * 判断是否是好友, 添加delFlag = 0的条件限制
	 * @param qxFollow
	 * @return  
	 */    
	
	public int isExistNew(QxFollow qxFollow) {
		return super.dao.isExistNew(qxFollow);
	}

	/**
	 * 存储好友关系
	 * @param qxFollow
	 */
	@Transactional(readOnly = false)
	public void saveFollow(QxFollow qxFollow) {
		super.dao.saveFollow(qxFollow);
	}

	/**
	 * 更新好友关系
	 * @param qxFollow
	 */
	@Transactional(readOnly = false)
	public void updateFollow(QxFollow qxFollow) {
		super.dao.updateFollow(qxFollow);
	}

	/**
	 * 查询用户好友列表
	 * @param uid
	 * @return
	 */
	public List<QxFriend> findFrindList(String uid) {
		return super.dao.findFrindList(uid);
	}


	/**
	 * 批量关注存储
	 * 
	 * @param flid
	 * @param addid
	 */
	@Transactional(readOnly = false)
	public void saveFollows(String flid, String addid) {
		super.dao.saveFollows(flid, addid);
	}


	/**  
	 * 根据uid更新好友的昵称和头像
	 * @param uid
	 * @param nickname
	 * @param headimgurl  
	 */    
	@Transactional(readOnly = false)
	public void updateFollowInfo(String uid, String nickname, String headimgurl) {
		super.dao.updateFollowInfo(uid, nickname, headimgurl);
	}

	/**
	 * 存储用户当天的精选题答案
	 * @param uid
	 * @param result
	 * @param currentDay
	 */
	@Transactional(readOnly = false)
	public void storeT0results(String uid, String result, int currentDay) {
		super.dao.storeT0results(uid,result,currentDay);
	}


}