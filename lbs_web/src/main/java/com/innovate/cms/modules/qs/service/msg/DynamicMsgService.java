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

	/**
	 *自增点赞数
	 */
	@Transactional(readOnly = false)
	public void addPriseNum(int mid) {
		super.dao.addPriseNum(mid);
	}
	/**
	 * 自减点赞数
	 */
	@Transactional(readOnly = false)
	public void subPriseNum(int mid) {
		super.dao.subPriseNum(mid);
	}
	/**
	 * 自增评论数
	 */
	@Transactional(readOnly = false)
	public void addCommentNum(int mid) {
		super.dao.addCommentNum(mid);
	}

	/**
	 * 根据指定的消息mid获取一批消息
	 * @param mids
	 * @return
	 */
	public List<DynamicMsgForService> nearMsg(int[] mids) {
		return super.dao.nearMsg(mids);
	}

	/**
	 * 获取用户点赞消息列表
	 * @param uid
	 * @return
	 */
	public List<Integer> userPriseList(String uid) {
		return super.dao.userPriseList(uid);
	}

	

	

}
