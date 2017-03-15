/**
 * 
 */
package com.innovate.cms.modules.qs.dao.sns;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintPraise;

/**
 * 点赞相关
 * @author hushasha
 * @date 2016年8月5日
 */
@MyBatisDao
public interface QxFootprintPraiseDao extends CrudDao<QxFootprintPraise> {

	/**  
	 * 根据足迹id,用户id,点赞人id获取有效的点赞信息
	 * @param footid
	 * @param uid
	 * @param praiseUid
	 * @return  
	 */    
	
	QxFootprintPraise getPraise(@Param("footid")Integer footid, @Param("uid")String uid, @Param("praiseUid")String praiseUid);

	/**  
	 * 保存点赞信息
	 * @param qxFootprintPraise  
	 */    
	
	void savePraise(QxFootprintPraise qxFootprintPraise);

	/**  
	 * 更新点赞消息
	 * @param qxFootprintPraise  
	 */    
	
	void updatePraise(QxFootprintPraise qxFootprintPraise);

}
