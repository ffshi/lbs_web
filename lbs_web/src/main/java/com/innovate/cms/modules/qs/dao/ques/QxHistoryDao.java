/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.ques;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.data.entity.UserSelect;
import com.innovate.cms.modules.data.entity.VisibleToJson;
import com.innovate.cms.modules.qs.entity.ques.QxHistory;
import com.innovate.cms.modules.qs.entity.ques.T4result;

/**
 * 用户答题记录DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxHistoryDao extends CrudDao<QxHistory> {

	/**
	 * 存储用户做题记录
	 * 
	 * @param history
	 */
	public void saveHistory(QxHistory history);

	/**
	 * 判断用户是否做过题目
	 * 
	 * @param history
	 * @return
	 */
	public int isExist(QxHistory history);

	/**
	 * 用户已经做过，更新用户做题结果
	 * 
	 * @param history
	 */
	public void updateHistory(QxHistory history);

	public int totalByuid(String uid);

	public int totalByuidgid(@Param("uid") String uid, @Param("gid") String gid);

	/**
	 * 判断用户是否做过该专题
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public int doneGroup(@Param("uid") String uid, @Param("gid") String gid);

	/**
	 * 设置模板4排行榜可见
	 * 
	 * @param uid
	 * @param gid
	 * @param visible
	 */
	public void setVisible(@Param("uid") String uid, @Param("gid") int gid, @Param("visible") int visible);

	/**
	 * 获取好友可见标识
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public VisibleToJson getVisible(@Param("uid") String uid, @Param("gid") int gid);

	/**  
	 * 获取做题结果相同的小伙伴
	 * @param uid
	 * @param gid
	 * @param resultsTag
	 * @return  
	 */    
	
	public List<RegUserInfoToJson> findUsersWithSameResult(@Param("uid")String uid, @Param("gid") String gid, @Param("resultsTag") String resultsTag);

	/**
	 * 根据专题类型获取用户做过的专题id
	 * @param uid
	 * @param templateTag
	 * @return
	 */
	public List<Integer> getDoneQuestionGid(@Param("uid")String uid, @Param("templateTag")int templateTag);

	/**
	 * 获取分享者
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<UserSelect> userShare(@Param("uid")String uid, @Param("gid")int gid);

	/**
	 * 获取指定选项用户列表
	 * @param gid
	 * @param sqlId
	 * @return
	 */
	public List<UserSelect> userSelects(@Param("gid")int gid, @Param("seqId")int seqId);

	/**
	 * 判断是否做过该专题
	 */
	public int doneGroup(QxHistory history);

	/**
	 * 获取模板4的做题结果
	 * @param uid
	 * @param gid
	 * @return
	 */
	public T4result getT4result(@Param("uid")String uid, @Param("gid")int gid);

}