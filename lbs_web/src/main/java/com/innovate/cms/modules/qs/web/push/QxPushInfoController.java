/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.push;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.google.gson.JsonObject;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.push.PushContent2DB;
import com.innovate.cms.modules.qs.entity.msg.NoticeUserForService;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;

/**
 * 推送管理Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxPushInfoController extends BaseController {

	@Autowired
	private QxPushInfoService qxPushInfoService;

	/**
	 * 
	 * @Title: addDevice
	 * @Description: 新增推送注册设备
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 BackInfo<?>
	 *
	 */
	@RequestMapping(value = "/v1/addDevice", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo addDevice(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo backInfo = new BaseBackInfo();
		String uid = map.get("uid"); // 用户ID
		String account = map.get("account"); // 用户别名
		String deviceId = map.get("deviceId"); // 设备唯一ID
		String deviceType = map.get("deviceType"); // 设备类型0:iOS设备,1:Andriod设备
		String tagAppVersion = map.get("tagAppVersion"); // 设备版本标签
		logger.debug("[uid={} account={} deviceId={} deviceType={} tagAppVersion={}]", uid, account, deviceId, deviceType, tagAppVersion);
		/**
		 * 设备唯一ID ,设备类型, 设备版本 必输
		 */
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(deviceId) || StrUtil.isBlank(deviceType) || StrUtil.isBlank(tagAppVersion) || !StrUtil.isNum(deviceType)) {
			// logger.debug("[StrUtil.isBlank(deviceId)={} StrUtil.isBlank(deviceType)={} StrUtil.isBlank(tagAppVersion)={} !StrUtil.isNum(deviceType)={} ]",StrUtil.isBlank(deviceId),StrUtil.isBlank(deviceType),StrUtil.isBlank(tagAppVersion),!StrUtil.isNum(deviceType));
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			account = ""; // 给默认值
			try {
				// 是否更新 默认不更新
				boolean isUpdate = false;
				// 先根据设备ID查询是否有该设备
				QxPushInfo pushInfo = qxPushInfoService.getInfoByDevID(deviceId);
				// 如果设备为空新增一条记录，否则更新一条记录
				if (null == pushInfo) {
					pushInfo = new QxPushInfo();
					pushInfo.setDeviceId(deviceId);
					pushInfo.setAccount(account);
					pushInfo.setUid(uid);
				} else {
					isUpdate = true;
					pushInfo.setUid(uid);
					pushInfo.setAccount(account);
				}
				pushInfo.setDeviceType(Integer.parseInt(deviceType)); // 必须字段
				pushInfo.setTagAppVersion(tagAppVersion); // 必须字段

				// 如果有更新 没有 新增
				qxPushInfoService.saveByDevID(pushInfo, isUpdate);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			} catch (Exception e) {
				logger.debug("[addDevice]" + e.getMessage());
				backInfo.setStateCode(Global.int300302);
				backInfo.setRetMsg(Global.str300302);
			}

		}
		return backInfo;
	}

	@RequestMapping(value = "/v1/pushAll", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo pushAll(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// String umid = map.get("umid");
		// 摘要，描述，通知栏要展示的内容展示
		String summary = map.get("summary");
		String dateStr = map.get("dateStr");
		dateStr = dateStr.replace("/", " ");
		// //用户自定义1 跳到用户主页
		// public static final int toUserMainpage = 5;
		// 用户自定义1 跳到指定专题
		// public static final int toQuestion = 6;
		// 用户自定义推送类型
		String type = map.get("type");
		String jumpId = map.get("jumpId");
		String template = map.get("template");

		// 时间格式：2016-7-27 13:30:30
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = null;

		// String umid = map.get("umid");
		// String umid = map.get("umid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(dateStr) || StrUtil.isBlank(summary) || StrUtil.isBlank(type) || StrUtil.isBlank(jumpId)) {
			BaseBackInfo info = new BaseBackInfo();

			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(" timeFormat error");
			return info;
		}
		try {

			QxPushInfo device = new QxPushInfo();
			device.setDeviceType(0);
			device.setDeviceId("4aa4e5104fda417a843e0fe91a2ecde1");
			PushContent pushContent = new PushContent();

			// 设置内容，可以不设置
			pushContent.setBody(summary);
			// 摘要，描述，通知栏要展示的内容展示
			pushContent.setSummary(summary);

			// 设置自定义json扩展属性
			JsonObject extParameters = new JsonObject();
			extParameters.addProperty("type", Integer.parseInt(type));
			extParameters.addProperty("jumpId", jumpId);
			if (template != null) {
				extParameters.addProperty("template", template);
			}

			pushContent.setExtParameters(extParameters);
			// PushClient.pushContentNotice(pushContent);
			// 定时推送通知
			PushClient.pushNoticeToAll(pushContent, date);

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
	 * IM聊天信息推送
	 * 
	 * 消息格式有待5期新需求确认
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/imPush", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo impush(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 推送人
		final String uid = map.get("uid");
		// String umid = map.get("umid");
		// 摘要，描述，通知栏要展示的内容展示
		String summary = map.get("summary");
		// //用户自定义1 跳到用户主页
		// public static final int toUserMainpage = 5;
		// 用户自定义1 跳到指定专题
		// public static final int toQuestion = 6;
		// 打开应用首页 打开首页:7
		String type = map.get("type");
		// 只需要打开app，可以不需要jumpID
		// String jumpId = map.get("jumpId");

		// String template = map.get("template");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(summary) || StrUtil.isBlank(type) || StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();

			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		try {
			// 优化 后期改为别名推送 uid（别名）绑定设备
			QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);

			PushContent pushContent = new PushContent();
			pushContent.setDeviceType(device.getDeviceType());
			pushContent.setTargetValue(device.getDeviceId());

			// 设置内容，可以不设置
			pushContent.setBody(summary);
			// 摘要，描述，通知栏要展示的内容展示
			pushContent.setSummary(summary);

			// 设置自定义json扩展属性，等待新推送信息格式
			JsonObject extParameters = new JsonObject();
			extParameters.addProperty("type", Integer.parseInt(type));
			// extParameters.addProperty("jumpId", jumpId);
			// if (template != null) {
			// extParameters.addProperty("template", template);
			// }

			pushContent.setExtParameters(extParameters);
			// PushClient.pushContentNotice(pushContent);
			// 定时推送通知
			PushClient.pushContentNotice(pushContent);

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
	 * 普通推送调用接口，注意type和jumpId的配置
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/pushInfo", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo pushInfo(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 推送人
		final String uid = map.get("uid");
		// String umid = map.get("umid");
		// 摘要，描述，通知栏要展示的内容展示
		String summary = map.get("summary");
		// //用户自定义1 跳到用户主页
		// public static final int toUserMainpage = 5;
		// 用户自定义1 跳到指定专题
		// public static final int toQuestion = 6;
		// 打开应用首页 打开首页:7
		String type = map.get("type");
		// 只需要打开app，可以不需要jumpID
		String jumpId = map.get("jumpId");

		// 如果跳转到专题的时候，必须要
		String template = map.get("template");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(summary) || StrUtil.isBlank(type) || StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();

			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		try {
			// 优化 后期改为别名推送 uid（别名）绑定设备
			QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);

			PushContent pushContent = new PushContent();
			pushContent.setDeviceType(device.getDeviceType());
			pushContent.setTargetValue(device.getDeviceId());

			// 设置内容，可以不设置
			pushContent.setBody(summary);
			// 摘要，描述，通知栏要展示的内容展示
			pushContent.setSummary(summary);

			// 设置自定义json扩展属性，等待新推送信息格式
			JsonObject extParameters = new JsonObject();
			extParameters.addProperty("type", Integer.parseInt(type));
			if (null != jumpId) {
				extParameters.addProperty("jumpId", jumpId);
			}
			if (null != template) {
				extParameters.addProperty("template", template);
			}

			pushContent.setExtParameters(extParameters);
			// PushClient.pushContentNotice(pushContent);
			// 定时推送通知
			PushClient.pushContentNotice(pushContent);

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
	 * 批量推送 普通推送调用接口，注意type和jumpId的配置
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/pushInfos", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo pushInfos(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 那个报名类消息相关的推送
		final String midStr = map.get("mid");
		// 发通知的人
		final String puid = map.get("puid");
		// 接收人，接到通知的人,可能是一批人用英文逗号分开
		final String uidStr = map.get("uid");
		// String umid = map.get("umid");
		// 摘要，描述，通知栏要展示的内容展示
		String summary = map.get("summary");
		// //用户自定义1 跳到用户主页
		// public static final int toUserMainpage = 5;
		// 用户自定义1 跳到指定专题
		// public static final int toQuestion = 6;
		// 打开应用首页 打开首页:7
		String ptype = map.get("ptype");
		// 只需要打开app，可以不需要jumpID
		String jumpId = map.get("jumpId");

		// 如果跳转到专题的时候，必须要
		String template = map.get("template");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(summary) || StrUtil.isBlank(ptype) || StrUtil.isBlank(uidStr)) {
			BaseBackInfo info = new BaseBackInfo();

			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		try {
			String[] uids = uidStr.split(",");
			long massId = System.nanoTime();
			for (String uid : uids) {
				PushContent2DB pushContent2DB = new PushContent2DB(puid, uid, summary, Integer.parseInt(ptype), Integer.parseInt(midStr), jumpId, massId);
				// 保存推送记录
				qxPushInfoService.savePushContent(pushContent2DB);
				// 优化 后期改为别名推送 uid（别名）绑定设备
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);

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
					extParameters.addProperty("type", Integer.parseInt(ptype));
					if (null != jumpId) {
						extParameters.addProperty("jumpId", jumpId);
					}
					if (null != template) {
						extParameters.addProperty("template", template);
					}

					pushContent.setExtParameters(extParameters);
					// PushClient.pushContentNotice(pushContent);
					// 定时推送通知
					PushClient.pushContentNotice(pushContent);
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
	 * 生产环境推送
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/pushInfosProduct", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo pushInfosProduct(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 那个报名类消息相关的推送
		final String midStr = map.get("mid");
		// 发通知的人
		final String puid = map.get("puid");
		// 接收人，接到通知的人,可能是一批人用英文逗号分开
		final String uidStr = map.get("uid");
		// String umid = map.get("umid");
		// 摘要，描述，通知栏要展示的内容展示
		String summary = map.get("summary");
		// //用户自定义1 跳到用户主页
		// public static final int toUserMainpage = 5;
		// 用户自定义1 跳到指定专题
		// public static final int toQuestion = 6;
		// 打开应用首页 打开首页:7
		String ptype = map.get("ptype");
		// 只需要打开app，可以不需要jumpID
		String jumpId = map.get("jumpId");

		// 如果跳转到专题的时候，必须要
		String template = map.get("template");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(summary) || StrUtil.isBlank(ptype) || StrUtil.isBlank(uidStr)) {
			BaseBackInfo info = new BaseBackInfo();

			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		try {
			String[] uids = uidStr.split(",");
			long massId = System.nanoTime();
			for (String uid : uids) {
				PushContent2DB pushContent2DB = new PushContent2DB(puid, uid, summary, Integer.parseInt(ptype), Integer.parseInt(midStr), jumpId, massId);
				// 保存推送记录
				qxPushInfoService.savePushContent(pushContent2DB);
				// 优化 后期改为别名推送 uid（别名）绑定设备
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);

				PushContent pushContent = new PushContent();
				pushContent.setDeviceType(device.getDeviceType());
				pushContent.setTargetValue(device.getDeviceId());

				// 设置内容，可以不设置
				pushContent.setBody(summary);
				// 摘要，描述，通知栏要展示的内容展示
				pushContent.setSummary(summary);

				// 设置自定义json扩展属性，等待新推送信息格式
				JsonObject extParameters = new JsonObject();
				extParameters.addProperty("type", Integer.parseInt(ptype));
				if (null != jumpId) {
					extParameters.addProperty("jumpId", jumpId);
				}
				if (null != template) {
					extParameters.addProperty("template", template);
				}

				pushContent.setExtParameters(extParameters);
				// 推送到生产环境
				PushClient.pushContentNoticeProduct(pushContent);
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
	 * 获取用户通知列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/userNotices", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userNotices(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		DataBackInfo<PushContent2DB> backInfo = new DataBackInfo<>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 获取用户通知列表
			List<PushContent2DB> data = qxPushInfoService.userNotices(uid);
			backInfo.setData(data);
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
	 * 上拉获取用户通知列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/userUpNotices", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userUpNotices(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String pushConIdStr = map.get("pushConId");

		DataBackInfo<PushContent2DB> backInfo = new DataBackInfo<>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(pushConIdStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int pushConId = Integer.parseInt(pushConIdStr);
			// 上拉获取用户通知列表
			List<PushContent2DB> data = qxPushInfoService.userUpNotices(uid, pushConId);
			backInfo.setData(data);
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
	 * 获取消息报名通知用户列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msgNoticeUsers", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo msgNoticeUsers(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String massPushIdStr = map.get("massPushId");

		DataBackInfo<NoticeUserForService> backInfo = new DataBackInfo<>();
		if (StrUtil.isBlank(massPushIdStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			long massPushId = Long.parseLong(massPushIdStr);
			// 获取消息报名通知用户列表
			List<NoticeUserForService> data = qxPushInfoService.msgNoticeUsers(massPushId);
			backInfo.setData(data);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

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