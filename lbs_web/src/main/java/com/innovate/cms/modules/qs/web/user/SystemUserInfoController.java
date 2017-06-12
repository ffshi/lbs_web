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
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.common.entity.MainPageBackInfo;
import com.innovate.cms.modules.common.entity.UserPic;
import com.innovate.cms.modules.data.entity.BaseUserPropertyToJson;
import com.innovate.cms.modules.data.entity.BubbleInfoToJson;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.data.entity.UserInfoToJson;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;
import com.innovate.cms.modules.qs.entity.user.FollowerUser;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgService;
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
	private DynamicMsgService dynamicMsgService;

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
		if (StrUtil.isBlank(_uid) || _uid.trim().length() != 32) {
			logger.debug("输入参数中头像地址为：{}", userInfoToJson.getHeadimgurl());
			logger.debug("UserInfoController - putUserInfo -  参数错误或为空");
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			try {
				SystemUser systemUser = systemUserService.get(_uid);
				// String nickname = systemUser.getNickname();
				// String headimgurl = systemUser.getHeadimgurl();
				// boolean imgBoolean = userInfoToJson.getHeadimgurl() != null
				// && userInfoToJson.getHeadimgurl().trim().length() > 0 &&
				// !systemUser.getHeadimgurl().equals(userInfoToJson.getHeadimgurl());
				// boolean nameBoolean = userInfoToJson.getNickname() != null &&
				// userInfoToJson.getNickname().trim().length() > 0 &&
				// !systemUser.getNickname().equals(userInfoToJson.getNickname());
				boolean imgBoolean = userInfoToJson.getHeadimgurl() != null && userInfoToJson.getHeadimgurl().trim().length() > 0;
				boolean nameBoolean = userInfoToJson.getNickname() != null && userInfoToJson.getNickname().trim().length() > 0;
				if (systemUser != null) {
					// 为了防止关联数据更新错误，因为数据不更改客户端可能不传递
					if (userInfoToJson.getHeadimgurl() == null || userInfoToJson.getHeadimgurl().trim().length() < 1) {
						if (systemUser.getHeadimgurl() != null && systemUser.getHeadimgurl().trim().length() > 2) {
							userInfoToJson.setHeadimgurl(systemUser.getHeadimgurl());
						} else {
							userInfoToJson.setHeadimgurl("-1");
						}

					}
					if (userInfoToJson.getNickname() == null || userInfoToJson.getNickname().trim().length() < 1) {
						if (systemUser.getNickname() != null && !systemUser.getNickname().trim().equals("-1") && systemUser.getNickname().trim().length() > 0) {
							userInfoToJson.setNickname(systemUser.getNickname());
						} else {
							userInfoToJson.setNickname("-1");
						}

					}
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
					if (userInfoToJson.getPersonalSignature() == null || userInfoToJson.getPersonalSignature().trim().equals("")) {
						userInfoToJson.setPersonalSignature("-1");
					}
					systemUser.setPersonalSignature(userInfoToJson.getPersonalSignature());
					logger.debug("保存的头像={}", systemUser.getHeadimgurl());
					systemUserService.putUserInfo(systemUser);

					// 如果修改头像或昵称，那么要修改相关缓存表的数据
					if (imgBoolean || nameBoolean) {
						// 更新好友数据
						qxFollowService.updateFollowInfo(_uid, userInfoToJson.getNickname(), userInfoToJson.getHeadimgurl());
						// 更新消息中的用户信息
						dynamicMsgService.updateUserInfo(_uid, userInfoToJson.getNickname(), userInfoToJson.getHeadimgurl());
					}

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
				e.printStackTrace();
				logger.debug("更新用户失败", e.getMessage());
				backInfo.setStateCode(Global.int300302);
				backInfo.setRetMsg(Global.str300302);
			}
		}
		return backInfo;
	}

	/**
	 * 上传/屏蔽通信录
	 * 
	 * @param userInfoToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/shieldAddressList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo uploadAddressList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// 是否屏蔽通信录0-否1-是
		String isShieldStr = map.get("isShield");
		String uid = map.get("uid");
		// 通讯录,修改屏蔽状态的时候addressList传空字符串
		String addressList = map.get("addressList");

		BaseBackInfo backInfo = new BaseBackInfo();

		// 如果用户名为空则直接返回
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32 || StrUtil.isBlank(isShieldStr) || StrUtil.isBlank(addressList)) {
			logger.debug("UserInfoController - uploadAddressList -  参数错误或为空");
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			try {
				int isShield = Integer.parseInt(isShieldStr);
				// 上传/屏蔽通信录
				systemUserService.shieldAddressList(uid, addressList, isShield);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			} catch (Exception e) {
				logger.debug("更新用户失败", e.getMessage());
				backInfo.setStateCode(Global.int300302);
				backInfo.setRetMsg(Global.str300302);
			}
		}
		return backInfo;
	}

	/**
	 * 修改用户背景图
	 * 
	 * @param userInfoToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/uploadBackgroundImg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo uploadBackgroundImg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String backgroundImage = map.get("backgroundImage");
		String uid = map.get("uid");

		BaseBackInfo backInfo = new BaseBackInfo();

		// 如果用户名为空则直接返回
		if (StrUtil.isBlank(uid) || uid.trim().length() != 32 || StrUtil.isBlank(backgroundImage)) {
			logger.debug("UserInfoController - uploadAddressList -  参数错误或为空");
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 修改背景图
				systemUserService.uploadBackgroundImg(uid, backgroundImage);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
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
	 * 存储用户相册，存储多个
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	// @RequestMapping(value = "/v1/user/savePhoto", method =
	// RequestMethod.POST)
	// public @ResponseBody BaseBackInfo savePhoto(@RequestBody Map<String,
	// String> map, HttpServletRequest request, HttpServletResponse response) {
	//
	// String uid = map.get("uid");
	// String pics = map.get("pics");
	//
	// BaseBackInfo backInfo = new BaseBackInfo();
	// if (StrUtil.isBlank(uid) || StrUtil.isBlank(pics)) {
	// BaseBackInfo info = new BaseBackInfo();
	// info.setStateCode(Global.int300209);
	// info.setRetMsg(Global.str300209);
	// return info;
	// }
	// try {
	// String[] picStrings = pics.split(",");
	// for (String pic : picStrings) {
	// // 存储相册
	// systemUserService.savePhoto(uid, pic);
	// }
	// backInfo.setStateCode(Global.intYES);
	// backInfo.setRetMsg(Global.SUCCESS);
	// } catch (Exception e) {
	// logger.debug("[" +
	// Thread.currentThread().getStackTrace()[1].getClassName() + " - " +
	// Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]",
	// e.getMessage());
	// backInfo.setRetMsg(Global.ERROR);
	// backInfo.setStateCode(Global.intNO);
	// }
	// return backInfo;
	// }

	/**
	 * 存储用户相册
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/savePhoto", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo savePhoto(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String pic = map.get("pic");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(pic)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {

			UserPic userPic = new UserPic(uid, pic);
			// 用户相册-存储单张图片
			systemUserService.saveSinglePhoto(userPic);
			backInfo.setItem(userPic);
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
	 * 删除用户相册
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/delPhoto", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delPhoto(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String picIdsStr = map.get("picIds");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(picIdsStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			String[] picIds = picIdsStr.split(",");
			for (String pid : picIds) {
				// 删除相册
				int picId = Integer.parseInt(pid);
				systemUserService.delPhoto(picId);
			}
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
	 * 获取用户相册 最新200张，最多200张
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userPics", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userPics(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		DataBackInfo<UserPic> backInfo = new DataBackInfo<>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 获取用户相册
			List<UserPic> pics = systemUserService.userPics(uid);
			backInfo.setData(pics);
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
	 * 上拉获取20张
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/userUpPics", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userUpPics(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String picIdStr = map.get("picId");

		DataBackInfo<UserPic> backInfo = new DataBackInfo<>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(picIdStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int picId = Integer.parseInt(picIdStr);
			// 上拉获取用户相册
			List<UserPic> pics = systemUserService.userUpPics(uid, picId);
			backInfo.setData(pics);
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
		MainPageBackInfo<DynamicMsgForService> backInfo = new MainPageBackInfo<DynamicMsgForService>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			List<DynamicMsgForService> msgs = dynamicMsgService.userLatestMsg(uid);
			backInfo.setData(msgs);
			SystemUser user = systemUserService.findByUid(uid);
			backInfo.setHeadimgurl(user.getHeadimgurl());
			backInfo.setNickname(user.getNickname());
			backInfo.setuNum(user.getuNum());
			backInfo.setBackgroundImage(user.getBackgroundImage());
			backInfo.setSex(user.getSex());
			// 获取用户消息总数
			backInfo.setMsgNum(dynamicMsgService.getMsgNum(uid));
			backInfo.setPersonalSignature(user.getPersonalSignature());
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
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