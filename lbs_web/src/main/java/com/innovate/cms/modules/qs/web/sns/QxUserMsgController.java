/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mybatis.Page;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.data.entity.CommentAndPraiseMsgToJSon;
import com.innovate.cms.modules.data.entity.FollowMsgToJson;
import com.innovate.cms.modules.data.entity.InteractionInfoMsgToJSon;
import com.innovate.cms.modules.data.entity.OfficialInfoMsgToJSon;
import com.innovate.cms.modules.data.entity.OneLevelMsgCountToJson;
import com.innovate.cms.modules.data.entity.OneLevelMsgToJson;
import com.innovate.cms.modules.data.entity.RecommendFriendMsgToJSon;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;

/**
 * 用户消息表 Controller
 * 
 * @author shifangfang
 *
 */
@Controller
@RequestMapping(value = "/api")
public class QxUserMsgController extends BaseController {

	@Autowired
	private QxUserMsgService qxUserMsgService;

	@RequestMapping(value = "/v1/sns/delUserMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delUserMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String umid = map.get("umid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(umid) || !StrUtil.isPositiveNum(umid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			QxUserMsg msg = new QxUserMsg();
			msg.setUmid(Integer.valueOf(umid));
			qxUserMsgService.delUserMsg(msg);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/***
	 * 5期需求 获取用户一级消息列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/get1LevelMsgList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<OneLevelMsgToJson> get1LevelMsgList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 用户消息id：umid,每次根据umid进行增量汇总
		String umid = map.get("umid");
		String uid = map.get("uid");
		DataBackInfo<OneLevelMsgToJson> backInfo = new DataBackInfo<OneLevelMsgToJson>();
		// 简单校验
		if (StrUtil.isBlank(umid) || !StrUtil.isNum(umid) || StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		List<OneLevelMsgToJson> userMsgList = new ArrayList<OneLevelMsgToJson>();
		try {
			userMsgList = qxUserMsgService.get1LevelMsgList(uid,Integer.parseInt(umid));
			// 获取各类消息汇总
			List<OneLevelMsgCountToJson> countMsgs = qxUserMsgService.countMsgByMsgType(uid, Integer.parseInt(umid));

			userMsgList = dealMsgList(userMsgList, countMsgs);

			backInfo.setData(userMsgList);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return backInfo;
	}

	/**
	 * 合并消息一级列表汇总结果
	 * 
	 * @param userMsgList
	 * @param countMsgs
	 * @return
	 */
	private List<OneLevelMsgToJson> dealMsgList(List<OneLevelMsgToJson> userMsgList, List<OneLevelMsgCountToJson> countMsgs) {
		if (userMsgList.size() < 1 || countMsgs.size() < 1) {
			return userMsgList;
		}

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (OneLevelMsgCountToJson msgCount : countMsgs) {
			map.put(msgCount.getMsgGroupId(), msgCount.getCount());
		}
		for (OneLevelMsgToJson msg : userMsgList) {
			msg.setCount(map.get(msg.getMsgGroupId()));
		}

		return userMsgList;
	}
	
	
	/**
	 * 备用
	 * 
	 * 每次累加返回消息，按照消息id获取消息列表
	 * 
	 * 获取关注消息二级列表 msg_group_id=1  msg_type in('3')
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFollowMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getFollowMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 加载开始id
		String umidStr = map.get("umid");
		String uid = map.get("uid");
		// 加载类型0:取最新pageSize条
		// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
		String typeStr = map.get("type");
		// 加载数量
		String pageSizeStr = map.get("pageSize");

		DataBackInfo<FollowMsgToJson> backInfo = new DataBackInfo<FollowMsgToJson>();
		if (StrUtil.isBlank(umidStr) || StrUtil.isBlank(uid) || StrUtil.isBlank(typeStr) || StrUtil.isBlank(pageSizeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int type = Integer.parseInt(typeStr);
			int umid = Integer.parseInt(umidStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			List<FollowMsgToJson> countMsgs = new ArrayList<FollowMsgToJson>();
			// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
			if (type == 0) {
				// 最新
				countMsgs = qxUserMsgService.lastestFollowMsg(uid, pageSize);
			} else if (type == 1) {
				// 上拉
				countMsgs = qxUserMsgService.pullupFollowMsg(uid, umid, pageSize);
			} else if (type == 2) {
				// 下拉
				countMsgs = qxUserMsgService.pulldownFollowMsg(uid, umid, pageSize);
			}
			backInfo.setData(countMsgs);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 备用
	 * 
	 * 每次累加返回消息，按照消息id获取消息列表
	 * 
	 * 获取推荐好友二级列表 msg_group_id=2  msg_type in('5')
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getRecommendFriendMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getRecommendFriendMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 加载开始id
		String umidStr = map.get("umid");
		String uid = map.get("uid");
		// 加载类型0:取最新pageSize条
		// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
		String typeStr = map.get("type");
		// 加载数量
		String pageSizeStr = map.get("pageSize");

		DataBackInfo<RecommendFriendMsgToJSon> backInfo = new DataBackInfo<RecommendFriendMsgToJSon>();
		if (StrUtil.isBlank(umidStr) || StrUtil.isBlank(uid) || StrUtil.isBlank(typeStr) || StrUtil.isBlank(pageSizeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int type = Integer.parseInt(typeStr);
			int umid = Integer.parseInt(umidStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			List<RecommendFriendMsgToJSon> countMsgs = new ArrayList<RecommendFriendMsgToJSon>();
			// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
			if (type == 0) {
				// 最新
				countMsgs = qxUserMsgService.lastestRecommendFriendMsg(uid, pageSize);
			} else if (type == 1) {
				// 上拉
				countMsgs = qxUserMsgService.pullupRecommendFriendMsg(uid, umid, pageSize);
			} else if (type == 2) {
				// 下拉
				countMsgs = qxUserMsgService.pulldownRecommendFriendMsg(uid, umid, pageSize);
			}
			backInfo.setData(countMsgs);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	
	
	/**
	 * 备用
	 * 
	 * 每次累加返回消息，按照消息id获取消息列表
	 * 
	 * 获取评论和赞二级列表 msg_group_id=3 msg_type in ('1','2','8','9','10','11')
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getCommentAndPraiseMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getCommentAndPraiseMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 加载开始id
		String umidStr = map.get("umid");
		String uid = map.get("uid");
		// 加载类型0:取最新pageSize条
		// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
		String typeStr = map.get("type");
		// 加载数量
		String pageSizeStr = map.get("pageSize");

		DataBackInfo<CommentAndPraiseMsgToJSon> backInfo = new DataBackInfo<CommentAndPraiseMsgToJSon>();
		if (StrUtil.isBlank(umidStr) || StrUtil.isBlank(uid) || StrUtil.isBlank(typeStr) || StrUtil.isBlank(pageSizeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int type = Integer.parseInt(typeStr);
			int umid = Integer.parseInt(umidStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			List<CommentAndPraiseMsgToJSon> countMsgs = new ArrayList<CommentAndPraiseMsgToJSon>();
			// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
			if (type == 0) {
				// 最新
				countMsgs = qxUserMsgService.lastestCommentAndPraiseMsg(uid, pageSize);
			} else if (type == 1) {
				// 上拉
				countMsgs = qxUserMsgService.pullupCommentAndPraiseMsg(uid, umid, pageSize);
			} else if (type == 2) {
				// 下拉
				countMsgs = qxUserMsgService.pulldownCommentAndPraiseMsg(uid, umid, pageSize);
			}
			backInfo.setData(countMsgs);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	
	/**
	 * 备用
	 * 
	 * 每次累加返回消息，按照消息id获取消息列表
	 * 
	 * 获取系统消息二级列表 msg_group_id=4 msg_type in ('6','7'')
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getOfficialInfoMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getOfficialInfoMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 加载开始id
		String umidStr = map.get("umid");
		String uid = map.get("uid");
		// 加载类型0:取最新pageSize条
		// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
		String typeStr = map.get("type");
		// 加载数量
		String pageSizeStr = map.get("pageSize");

		DataBackInfo<OfficialInfoMsgToJSon> backInfo = new DataBackInfo<OfficialInfoMsgToJSon>();
		if (StrUtil.isBlank(umidStr) || StrUtil.isBlank(uid) || StrUtil.isBlank(typeStr) || StrUtil.isBlank(pageSizeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int type = Integer.parseInt(typeStr);
			int umid = Integer.parseInt(umidStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			List<OfficialInfoMsgToJSon> countMsgs = new ArrayList<OfficialInfoMsgToJSon>();
			// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
			if (type == 0) {
				// 最新
				countMsgs = qxUserMsgService.lastestOfficialInfoMsg(uid, pageSize);
			} else if (type == 1) {
				// 上拉
				countMsgs = qxUserMsgService.pullupOfficialInfoMsg(uid, umid, pageSize);
			} else if (type == 2) {
				// 下拉
				countMsgs = qxUserMsgService.pulldownOfficialInfoMsg(uid, umid, pageSize);
			}
			backInfo.setData(countMsgs);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	
	/**
	 * 备用
	 * 
	 * 每次累加返回消息，按照消息id获取消息列表
	 * 
	 * 获取互动消息二级列表 msg_group_id=5 msg_type in ('4')
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getInteractionInfoMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getInteractionInfoMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 加载开始id
		String umidStr = map.get("umid");
		String uid = map.get("uid");
		// 加载类型0:取最新pageSize条
		// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
		String typeStr = map.get("type");
		// 加载数量
		String pageSizeStr = map.get("pageSize");

		DataBackInfo<InteractionInfoMsgToJSon> backInfo = new DataBackInfo<InteractionInfoMsgToJSon>();
		if (StrUtil.isBlank(umidStr) || StrUtil.isBlank(uid) || StrUtil.isBlank(typeStr) || StrUtil.isBlank(pageSizeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int type = Integer.parseInt(typeStr);
			int umid = Integer.parseInt(umidStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			List<InteractionInfoMsgToJSon> countMsgs = new ArrayList<InteractionInfoMsgToJSon>();
			// 上拉1(加载历史小于umid的pageSize条消息),下拉2(加载最新大于umid的pageSize条消息)
			if (type == 0) {
				// 最新
				countMsgs = qxUserMsgService.lastestInteractionInfoMsg(uid, pageSize);
			} else if (type == 1) {
				// 上拉
				countMsgs = qxUserMsgService.pullupInteractionInfoMsg(uid, umid, pageSize);
			} else if (type == 2) {
				// 下拉
				countMsgs = qxUserMsgService.pulldownInteractionInfoMsg(uid, umid, pageSize);
			}
			backInfo.setData(countMsgs);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	
	
	
	


	/**
	 * 消息二级页面：关注消息列表
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFollowMsgList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<FollowMsgToJson> getFollowMsgList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<FollowMsgToJson> backInfo = new DataBackInfo<FollowMsgToJson>();
		// 简单校验
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32) {
			logger.debug("QxUserMsgController - getFollowMsgList - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<FollowMsgToJson> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<FollowMsgToJson>();
			} else {
				page = new Page<FollowMsgToJson>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<FollowMsgToJson> followMsgList = new ArrayList<FollowMsgToJson>();
			int totalCount = 0;
			totalCount = qxUserMsgService.getFollowMsgCount(uid);
			if (totalCount > 0) {
				page.setTotalCount(totalCount);
				followMsgList = qxUserMsgService.getFollowMsgList(uid, page);
			}
			backInfo.setData(followMsgList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxUserMsgController - getFollowMsgList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return backInfo;
	}
	
	/**
	 * 消息二级页面：评论和赞
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getCommentPraiseMsgList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<QxUserMsg> getCommentPraiseMsgList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<QxUserMsg> backInfo = new DataBackInfo<QxUserMsg>();
		// 简单校验
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32) {
			logger.debug("QxUserMsgController - getCommentPraiseMsgList - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<QxUserMsg> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<QxUserMsg>();
			} else {
				page = new Page<QxUserMsg>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<QxUserMsg> msgList = Lists.newArrayList();
			int totalCount = 0;
			Map<String, Object> maps = Maps.newHashMap();
			maps.put("uid", uid);
			maps.put("msgTypes", new String[]{Global.MESSAGE_MSG, Global.REPLY_MSG, Global.PRAISE_MSG, Global.COMMENT_MSG, Global.COMMENT_REPLY_MSG, Global.GROUPS_COMMENT_REPLY_MSG});
			totalCount = qxUserMsgService.getCommentPraiseMsgCount(maps);
			if (totalCount > 0) {
				page.setTotalCount(totalCount);
				maps.put("offset", page.getOffset());
				maps.put("limit", page.getLimit());
				msgList = qxUserMsgService.getCommentPraiseMsgList(maps);
			}
			backInfo.setData(msgList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxUserMsgController - getFollowMsgList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * 消息二级页面：系统消息
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getSystemMsgList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<QxUserMsg> getSystemMsgList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<QxUserMsg> backInfo = new DataBackInfo<QxUserMsg>();
		// 简单校验
		if (StrUtil.isBlank(uid)) {
			logger.debug("QxUserMsgController - getSystemMsgList - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<QxUserMsg> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<QxUserMsg>();
			} else {
				page = new Page<QxUserMsg>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<QxUserMsg> msgList = Lists.newArrayList();
			int totalCount = 0;
			totalCount = qxUserMsgService.getSystemMsgCount(new String[]{Global.UPDATE_MSG, Global.GROUP_UPDFATE_MSG});
			if (totalCount > 0) {
				Map<String, Object> maps = Maps.newHashMap();
				maps.put("msgTypes", new String[]{Global.UPDATE_MSG, Global.GROUP_UPDFATE_MSG});
				page.setTotalCount(totalCount);
				maps.put("offset", page.getOffset());
				maps.put("limit", page.getLimit());
				msgList = qxUserMsgService.getSystemMsgList(maps);
			}
			backInfo.setData(msgList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxUserMsgController - getSystemMsgList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * 消息二级页面：好友推荐
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getRecommendFriendMsgList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<FollowMsgToJson> getRecommendFriendMsgList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<FollowMsgToJson> backInfo = new DataBackInfo<FollowMsgToJson>();
		// 简单校验
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32) {
			logger.debug("QxUserMsgController - getRecommendFriendMsgList - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<FollowMsgToJson> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<FollowMsgToJson>();
			} else {
				page = new Page<FollowMsgToJson>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<FollowMsgToJson> followMsgList = new ArrayList<FollowMsgToJson>();
			int totalCount = 0;
			totalCount = qxUserMsgService.getMsgCountByType(uid, Global.RECOMMEND_MSG);
			if (totalCount > 0) {
				page.setTotalCount(totalCount);
				followMsgList = qxUserMsgService.getMsgListByType(uid, page, Global.RECOMMEND_MSG);
			}
			backInfo.setData(followMsgList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxUserMsgController - getRecommendFriendMsgList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return backInfo;
	}
	
	/**
	 * 消息二级页面：互动消息
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getInteractionMsgList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<FollowMsgToJson> getInteractionMsgList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<FollowMsgToJson> backInfo = new DataBackInfo<FollowMsgToJson>();
		// 简单校验
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32) {
			logger.debug("QxUserMsgController - getInteractionMsgList - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<FollowMsgToJson> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<FollowMsgToJson>();
			} else {
				page = new Page<FollowMsgToJson>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<FollowMsgToJson> followMsgList = new ArrayList<FollowMsgToJson>();
			int totalCount = 0;
			totalCount = qxUserMsgService.getMsgCountByType(uid, Global.INTERACTION_MSG);
			if (totalCount > 0) {
				page.setTotalCount(totalCount);
				followMsgList = qxUserMsgService.getMsgListByType(uid, page, Global.INTERACTION_MSG);
			}
			backInfo.setData(followMsgList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxUserMsgController - getInteractionMsgList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return backInfo;
	}
}