/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.BeanMapper;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.mybatis.Page;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.MainPageBackInfo;
import com.innovate.cms.modules.data.entity.BaseUserPropertyToJson;
import com.innovate.cms.modules.data.entity.BubbleInfoToJson;
import com.innovate.cms.modules.data.entity.RandomTwoUserToJson;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.data.entity.UgcGroupToJson;
import com.innovate.cms.modules.data.entity.UgcHistoryGroupToJson;
import com.innovate.cms.modules.data.entity.UserInfoToJson;
import com.innovate.cms.modules.qs.entity.user.FollowerUser;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.ques.QxQuestionsService;
import com.innovate.cms.modules.qs.service.sns.QxFollowService;
import com.innovate.cms.modules.qs.service.user.SystemUserInfoService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 系统用户信息Controller
 * 
 * @author gaoji
 * @version 2016-04-12
 */
@Controller
@RequestMapping(value = "/api")
public class SystemUserInfoController extends BaseController {

	@Autowired
	private SystemUserInfoService systemUserInfoService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private QxFollowService qxFollowService;
	@Autowired
	private QxQuestionsService qxQuestionsService;

	/*
	 * @Autowired private QxUserMsgService qxUserMsgService;
	 * 
	 * @Autowired private QxMessageService qxMessageService;
	 * 
	 * @Autowired private QxMessagePostService qxMessagePostService;
	 * 
	 * @Autowired private QxFootprintService qxFootprintService;
	 * 
	 * @Autowired private QxMatchService qxMatchService;
	 */
	/**
	 * 用户足迹
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userHistory", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userHistory(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		DataBackInfo<UgcHistoryGroupToJson> backInfo = new DataBackInfo<UgcHistoryGroupToJson>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 获取用户做过的站边专题(模板0类型的)历史记录
			List<UgcHistoryGroupToJson> groups = qxQuestionsService.getHistoryGroups(uid);
			// if (groups.size()>0) {
			backInfo.setData(groups);
			// }
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
	 * 用户主页
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userMainPage", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userMainPage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		MainPageBackInfo<UgcGroupToJson> backInfo = new MainPageBackInfo<UgcGroupToJson>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 获取用户UGC专题/用户上传专题
			List<UgcGroupToJson> groups = qxQuestionsService.getUgcGroups(uid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setHeadimgurl(groups.get(0).getHeadimgurl());
				backInfo.setNickname(groups.get(0).getNickname());
				backInfo.setUgcCount(groups.size());
			} else {
				SystemUser user = systemUserService.findByUid(uid);
				backInfo.setHeadimgurl(user.getHeadimgurl());
				backInfo.setNickname(user.getNickname());
				backInfo.setData(groups);
			}
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
			// 获取用户足迹数
			backInfo.setHistoryNum(systemUserService.historyNum(uid));
			backInfo.setUid(uid);
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
	 * 小程序-用户主页
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userXcxMainPage", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userXcxMainPage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		MainPageBackInfo<UgcHistoryGroupToJson> backInfo = new MainPageBackInfo<UgcHistoryGroupToJson>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 小程序-获取用户UGC专题/用户上传专题
			List<UgcHistoryGroupToJson> groups = qxQuestionsService.getXcxUgcGroups(uid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setHeadimgurl(groups.get(0).getHeadimgurl());
				backInfo.setNickname(groups.get(0).getNickname());
				backInfo.setUgcCount(groups.size());
			} else {
				SystemUser user = systemUserService.findByUid(uid);
				backInfo.setHeadimgurl(user.getHeadimgurl());
				backInfo.setNickname(user.getNickname());
				backInfo.setData(groups);
			}
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
			// 获取用户足迹数
			backInfo.setHistoryNum(systemUserService.historyNum(uid));
			backInfo.setUid(uid);
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
	 * 小程序-用户主页
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userXcxfMainPage", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userXcxfMainPage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String fuid = map.get("fuid");
		MainPageBackInfo<UgcHistoryGroupToJson> backInfo = new MainPageBackInfo<UgcHistoryGroupToJson>();
		if (StrUtil.isBlank(uid)||StrUtil.isBlank(fuid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 小程序-获取好友用户UGC专题/用户上传专题
			List<UgcHistoryGroupToJson> groups = qxQuestionsService.getXcxfUgcGroups(uid,fuid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setHeadimgurl(groups.get(0).getHeadimgurl());
				backInfo.setNickname(groups.get(0).getNickname());
				backInfo.setUgcCount(groups.size());
			} else {
				SystemUser user = systemUserService.findByUid(uid);
				backInfo.setHeadimgurl(user.getHeadimgurl());
				backInfo.setNickname(user.getNickname());
				backInfo.setData(groups);
			}
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(fuid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(fuid));
			// 获取用户足迹数
			backInfo.setHistoryNum(systemUserService.historyNum(fuid));
			backInfo.setUid(fuid);
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
	 * 获取用户关注的好友
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/findFollows", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo findFollows(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		DataBackInfo<SystemUser> backInfo = new DataBackInfo<SystemUser>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			/**
			 * 获取用户关注的好友
			 */
			List<SystemUser> users = systemUserService.findFollows(uid);
			backInfo.setData(users);
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
	 * 获取用户粉丝
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/findFollowers", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo findFollowers(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		DataBackInfo<FollowerUser> backInfo = new DataBackInfo<FollowerUser>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			/**
			 * 获取用户关注的好友
			 */
			List<SystemUser> fusers = systemUserService.findFollows(uid);
			/**
			 * 获取用户粉丝
			 */
			List<FollowerUser> users = systemUserService.findFollowers(uid);
			dealUsers(fusers, users);
			backInfo.setData(users);
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
	 * 处理用户粉丝的关注信息
	 * 
	 * @param fusers
	 * @param users
	 */
	private void dealUsers(List<SystemUser> fusers, List<FollowerUser> users) {
		List<String> fuids = Lists.newArrayList();
		for (SystemUser user : fusers) {
			fuids.add(user.getUid());
		}
		for (FollowerUser fuser : users) {
			// 如果我的好友列表里有该粉丝，则不需要关注
			if (fuids.contains(fuser.getUid())) {
				// 0-需要 1-不需要
				fuser.setNeedFollow(1);
			} else {
				fuser.setNeedFollow(0);
			}
		}
	}

