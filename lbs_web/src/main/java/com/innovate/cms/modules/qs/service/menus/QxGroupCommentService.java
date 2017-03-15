package com.innovate.cms.modules.qs.service.menus;

/**
 * 
 */


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.menus.QxGroupCommentDao;
import com.innovate.cms.modules.qs.entity.menus.QxGroupComment;
import com.innovate.cms.modules.qs.entity.ques.QxGroupsComment;

/**
 * 专题评论服务层
 * 
 * @author hushasha
 * @date 2016年9月9日
 */

@Service
@Transactional(readOnly = true)
public class QxGroupCommentService extends CrudService<QxGroupCommentDao, QxGroupComment> {

	/**
	 * 保存专题评论
	 * 
	 * @param qxGroupsComment
	 */

	@Transactional(readOnly = false)
	public void saveComment(QxGroupsComment qxGroupsComment) {
		super.dao.saveComment(qxGroupsComment);
	}

	/**  
	 * 更新评论数量
	 * @param gid  
	 */    
	@Transactional(readOnly = false)
	public void updateCommentNum(Integer gid) {
		super.dao.updateCommentNum(gid);
	}

	/**  
	 * 专题评论列表--获取最新的20条数据
	 * @param gid
	 * @return  
	 */    
	
	public List<QxGroupComment> getCommentList(Integer gid) {
		return super.dao.getCommentList(gid);
	}

	/**  
	 * 上拉加载更多评论--小于gcid的最近的20条数据
	 * @param gid 专题ID
	 * @param gcid 专题评论ID
	 * @return  
	 */    
	
	public List<QxGroupComment> getMoreComments(Integer gid, Integer gcid) {
		return super.dao.getMoreComments(gid, gcid);
	}

	/**  
	 * 下拉刷新专题评论--(大于gcid的所有最新数据)
	 * @param gid 专题ID
	 * @param gcid 专题评论ID
	 * @return  
	 */       
	
	public List<QxGroupComment> getAllNewCommentList(Integer gid, Integer gcid) {
		return super.dao.getAllNewCommentList(gid, gcid);
	}

}
