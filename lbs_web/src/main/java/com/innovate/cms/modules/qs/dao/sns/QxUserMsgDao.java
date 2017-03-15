/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.CommentAndPraiseMsgToJSon;
import com.innovate.cms.modules.data.entity.FollowMsgToJson;
import com.innovate.cms.modules.data.entity.InteractionInfoMsgToJSon;
import com.innovate.cms.modules.data.entity.OfficialInfoMsgToJSon;
import com.innovate.cms.modules.data.entity.OneLevelMsgCountToJson;
import com.innovate.cms.modules.data.entity.OneLevelMsgToJson;
import com.innovate.cms.modules.data.entity.RecommendFriendMsgToJSon;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;

/**
 * 用户消息表 Dao
 * 
 * @author shifangfang
 *
 */
@MyBatisDao
public interface QxUserMsgDao extends CrudDao<QxUserMsg> {

	/**
	 * 存储用户消息
	 * 
	 * @param msg
	 */
	public void saveUserMsg(QxUserMsg msg);

	/**
	 * 删除用户消息
	 * 
	 * @param msg
	 */
	public void delUserMsg(QxUserMsg msg);

	/**
	 * 根据用户ID获取消息总数
	 * 
	 * @param uid
	 * @return
	 */

	public int getCount(String uid);

	/**
	 * 更新用户的留言消息的昵称和头像
	 * 
	 * @param uid
	 * @param nickname
	 * @param headimgurl
	 */

	public void updateUserMessage(@Param("uid") String uid, @Param("nickname") String nickname, @Param("headimgurl") String headimgurl);

	/**
	 * 获取某一类型消息数量
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 */

	public int getFollowMsgCount(@Param("uid") String uid);

	/**
	 * 分页查询关注消息列表
	 * 
	 * @param uid
	 *            用户ID
	 * @param offset
	 *            偏移量 : 第一条数据在表中的位置
	 * @param limit
	 *            限定数 : 每页的数量
	 * @return
	 */

	public List<FollowMsgToJson> getFollowMsgList(@Param("uid") String uid, @Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 按照消息类型分别获取最新一条消息
	 * 
	 * @return
	 */
	public List<OneLevelMsgToJson> get1LevelMsgList(@Param("uid") String uid, @Param("umid") int umid);

	/**
	 * 按照消息类型对各类消息汇总
	 * 
	 * @param umid
	 * @return
	 */
	public List<OneLevelMsgCountToJson> countMsgByMsgType(@Param("uid") String uid, @Param("umid") int umid);

	/**
	 * 获取最新好友推荐消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<RecommendFriendMsgToJSon> lastestRecommendFriendMsg(@Param("uid") String uid, @Param("pageSize") int pageSize);

	/**
	 * 获取最新评论和赞消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<CommentAndPraiseMsgToJSon> lastestCommentAndPraiseMsg(@Param("uid") String uid, @Param("pageSize") int pageSize);

	/**
	 * 获取最系统消息消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<OfficialInfoMsgToJSon> lastestOfficialInfoMsg(@Param("uid") String uid, @Param("pageSize") int pageSize);

	/**
	 * 获取最互动消息消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<InteractionInfoMsgToJSon> lastestInteractionInfoMsg(@Param("uid") String uid, @Param("pageSize") int pageSize);

	/**
	 * 上拉获取历史好友推荐消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<RecommendFriendMsgToJSon> pullupRecommendFriendMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 上拉获取历史评论和赞消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<CommentAndPraiseMsgToJSon> pullupCommentAndPraiseMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 上拉获取历史系统信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<OfficialInfoMsgToJSon> pullupOfficialInfoMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 上拉获取历史互动信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<InteractionInfoMsgToJSon> pullupInteractionInfoMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 下拉获取好友推荐消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<RecommendFriendMsgToJSon> pulldownRecommendFriendMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 下拉获取评论和赞消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<CommentAndPraiseMsgToJSon> pulldownCommentAndPraiseMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 下拉获取系统信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<OfficialInfoMsgToJSon> pulldownOfficialInfoMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 下拉获取交互信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<InteractionInfoMsgToJSon> pulldownInteractionInfoMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 获取评论和赞消息数量
	 * 
	 * @param map
	 * @return
	 */

	public int getCommentPraiseMsgCount(Map<String, Object> map);

	/**
	 * 分页获取评论和赞二级列表
	 * 
	 * @param map
	 * @return
	 */

	public List<QxUserMsg> getCommentPraiseMsgList(Map<String, Object> map);

	/**
	 * 获取系统消息数量
	 * 
	 * @param msgTypes
	 * @return
	 */

	public int getSystemMsgCount(String[] msgTypes);

	/**
	 * 分页获取系统消息二级列表
	 * 
	 * @param map
	 * @return
	 */

	public List<QxUserMsg> getSystemMsgList(Map<String, Object> map);

	/**
	 * 获取单个类型的消息数量
	 * 
	 * @param uid
	 * @param msgType
	 * @return
	 */

	public int getMsgCountByType(@Param("uid") String uid, @Param("msgType") String msgType);

	/**
	 * 分页获取单个类型的二级消息列表
	 * 
	 * @param uid
	 * @param offset
	 * @param limit
	 * @param msgType
	 * @return
	 */

	public List<FollowMsgToJson> getMsgListByType(@Param("uid") String uid, @Param("offset") int offset, @Param("limit") int limit, @Param("msgType") String msgType);

	/**
	 * 获取最新关注列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<FollowMsgToJson> lastestFollowMsg(@Param("uid") String uid, @Param("pageSize") int pageSize);

	/**
	 * 获取历史关注列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<FollowMsgToJson> pullupFollowMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

	/**
	 * 获取最近关注列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<FollowMsgToJson> pulldownFollowMsg(@Param("uid") String uid, @Param("umid") int umid, @Param("pageSize") int pageSize);

}