/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ques;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.data.entity.UserSelect;
import com.innovate.cms.modules.data.entity.VisibleToJson;
import com.innovate.cms.modules.qs.dao.ques.QxHistoryDao;
import com.innovate.cms.modules.qs.entity.ques.QxHistory;
import com.innovate.cms.modules.qs.entity.ques.T4result;

/**
 * 用户答题记录Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxHistoryService extends CrudService<QxHistoryDao, QxHistory> {

	public QxHistory get(String id) {
		return super.get(id);
	}
	
	public List<QxHistory> findList(QxHistory qxHistory) {
		return super.findList(qxHistory);
	}

	@Transactional(readOnly = false)
	public void save(QxHistory qxHistory) {
		if (StringUtils.isBlank(qxHistory.getId()))	
		{
			qxHistory.setIsNewRecord(true);
		}else {
			qxHistory.setIsNewRecord(false);
		}
		super.save(qxHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxHistory qxHistory) {
		super.delete(qxHistory);
	}

	/**
	 * 存储用户做题记录
	 * @param history
	 */
	@Transactional(readOnly = false)
	public void saveHistory(QxHistory history) {
		super.dao.saveHistory(history);
	}

	/**
	 * 判断用户是否做过题目
	 * @param history
	 * @return
	 */
	public int isExist(QxHistory history) {
		return super.dao.isExist(history);
	}

	/**
	 * 用户已经做过，更新用户做题结果
	 * @param history
	 */
	@Transactional(readOnly = false)
	public void updateHistory(QxHistory history) {
		super.dao.updateHistory(history);
	}

	public int totalByuid(String uid) {
		// TODO Auto-generated method stub
		return super.dao.totalByuid(uid);
	}

	public int totalByuidgid(String uid, String gid) {
		return super.dao.totalByuidgid(uid,gid);
	}

	/**
	 * 判断用户是否做过该专题
	 * @param uid
	 * @param gid
	 * @return
	 */
	public int doneGroup(String uid, String gid) {
		// TODO Auto-generated method stub
		return super.dao.doneGroup(uid,gid);
	}

	/**
	 * 设置模板4排行榜可见
	 * @param uid
	 * @param gid
	 * @param visible
	 */
	@Transactional(readOnly = false)
	public void setVisible(String uid, int gid, int visible) {
		super.dao.setVisible(uid,gid,visible);
	}

	/**
	 * 获取好友可见标识
	 * @param uid
	 * @param gid
	 * @return
	 */
	public VisibleToJson getVisible(String uid, int gid) {
		return super.dao.getVisible(uid,gid);
	}

	/**  
	 * 获取做题结果相同的小伙伴
	 * @param uid
	 * @param gid
	 * @param resultsTag
	 * @return  
	 */    
	
	public List<RegUserInfoToJson> findUsersWithSameResult(String uid, String gid, String resultsTag) {
		return super.dao.findUsersWithSameResult(uid, gid, resultsTag);
	}

	/**
	 * 根据专题类型获取用户做过的专题id
	 * @param uid
	 * @param templateTag
	 * @return
	 */
	public List<Integer> getDoneQuestionGid(String uid, int templateTag) {
		return super.dao.getDoneQuestionGid(uid,templateTag);
	}

	/**
	 * 获取分享者
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<UserSelect> userShare(String uid, int gid) {
		return super.dao.userShare(uid,gid);
	}

	/**
	 * 获取指定选项用户列表
	 * @param gid
	 * @param sqlId
	 * @return
	 */
	public List<UserSelect> userSelects(int gid, int seqId) {
		return super.dao.userSelects(gid,seqId);
	}

	/**
	 * 判断是否做过该专题
	 */
	public int doneGroup(QxHistory history) {
		return super.dao.doneGroup(history);
	}

	/**
	 * 获取模板4的做题结果
	 * @param uid
	 * @param gid
	 * @return
	 */
	public T4result getT4result(String uid, int gid) {
		return super.dao.getT4result(uid,gid);
	}
	
	
	
}