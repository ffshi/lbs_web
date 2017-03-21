package com.innovate.cms.modules.qs.service.msg;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.msg.DynamicMsgPriseDao;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPrise;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPriseForService;
/**
 * 用户动态消息prise
 * @author shifangfang
 * @date 2017年3月16日 上午11:24:09
 */
@Service
@Transactional(readOnly = true)
public class DynamicMsgPriseService extends CrudService<DynamicMsgPriseDao, DynamicMsgPrise>{

	/**
	 * 
	 * @param dynamicMsgPrise
	 */
	@Transactional(readOnly = false)
	public void savePrise(DynamicMsgPrise dynamicMsgPrise) {
		super.dao.savePrise(dynamicMsgPrise);
	}

	/**
	 * 
	 * @param dynamicMsgPrise
	 */
	@Transactional(readOnly = false)
	public void cancelPrise(DynamicMsgPrise dynamicMsgPrise) {
		super.dao.cancelPrise(dynamicMsgPrise);
	}

	/**
	 * 获取点赞列表
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgPriseForService> priseList(int mid) {
		return super.dao.priseList(mid);
	}

	/**
	 * 上拉获取更早时间点赞数据
	 * @param mid
	 * @param pid
	 * @return
	 */
	public List<DynamicMsgPriseForService> upPriseList(int mid, int pid) {
		return super.dao.upPriseList(mid,pid);
	}

	/**
	 * 下拉获取最新点赞数据
	 * @param mid
	 * @param pid
	 * @return
	 */
	public List<DynamicMsgPriseForService> downPriseList(int mid, int pid) {
		return super.dao.downPriseList(mid,pid);
	}


}
