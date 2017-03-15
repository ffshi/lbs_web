/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.menus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.menus.Group;
import com.innovate.cms.modules.qs.entity.menus.GroupRecommend;
import com.innovate.cms.modules.qs.entity.menus.Groups;
import com.innovate.cms.modules.qs.entity.menus.QxGroups;
import com.innovate.cms.modules.qs.entity.ques.QxQuestions;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsOption;

/**
 * 专题表DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxGroupsDao extends CrudDao<QxGroups> {

	/**
	 * 根据专题组fsid获取所有专题
	 * @param fsid
	 * @return
	 */
	public List<Group> getGroupByfsid(@Param("fsid")int fsid);

	/**
	 * 根据专题组ID 获取前4个热门专题
	 * @param cid 频道ID
	 * @param gid 专题id
	 * @return
	 */
	public List<GroupRecommend> getHotGroup(@Param("cid")Integer cid, @Param("gid")Integer gid);

	/**  
	 * 根据专题id(gid)获取频道ID
	 * @param gid 专题id
	 * @return  
	 */    
	public Integer getCidByGid(@Param("gid")Integer gid);

	/**
	 * 根据gid获取group相关信息
	 * @param gid
	 * @return
	 */
	public Group getGroupBygid(@Param("gid")int gid);

	/**  
	 * 随机专题推荐
	 * @param gid 专题id
	 * @return  
	 */    
	
	public List<GroupRecommend> getRandomGroup(Integer gid);

	/**  
	 * 热门专题推荐： 获取做过的题之外的4个热门专题
	 * @param cid 频道ID
	 * @param gid 专题id
	 * @param uid 用户id
	 * @return  
	 */    
	
	public List<GroupRecommend> getHotGroupWithUid(@Param("cid")Integer cid, @Param("gid")Integer gid, @Param("uid")String uid);

	/**  
	 * 更新点赞数量--点赞数+1
	 * @param gid  
	 */    
	
	public void updatePraiseNumForAdding(@Param("gid")Integer gid);

	/**  
	 * 更新点赞数量--点赞数-1
	 * @param gid  
	 */    
	
	public void updatePraiseNumForSubtraction(@Param("gid")Integer gid);

	/**  
	 * 保存专题
	 * @param group  
	 */    
	
	public void saveGroup(QxGroups group);

	/**  
	 * 保存问题
	 * @param question  
	 */    
	
	public void saveQuestion(QxQuestions question);

	/**  
	 * 保存选项
	 * @param optionsList  
	 */    
	
	public void saveOptions(List<QxQuestionsOption> optionsList);

	/**  
	 * 根据fsid获取专题列表--显示最新的10条数据
	 * @param fsid
	 * @return  
	 */    
	
	public List<Groups> getGroupsListByFsid(@Param("fsid") Integer fsid);

	/**  
	 * 上拉显示更多专题--显示小于gid的10条数据
	 * @param fsid
	 * @param gid
	 * @return  
	 */    
	
	public List<Groups> showMoreGroupsByFsid(@Param("fsid") Integer fsid, @Param("gid") Integer gid);

	/**  
	 * 下拉刷新专题列表--显示大于gid的所有数据
	 * @param fsid
	 * @param gid
	 * @return  
	 */    
	
	public List<Groups> refreshGroupsByFsid(@Param("fsid") Integer fsid, @Param("gid") Integer gid);
	
}