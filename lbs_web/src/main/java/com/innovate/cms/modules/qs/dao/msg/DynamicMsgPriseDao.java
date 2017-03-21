package com.innovate.cms.modules.qs.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPrise;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPriseForService;

/**
 * 动态消息prise DAO
 * 
 * @author shifangfang
 * @date 2017年3月16日 上午11:26:30
 */
@MyBatisDao
public interface DynamicMsgPriseDao extends CrudDao<DynamicMsgPrise> {

	/**
	 * 
	 * @param dynamicMsgPrise
	 */
	void savePrise(DynamicMsgPrise dynamicMsgPrise);

	/**
	 * 
	 * @param dynamicMsgPrise
	 */
	void cancelPrise(DynamicMsgPrise dynamicMsgPrise);

	/**
	 * 获取点赞列表
	 * @param mid
	 * @return
	 */
	List<DynamicMsgPriseForService> priseList(int mid);

	/**
	 * 上拉获取更早时间点赞数据
	 * @param mid
	 * @param pid
	 * @return
	 */
	List<DynamicMsgPriseForService> upPriseList(@Param("mid")int mid, @Param("pid")int pid);

	/**
	 * 下拉获取最新点赞数据
	 * @param mid
	 * @param pid
	 * @return
	 */
	List<DynamicMsgPriseForService> downPriseList(@Param("mid")int mid, @Param("pid")int pid);

}
