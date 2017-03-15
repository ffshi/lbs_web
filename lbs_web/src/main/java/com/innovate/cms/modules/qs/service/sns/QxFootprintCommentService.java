/**
 * 
 */
package com.innovate.cms.modules.qs.service.sns;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.sns.QxFootprintCommentDao;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintComment;

/**
 * 足迹评论service层
 * @author hushasha
 * @date 2016年8月5日
 */
@Service
@Transactional(readOnly = true)
public class QxFootprintCommentService extends CrudService<QxFootprintCommentDao, QxFootprintComment>{

	/**
	 * 保存足迹的评论    
	 * @param qxFootprintComment
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveComment(QxFootprintComment qxFootprintComment) {
		return super.dao.saveComment(qxFootprintComment);
	}

	/**  
	 * 删除评论
	 * @param uid
	 * @param valueOf
	 * @return  
	 */    
	@Transactional(readOnly = false)
	public String delComment(String uid, Integer fcid) {
		String result = "";
		int num = super.dao.isCommentExist(uid, fcid);
		if(num == 0) {
			return result;  //没有删除权限或者没有相应评论
		} else {
			super.dao.delCommentById(fcid);
			result = Global.SUCCESS;
		}
		return result;
	}

}
