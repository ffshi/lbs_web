package com.innovate.cms.modules.qs.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	void addPriseNum(@Param("mid")int mid);

	void subPriseNum(@Param("mid")int mid);

	void addCommentNum(@Param("mid")int mid);

	/**
	 * 根据指定的消息mid获取一批消息
	 * @param mids
	 * @return
	 */
	List<DynamicMsgForService> nearMsg(int[] mids);

	/**
	 * 获取用户点赞消息列表
	 * @param uid
	 * @return
	 */
	List<Integer> userPriseList(String uid);

}
