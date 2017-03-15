/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.sns.QxMessageDao;
import com.innovate.cms.modules.qs.entity.sns.QxMessage;

/**
 * 留言表Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxMessageService extends CrudService<QxMessageDao, QxMessage> {

	public QxMessage get(String id) {
		return super.get(id);
	}
	
	public List<QxMessage> findList(QxMessage qxMessage) {
		return super.findList(qxMessage);
	}

	@Transactional(readOnly = false)
	public void save(QxMessage qxMessage) {
		if (StringUtils.isBlank(qxMessage.getId()))	
		{
			qxMessage.setIsNewRecord(true);
		}else {
			qxMessage.setIsNewRecord(false);
		}
		super.save(qxMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxMessage qxMessage) {
		super.delete(qxMessage);
	}
	
	@Transactional(readOnly = false)
	public void saveMessage(QxMessage message) {
		super.dao.saveMessage(message);
	}

	/**  
	 * 根据uid更新留言表用户的昵称和头像
	 * @param uid
	 * @param nickname
	 * @param headimgurl  
	 */    
	@Transactional(readOnly = false)
	public void updateMessage(String uid, String nickname, String headimgurl) {
		super.dao.updateMessage(uid, nickname, headimgurl);
	}
}