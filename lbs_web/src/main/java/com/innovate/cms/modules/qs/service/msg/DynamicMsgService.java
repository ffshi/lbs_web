package com.innovate.cms.modules.qs.service.msg;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;
import com.innovate.cms.modules.qs.dao.msg.DynamicMsgDao;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsg;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;
/**
 * 用户动态消息
 * @author shifangfang
 * @date 2017年3月16日 上午11:24:09
 */
@Service
@Transactional(readOnly = true)
public class DynamicMsgService extends CrudService<DynamicMsgDao, DynamicMsg>{

	/**
	 * 存储用户发布的动态
	 * @param dynamicMsgToJson
	 */
	@Transactional(readOnly = false)
	public void save(DynamicMsgToJson dynamicMsgToJson) {
		super.dao.save(dynamicMsgToJson);
	}

	/**
	 * 未获取用户位置信息时获取首页信息接口
	 * @return
	 */
	public List<DynamicMsgForService> lastesMsg() {
		return super.dao.lastesMsg();
	}

}
