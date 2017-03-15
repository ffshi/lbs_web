/**
 * 
 */
package com.innovate.cms.modules.qs.service.sns;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.sns.QxFootprintPraiseDao;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintPraise;

/**
 * 点赞相关
 * @author hushasha
 * @date 2016年8月5日
 */
@Service
@Transactional(readOnly = true)
public class QxFootprintPraiseService extends CrudService<QxFootprintPraiseDao, QxFootprintPraise> {

	/**  
	 * 根据足迹id,用户id,点赞人id获取有效的点赞信息
	 * @param footid 足迹id
	 * @param uid 用户id
	 * @param praiseUid 点赞人id
	 * @return  
	 */    
	
	public QxFootprintPraise getPraise(Integer footid, String uid, String praiseUid) {
		return super.dao.getPraise(footid, uid, praiseUid);
	}

	/**  
	 * 保存点赞信息
	 * @param qxFootprintPraise  
	 */    
	@Transactional(readOnly = false)
	public void savePraise(QxFootprintPraise qxFootprintPraise) {
		super.dao.savePraise(qxFootprintPraise);
	}

	/**  
	 * 更新点赞消息
	 * @param qxFootprintPraise  
	 */    
	@Transactional(readOnly = false)
	public void updatePraise(QxFootprintPraise qxFootprintPraise) {
		super.dao.updatePraise(qxFootprintPraise);
	}

}
