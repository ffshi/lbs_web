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
 * 
 * @author shifangfang
 * @date 2017年3月16日 上午11:24:09
 */
@Service
@Transactional(readOnly = true)
public class DynamicMsgService extends CrudService<DynamicMsgDao, DynamicMsg> {

	/**
	 * 存储用户发布的动态
	 * 
	 * @param dynamicMsgToJson
	 */
	@Transactional(readOnly = false)
	public void save(DynamicMsgToJson dynamicMsgToJson) {
		super.dao.save(dynamicMsgToJson);
	}

	/**
	 * 未获取用户位置信息时获取首页信息接口
	 * 
	 * @return
	 */
	public List<DynamicMsgForService> lastesMsg() {
		return super.dao.lastesMsg();
	}

	/**
	 * 自增点赞数
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
	 * 
	 * @param mids
	 * @return
	 */
	public List<DynamicMsgForService> nearMsg(int[] mids) {
		return super.dao.nearMsg(mids);
	}

	/**
	 * 获取用户点赞消息列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<Integer> userPriseList(String uid) {
		return super.dao.userPriseList(uid);
	}

	/**
	 * 根据消息id获取消息
	 * 
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgForService> getMsgByMid(int mid) {
		return super.dao.getMsgByMid(mid);
	}

	/**
	 * 获取用户最新发布的消息 前20条
	 * 
	 * @param uid
	 * @return
	 */
	public List<DynamicMsgForService> userLatestMsg(String uid) {
		return super.dao.userLatestMsg(uid);
	}

	/**
	 * 上拉获取下一页消息
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgForService> userUpLatestMsg(String uid, int mid) {
		return super.dao.userUpLatestMsg(uid, mid);
	}

	/**
	 * 下拉刷新获取最新
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgForService> userDownLatestMsg(String uid, int mid) {
		return super.dao.userDownLatestMsg(uid, mid);
	}

	/**
	 * 好友动态 获取用户好友最新消息
	 * 
	 * @param uid
	 * @return
	 */
	public List<DynamicMsgForService> friendLatestMsg(String uid) {
		return super.dao.friendLatestMsg(uid);
	}

	/**
	 * 好友动态 上拉获取下一页好友动态
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgForService> friendUpLatestMsg(String uid, int mid) {
		return super.dao.friendUpLatestMsg(uid, mid);
	}

	/**
	 * 好友动态 下拉刷新获取最新
	 * 
	 * @param uid
	 * @param mid
	 * @return
	 */
	public List<DynamicMsgForService> friendDownLatestMsg(String uid, int mid) {
		return super.dao.friendDownLatestMsg(uid, mid);
	}

	/**
	 * 获取用户消息总数/动态总数
	 * 
	 * @return
	 */
	public int getMsgNum(String uid) {
		return super.dao.getMsgNum(uid);
	}

	/**
	 * 获取虚拟消息
	 * 
	 * @return
	 */
	public List<DynamicMsgForService> virtualMsg() {
		return super.dao.virtualMsg();
	}

	/**
	 * 统计分享次数
	 * 
	 * @param mid
	 */
	@Transactional(readOnly = false)
	public void addShareNum(int mid) {
		super.dao.addShareNum(mid);
	}

	/**
	 * 更新消息中的用户信息
	 * 
	 * @param _uid
	 * @param nickname
	 * @param headimgurl
	 */
	@Transactional(readOnly = false)
	public void updateUserInfo(String uid, String nickname, String headimgurl) {
		super.dao.updateUserInfo(uid, nickname, headimgurl);
	}

	/**
	 * 根据消息类型获取用户好友最新消息
	 * 
	 * @param uid
	 * @param msgType
	 * @return
	 */
	public List<DynamicMsgForService> friendLatestMsgByMsgtype(String uid, int[] msgType) {
		return super.dao.friendLatestMsgByMsgtype(uid, msgType);
	}

	/**
	 * 根据消息类型上拉获取下一页好友动态
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @return
	 */
	public List<DynamicMsgForService> friendUpLatestMsgByMsgtype(String uid, int mid, int[] msgType) {
		return super.dao.friendUpLatestMsgByMsgtype(uid,mid,msgType);
	}

	/**
	 * 根据消息类型下拉刷新好友的最新消息
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @return
	 */
	public List<DynamicMsgForService> friendDownLatestMsgByMsgType(String uid, int mid, int[] msgType) {
		return super.dao.friendDownLatestMsgByMsgType(uid,mid,msgType);
	}

	/**
	 * 根据消息类型获取虚拟消息
	 * @param msgType
	 * @return
	 */
	public List<DynamicMsgForService> virtualMsgByMsgtype(int[] msgType) {
		return super.dao.virtualMsgByMsgtype(msgType);
	}

	/**
	 * 根据消息类型和性别筛选虚拟最新消息
	 * @param msgType
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> virtualMsgByMsgtypeSex(int[] msgType, int sex) {
		return super.dao.virtualMsgByMsgtypeSex(msgType,sex);
	}

	/**
	 * 根据性别获取虚拟消息
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> virtualMsgBySex(int sex) {
		return super.dao.virtualMsgBySex(sex);
	}

	/**
	 * 根据消息类型和性别筛选用户好友最新消息
	 * @param uid
	 * @param msgType
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> friendLatestMsgByMsgtypeSex(String uid, int[] msgType, int sex) {
		return super.dao.friendLatestMsgByMsgtypeSex(uid,msgType,sex);
	}

	/**
	 * 根据性别筛选用户好友最新消息
	 * @param uid
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> friendLatestMsgBySex(String uid, int sex) {
		return super.dao.friendLatestMsgBySex(uid,sex);
	}

	/**
	 * 根据消息类型和性别筛选用户好友下一页消息
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> friendUpLatestMsgByMsgtypeSex(String uid, int mid, int[] msgType, int sex) {
		return super.dao.friendUpLatestMsgByMsgtypeSex(uid,mid,msgType,sex);
	}

	/**
	 * 根据性别筛选用户好友下一页消息
	 * @param uid
	 * @param mid
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> friendUpLatestMsgBySex(String uid, int mid, int sex) {
		return super.dao.friendUpLatestMsgBySex(uid,mid,sex);
	}

	/**
	 *  根据消息类型和性别筛选用户好友最新消息
	 * @param uid
	 * @param mid
	 * @param msgType
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> friendDownLatestMsgByMsgTypeSex(String uid, int mid, int[] msgType, int sex) {
		return super.dao.friendDownLatestMsgByMsgTypeSex(uid,mid,msgType,sex);
	}

	/**
	 * 根据性别筛选用户好友最新消息
	 * @param uid
	 * @param mid
	 * @param sex
	 * @return
	 */
	public List<DynamicMsgForService> friendDownLatestMsgBySex(String uid, int mid, int sex) {
		return super.dao.friendDownLatestMsgBySex(uid,mid,sex);
	}

}
