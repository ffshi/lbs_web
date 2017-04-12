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

	/**
	 * 根据消息id获取消息
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> getMsgByMid(@Param("mid")int mid);

	/**
	 * 获取用户最新发布的消息 前20条
	 * @param uid
	 * @return
	 */
	List<DynamicMsgForService> userLatestMsg(@Param("uid")String uid);

	/**
	 * 上拉获取下一页消息
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userUpLatestMsg(@Param("uid")String uid, @Param("mid")int mid);

	/**
	 * 下拉刷新获取最新
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userDownLatestMsg(@Param("uid")String uid, @Param("mid")int mid);

	/**
	 * 好友动态 获取用户好友最新消息
	 * @param uid
	 * @return
	 */
	List<DynamicMsgForService> friendLatestMsg(String uid);

	/**
	 * 好友动态 上拉获取下一页好友动态
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> friendUpLatestMsg(@Param("uid")String uid, @Param("mid")int mid);

	/**
	 * 好友动态 下拉刷新获取最新
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> friendDownLatestMsg(@Param("uid")String uid, @Param("mid")int mid);

	/**
	 * 获取用户消息总数/动态总数
	 * @return
	 */
	int getMsgNum(@Param("uid")String uid);

	/**
	 * 获取虚拟消息
	 * @return
	 */
	List<DynamicMsgForService> virtualMsg();
	

}
