package com.innovate.cms.modules.qs.dao.msg;

import java.util.List;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsg;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;

/**
 * 动态消息DAO
 * 
 * @author shifangfang
 * @date 2017年3月16日 上午11:26:30
 */
@MyBatisDao
public interface DynamicMsgDao extends CrudDao<DynamicMsg> {

	/**
	 * 存储用户发布的动态
	 * @param dynamicMsgToJson
	 */
	void save(DynamicMsgToJson dynamicMsgToJson);

	/**
	 * 未获取用户位置信息时获取首页信息接口
	 * @return
	 */
	List<DynamicMsgForService> lastesMsg();

}
