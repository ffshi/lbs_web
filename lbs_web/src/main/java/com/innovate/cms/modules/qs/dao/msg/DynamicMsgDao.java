package com.innovate.cms.modules.qs.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsg;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgApplyForService;
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

	/**
	 * //管理消息状态0-未完成(默认) 1-完成
	 * 
	 * @param mid
	 * @param msgState
	 */
	int updateMsgState(@Param("mid") int mid, @Param("msgState") int msgState);

	/**
	 * // 存储活动报名用户信息
	 * 
	 * @param mid
	 * @param uid
	 * @return
	 */
	int applyFor(@Param("mid") int mid, @Param("uid") String uid);

	/**
	 * 统计报名人数
	 * 
	 * @param mid
	 * @return
	 */
	int addApplyForNum(@Param("mid") int mid);

	/**
	 * 审核活动报名人员 0-未审核 1-通过 2-拒绝
	 * 
	 * @param mid
	 * @param uid
	 * @param checkState
	 */
	int updateCheckState(@Param("mid") int mid, @Param("uid") String uid, @Param("checkState") int checkState);

	/**
	 * 获取活动类消息报名用户列表
	 * 
	 * @param mid
	 * @return
	 */
	List<DynamicMsgApplyForService> applyForList(@Param("mid") int mid);
	/**
	 * 按照一级分类获取用户最新发布的消息 前20条
	 * 
	 * @param uid
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> userLatestMsgType(@Param("uid")String uid, @Param("msgType")int msgType);
	/**
	 *  按照一级分类上拉获取用户发布的下一页消息
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> userUpLatestMsgType(@Param("uid")String uid, @Param("mid")int mid, @Param("msgType")int msgType);
	/**
	 * 获取用户报名审核状态获取
	 * @param mid
	 * @param uid
	 * @return
	 */
	DynamicMsgApplyForService applyCheckState(@Param("mid")int mid, @Param("uid")String uid);
	/**
	 * 获取用户最新发布的活动类消息 前20条
	 * @param uid
	 * @return
	 */
	List<DynamicMsgForService> userLatestEventMsg(@Param("uid")String uid);
	/**
	 * 上拉获取下一页活动类消息
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userUpLatestEventMsg(@Param("uid")String uid, @Param("mid")int mid);
	/**
	 * 获取用户最新报名的消息
	 * @param uid
	 * @return
	 */
	List<DynamicMsgForService> userApplyMsg(@Param("uid")String uid);
	/**
	 * 按一级分类获取用户最新报名的消息
	 * @param uid
	 * @param msgType
	 * @return
	 */
	List<DynamicMsgForService> userApplyMsgFilter(@Param("uid")String uid, @Param("msgType")int msgType);
	/**
	 * 按一级分类上拉获取用户最新报名的消息
	 * 
	 * @param uid
	 * @param msgType
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userApplyUpMsgFilter(@Param("uid")String uid, @Param("msgType")int msgType, @Param("mid")int mid);
	/**
	 * 上拉获取用户报名的消息
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	List<DynamicMsgForService> userApplyUpMsg(@Param("uid")String uid, @Param("mid")int mid);
	/**
	 * 获取消息详情
	 * @param mid
	 * @return
	 */
	DynamicMsgForService msgInfo(@Param("mid")int mid);
	/**
	 * 获取用户报名信息
	 * 
	 * @param curUid
	 * @param mid
	 * @return
	 */
	DynamicMsgApplyForService getApplyInfo(@Param("uid")String uid, @Param("mid")int mid);

}
