/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mybatis.Page;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.data.entity.MessageToJson;
import com.innovate.cms.modules.qs.dao.sns.QxMessagePostDao;
import com.innovate.cms.modules.qs.entity.sns.QxMessage;
import com.innovate.cms.modules.qs.entity.sns.QxMessagePost;

/**
 * 回复表Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxMessagePostService extends CrudService<QxMessagePostDao, QxMessagePost> {

	public QxMessagePost get(String id) {
		return super.get(id);
	}
	
	public List<QxMessagePost> findList(QxMessagePost qxMessagePost) {
		return super.findList(qxMessagePost);
	}

	@Transactional(readOnly = false)
	public void save(QxMessagePost qxMessagePost) {
		if (StringUtils.isBlank(qxMessagePost.getId()))	
		{
			qxMessagePost.setIsNewRecord(true);
		}else {
			qxMessagePost.setIsNewRecord(false);
		}
		super.save(qxMessagePost);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxMessagePost qxMessagePost) {
		super.delete(qxMessagePost);
	}

	/**  
	 * @param messagePost  
	 */    
	@Transactional(readOnly = false)
	public void saveReplyMessage(QxMessagePost messagePost) {
		super.dao.saveReplyMessage(messagePost);
		
	}

	/**  
	 * @param uid 用户id
	 * @param funType 功能来源，1，留言板留言 2，足迹留言
	 * @param page 分页信息
	 * @return  
	 */    
	
	public List<MessageToJson> getMessageList(String uid, String funType, Page<MessageToJson> page) {
		return super.dao.getMessageList(uid, funType, page.getOffset(), page.getLimit());
	}

	/**  
	 * 删除一条留言及其所有回复
	 * @param uid 操作人ID  
	 * @param id 留言ID 
	 */    
	@Transactional(readOnly = false)
	public String delMessage(String uid, String id) {
		String result = "";
		int num = super.dao.isMessageExist(uid, id);
		if(num == 0) {
			return result;  //没有删除权限或者没有相应留言
		} else {
			super.dao.delMessageById(id);
			result = Global.SUCCESS;
		}
		return result;
	}

	/**  
	 * 根据回复ID 删除某一条回复
	 * @param uid  操作人ID
	 * @param id  回复ID
	 */    
	@Transactional(readOnly = false)
	public String delReply(String uid, String id) {
		String result = "";
		QxMessagePost qxMessagePost = super.dao.getReply(uid, id);
		if(qxMessagePost == null) {
			return result;
		}
		result = qxMessagePost.getMsid();
		super.dao.delReplyMessageById(id);
		return result;
		
		
	}

	/**  
	 * 根据uid和funType获取记录总数
	 * @param uid 用户IＤ
	 * @param funType　功能来源，1，留言板留言 2，足迹留言
	 * @return  
	 */    
	
	public int getCount(String uid, String funType) {
		return super.dao.getCount(uid, funType);
	}

	/**  
	 * 根据留言ID获取留言信息
	 * @param msid
	 * @return  
	 */    
	
	public QxMessage getMessageById(String msid) {
		return super.dao.getMessageById(msid);
	}

	/**  
	 * 根据uid更新回复表用户的昵称和头像
	 * @param uid
	 * @param nickname
	 * @param headimgurl  
	 */    
	@Transactional(readOnly = false)
	public void updatePost(String uid, String nickname, String headimgurl) {
		super.dao.updatePost(uid, nickname, headimgurl);
	}

}