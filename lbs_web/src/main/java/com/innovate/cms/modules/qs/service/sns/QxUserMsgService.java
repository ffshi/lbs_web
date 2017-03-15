/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.mybatis.Page;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.data.entity.CommentAndPraiseMsgToJSon;
import com.innovate.cms.modules.data.entity.FollowMsgToJson;
import com.innovate.cms.modules.data.entity.InteractionInfoMsgToJSon;
import com.innovate.cms.modules.data.entity.OfficialInfoMsgToJSon;
import com.innovate.cms.modules.data.entity.OneLevelMsgCountToJson;
import com.innovate.cms.modules.data.entity.OneLevelMsgToJson;
import com.innovate.cms.modules.data.entity.RecommendFriendMsgToJSon;
import com.innovate.cms.modules.qs.dao.sns.QxUserMsgDao;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;

/**
 * 用户消息表sevice
 * 
 * @author shifangfang
 *
 */
@Service
@Transactional(readOnly = true)
public class QxUserMsgService extends CrudService<QxUserMsgDao, QxUserMsg> {

	/**
	 * 存储用户消息
	 * 
	 * @param msg
	 */
	@Transactional(readOnly = false)
	public void saveUserMsg(QxUserMsg msg) {
		super.dao.saveUserMsg(msg);
	}

	/**
	 * 删除用户消息
	 * 
	 * @param msg
	 */
	@Transactional(readOnly = false)
	public void delUserMsg(QxUserMsg msg) {
		// TODO Auto-generated method stub
		super.dao.delUserMsg(msg);
	}

	/**
	 * 根据用户ID获取消息总数
	 * 
	 * @param uid
	 * @return
	 */

	public int getCount(String uid) {
		return super.dao.getCount(uid);
	}

	/**
	 * 更新用户的留言消息的昵称和头像
	 * 
	 * @param uid
	 * @param nickname
	 * @param headimgurl
	 */
	@Transactional(readOnly = false)
	public void updateUserMessage(String uid, String nickname, String headimgurl) {
		super.dao.updateUserMessage(uid, nickname, headimgurl);

	}

	/**
	 * 获取某一类型消息数量
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 */

	public int getFollowMsgCount(String uid) {
		return super.dao.getFollowMsgCount(uid);
	}

	/**
	 * 分页查询关注消息列表
	 * 
	 * @param uid
	 *            用户ID
	 * @param page
	 *            分页信息
	 * @return
	 */

	public List<FollowMsgToJson> getFollowMsgList(String uid, Page<FollowMsgToJson> page) {
		return super.dao.getFollowMsgList(uid, page.getOffset(), page.getLimit());
	}

	/**
	 * 按照消息类型分别获取最新一条消息
	 * 
	 * @return
	 */
	public List<OneLevelMsgToJson> get1LevelMsgList(String uid, int umid) {
		return super.dao.get1LevelMsgList(uid,umid);
	}

	/**
	 * 按照消息类型对各类消息汇总
	 * 
	 * @param umid
	 * @return
	 */
	public List<OneLevelMsgCountToJson> countMsgByMsgType(String uid, int umid) {
		return super.dao.countMsgByMsgType(uid, umid);
	}

