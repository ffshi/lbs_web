package com.innovate.cms.modules.qs.service.msg;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.msg.DynamicMsgCommentDao;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgComment;
/**
 * 用户动态消息comment
 * @author shifangfang
 * @date 2017年3月16日 上午11:24:09
 */
@Service
@Transactional(readOnly = true)
public class DynamicMsgCommentService extends CrudService<DynamicMsgCommentDao, DynamicMsgComment>{

	/**
	 * 存储评论/回复
	 * @param dynamicMsgComment
	 */
	@Transactional(readOnly = false)
	public void saveComment(DynamicMsgComment dynamicMsgComment) {
		super.dao.saveComment(dynamicMsgComment);
	}

	/**
	 * 上拉获取更早时间的评论
	 * @param mid
	 * @param cid
	 * @return
	 */
	public List<DynamicMsgComment> upCommentList(int mid, int cid) {
		return super.dao.upCommentList(mid,cid);
	}

	/**
	 * 获取最新的评论
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgComment> latestCommentList(int mid) {
		return super.dao.latestCommentList(mid);
	}

	/**
	 * 获取最新3条评论
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgComment> latestCommentListLimit3(int mid) {
		return super.dao.latestCommentListLimit3(mid);
	}


}
