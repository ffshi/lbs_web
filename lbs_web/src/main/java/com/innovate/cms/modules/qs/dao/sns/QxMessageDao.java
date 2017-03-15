/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.sns.QxMessage;

/**
 * 留言表DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxMessageDao extends CrudDao<QxMessage> {
	
	public void saveMessage(QxMessage message);

	/**  
	 * 根据uid更新留言表用户的昵称和头像
	 * @param uid
	 * @param nickname
	 * @param headimgurl  
	 */    
	
	public void updateMessage(@Param("uid")String uid, @Param("nickname")String nickname, @Param("headimgurl")String headimgurl);
	
}