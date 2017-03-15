/**
 * 
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.entity.sns.FootprintComment;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintComment;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.sns.QxFootprintCommentService;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;

/**
 * 足迹评论控制层
 * @author hushasha
 * @date 2016年8月9日
 */
@Controller
@RequestMapping(value = "/api")
public class QxFootprintCommentController extends BaseController {
	@Autowired
	private QxFootprintCommentService qxFootprintCommentService;
	@Autowired
	private QxUserMsgService qxUserMsgService;
	@Autowired
	private QxPushInfoService qxPushInfoService;
	
	@RequestMapping(value = "/v1/sns/comment")
	public @ResponseBody ItemBackInfo saveComment(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String footid = map.get("footid");
		String uid = map.get("uid");
		String msgType = map.get("msgType");
		String fromCommentUid = map.get("fromCommentUid");
		String fromCommentName = map.get("fromCommentName");
		String fromCommentImg = map.get("fromCommentImg");
		String toCommentUid = map.get("toCommentUid");
		String toCommentName = map.get("toCommentName");
		String comment = map.get("comment");
		ItemBackInfo backInfo = new ItemBackInfo();
		//参数校验
		if(Strings.isNullOrEmpty(footid) || Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(msgType) || Strings.isNullOrEmpty(fromCommentUid) || Strings.isNullOrEmpty(fromCommentName) || 
				Strings.isNullOrEmpty(fromCommentImg) || Strings.isNullOrEmpty(toCommentUid) || Strings.isNullOrEmpty(toCommentName) || Strings.isNullOrEmpty(comment)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(uid.length() != 32 || fromCommentUid.length() != 32 || toCommentUid.length() != 32 || !StrUtil.isPositiveNum(footid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		
		String regex = "9|10";
		if(!ReUtil.isMatch(regex, msgType)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		
		QxFootprintComment qxFootprintComment = new QxFootprintComment();
		qxFootprintComment.setFootid(Integer.valueOf(footid));
		qxFootprintComment.setUid(uid);
		qxFootprintComment.setMsgType(msgType);
		qxFootprintComment.setFromCommentUid(fromCommentUid);
		qxFootprintComment.setFromCommentName(fromCommentName);
		qxFootprintComment.setFromCommentImg(fromCommentImg);
		qxFootprintComment.setToCommentUid(toCommentUid);
		qxFootprintComment.setToCommentName(toCommentName);
		qxFootprintComment.setComment(comment);
		qxFootprintComment.setCreateTime(new Date());
		try {
			qxFootprintCommentService.saveComment(qxFootprintComment);
			FootprintComment footprintComment = new FootprintComment();
			footprintComment.setFcid(qxFootprintComment.getFcid());
			backInfo.setItem(footprintComment);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch(Exception e) {
			logger.debug("[QxFootprintCommentController - saveComment()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		if(!fromCommentUid.equals(toCommentUid)) { //不是自己给自己评论时, 添加消息和推送
			//保存评论消息
			saveCommentMsg(uid, msgType, fromCommentName, fromCommentImg, toCommentUid, comment, footid);
			//推送
			pushCommentMsg(uid, toCommentUid, fromCommentName, Integer.valueOf(footid));
		}
		return backInfo;
	}
	
	/**
	 * 删除评论
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/delComment")
	public @ResponseBody BaseBackInfo delComment(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String fcid = map.get("fcid");
		String uid = map.get("uid");
		BaseBackInfo backInfo = new BaseBackInfo();
		//参数校验
		if(Strings.isNullOrEmpty(fcid) || Strings.isNullOrEmpty(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(uid.length() != 32 || !StrUtil.isPositiveNum(fcid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			String result = qxFootprintCommentService.delComment(uid, Integer.valueOf(fcid));
			if ("".equals(result)) {
				backInfo.setStateCode(Global.int300221);
				backInfo.setRetMsg(Global.str300221);
			} else {
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			}
		} catch(Exception e) {
			logger.debug("[QxFootprintCommentController - delComment()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}		
		return backInfo;
	}

	private void saveCommentMsg(String uid, String msgType, String fromCommentName, String fromCommentImg, String toCommentUid, String comment, String footid) {
		QxUserMsg msg = new QxUserMsg();
		msg.setUid(toCommentUid);
		//msg.setMsgUid(praiseUid);
		msg.setMsgName(fromCommentName);
		msg.setMsgImg(fromCommentImg);
		
		//判断是评论还是针对评论的回复
		if(Global.COMMENT_MSG.equals(msgType)) {
			msg.setMsgType(Global.COMMENT_MSG);
			msg.setMsgTypeName(Global.COMMENT_MSG_NAME);
		} else {
			msg.setMsgType(Global.COMMENT_REPLY_MSG);
			msg.setMsgTypeName(Global.COMMENT_REPLY_MSG_NAME);
		}

		//跳转到足迹详情页
		msg.setMsgJumpId(footid);
		msg.setMsgContent1(comment);
		msg.setCreateTime(new Date());
		try {
			qxUserMsgService.saveUserMsg(msg);
		} catch (Exception e) {
			logger.debug("[QxMessageController - qxUserMsgService.saveUserMsg(msg)方法报错：{}]", e.getMessage());
		}
	}
	
	/**
	 * 推送
	 * @param uid
	 * @param praiseName
	 */
	private void pushCommentMsg(final String uid, final String toCommentUid, final String fromCommentName, final int footid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(toCommentUid);

				if (null != device && null != device.getDeviceId()) {
					PushContent pushContent = new PushContent();
					pushContent.setDeviceType(device.getDeviceType());
					pushContent.setTargetValue(device.getDeviceId());
					//判断是评论还是针对评论的回复
					if(uid.equals(toCommentUid)) { //两者相等，则是评论
						// 设置内容，可以不设置
						pushContent.setBody(fromCommentName + Global.PUSH_COMMENT_MSG_CONTENT);
						// 摘要，描述，通知栏要展示的内容展示
						pushContent.setSummary(fromCommentName + Global.PUSH_COMMENT_MSG_CONTENT);
					} else { //针对评论的回复
						// 设置内容，可以不设置
						pushContent.setBody(fromCommentName + Global.PUSH_COMMENT_REPLY_MSG_CONTENT);
						// 摘要，描述，通知栏要展示的内容展示
						pushContent.setSummary(fromCommentName + Global.PUSH_COMMENT_REPLY_MSG_CONTENT);
					}
	

					// 设置自定义json扩展属性
					JsonObject extParameters = new JsonObject();
					extParameters.addProperty("jumpId", footid);
					extParameters.addProperty("type", PushContent.COMMENT);

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
	}
}
		