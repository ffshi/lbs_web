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
import com.innovate.cms.common.mybatis.Page;
import com.innovate.cms.common.utils.IdGen;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.MessageToJson;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.entity.sns.DelReply;
import com.innovate.cms.modules.qs.entity.sns.QxMessage;
import com.innovate.cms.modules.qs.entity.sns.QxMessagePost;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.entity.sns.Reply;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.sns.QxMessagePostService;
import com.innovate.cms.modules.qs.service.sns.QxMessageService;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;

/**
 * 回复表Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxMessagePostController extends BaseController {

	@Autowired
	private QxMessagePostService qxMessagePostService;
	@Autowired
	private QxMessageService qxMessageService;
	@Autowired
	private QxUserMsgService qxUserMsgService;

	@Autowired
	private QxPushInfoService qxPushInfoService;

	// private int NO_PERMISSION = -1;

	/**
	 * 保存留言回复接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/saveReplyMessage", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo saveReplyMessage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		final String msid = map.get("msid");
		final String fromReplyUid = map.get("fromReplyUid");
		final String fromReplyName = map.get("fromReplyName");
		final String fromReplyImg = map.get("fromReplyImg");
		final String toReplyUid = map.get("toReplyUid");
		final String toReplyName = map.get("toReplyName");
		final String replyContent = map.get("replyContent");
		ItemBackInfo replyBackInfo = new ItemBackInfo();
		// 简单参数校验
		if (StrUtil.isBlank(msid) || StrUtil.isBlank(fromReplyUid) || StrUtil.isBlank(fromReplyName) || StrUtil.isBlank(fromReplyImg) || StrUtil.isBlank(toReplyUid) || StrUtil.isBlank(toReplyName) || StrUtil.isBlank(replyContent)) {
			replyBackInfo.setStateCode(Global.int300209);
			replyBackInfo.setRetMsg(Global.str300209);
			return replyBackInfo;
		}
		// 判断是否是自己给自己回复
		if (fromReplyUid.equals(toReplyUid)) {
			replyBackInfo.setStateCode(Global.int300214);
			replyBackInfo.setRetMsg(Global.str300214);
			return replyBackInfo;
		}

		String poid = "";
		QxMessage message = null;
		try {
			// 判断留言是否存在
			message = qxMessagePostService.getMessageById(msid);
			if (message == null) {
				replyBackInfo.setStateCode(Global.int300211);
				replyBackInfo.setRetMsg(Global.str300211);
				return replyBackInfo;
			}
			QxMessagePost messagePost = new QxMessagePost();
			Date now = new Date();
			poid = IdGen.uuid();
			messagePost.setPoid(poid);
			messagePost.setMsid(msid);
			messagePost.setFromReplyUid(fromReplyUid);
			messagePost.setFromReplyName(fromReplyName);
			messagePost.setFromReplyImg(fromReplyImg);
			messagePost.setToReplyUid(toReplyUid);
			messagePost.setToReplyName(toReplyName);
			messagePost.setReplyContent(replyContent);
			messagePost.setCreateDate(now);

			qxMessagePostService.saveReplyMessage(messagePost);

			Reply replyInfo = new Reply(poid, now);
			replyBackInfo.setItem(replyInfo);
			replyBackInfo.setStateCode(Global.intYES);
			replyBackInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxMessagePostController - saveReplyMessage()接口报错：{}]", e.getMessage());
			replyBackInfo.setStateCode(Global.int300302);
			replyBackInfo.setRetMsg(Global.str300302);
		}

		// 给被回复者推送回复信息
		QxUserMsg msg = new QxUserMsg();
		msg.setUid(toReplyUid);
		msg.setMsgName(fromReplyName);
		msg.setMsgImg(fromReplyImg);
		msg.setMsgType("2");
		msg.setMsgTypeName("回复");
		msg.setMsgJumpId(message.getToId());
		msg.setMsgContent1(replyContent);
		msg.setCreateDate(new Date());
		try {
			qxUserMsgService.saveUserMsg(msg);
		} catch (Exception e) {
			logger.debug("[QxMessagePostController - qxUserMsgService.saveUserMsg(msg)方法报错：{}]", e.getMessage());
		}

		// final String pushPoid = poid;
		// 推送
		new Thread(new Runnable() {
			@Override
			public void run() {
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(toReplyUid);
				// 获取留言所在用户的id
				QxMessage qxMessage = qxMessageService.get(msid);

				if (null != device && null != device.getDeviceId()) {
					// PushClient.PushNoticeToiOS(PushGlobal.device,
					// device.getDeviceId(), "title", "body", "summery",
					// extraParameter"{\"uid\":\"4aa4e5104fda417a843e0fe91a2ecde1\",\"gid\":\"xxxxxxxxxxxx\"}");
					PushContent pushContent = new PushContent();
					pushContent.setDeviceType(device.getDeviceType());
					pushContent.setTargetValue(device.getDeviceId());

					// 设置内容，可以不设置
					pushContent.setBody(fromReplyName + "回复你:" + replyContent);
					// 摘要，描述，通知栏要展示的内容展示
					pushContent.setSummary(fromReplyName + "回复你:" + replyContent);

					// 设置自定义json扩展属性
					JsonObject extParameters = new JsonObject();
					extParameters.addProperty("type", PushContent.Replay);
					extParameters.addProperty("jumpId", qxMessage.getToId());
					// extParameters.addProperty("umid", "消息id-留言/回复/关注");
					// extParameters.addProperty("gid", "someone做了您足迹内的any专题");
					// 留言id
					// extParameters.addProperty("msid", msid);
					// //回复id
					// extParameters.addProperty("poid", pushPoid);
					// extParameters.addProperty("uid", toReplyUid);

					pushContent.setExtParameters(extParameters);
					PushClient.pushContentNotice(pushContent);

				}
			}
		}).start();

		return replyBackInfo;
	}

	/**
	 * 获取留言板列表接口
	 * 
	 * @param map
	 *            (用户uid)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getMessageList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<MessageToJson> getMessageList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String funType = StrUtil.isBlank(map.get("funType")) ? "1" : map.get("funType");
		String pageNo = StrUtil.isBlank(map.get("pageNo")) ? "1" : map.get("pageNo");
		String pageSize = StrUtil.isBlank(map.get("pageSize")) ? Global.getConfig("page.pageSize") : map.get("pageSize");
		DataBackInfo<MessageToJson> backInfo = new DataBackInfo<MessageToJson>();
		// 简单校验
		if (StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			Page<MessageToJson> page = null;
			// 如果不是正整数 则用系统默认 pageNo=1 pageSize=10 调用构造创建分页
			if (!StrUtil.isPositiveNum(pageNo) || !StrUtil.isPositiveNum(pageSize)) {
				page = new Page<MessageToJson>();
			} else {
				page = new Page<MessageToJson>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}

			List<MessageToJson> messageList = new ArrayList<MessageToJson>();
			int totalCount = 0;
			totalCount = qxMessagePostService.getCount(uid, funType);
			if (totalCount > 0) {
				page.setTotalCount(totalCount);
				messageList = qxMessagePostService.getMessageList(uid, funType, page);
			}
			backInfo.setData(messageList);
			backInfo.setTotalResult(totalCount);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxMessagePostController - getMessageList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return backInfo;
	}

	/**
	 * 删除某条留言或删除某条回复接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/delMessage", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delMessage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String objId = map.get("objId");
		String flag = map.get("flag");
		ItemBackInfo itemBackInfo = new ItemBackInfo();
		// 简单校验
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(objId) || StrUtil.isBlank(flag)) {
			itemBackInfo.setStateCode(Global.int300209);
			itemBackInfo.setRetMsg(Global.str300209);
			return itemBackInfo;
		}
		String result = null;
		try {
			switch (flag) {
			case "0": // 删除某条留言
				result = qxMessagePostService.delMessage(uid, objId);
				if ("".equals(result)) {
					itemBackInfo.setStateCode(Global.int300210);
					itemBackInfo.setRetMsg(Global.str300210);
				} else {
					itemBackInfo.setStateCode(Global.intYES);
					itemBackInfo.setRetMsg(Global.SUCCESS);
				}
				break;
			case "1": // 删除某条回复
				result = qxMessagePostService.delReply(uid, objId);
				if ("".equals(result)) {
					itemBackInfo.setStateCode(Global.int300212);
					itemBackInfo.setRetMsg(Global.str300212);
				} else {
					itemBackInfo.setStateCode(Global.intYES);
					itemBackInfo.setRetMsg(Global.SUCCESS);
					DelReply delReply = new DelReply(result);
					itemBackInfo.setItem(delReply);
				}
				break;

			default: // 参数值错误
				itemBackInfo.setStateCode(Global.int300209);
				itemBackInfo.setRetMsg(Global.str300209);
				break;
			}
		} catch (Exception e) {
			logger.debug("[QxMessagePostController - delMessage()接口报错：{}], id = " + objId + ", flag = " + flag, e.getMessage());
			itemBackInfo.setStateCode(Global.int300302);
			itemBackInfo.setRetMsg(Global.str300302);
		}
		return itemBackInfo;
	}

}