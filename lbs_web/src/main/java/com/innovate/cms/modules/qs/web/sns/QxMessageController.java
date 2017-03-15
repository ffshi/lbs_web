/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

import java.util.Date;
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
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.entity.sns.Message;
import com.innovate.cms.modules.qs.entity.sns.QxMessage;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.sns.QxMessageService;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;

/**
 * 留言表Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxMessageController extends BaseController {

	@Autowired
	private QxMessageService qxMessageService;
	@Autowired
	private QxUserMsgService qxUserMsgService;

	@Autowired
	private QxPushInfoService qxPushInfoService;

	@RequestMapping(value = "/v1/sns/saveMessage", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo saveMessage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		final String toId = map.get("toId");
		final String toName = map.get("toName");
		final String fromId = map.get("fromId");
		final String fromImg = map.get("fromImg");
		final String fromName = map.get("fromName");
		final String fromContent = map.get("fromContent");
		final String funType = StrUtil.isBlank(map.get("funType")) ? "1" : map.get("funType");
		ItemBackInfo messageBackInfo = new ItemBackInfo();

		// 简单校验
		if (StrUtil.isBlank(toId) || StrUtil.isBlank(toName) || StrUtil.isBlank(fromId) || StrUtil.isBlank(fromImg) || StrUtil.isBlank(fromName) || StrUtil.isBlank(fromContent)) {
			messageBackInfo.setStateCode(Global.int300209);
			messageBackInfo.setRetMsg(Global.str300209);
			return messageBackInfo;
		}
		// 判断是否是自己给自己留言
		if (toId.equals(fromId)) {
			messageBackInfo.setStateCode(Global.int300213);
			messageBackInfo.setRetMsg(Global.str300213);
			return messageBackInfo;
		}
		String msid = "";
		try {
			QxMessage message = new QxMessage();
			Date now = new Date();
			msid = IdGen.uuid();
			message.setMsid(msid);
			message.setToId(toId);
			message.setToName(toName);
			message.setFromId(fromId);
			message.setFromImg(fromImg);
			message.setFromName(fromName);
			message.setFromContent(fromContent);
			message.setFunType(funType);
			message.setCreateDate(now);
			qxMessageService.saveMessage(message);

			Message messageInfo = new Message(msid, now);
			messageBackInfo.setItem(messageInfo);
			messageBackInfo.setStateCode(Global.intYES);
			messageBackInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxMessageController - saveMessage()接口报错：{}]", e.getMessage());
			messageBackInfo.setStateCode(Global.int300302);
			messageBackInfo.setRetMsg(Global.str300302);
		}
		// 给被留言者推送留言信息
		QxUserMsg msg = new QxUserMsg();
		msg.setUid(toId);
		msg.setMsgName(fromName);
		msg.setMsgImg(fromImg);
		msg.setMsgType(Global.MESSAGE_MSG);
		msg.setMsgTypeName(Global.MESSAGE_MSG_NAME);
		msg.setMsgJumpId(toId);
		msg.setMsgContent1(fromContent);
		msg.setCreateDate(new Date());
		try {
			qxUserMsgService.saveUserMsg(msg);
		} catch (Exception e) {
			logger.debug("[QxMessageController - qxUserMsgService.saveUserMsg(msg)方法报错：{}]", e.getMessage());
		}

		// final String pushMsid = msid;
		// 推送
		new Thread(new Runnable() {
			@Override
			public void run() {
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(toId);

				if (null != device && null != device.getDeviceId()) {
					// PushClient.PushNoticeToiOS(PushGlobal.device,
					// device.getDeviceId(), "title", "body", "summery",
					// extraParameter"{\"uid\":\"4aa4e5104fda417a843e0fe91a2ecde1\",\"gid\":\"xxxxxxxxxxxx\"}");
					PushContent pushContent = new PushContent();
					pushContent.setDeviceType(device.getDeviceType());
					pushContent.setTargetValue(device.getDeviceId());

					// 设置内容，可以不设置
					pushContent.setBody(fromName + "给你留言:" + fromContent);
					// 摘要，描述，通知栏要展示的内容展示
					pushContent.setSummary(fromName + "给你留言:" + fromContent);

					// 设置自定义json扩展属性
					JsonObject extParameters = new JsonObject();
					extParameters.addProperty("jumpId", toId);
					extParameters.addProperty("type", PushContent.LevaeMessage);

					// extParameters.addProperty("uid", toId);
					// extParameters.addProperty("umid", "消息id-留言/回复/关注");
					// extParameters.addProperty("gid", "someone做了您足迹内的any专题");
					// 留言id
					// extParameters.addProperty("msid", pushMsid);

					pushContent.setExtParameters(extParameters);
					PushClient.pushContentNotice(pushContent);

				}
			}
		}).start();

		return messageBackInfo;
	}

}