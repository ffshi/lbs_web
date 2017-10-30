/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

import java.util.ArrayList;
import java.util.Date;
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

import com.google.gson.JsonObject;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.IdGen;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.push.PushContent2DB;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.entity.sns.IsFriend;
import com.innovate.cms.modules.qs.entity.sns.QxFollow;
import com.innovate.cms.modules.qs.entity.sns.QxFriend;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.sns.QxFollowService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;
import com.innovate.cms.modules.robot.RobotUtil;

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
	private QxPushInfoService qxPushInfoService;

	/**
	 * 添加好友/删除好友 flag：0：add 1:del
	 * 
	 * uid关注followUid，
	 * 
	 * 关注后： uid多一个followUid的朋友 followUid不是uid的好友
	 * 
	 * 应该向followUid推送一条被加好友的信息
	 * 
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
					qxFollow.setFollowName(fuser.getNickname() == null ? "-1" : fuser.getNickname());
					qxFollow.setFollowImg(fuser.getHeadimgurl() == null ? "-1" : fuser.getHeadimgurl());
					qxFollow.setFollowUnionid(fuser.getUnionid() == null ? "-1" : fuser.getUnionid());

					qxFollow.setName(user.getNickname() == null ? "-1" : user.getNickname());
					qxFollow.setImg(user.getHeadimgurl() == null ? "-1" : user.getHeadimgurl());
					qxFollow.setUnionid(user.getUnionid() == null ? "-1" : user.getUnionid());
					qxFollowService.saveFollow(qxFollow);
				} else {
					// 曾经添加过,但是删除了，现在再次添加
					qxFollowService.updateFollow(qxFollow);
				}

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
	 * 增加机器人关注
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/robotFollow", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo robotFollow(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String followUid = map.get("uid");
		String numStr = map.get("num");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(followUid) || StrUtil.isBlank(numStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// // 添加删除标记 0：添加 1：删除
			// final String flag = map.get("flag");
			// // 用户id
			// final String uid = map.get("uid");
			// // 被关注用户id
			// final String followUid = map.get("followUid");

			int num = Integer.parseInt(numStr);
			// 随机num个机器人
			List<String> uids = RobotUtil.radomNumUid(num);
			for (int i = 0; i < num; i++) {
				QxFollow qxFollow = new QxFollow();
				qxFollow.setUid(uids.get(i));
				qxFollow.setFollowUid(followUid);
				qxFollow.setCreateDate(new Date());
				qxFollow.setDelFlag("0");
				SystemUser user = null;

				// 添加好友
				int exist = qxFollowService.isExist(qxFollow);
				user = systemUserService.findByUid(uids.get(i));
				if (exist == 0) {
					// 没添加过该好友
					SystemUser fuser = systemUserService.findByUid(followUid);
					qxFollow.setFlid(IdGen.uuid());
					qxFollow.setFollowName(fuser.getNickname() == null ? "-1" : fuser.getNickname());
					qxFollow.setFollowImg(fuser.getHeadimgurl() == null ? "-1" : fuser.getHeadimgurl());
					qxFollow.setFollowUnionid(fuser.getUnionid() == null ? "-1" : fuser.getUnionid());

					qxFollow.setName(user.getNickname() == null ? "-1" : user.getNickname());
					qxFollow.setImg(user.getHeadimgurl() == null ? "-1" : user.getHeadimgurl());
					qxFollow.setUnionid(user.getUnionid() == null ? "-1" : user.getUnionid());
					qxFollowService.saveFollow(qxFollow);

					
					//添加推送
					String summary = user.getNickname() + "关注了你";
					PushContent2DB pushContent2DB = new PushContent2DB(user.getUid(), followUid, summary, 5, -1, followUid, 0L);
					// 保存推送记录到服务器
					qxPushInfoService.savePushContent(pushContent2DB);
					// 优化 后期改为别名推送 uid（别名）绑定设备
					QxPushInfo device = qxPushInfoService.getPushInfoByUid(followUid);
					if (null != device && null != device.getDeviceId() && device.getDeviceId().length() > 0) {
						PushContent pushContent = new PushContent();
						pushContent.setDeviceType(device.getDeviceType());
						pushContent.setTargetValue(device.getDeviceId());

						// 设置内容，可以不设置
						pushContent.setBody(summary);
						// 摘要，描述，通知栏要展示的内容展示
						pushContent.setSummary(summary);

						// 设置自定义json扩展属性，等待新推送信息格式
						JsonObject extParameters = new JsonObject();
						extParameters.addProperty("type", 5);
						extParameters.addProperty("jumpId", followUid);

						pushContent.setExtParameters(extParameters);
						// 推送到生产环境
						PushClient.pushContentNotice(pushContent);

					}
				}
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
	 * 增加机器人关注  线上推送版本
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/robotFollowProduct", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo robotFollowProduct(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String followUid = map.get("uid");
		String numStr = map.get("num");
		
		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(followUid) || StrUtil.isBlank(numStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// // 添加删除标记 0：添加 1：删除
			// final String flag = map.get("flag");
			// // 用户id
			// final String uid = map.get("uid");
			// // 被关注用户id
			// final String followUid = map.get("followUid");
			
			int num = Integer.parseInt(numStr);
			// 随机num个机器人
			List<String> uids = RobotUtil.radomNumUid(num);
			for (int i = 0; i < num; i++) {
				QxFollow qxFollow = new QxFollow();
				qxFollow.setUid(uids.get(i));
				qxFollow.setFollowUid(followUid);
				qxFollow.setCreateDate(new Date());
				qxFollow.setDelFlag("0");
				SystemUser user = null;
				
				// 添加好友
				int exist = qxFollowService.isExist(qxFollow);
				user = systemUserService.findByUid(uids.get(i));
				if (exist == 0) {
					// 没添加过该好友
					SystemUser fuser = systemUserService.findByUid(followUid);
					qxFollow.setFlid(IdGen.uuid());
					qxFollow.setFollowName(fuser.getNickname() == null ? "-1" : fuser.getNickname());
					qxFollow.setFollowImg(fuser.getHeadimgurl() == null ? "-1" : fuser.getHeadimgurl());
					qxFollow.setFollowUnionid(fuser.getUnionid() == null ? "-1" : fuser.getUnionid());
					
					qxFollow.setName(user.getNickname() == null ? "-1" : user.getNickname());
					qxFollow.setImg(user.getHeadimgurl() == null ? "-1" : user.getHeadimgurl());
					qxFollow.setUnionid(user.getUnionid() == null ? "-1" : user.getUnionid());
					qxFollowService.saveFollow(qxFollow);
					
					
					//添加推送
					String summary = user.getNickname() + "关注了你";
					PushContent2DB pushContent2DB = new PushContent2DB(user.getUid(), followUid, summary, 5, -1, followUid, 0L);
					// 保存推送记录
					qxPushInfoService.savePushContent(pushContent2DB);
					// 优化 后期改为别名推送 uid（别名）绑定设备
					QxPushInfo device = qxPushInfoService.getPushInfoByUid(followUid);
					if (null != device && null != device.getDeviceId() && device.getDeviceId().length() > 0) {
						PushContent pushContent = new PushContent();
						pushContent.setDeviceType(device.getDeviceType());
						pushContent.setTargetValue(device.getDeviceId());
						
						// 设置内容，可以不设置
						pushContent.setBody(summary);
						// 摘要，描述，通知栏要展示的内容展示
						pushContent.setSummary(summary);
						
						// 设置自定义json扩展属性，等待新推送信息格式
						JsonObject extParameters = new JsonObject();
						extParameters.addProperty("type", 5);
						extParameters.addProperty("jumpId", followUid);
						
						pushContent.setExtParameters(extParameters);
						// 推送到生产环境
						PushClient.pushContentNoticeProduct(pushContent);
						
					}
				}
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

}