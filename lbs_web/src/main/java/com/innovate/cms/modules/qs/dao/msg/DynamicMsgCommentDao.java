package com.innovate.cms.modules.qs.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgComment;

/**
 * 动态消息comment DAO
 * 
 * @author shifangfang
 * @date 2017年3月16日 上午11:26:30
 */
@MyBatisDao
public interface DynamicMsgCommentDao extends CrudDao<DynamicMsgComment> {

	/**
	 * 存储评论/回复
	 * @param dynamicMsgComment
	 */
	void saveComment(DynamicMsgComment dynamicMsgComment);

	/**
	 * 上拉获取更早时间的评论
	 * @param mid
	 * @param cid
	 * @return
	 */
	List<DynamicMsgComment> upCommentList(@Param("mid")int mid, @Param("cid")int cid);

	/**
	 * 获取最新的评论
	 * @param mid
	 * @return
	 */
	List<DynamicMsgComment> latestCommentList(int mid);

	


}
