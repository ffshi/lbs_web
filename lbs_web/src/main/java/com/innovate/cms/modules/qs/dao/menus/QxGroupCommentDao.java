package com.innovate.cms.modules.qs.dao.menus;

/**
 * 
 */


import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.menus.QxGroupComment;
import com.innovate.cms.modules.qs.entity.ques.QxGroupsComment;

/**
 * 专题评论
 * 
 * @author hushasha
 * @date 2016年9月9日
 */

@MyBatisDao
public interface QxGroupCommentDao extends CrudDao<QxGroupComment> {

	/**
	 * 保存专题评论
	 * 
	 * @param qxGroupsComment
	 */

	void saveComment(QxGroupsComment qxGroupsComment);
	
	/**  
	 * 更新评论数量
	 * @param gid  
	 */    
	
	void updateCommentNum(@Param("gid")Integer gid);
	
	/**  
	 * 专题评论列表--获取最新的20条数据
	 * @param gid
	 * @return  
	 */    
	
	List<QxGroupComment> getCommentList(@Param("gid")Integer gid);
	
	/**  
	 * 上拉加载更多评论--小于gcid的最近的20条数据
	 * @param gid 专题ID
	 * @param gcid 专题评论ID
	 * @return  
	 */    
	
	List<QxGroupComment> getMoreComments(@Param("gid")Integer gid, @Param("gcid")Integer gcid);

	/**  
	 * 下拉刷新专题评论--大于gcid的所有最新数据
	 * @param gid 专题ID
	 * @param gcid 专题评论ID
	 * @return  
	 */    
	
	List<QxGroupComment> getAllNewCommentList(@Param("gid")Integer gid, @Param("gcid")Integer gcid);
	

}