	/**
	 * 
	 * @Title: getUserInfo
	 * @Description: 根据UID获取用户资料
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<UserInfoToJson>
	 *
	 */
	@RequestMapping(value = "/v1/user/info", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<UserInfoToJson> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		// 创建内部集合对象
		List<UserInfoToJson> data = new ArrayList<UserInfoToJson>();
		DataBackInfo<UserInfoToJson> backInfo = new DataBackInfo<UserInfoToJson>();
		String _uid = request.getHeader("Uid");

		// 判断参数
		if (StrUtil.isBlank(_uid) || _uid.trim().length() != 32) {
			logger.debug("UserInfoController - getUserInfo - 参数错误_uid=：{}", _uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			backInfo.setData(data);
		} else {
			// 查询数据
			SystemUser systemUser = systemUserService.getUserInfoByUid(_uid);
			if (systemUser != null) {
				try {
					UserInfoToJson userInfoToJson = new UserInfoToJson();
					BeanMapper.copy(systemUser, userInfoToJson);
					data.add(userInfoToJson);
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					backInfo.setData(data);
				} catch (Exception e) {
					logger.debug(e.getMessage());
					backInfo.setStateCode(Global.int300302);
					backInfo.setRetMsg(Global.str300302);
				}
			} else {
				logger.debug("User不存在- {}", _uid);
				backInfo.setStateCode(Global.int300204);
				backInfo.setRetMsg(Global.str300204);
			}

		}
		return backInfo;
	}

	/**
	 * h5获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/info", method = RequestMethod.GET)
	public @ResponseBody String getUserinfo(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");

		// 创建内部集合对象
		List<UserInfoToJson> data = new ArrayList<UserInfoToJson>();
		DataBackInfo<UserInfoToJson> backInfo = new DataBackInfo<UserInfoToJson>();
		String _uid = request.getParameter("uid");

		// 判断参数
		if (StrUtil.isBlank(_uid) || _uid.trim().length() != 32) {
			logger.debug("UserInfoController - getUserInfo - 参数错误_uid=：{}", _uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			backInfo.setData(data);
		} else {
			// 查询数据
			SystemUser systemUser = systemUserService.getUserInfoByUid(_uid);
			if (systemUser != null) {
				try {
					UserInfoToJson userInfoToJson = new UserInfoToJson();
					BeanMapper.copy(systemUser, userInfoToJson);
					data.add(userInfoToJson);
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					backInfo.setData(data);
				} catch (Exception e) {
					logger.debug(e.getMessage());
					backInfo.setStateCode(Global.int300302);
					backInfo.setRetMsg(Global.str300302);
				}
			} else {
				logger.debug("User不存在- {}", _uid);
				backInfo.setStateCode(Global.int300204);
				backInfo.setRetMsg(Global.str300204);
			}

		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 
	 * @Title: putUserInfo
	 * @Description: 根据UID更新用户信息
	 * @param userInfoToJson
	 * @param request
	 * @param response
	 * @return 返回类型 BaseBackInfo
	 *
	 */
	@RequestMapping(value = "/v1/user/info", method = RequestMethod.PUT)
	public @ResponseBody BaseBackInfo putUserInfo(@RequestBody UserInfoToJson userInfoToJson, HttpServletRequest request, HttpServletResponse response) {
		String _uid = StrUtil.nullToEmpty(request.getHeader("Uid"));
		BaseBackInfo backInfo = new BaseBackInfo();
		// 如果用户名为空则直接返回
		if (StrUtil.isBlank(_uid) || _uid.trim().length() != 32 || StrUtil.isBlank(userInfoToJson.getNickname()) || StrUtil.isBlank(userInfoToJson.getHeadimgurl())
		// ||StrUtil.isBlank(userInfoToJson.getConstellation())
		// ||StrUtil.isBlank(userInfoToJson.getBirthday().toString())
		// ||StrUtil.isBlank(userInfoToJson.getSex())
		) {
			logger.debug("输入参数中头像地址为：{}", userInfoToJson.getHeadimgurl());
			logger.debug("UserInfoController - putUserInfo -  参数错误或为空");
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			try {
				SystemUser systemUser = systemUserService.get(_uid);
				// String nickname = systemUser.getNickname();
				// String headimgurl = systemUser.getHeadimgurl();
				if (systemUser != null) {
					systemUser.setNickname(userInfoToJson.getNickname());
					// systemUser.setHeadimgurl(userInfoToJson.getHeadimgurl().replace(Global._URL,
					// ""));
					systemUser.setHeadimgurl(userInfoToJson.getHeadimgurl());

					systemUser.setConstellation(userInfoToJson.getConstellation());
					systemUser.setBirthday(userInfoToJson.getBirthday());
					systemUser.setSex(userInfoToJson.getSex());
					systemUser.setProvince(userInfoToJson.getProvince());
					systemUser.setCity(userInfoToJson.getCity());
					systemUser.setCountry(userInfoToJson.getCountry());
					logger.debug("保存的头像={}", systemUser.getHeadimgurl());
					systemUserService.putUserInfo(systemUser);
					// 更新好友数据
					qxFollowService.updateFollowInfo(_uid, userInfoToJson.getNickname(), userInfoToJson.getHeadimgurl());
					// 同步到aliIM
					IMUtils.updateUser(systemUser);
				}
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				// 判断是否有修改昵称或头像
				/*
				 * if(systemUser!= null &&
				 * !(nickname.equals(userInfoToJson.getNickname()) &&
				 * headimgurl.equals(userInfoToJson.getHeadimgurl()))) {
				 * //开启一个线程更新历史数据：更新消息表的留言相关、留言、回复、好友、足迹、模板五结果页等数据 new Thread()
				 * { public void run() { try {
				 * qxUserMsgService.updateUserMessage(_uid,
				 * userInfoToJson.getNickname(),
				 * userInfoToJson.getHeadimgurl());
				 * qxMessageService.updateMessage(_uid,
				 * userInfoToJson.getNickname(),
				 * userInfoToJson.getHeadimgurl());
				 * qxMessagePostService.updatePost(_uid,
				 * userInfoToJson.getNickname(),
				 * userInfoToJson.getHeadimgurl());
				 * qxFollowService.updateFollowInfo(_uid,
				 * userInfoToJson.getNickname(),
				 * userInfoToJson.getHeadimgurl());
				 * qxFootprintService.updateFootprint(_uid,
				 * userInfoToJson.getNickname(),
				 * userInfoToJson.getHeadimgurl());
				 * qxMatchService.updateMatchData(_uid,
				 * userInfoToJson.getNickname(),
				 * userInfoToJson.getHeadimgurl()); } catch (Exception e) {
				 * logger.error("更新用户头像昵称相关历史信息失败: " + e.getMessage()); }
				 * 
				 * }; }.start(); }
				 */
			} catch (Exception e) {
				logger.debug("更新用户失败", e.getMessage());
				backInfo.setStateCode(Global.int300302);
				backInfo.setRetMsg(Global.str300302);
			}
		}
		return backInfo;
	}

	/**
	 * 举报 暂未开放
	 * 
	 * @Title: saveReport
	 * @Description:
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 BaseBackInfo
	 *
	 */
	@RequestMapping(value = "/v1/user/report", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo saveReport(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = request.getHeader("Uid");
		String qid = map.get("qid");
		BaseBackInfo backInfo = new BaseBackInfo();
		if (!uid.equals("-1") && (StrUtil.isBlank(uid) || StrUtil.isBlank(qid) || uid.trim().length() != 32)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			// userInfoService.saveReport(uid,Integer.parseInt(qid));
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("保存举报信息失败[saveReport]" + e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return backInfo;
	}

	/**
	 * 反馈 暂未开放
	 * 
	 * @Title: saveFeedback
	 * @Description:
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 BaseBackInfo
	 *
	 */
	@RequestMapping(value = "/v1/user/feedback", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo saveFeedback(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// TODO see saveFeedback 反馈不登陆也行
		String uid = request.getHeader("Uid");
		String content = map.get("content");
		// String mobile = StrUtil.isBlank(map.get("mobile"))? "" :
		// map.get("mobile");
		BaseBackInfo backInfo = new BaseBackInfo();

		if (uid.equals("-1") || StrUtil.isBlank(content) || uid.trim().length() != 32) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			// userInfoService.saveFeedback(uid,content,mobile);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("保存反馈信息失败[saveFeedback]" + e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 气泡好友推荐
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/bubbleNominate", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<BubbleInfoToJson> getBubbleNominate(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<BubbleInfoToJson> backInfo = new DataBackInfo<BubbleInfoToJson>();
		String sex = map.get("sex");
		// 简单参数校验
		String regex = "[12]|-1";
		List<BubbleInfoToJson> bubbleInfoList = Lists.newArrayList();
		if (!ReUtil.isMatch(regex, sex)) {
			backInfo.setData(bubbleInfoList);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			bubbleInfoList = systemUserInfoService.getBubbleNominate(sex);
			if (bubbleInfoList.size() == 0) {
				bubbleInfoList = Lists.newArrayList();
			}
			// logger.debug(JsonMapper.getInstance().toJson(bubbleInfoList));
			backInfo.setData(bubbleInfoList);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[SystemUserInfoController - getBubbleNominate()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 模板五结果页随机推荐2人接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/getRandomTwoUser", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<RandomTwoUserToJson> getRandomTwoUser(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<RandomTwoUserToJson> backInfo = new DataBackInfo<RandomTwoUserToJson>();
		String uid = map.get("uid");
		String gid = map.get("gid");
		List<RandomTwoUserToJson> randomTwoUserList = Lists.newArrayList();
		// 简单参数校验
		if (Strings.isNullOrEmpty(gid) || Strings.isNullOrEmpty(uid)) {
			backInfo.setData(randomTwoUserList);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			randomTwoUserList = systemUserInfoService.getRandomTwoUser(uid, gid);
			backInfo.setData(randomTwoUserList);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[SystemUserInfoController - getRandomTwoUser()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 获取更多推荐人，最多推荐20 模板五结果页随机推荐20人接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/getRandomTwentyUser", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<RandomTwoUserToJson> getRandomTwentyUser(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<RandomTwoUserToJson> backInfo = new DataBackInfo<RandomTwoUserToJson>();
		String uid = map.get("uid");
		String gid = map.get("gid");
		List<RandomTwoUserToJson> randomTwoUserList = Lists.newArrayList();
		// 简单参数校验
		if (Strings.isNullOrEmpty(gid) || Strings.isNullOrEmpty(uid)) {
			backInfo.setData(randomTwoUserList);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			randomTwoUserList = systemUserInfoService.getRandomTwentyUser(uid, gid);
			backInfo.setData(randomTwoUserList);
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
	 * 获取用户基本属性---性别、年龄、星座、地区
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/baseUserProperty", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<BaseUserPropertyToJson> getBaseUserProperty(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// 创建内部集合对象
		List<BaseUserPropertyToJson> data = new ArrayList<BaseUserPropertyToJson>();
		DataBackInfo<BaseUserPropertyToJson> backInfo = new DataBackInfo<BaseUserPropertyToJson>();
		String uid = map.get("uid");

		// 判断参数
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32) {
			logger.debug("UserInfoController - getBaseUserProperty - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			backInfo.setData(data);
		} else {
			// 查询数据
			SystemUser systemUser = systemUserService.getUserInfoByUid(uid);
			if (systemUser != null) {
				try {
					BaseUserPropertyToJson baseUserPropertyToJson = new BaseUserPropertyToJson();
					BeanMapper.copy(systemUser, baseUserPropertyToJson);
					Integer age = null;
					Date birthday = systemUser.getBirthday();
					// 生日不为空, 并且不为默认的"1900-01-01 00:00:00" 时
					if (birthday != null && !Global.DEFAULT_BIRTHDAY.equals(DateUtils.formatDate(birthday, "yyyy-MM-dd HH:mm:ss"))) {
						age = DateUtils.getAge(systemUser.getBirthday());
					}
					baseUserPropertyToJson.setAge(age);
					data.add(baseUserPropertyToJson);
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					backInfo.setData(data);
				} catch (Exception e) {
					logger.debug(e.getMessage());
					backInfo.setStateCode(Global.int300302);
					backInfo.setRetMsg(Global.str300302);
				}
			} else {
				logger.debug("User不存在- {}", uid);
				backInfo.setStateCode(Global.int300204);
				backInfo.setRetMsg(Global.str300204);
			}

		}
		return backInfo;
	}

	/**
	 * 注册用户列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<RegUserInfoToJson> getUserList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<RegUserInfoToJson> backInfo = new DataBackInfo<RegUserInfoToJson>();
		// 简单校验
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32) {
			logger.debug("SystemUserInfoController - getUserList - 参数错误uid=：{}", uid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<RegUserInfoToJson> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<RegUserInfoToJson>();
			} else {
				page = new Page<RegUserInfoToJson>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<RegUserInfoToJson> regUserList = Lists.newArrayList();
			int totalCount = 0;
			totalCount = systemUserService.getUserCount(uid);
			if (totalCount > 0) {
				page.setTotalCount(totalCount);
				regUserList = systemUserService.getUserList(uid, page);
			}
			backInfo.setData(regUserList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[SystemUserInfoController - getUserList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 根据昵称搜索用户
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/searchUser", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<RegUserInfoToJson> searchUserByNickname(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String nickname = map.get("nickname");
		DataBackInfo<RegUserInfoToJson> backInfo = new DataBackInfo<RegUserInfoToJson>();
		// 简单校验
		if (Strings.isNullOrEmpty(nickname)) {
			logger.debug("SystemUserInfoController - searchUserByNickname - 参数错误nickname=：{}", nickname);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			List<RegUserInfoToJson> regUserList = Lists.newArrayList();
			regUserList = systemUserService.getUserInfoByNickname(nickname);
			backInfo.setData(regUserList);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[SystemUserInfoController - searchUserByNickname()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 
	 * @Title: exp
	 * @Description: 放在最后位置别动
	 * @param ex
	 * @return 返回类型 BaseBackInfo
	 *
	 */
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex) {
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg)) {
			logger.info("参数错误" + retMsg);
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}
}