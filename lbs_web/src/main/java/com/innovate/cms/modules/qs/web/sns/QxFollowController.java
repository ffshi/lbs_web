/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.IdGen;
import com.innovate.cms.common.utils.ListSortUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.FriendsTopicToJson;
import com.innovate.cms.modules.qs.entity.sns.IsFriend;
import com.innovate.cms.modules.qs.entity.sns.QxFollow;
import com.innovate.cms.modules.qs.entity.sns.QxFriend;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.entity.sns.RecommendUser;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.sns.QxFollowService;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 关注表Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxFollowController extends BaseController {

	@Autowired
	private QxFollowService qxFollowService;

	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private QxUserMsgService qxUserMsgService;

	@Autowired
	private QxPushInfoService qxPushInfoService;

	/**
	 * whishbone2.0 我的关注内的推荐 推荐一个当天做题结果匹配度最高的人
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getRecommendUser", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getRecommendUser(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int currentDay = Integer.parseInt(DateUtils.getDate("yyyyMMdd"));
			// 获取当天做完专题的用户
			List<RecommendUser> users = qxFollowService.getRecommendUser(currentDay);
			backInfo.setItem(dealRecommendUser(uid, users));
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
	 * 对比算法，获取最匹配的一个用户
	 * 
	 * @param uid
	 * @param users
	 * @return
	 */
	public RecommendUser dealRecommendUser(String uid, List<RecommendUser> users) {
		RecommendUser res = new RecommendUser();

		List<RecommendUser> temp = Lists.newArrayList();
		if (users.size() < 2) {
			return res;
		} else {
			for (RecommendUser user : users) {
				if (user.getUid().equals(uid)) {
					res = user;
				} else {
					temp.add(user);
				}
			}

			if (res.getUid() == null || temp.size() < 1) {
				return res;
			} else {
				for (RecommendUser u : temp) {
					u.setMatch(compareRes(res.getResult(), u.getResult()));
				}
			}
			// 排序
			ListSortUtil<RecommendUser> sortList = new ListSortUtil<RecommendUser>();
			sortList.sort(temp, "match", "desc");
			return temp.get(0);
		}
	}

	private int compareRes(String r1, String r2) {
		int count = 0;
		for (int i = 0; i < r1.length(); i++) {
			if (r1.charAt(i) == r2.charAt(i)) {
				count++;
			}
		}
		return 10 * count + new Random().nextInt(10);
	}

	/**
	 * 存储用户当天所有首页精选专题接口 当用户做完当天精选题目的时候调用该接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/storeT0results", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo storeT0results(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String result = map.get("result");

		int currentDay = Integer.parseInt(DateUtils.getDate("yyyyMMdd"));

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(result)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		if (result.contains("0") || result.length() != 10) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg("User can't done the questions !");
			return info;
		}
		try {
			// 存储用户当天的精选题答案
			qxFollowService.storeT0results(uid, result, currentDay);
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
	 * 添加好友/删除好友 flag：0：add 1:del
	 * 
	 * uid关注followUid，
	 * 
	 * 关注后： uid多一个followUid的朋友 followUid不是uid的好友
	 * 
	 * 应该向followUid推送一条被加好友的信息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/addOrDelFriend", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo addFriend(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// 添加删除标记 0：添加 1：删除
		final String flag = map.get("flag");
		// 用户id
		final String uid = map.get("uid");
		// 被关注用户id
		final String followUid = map.get("followUid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(flag) || StrUtil.isBlank(followUid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		if (uid.equals(followUid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			QxFollow qxFollow = new QxFollow();
			qxFollow.setUid(uid);
			qxFollow.setFollowUid(followUid);
			qxFollow.setCreateDate(new Date());
			qxFollow.setDelFlag(flag);
			SystemUser user = null;
			if (Integer.parseInt(flag) == 0) {
				// 添加好友
				int num = qxFollowService.isExist(qxFollow);
				user = systemUserService.findByUid(uid);
				if (num == 0) {
					// 没添加过该好友
					SystemUser fuser = systemUserService.findByUid(followUid);
					qxFollow.setFlid(IdGen.uuid());
					qxFollow.setFollowName(fuser.getNickname());
					qxFollow.setFollowImg(fuser.getHeadimgurl());
					qxFollow.setFollowUnionid(fuser.getUnionid());

					qxFollow.setName(user.getNickname());
					qxFollow.setImg(user.getHeadimgurl());
					qxFollow.setUnionid(user.getUnionid());
					qxFollowService.saveFollow(qxFollow);
				} else {
					// 曾经添加过,但是删除了，现在再次添加
					qxFollowService.updateFollow(qxFollow);
				}

				// 给被关注用户推送被关注信息
				QxUserMsg msg = new QxUserMsg();

				msg.setUid(followUid);
				msg.setMsgName(user.getNickname());
				msg.setMsgImg(user.getHeadimgurl());
				msg.setMsgType("3");
				msg.setMsgTypeName("关注");
				msg.setMsgJumpId(uid);
				msg.setMsgContent1("我关注了你,来我的主页看看");
				msg.setCreateDate(new Date());
				qxUserMsgService.saveUserMsg(msg);

				final SystemUser puser = user;

				// 推送
				// new Thread(new Runnable() {
				// @Override
				// public void run() {
				// QxPushInfo device =
				// qxPushInfoService.getPushInfoByUid(followUid);
				//
				// if (null != device && null != device.getDeviceId()) {
				// // PushClient.PushNoticeToiOS(PushGlobal.device,
				// // device.getDeviceId(), "title", "body", "summery",
				// //
				// extraParameter"{\"uid\":\"4aa4e5104fda417a843e0fe91a2ecde1\",\"gid\":\"xxxxxxxxxxxx\"}");
				// PushContent pushContent = new PushContent();
				// pushContent.setDeviceType(device.getDeviceType());
				// pushContent.setTargetValue(device.getDeviceId());
				//
				// // 设置内容，可以不设置
				// pushContent.setBody(puser.getNickname() + "刚刚关注了您");
				// // 摘要，描述，通知栏要展示的内容展示
				// pushContent.setSummary(puser.getNickname() + "刚刚关注了您");
				//
				// // 设置自定义json扩展属性
				// JsonObject extParameters = new JsonObject();
				//
				// extParameters.addProperty("jumpId", uid);
				// extParameters.addProperty("type", PushContent.Follow);
				// // extParameters.addProperty("umid",
				// // "消息id-留言/回复/关注");
				// // extParameters.addProperty("gid",
				// // "someone做了您足迹内的any专题");
				// // // 留言id
				// // extParameters.addProperty("msid", "");
				// // //回复id
				// // extParameters.addProperty("poid", "");
				// // extParameters.addProperty("uid", followUid);
				//
				// pushContent.setExtParameters(extParameters);
				// PushClient.pushContentNotice(pushContent);
				//
				// }
				// }
				// }).start();

			} else {
				// 删除好友
				qxFollowService.updateFollow(qxFollow);
			}

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxFollowController - addOrDelFriend()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 判断是否是好友 uid进入otherUid的页面
	 * 
	 * 用户:uid 进入别人主页：otherUid
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/isFriend", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo isFriend(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 用户id
		String uid = map.get("uid");
		// 被关注用户id
		String followUid = map.get("otherUid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(followUid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			QxFollow qxFollow = new QxFollow();
			qxFollow.setUid(uid);
			qxFollow.setFollowUid(followUid);
			qxFollow.setCreateDate(new Date());

			int num = qxFollowService.isExistNew(qxFollow);
			if (num == 0) {// 非好友
				backInfo.setItem(new IsFriend(0));
			} else {// 是好友
				backInfo.setItem(new IsFriend(1));
			}

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxFollowController - addOrDelFriend()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 获取好友列表 uid：用户uid
	 * 
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFriendList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getFriendList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");

		DataBackInfo<QxFriend> backInfo = new DataBackInfo<QxFriend>();
		if (StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			List<QxFriend> frinds = qxFollowService.findFrindList(uid);
			if (frinds.size() == 0) {
				frinds = new ArrayList<QxFriend>();
			}
			backInfo.setData(frinds);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxFollowController - getFriendList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 好友参与的话题 接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFriendsTopic", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<FriendsTopicToJson> getFriendsTopic(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");

		DataBackInfo<FriendsTopicToJson> backInfo = new DataBackInfo<FriendsTopicToJson>();
		if (StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			List<FriendsTopicToJson> friendsTopicList = qxFollowService.findFriendsTopic(uid);
			// logger.debug(JsonMapper.getInstance().toJson(friendsTopicList));
			backInfo.setData(friendsTopicList);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxFollowController - getFriendsTopic()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

}