	/**
	 * 获取最新好友推荐消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<RecommendFriendMsgToJSon> lastestRecommendFriendMsg(String uid, int pageSize) {
		return super.dao.lastestRecommendFriendMsg(uid, pageSize);
	}

	/**
	 * 获取最新评论和赞消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<CommentAndPraiseMsgToJSon> lastestCommentAndPraiseMsg(String uid, int pageSize) {
		return super.dao.lastestCommentAndPraiseMsg(uid, pageSize);
	}

	/**
	 * 获取最系统消息消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<OfficialInfoMsgToJSon> lastestOfficialInfoMsg(String uid, int pageSize) {
		return super.dao.lastestOfficialInfoMsg(uid, pageSize);
	}

	/**
	 * 获取最互动消息消息列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<InteractionInfoMsgToJSon> lastestInteractionInfoMsg(String uid, int pageSize) {
		return super.dao.lastestInteractionInfoMsg(uid, pageSize);
	}

	/**
	 * 上拉获取历史好友推荐消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<RecommendFriendMsgToJSon> pullupRecommendFriendMsg(String uid, int umid, int pageSize) {
		return super.dao.pullupRecommendFriendMsg(uid, umid, pageSize);
	}

	/**
	 * 上拉获取历史评论和赞消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<CommentAndPraiseMsgToJSon> pullupCommentAndPraiseMsg(String uid, int umid, int pageSize) {
		return super.dao.pullupCommentAndPraiseMsg(uid, umid, pageSize);
	}

	/**
	 * 上拉获取历史系统信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<OfficialInfoMsgToJSon> pullupOfficialInfoMsg(String uid, int umid, int pageSize) {
		return super.dao.pullupOfficialInfoMsg(uid, umid, pageSize);
	}

	/**
	 * 上拉获取历史互动信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<InteractionInfoMsgToJSon> pullupInteractionInfoMsg(String uid, int umid, int pageSize) {
		return super.dao.pullupInteractionInfoMsg(uid, umid, pageSize);
	}

	/**
	 * 下拉获取好友推荐消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<RecommendFriendMsgToJSon> pulldownRecommendFriendMsg(String uid, int umid, int pageSize) {
		return super.dao.pulldownRecommendFriendMsg(uid, umid, pageSize);
	}

	/**
	 * 下拉获取评论和赞消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<CommentAndPraiseMsgToJSon> pulldownCommentAndPraiseMsg(String uid, int umid, int pageSize) {
		return super.dao.pulldownCommentAndPraiseMsg(uid, umid, pageSize);
	}

	/**
	 * 下拉获取系统信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<OfficialInfoMsgToJSon> pulldownOfficialInfoMsg(String uid, int umid, int pageSize) {
		return super.dao.pulldownOfficialInfoMsg(uid, umid, pageSize);
	}

	/**
	 * 下拉获取交互信息消息列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<InteractionInfoMsgToJSon> pulldownInteractionInfoMsg(String uid, int umid, int pageSize) {
		return super.dao.pulldownInteractionInfoMsg(uid, umid, pageSize);
	}

	/**
	 * 
	 * 获取评论和赞消息数量
	 * 
	 * @param map
	 * @return
	 */

	public int getCommentPraiseMsgCount(Map<String, Object> map) {
		return super.dao.getCommentPraiseMsgCount(map);
	}

	/**
	 * 分页获取评论和赞二级列表
	 * 
	 * @param map
	 * @return
	 */

	public List<QxUserMsg> getCommentPraiseMsgList(Map<String, Object> map) {
		return super.dao.getCommentPraiseMsgList(map);
	}

	/**
	 * 获取系统消息数量
	 * 
	 * @param msgTypes
	 * @return
	 */

	public int getSystemMsgCount(String[] msgTypes) {
		return super.dao.getSystemMsgCount(msgTypes);
	}

	/**
	 * 分页获取系统消息二级列表
	 * 
	 * @param map
	 * @return
	 */

	public List<QxUserMsg> getSystemMsgList(Map<String, Object> map) {
		return super.dao.getSystemMsgList(map);
	}

	/**
	 * 获取单个类型的消息数量
	 * 
	 * @param uid
	 * @param msgType
	 * @return
	 */

	public int getMsgCountByType(String uid, String msgType) {
		return super.dao.getMsgCountByType(uid, msgType);
	}

	/**
	 * 分页获取单个类型的二级消息列表
	 * 
	 * @param uid
	 * @param page
	 * @param msgType
	 * @return
	 */
	public List<FollowMsgToJson> getMsgListByType(String uid, Page<FollowMsgToJson> page, String msgType) {
		return super.dao.getMsgListByType(uid, page.getOffset(), page.getLimit(), msgType);
	}

	/**
	 * 获取最新关注列表
	 * 
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<FollowMsgToJson> lastestFollowMsg(String uid, int pageSize) {
		return super.dao.lastestFollowMsg(uid, pageSize);
	}

	/**
	 * 获取历史关注列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<FollowMsgToJson> pullupFollowMsg(String uid, int umid, int pageSize) {
		return super.dao.pullupFollowMsg(uid, umid, pageSize);
	}

	/**
	 * 获取最近关注列表
	 * 
	 * @param uid
	 * @param umid
	 * @param pageSize
	 * @return
	 */
	public List<FollowMsgToJson> pulldownFollowMsg(String uid, int umid, int pageSize) {
		return super.dao.pulldownFollowMsg(uid, umid, pageSize);
	}
}