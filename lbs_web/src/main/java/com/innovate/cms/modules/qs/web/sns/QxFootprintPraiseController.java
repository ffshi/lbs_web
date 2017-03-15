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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintPraise;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.sns.QxFootprintPraiseService;
import com.innovate.cms.modules.qs.service.sns.QxFootprintService;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;

/**
 *点赞相关
 * @author hushasha
 * @date 2016年8月5日
 */

@Controller
@RequestMapping(value = "/api")
public class QxFootprintPraiseController extends BaseController {
	@Autowired
	private QxFootprintPraiseService qxFootprintPraiseService;
	@Autowired
	private QxUserMsgService qxUserMsgService;
	@Autowired
	private QxPushInfoService qxPushInfoService;
	@Autowired
	private QxFootprintService qxFootprintService;
	
	/**
	 * 点赞或者取消点赞
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/praiseOrCancel", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo saveOrDelPraise(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String flag = map.get("flag");
		final String uid = map.get("uid");
		String footid = map.get("footid");
		String praiseUid = map.get("praiseUid");
		final String praiseName = map.get("praiseName");
		String praiseImg = map.get("praiseImg");
		BaseBackInfo backInfo = new BaseBackInfo();
		// 简单校验
		if(Strings.isNullOrEmpty(flag) || Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(footid) || Strings.isNullOrEmpty(praiseUid) || Strings.isNullOrEmpty(praiseName) || Strings.isNullOrEmpty(praiseImg)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(!"0".equals(flag) && !"1".equals(flag)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(uid.length() != 32 || praiseUid.length() != 32 || !StrUtil.isPositiveNum(footid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			QxFootprintPraise qxFootprintPraise = new QxFootprintPraise();
			qxFootprintPraise.setFootid(Integer.valueOf(footid));
			qxFootprintPraise.setUid(uid);
			qxFootprintPraise.setPraiseUid(praiseUid);
			qxFootprintPraise.setPraiseName(praiseName);
			qxFootprintPraise.setCreateTime(new Date());
			qxFootprintPraise.setDelFlag(flag);
			if("0".equals(flag)) { //点赞	
				QxFootprintPraise praise = qxFootprintPraiseService.getPraise(Integer.valueOf(footid), uid, praiseUid);
				if(praise == null) { //点赞
					qxFootprintPraiseService.savePraise(qxFootprintPraise);
					if(!uid.equals(praiseUid)) { //不是自己给自己点赞
						//保存点赞消息
						savePraiseMsg(uid, praiseUid, praiseName, praiseImg, footid);
						//推送
						pushPraiseMsg(uid, praiseName, footid);
					}
				} else if("1".equals(praise.getDelFlag())){ //点赞后又取消了
					qxFootprintPraiseService.updatePraise(qxFootprintPraise);
					if(!uid.equals(praiseUid)) { //不是自己给自己点赞
						//保存点赞消息
						savePraiseMsg(uid, praiseUid, praiseName, praiseImg, footid);
						//推送
						pushPraiseMsg(uid, praiseName, footid);
					}
				}
			} else { //取消点赞
				qxFootprintPraiseService.updatePraise(qxFootprintPraise);
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxFootprintPraiseController - saveOrDelPraise()方法报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * 结果页排行榜 点赞--无论是否点赞，均推送消息
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/praise", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo savePraise(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		final String uid = map.get("uid");
		String gid = map.get("gid");
		String praiseUid = map.get("praiseUid");
		final String praiseName = map.get("praiseName");
		String praiseImg = map.get("praiseImg");
		BaseBackInfo backInfo = new BaseBackInfo();
		// 简单校验
		if(Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(praiseUid) || Strings.isNullOrEmpty(praiseName) || Strings.isNullOrEmpty(praiseImg)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(uid.length() != 32) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			//根据专题和被点赞人ID获取足迹ID
			Integer footid = qxFootprintService.getFootid(Integer.valueOf(gid), uid);
			if(footid != null) {
				//判断是否有点赞记录
				QxFootprintPraise praise = qxFootprintPraiseService.getPraise(Integer.valueOf(footid), uid, praiseUid);
				QxFootprintPraise qxFootprintPraise = new QxFootprintPraise();
				qxFootprintPraise.setFootid(Integer.valueOf(footid));
				qxFootprintPraise.setUid(uid);
				qxFootprintPraise.setPraiseUid(praiseUid);
				qxFootprintPraise.setPraiseName(praiseName);
				qxFootprintPraise.setCreateTime(new Date());
				if(praise == null) { //点赞		
					qxFootprintPraiseService.savePraise(qxFootprintPraise);
				} else if("1".equals(praise.getDelFlag())){ //点赞后又取消了
					qxFootprintPraiseService.updatePraise(qxFootprintPraise);
				}
				//无论是否已点赞，为了促进用户之间的交流，均给用户推送点赞消息
				if(!uid.equals(praiseUid)) { //不是自己给自己点赞
					//保存点赞消息
					saveResultPagePraiseMsg(uid, praiseUid, praiseName, praiseImg);
					//推送
					pushResultPagePraiseMsg(uid, praiseName, praiseUid);
				}
			}

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxFootprintPraiseController - savePraise()方法报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	
	/**
	 * 保存点赞消息
	 * @param uid
	 * @param praiseUid
	 * @param praiseName
	 * @param praiseImg
	 */
	private void savePraiseMsg(String uid, String praiseUid, String praiseName, String praiseImg, String footid) {
		QxUserMsg msg = new QxUserMsg();
		msg.setUid(uid);
		//msg.setMsgUid(praiseUid);
		msg.setMsgName(praiseName);
		msg.setMsgImg(praiseImg);
		msg.setMsgType(Global.PRAISE_MSG);
		msg.setMsgTypeName(Global.PRAISE_MSG_NAME);
		msg.setMsgJumpId(footid);
		msg.setMsgContent1(Global.PRAISE_MSG_CONTENT);
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
	private void pushPraiseMsg(final String uid, final String praiseName, final String footid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);

				if (null != device && null != device.getDeviceId()) {
					PushContent pushContent = new PushContent();
					pushContent.setDeviceType(device.getDeviceType());
					pushContent.setTargetValue(device.getDeviceId());

					// 设置内容，可以不设置
					pushContent.setBody(praiseName + Global.PUSH_PRAISE_MSG_CONTENT);
					// 摘要，描述，通知栏要展示的内容展示
					pushContent.setSummary(praiseName + Global.PUSH_PRAISE_MSG_CONTENT);

					// 设置自定义json扩展属性
					JsonObject extParameters = new JsonObject();
					extParameters.addProperty("jumpId", footid);
					extParameters.addProperty("type", PushContent.PRAISE_ME);

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
	
	private void saveResultPagePraiseMsg(String uid, String praiseUid, String praiseName, String praiseImg) {
		QxUserMsg msg = new QxUserMsg();
		msg.setUid(uid);
		//msg.setMsgUid(praiseUid);
		msg.setMsgName(praiseName);
		msg.setMsgImg(praiseImg);
		msg.setMsgType(Global.PRAISE_MSG);
		msg.setMsgTypeName(Global.PRAISE_MSG_NAME);
		msg.setMsgJumpId(praiseUid);
		msg.setMsgContent1(Global.PRAISE_MSG_CONTENT);
		msg.setCreateTime(new Date());
		try {
			qxUserMsgService.saveUserMsg(msg);
		} catch (Exception e) {
			logger.debug("[QxMessageController - qxUserMsgService.saveUserMsg(msg)方法报错：{}]", e.getMessage());
		}
	}
	
	/**
	 * 结果页点赞推送
	 * @param uid
	 * @param praiseName
	 * @param footid
	 */
	private void pushResultPagePraiseMsg(final String uid, final String praiseName, final String praiseUid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);

				if (null != device && null != device.getDeviceId()) {
					PushContent pushContent = new PushContent();
					pushContent.setDeviceType(device.getDeviceType());
					pushContent.setTargetValue(device.getDeviceId());

					// 设置内容，可以不设置
					pushContent.setBody(praiseName + Global.PUSH_PRAISE_MSG_CONTENT);
					// 摘要，描述，通知栏要展示的内容展示
					pushContent.setSummary(praiseName + Global.PUSH_PRAISE_MSG_CONTENT);

					// 设置自定义json扩展属性
					JsonObject extParameters = new JsonObject();
					extParameters.addProperty("jumpId", praiseUid);
					extParameters.addProperty("type", PushContent.RESULT_PAGE_PRAISE);

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
