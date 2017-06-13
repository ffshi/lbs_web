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
	 * 
	 * @param dynamicMsgToJson
	 */
	void save(DynamicMsgToJson dynamicMsgToJson);

	/**
	 * 未获取用户位置信息时获取首页信息接口
	 * 
	 * @return
	 */
	List<DynamicMsgForService> lastesMsg();

	void addPriseNum(@Param("mid") int mid);

	void subPriseNum(@Param("mid") int mid);

	void addCommentNum(@Param("mid") int mid);

	/**
	 * 根据指定的消息mid获取一批消息
	 * 
	 * @param mids
	 * @return
	 */
	List<DynamicMsgForService> nearMsg(int[] mids);

	/**
	 * 获取用户点赞消息列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Integer> userPriseList(String uid);

	/**
	 * 根据消息id获取消息
	 * 
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> getMsgByMid(@Param("mid") int mid);

	/**
	 * 获取用户最新发布的消息 前20条
	 * 
	 * @param uid
	 * @return
	 */
	List<DynamicMsgForService> userLatestMsg(@Param("uid") String uid);

	/**
	 * 上拉获取下一页消息
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userUpLatestMsg(@Param("uid") String uid, @Param("mid") int mid);

	/**
	 * 下拉刷新获取最新
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userDownLatestMsg(@Param("uid") String uid, @Param("mid") int mid);

	/**
	 * 好友动态 获取用户好友最新消息
	 * 
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
	List<DynamicMsgForService> friendUpLatestMsg(@Param("uid") String uid, @Param("mid") int mid);

	/**
	 * 好友动态 下拉刷新获取最新
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> friendDownLatestMsg(@Param("uid") String uid, @Param("mid") int mid);

	/**
	 * 获取用户消息总数/动态总数
	 * 
	 * @return
	 */
	int getMsgNum(@Param("uid") String uid);

	/**
	 * 获取虚拟消息
	 * 
	 * @return
	 */
	List<DynamicMsgForService> virtualMsg();

	/**
	 * 统计分享次数
	 * 
	 * @param mid
	 */
	void addShareNum(int mid);

	/**
	 * 更新消息中的用户信息
	 * 
	 * @param _uid
	 * @param nickname
	 * @param headimgurl
	 */
	void updateUserInfo(@Param("uid") String uid, @Param("nickname") String nickname, @Param("headimgurl") String headimgurl);

	/**
	 * 根据消息类型获取用户好友最新消息
	 * 
	 * @param uid
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> friendLatestMsgByMsgtype(@Param("uid") String uid, @Param("msgType") int[] msgType);

	/**
	 * 根据消息类型上拉获取下一页好友动态
	 * 
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> friendUpLatestMsgByMsgtype(@Param("uid") String uid, @Param("mid") int mid, @Param("msgType") int[] msgType);

	/**
	 * 根据消息类型下拉刷新好友的最新消息
	 * 
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> friendDownLatestMsgByMsgType(@Param("uid") String uid, @Param("mid") int mid, @Param("msgType") int[] msgType);

	/**
	 * 根据消息类型获取虚拟消息
	 * 
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> virtualMsgByMsgtype(@Param("msgType") int[] msgType);

	/**
	 * 根据消息类型和性别筛选虚拟最新消息
	 * 
	 * @param msgType
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> virtualMsgByMsgtypeSex(@Param("msgType") int[] msgType, @Param("sex") int sex);

	/**
	 * 根据性别获取虚拟消息
	 * 
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> virtualMsgBySex(@Param("sex") int sex);

	/**
	 * 根据消息类型和性别筛选用户好友最新消息
	 * 
	 * @param uid
	 * @param msgType
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> friendLatestMsgByMsgtypeSex(@Param("uid") String uid, @Param("msgType") int[] msgType, @Param("sex") int sex);

	/**
	 * 根据性别筛选用户好友最新消息
	 * 
	 * @param uid
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> friendLatestMsgBySex(@Param("uid") String uid, @Param("sex") int sex);

	/**
	 * 根据消息类型和性别筛选用户好友下一页消息
	 * 
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> friendUpLatestMsgByMsgtypeSex(@Param("uid") String uid, @Param("mid") int mid, @Param("msgType") int[] msgType, @Param("sex") int sex);

	/**
	 * 根据性别筛选用户好友下一页消息
	 * 
	 * @param uid
	 * @param mid
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> friendUpLatestMsgBySex(@Param("uid") String uid, @Param("mid") int mid, @Param("sex") int sex);

	/**
	 * 根据消息类型和性别筛选用户好友最新消息
	 * 
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> friendDownLatestMsgByMsgTypeSex(@Param("uid") String uid, @Param("mid") int mid, @Param("msgType") int[] msgType, @Param("sex") int sex);

	/**
	 * 根据性别筛选用户好友最新消息
	 * 
	 * @param uid
	 * @param mid
	 * @param sex
	 * @return
	 */
	List<DynamicMsgForService> friendDownLatestMsgBySex(@Param("uid") String uid, @Param("mid") int mid, @Param("sex") int sex);

}
