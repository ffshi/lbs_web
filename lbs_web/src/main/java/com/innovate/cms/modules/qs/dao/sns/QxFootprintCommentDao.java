/**
 * 
 */
package com.innovate.cms.modules.qs.dao.sns;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintComment;

/**
 * 足迹评论Dao层
 * @author hushasha
 * @date 2016年8月5日
 */
@MyBatisDao
public interface QxFootprintCommentDao extends CrudDao<QxFootprintComment> {

	/**
	 * 保存足迹的评论
	 * @param qxFootprintComment
	 * @return
	 */
	int saveComment(QxFootprintComment qxFootprintComment);

	/**  
	 * 查看评论是否存在
	 * @param uid
	 * @param fcid
	 * @return  
	 */    
	
	int isCommentExist(@Param("uid")String uid, @Param("fcid")Integer fcid);

	/**  
	 * 删除评论
	 * @param fcid  
	 */    
	
	void delCommentById(@Param("fcid")Integer fcid);

}
