/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.ques;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.utils.IdGen;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.data.entity.UserAtrribute;
import com.innovate.cms.modules.data.entity.UserSelect;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;
import com.innovate.cms.modules.qs.entity.menus.Group;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;
import com.innovate.cms.modules.qs.entity.ques.QxHistory;
import com.innovate.cms.modules.qs.entity.ques.QxHistoryAnswer;
import com.innovate.cms.modules.qs.entity.ques.T4result;
import com.innovate.cms.modules.qs.entity.sns.FriendHistory;
import com.innovate.cms.modules.qs.entity.sns.QxUserMsg;
import com.innovate.cms.modules.qs.entity.sns.UserHistory;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.menus.QxGroupsService;
import com.innovate.cms.modules.qs.service.push.QxPushInfoService;
import com.innovate.cms.modules.qs.service.ques.QxHistoryAnswerService;
import com.innovate.cms.modules.qs.service.ques.QxHistoryService;
import com.innovate.cms.modules.qs.service.sns.QxFollowDynamicStateService;
import com.innovate.cms.modules.qs.service.sns.QxFollowService;
import com.innovate.cms.modules.qs.service.sns.QxFootprintService;
import com.innovate.cms.modules.qs.service.sns.QxMatchService;
import com.innovate.cms.modules.qs.service.sns.QxUserMsgService;
import com.innovate.cms.modules.qs.service.tag.QxTagService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;
import com.thoughtworks.xstream.mapper.Mapper.Null;

/**
 * 用户答题记录Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxHistoryController extends BaseController {

	@Autowired
	private QxHistoryService qxHistoryService;

	@Autowired
	private QxFollowService qxFollowService;

	@Autowired
	private QxFootprintService qxFootprintService;
	@Autowired
	private QxMatchService qxMatchService;

	@Autowired
	private QxHistoryAnswerService qxHistoryAnswerService;

	@Autowired
	private QxPushInfoService qxPushInfoService;

	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private QxGroupsService qxGroupsService;

	@Autowired
	private QxUserMsgService qxUserMsgService;
	@Autowired
	private QxTagService qxTagService;
	@Autowired
	private QxFollowDynamicStateService qxFollowDynamicStateService;
	/**
	 * 获取模板4的做题结果
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/history/getT4result", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getT4result(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String gidStr = map.get("gid");
		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		int gid = Integer.parseInt(gidStr);
		try {
			//获取模板4的做题结果
			T4result res = qxHistoryService.getT4result(uid,gid);
			backInfo.setItem(res);
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
	 * 存储用户答题记录 模板0/4/5
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/saveHistory", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> saveHistory(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// String htid = map.get("htid");
		String uid = map.get("uid");
		String fsid = map.get("fsid");
		String gid = map.get("gid");
		// 默认-1,没有qid可以不传或者默认-1
		String qid = map.get("qid");

		// 模板4：秦王 模板5：存匹配度
		String results = map.get("results");
		// 模板4的结果选项,如：A
		String resultsTag = map.get("resultsTag");
		// 专题模板类型，存储足迹需要
		String templateTag = map.get("templateTag");
		// answer：1-A 2-B 3-C
		// [{qid:"10","answer":"1"},...]
		String qidAnswer = map.get("qidAnswer");

		DataBackInfo<?> backInfo = new DataBackInfo<Null>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(fsid) || StrUtil.isBlank(gid) || StrUtil.isBlank(qid) || StrUtil.isBlank(results) || StrUtil.isBlank(templateTag) || StrUtil.isBlank(qidAnswer)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			QxHistory history = new QxHistory();
			Date nowDate = new Date();
			history.setHtid(IdGen.uuid());
			history.setTemplateTag(templateTag);
			history.setResultsTag(resultsTag);
			history.setUid(uid);
			history.setFsid(Integer.parseInt(fsid));
			history.setGid(Integer.parseInt(gid));
			if (null == qid) {
				qid = "-1";
			}
			history.setQid(Integer.parseInt(qid));
			history.setResults(results);
			history.setCreateDate(nowDate);
			history.setUpdateTime(nowDate);
			// int num = qxHistoryService.isExist(history);
			// 判断是否做过该专题
			int num = qxHistoryService.doneGroup(history);
			if (num > 0) {
				// 用户已经做过，更新用户做题结果
				qxHistoryService.updateHistory(history);
			} else {
				qxHistoryService.saveHistory(history);
				//保存做题记录到好友动态表
				qxFollowDynamicStateService.saveGroupDoneDynamicState(uid, Integer.valueOf(gid), templateTag);

				// 统计用户该频道做题数量
				/* statNum(uid, Integer.valueOf(fsid)); */

			}

			// 存储用户做题足迹
			// 足迹类型（1、专题 2、模板五无数据 3、模板5有数据）
/*			if ("5".endsWith(templateTag)) {
				storeQxFootprint(uid, gid, results, "2");

				// 存储用户选项答案,模板4和1允许随便做，并且存储答案没什么意义，另一原因答案表有联合索引，频繁更改效率太低
				// qx_history_answer
				saveQxhistoryanswer(uid, gid, qidAnswer);
			} else if ("4".endsWith(templateTag) || "1".endsWith(templateTag)) {
				// `foot_result_desc` varchar(512) DEFAULT '' COMMENT
				// '模板4足迹做题结果描述',
				// 4期需求，足迹内模板4增加结果描述
				// 修改此处
				qxFootprintService.saveFootprintT4stage4(uid, gid, results, "1", resultsTag);
				// storeQxFootprint(uid, gid, results, "1");
			}*/

			// 为用户打上专题标签
			//qxTagService.saveQxTagUserByuidgid(uid, Integer.parseInt(gid));

			// 为用户打上选项标签和结果页标签
			/*
			 * saveLabelUser(uid, Integer.parseInt(gid), resultsTag, qidAnswer,
			 * templateTag);
			 */

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
	 * 模板4 模板5无匹配数据
	 * 
	 * H5 存储用户答题记录 模板0/4/5
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/saveHistory", method = RequestMethod.GET)
	public @ResponseBody String saveHistoryH5(HttpServletRequest request, HttpServletResponse response) {
		// String htid = map.get("htid");
		String callback = request.getParameter("callback");
		// 做题用户的uid
		String uid = request.getParameter("uid");
		String fsid = request.getParameter("fsid");
		String gid = request.getParameter("gid");
		// 默认-1,没有qid可以不传或者默认-1
		String qid = request.getParameter("qid");

		// 模板4：秦王 模板5：存匹配度
		String results = request.getParameter("results");
		// 模板4的结果选项,如：A
		String resultsTag = request.getParameter("resultsTag");
		// 专题模板类型，存储足迹需要
		String templateTag = request.getParameter("templateTag");
		// answer：1-A 2-B 3-C
		// [{qid:"10","answer":"1"},...]
		String qidAnswer = request.getParameter("qidAnswer");

		// 分享用户的uid
		String fuid = request.getParameter("fuid");

		DataBackInfo<?> backInfo = new DataBackInfo<Null>();
		if (!StrUtil.isUID1(uid) || !StrUtil.isNum(gid) || !StrUtil.isNum(qid) || !StrUtil.isNum(fsid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("param blank error", backInfo);
		}
		if ("4".endsWith(templateTag)) {
			if (!StrUtil.isUID1(fuid)) {
				backInfo.setStateCode(Global.int300209);
				backInfo.setRetMsg(Global.str300209);
				return JsonMapper.getInstance().toJsonP("fuid param blank error", backInfo);
			}
		}
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(fsid) || StrUtil.isBlank(callback) || StrUtil.isBlank(gid) || StrUtil.isBlank(qid) || StrUtil.isBlank(results) || StrUtil.isBlank(templateTag) || StrUtil.isBlank(qidAnswer)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("param blank error", backInfo);
		}
		try {
			QxHistory history = new QxHistory();
			Date nowDate = new Date();
			history.setHtid(IdGen.uuid());
			history.setTemplateTag(templateTag);
			history.setResultsTag(resultsTag);
			history.setUid(uid);
			history.setFsid(Integer.parseInt(fsid));
			history.setGid(Integer.parseInt(gid));
			if (null == qid) {
				qid = "-1";
			}
			history.setQid(Integer.parseInt(qid));
			history.setResults(results);
			history.setCreateDate(nowDate);
			history.setUpdateTime(nowDate);
			// int num = qxHistoryService.isExist(history);
			// 判断是否做过该专题
			int num = qxHistoryService.doneGroup(history);

			if (num > 0) {
				// 用户已经做过，更新用户做题结果
				qxHistoryService.updateHistory(history);
			} else {
				qxHistoryService.saveHistory(history);
				//保存做题记录到好友动态表
				qxFollowDynamicStateService.saveGroupDoneDynamicState(uid, Integer.valueOf(gid), templateTag);

				// 统计用户该频道做题数量
				/* statNum(uid, Integer.valueOf(fsid)); */

			}

		/*	// 存储用户做题足迹
			// 足迹类型（1、专题 2、模板五无数据 3、模板5有数据）
			if ("5".endsWith(templateTag)) {
				storeQxFootprint(uid, gid, results, "2");
				// 20160629存储用户选项答案,模板4和1允许随便做，并且存储答案没什么意义，另一原因答案表有联合索引，频繁更改效率太低
				// qx_history_answer
				saveQxhistoryanswer(uid, gid, qidAnswer);
			} else if ("4".endsWith(templateTag)) {
				// '模板4足迹做题结果描述',
				// 4期需求，足迹内模板4增加结果描述
				// 修改此处
				qxFootprintService.saveFootprintT4stage4(uid, gid, results, "1", resultsTag);
				// storeQxFootprint(uid, gid, results, "1");
				// 存储排行榜数据resultsTag 结果项目A/B/C/D 选项 results：选项对应结果标题
				// 首先判断是否存在
				// h5模板4判断是否存在匹配数据
				int _gid = Integer.parseInt(gid);
				int count = qxMatchService.isExisth5t4(_gid, fuid, uid);
				if (count > 0) {
					// h5模板4更新排行榜数据
					qxMatchService.updateMatchResh5t4(_gid, fuid, uid, results);
				} else {
					// h5模板4添加排行榜数据
					qxMatchService.addNewMatchh5t4(_gid, fuid, uid, results);
				}

			} else if ("1".endsWith(templateTag)) {
				storeQxFootprint(uid, gid, results, "1");
			}*/

			// 为用户打上专题标签
			//qxTagService.saveQxTagUserByuidgid(uid, Integer.parseInt(gid));

			// 为用户打上选项标签和结果页标签
			/*
			 * saveLabelUser(uid, Integer.parseInt(gid), resultsTag, qidAnswer,
			 * templateTag);
			 */

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());

			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 获取用户态度列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getUserAtrribute", method = RequestMethod.GET)
	public @ResponseBody String getUserAtrribute(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String uid = request.getParameter("uid");
		String gidStr = request.getParameter("gid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(callback) || StrUtil.isBlank(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", info);
		}

		try {
			int gid = Integer.parseInt(gidStr);
			UserAtrribute ua = new UserAtrribute();
			// 获取分享者
			List<UserSelect> userShare = qxHistoryService.userShare(uid, gid);
			// 获取A选项用户列表
			List<UserSelect> userSelectsA = qxHistoryService.userSelects(gid, 1);
			// 获取B选项用户列表
			List<UserSelect> userSelectsB = qxHistoryService.userSelects(gid, 2);

			ua.setUserSelectsA(userSelectsA);
			ua.setUserSelectsB(userSelectsB);
			ua.setUserShare(userShare);

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// 此处set返回对象
			backInfo.setItem(ua);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 存储用户选项答案
	 * 
	 * @param uid
	 * @param gid
	 * @param qidAnswer
	 */
	public void saveQxhistoryanswer(String uid, String gid, String qidAnswer) {
		JSONArray array = JSONArray.parseArray(qidAnswer);
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			QxHistoryAnswer his = new QxHistoryAnswer(uid, Integer.parseInt(gid), obj.getIntValue("qid"), obj.getString("answer"));
			qxHistoryAnswerService.addHistoryAnswer(his);
		}
	}

	/**
	 * 模板5有匹配数据做题结果存储，用户从好友足迹内做题，需要存储而这的匹配度到footprint
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/saveHistoryFromUser5", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> saveHistoryFromUser5(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// String htid = map.get("htid");
		// 足迹用户uid
		final String uid = map.get("uid");
		String fsid = map.get("fsid");
		final String gid = map.get("gid");
		// 默认-1,没有qid可以不传或者默认-1
		String qid = map.get("qid");

		String resultsTag = map.get("resultsTag");

		// 模板4的对应结果 比如：秦王 模板5：匹配度结果0.89
		final String results = map.get("results");
		// 专题模板类型，存储足迹需要
		String templateTag = map.get("templateTag");
		// 问题答案
		// [{qid:"10","answer":"A"}...]
		String qidAnswer = map.get("qidAnswer");

		// 匹配用户的uid，做题用户uid
		final String fuid = map.get("fuid");

		DataBackInfo<?> backInfo = new DataBackInfo<Null>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(fsid) || StrUtil.isBlank(fuid) || StrUtil.isBlank(gid) || StrUtil.isBlank(qid) || StrUtil.isBlank(results) || StrUtil.isBlank(templateTag) || StrUtil.isBlank(qidAnswer)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if (fuid.equals(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209 + ":同一个用户不能与自己匹配 !");
			return backInfo;
		}
		try {
			QxHistory history = new QxHistory();
			Date nowDate = new Date();
			history.setHtid(IdGen.uuid());
			history.setTemplateTag(templateTag);
			history.setResultsTag(resultsTag);
			history.setUid(fuid);
			history.setFsid(Integer.parseInt(fsid));
			history.setGid(Integer.parseInt(gid));
			if (null == qid) {
				qid = "-1";
			}
			history.setQid(Integer.parseInt(qid));
			history.setResults(results);
			history.setCreateDate(nowDate);
			history.setUpdateTime(nowDate);

			int num = qxHistoryService.isExist(history);
			if (num > 0) {
				// 用户已经做过，更新用户做题结果
				qxHistoryService.updateHistory(history);
			} else {
				qxHistoryService.saveHistory(history);

				// 统计用户该频道做题数量
				/* statNum(uid, Integer.valueOf(fsid)); */

			}

			// 存储用户做题足迹
			// 足迹类型（1、专题 2、模板五无数据 3、模板5有数据）
			if ("5".endsWith(templateTag)) {
				// 模板5有数据，有匹配度足迹存储
				// 4期新需求改变，不存储带匹配度的足迹，只存储一条足迹就可以
				storeQxFootprint(fuid, gid, results, "2");
				// storeQxFootprint5(uid, gid, fuid, results, "3");
				// 存储匹配度
				qxMatchService.addNewMatch(Integer.parseInt(gid), uid, fuid, Double.parseDouble(results));
			} else {
				storeQxFootprint(fuid, gid, results, "2");
			}

			// qx_history_answer存储用户选项答案
			saveQxhistoryanswer(fuid, gid, qidAnswer);

			// final int footid = storeFootPrint.getFootid();
			// 推送
			// new Thread(new Runnable() {
			// @Override
			// public void run() {
			// // 优化 后期改为别名推送 uid（别名）绑定设备
			// QxPushInfo device = qxPushInfoService.getPushInfoByUid(uid);
			// // 优化 让客户端传
			// SystemUser doUser = systemUserService.findByUid(fuid);
			// // 优化 让客户端传
			// Group group =
			// qxGroupsService.getGroupBygid(Integer.parseInt(gid));
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
			// // 设置内容，安卓用
			// pushContent.setBody(doUser.getNickname() + "参与了你足迹里的<<" +
			// group.getGroupName() + ">>,快来看看你们的合拍度和排名吧");
			// // 摘要，描述，通知栏要展示的内容展示 ios用
			// pushContent.setSummary(doUser.getNickname() + "参与了你足迹里的<<" +
			// group.getGroupName() + ">>,快来看看你们的合拍度和排名吧");
			//
			// // 设置自定义json扩展属性
			// JsonObject extParameters = new JsonObject();
			// extParameters.addProperty("jumpId", uid);
			// extParameters.addProperty("type", PushContent.FootPrint);
			// // extParameters.addProperty("umid",
			// // "消息id-留言/回复/关注");
			// // extParameters.addProperty("gid",
			// // "someone做了您足迹内的any专题");
			// // 留言id
			// // extParameters.addProperty("msid", "");
			// // // 回复id
			// // extParameters.addProperty("poid", "");
			// // // 足迹id
			// // extParameters.addProperty("footid", footid);
			// // extParameters.addProperty("uid", uid);
			//
			// pushContent.setExtParameters(extParameters);
			// PushClient.pushContentNotice(pushContent);
			//
			// // 模板5足迹内做题，存储用户提醒消息(互动消息)，有人做了您足迹内的专题
			// QxUserMsg msg = new QxUserMsg();
			// msg.setUid(uid);
			// msg.setMsgName(doUser.getNickname());
			// msg.setMsgImg(doUser.getHeadimgurl());
			// msg.setMsgType("4");
			// msg.setMsgTypeName("提醒");
			// // 跳到做题人
			// msg.setMsgJumpId(fuid);
			// msg.setMsgContent1(doUser.getNickname() + "参与了你的专题<<" +
			// group.getGroupName() + ">>,你们的匹配度是" + dealResult(results));
			// msg.setCreateDate(new Date());
			// qxUserMsgService.saveUserMsg(msg);
			//
			// }
			// }
			//
			// private String dealResult(String results) {
			// String resultString = "";
			// if (null == results || results.length() < 1) {
			// resultString = "";
			// } else {
			// float f = Float.parseFloat(results);
			// resultString = (int) (f * 100) + "%";
			// }
			// return resultString;
			// }
			// }).start();

			// 为用户打上专题标签
			qxTagService.saveQxTagUserByuidgid(uid, Integer.parseInt(gid));

			// 为用户打上选项标签和结果页标签
			/*
			 * saveLabelUser(uid, Integer.parseInt(gid), resultsTag, qidAnswer,
			 * templateTag);
			 */

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
	 * 模板5有匹配数据做题结果存储，用户从好友足迹内做题，需要存储而这的匹配度到footprint
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/saveHistoryFromUser5", method = RequestMethod.GET)
	public @ResponseBody String saveHistoryFromUser5H5(HttpServletRequest request, HttpServletResponse response) {
		// String htid = map.get("htid");
		String callback = request.getParameter("callback");
		// 足迹用户uid
		String uid = request.getParameter("uid");
		String fsid = request.getParameter("fsid");
		String gid = request.getParameter("gid");
		// 默认-1,没有qid可以不传或者默认-1
		String qid = request.getParameter("qid");

		String resultsTag = request.getParameter("resultsTag");

		// 模板4的对应结果 比如：秦王 模板5：匹配度结果0.89
		String results = request.getParameter("results");
		// 专题模板类型，存储足迹需要
		String templateTag = request.getParameter("templateTag");
		// 问题答案
		// [{qid:"10","answer":"A"}...]
		String qidAnswer = request.getParameter("qidAnswer");

		// 匹配用户的uid，做题用户uid
		String fuid = request.getParameter("fuid");

		DataBackInfo<?> backInfo = new DataBackInfo<Null>();
		if (!StrUtil.isUID1(uid) || !StrUtil.isUID1(fuid) || !StrUtil.isNum(gid) || !StrUtil.isNum(qid) || !StrUtil.isNum(fsid)) {
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
			return JsonMapper.getInstance().toJsonP("param blank error", backInfo);
		}
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(fsid) || StrUtil.isBlank(fuid) || StrUtil.isBlank(callback) || StrUtil.isBlank(gid) || StrUtil.isBlank(qid) || StrUtil.isBlank(results) || StrUtil.isBlank(templateTag) || StrUtil.isBlank(qidAnswer)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("error", backInfo);
		}

		if (fuid.equals(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209 + ":同一个用户不能与自己匹配 !");
			return JsonMapper.getInstance().toJsonP("error", backInfo);
		}
		try {
			// 存储做题人的做题记录
			QxHistory history = new QxHistory();
			Date nowDate = new Date();
			history.setHtid(IdGen.uuid());
			history.setTemplateTag(templateTag);
			history.setResultsTag(resultsTag);
			history.setUid(fuid);
			history.setFsid(Integer.parseInt(fsid));
			history.setGid(Integer.parseInt(gid));
			if (null == qid) {
				qid = "-1";
			}
			history.setQid(Integer.parseInt(qid));
			history.setResults(results);
			history.setCreateDate(nowDate);
			history.setUpdateTime(nowDate);

			int num = qxHistoryService.isExist(history);
			if (num > 0) {
				// 用户已经做过，更新用户做题结果
				qxHistoryService.updateHistory(history);
			} else {
				qxHistoryService.saveHistory(history);

				// 统计用户该频道做题数量
				/* statNum(uid, Integer.valueOf(fsid)); */

			}

			// 存储用户做题足迹
			// 足迹类型（1、专题 2、模板五无数据 3、模板5有数据）
			if ("5".endsWith(templateTag)) {
				// 模板5有数据，有匹配度足迹存储
				// 4期新需求改变，不存储带匹配度的足迹，只存储一条足迹就可以
				storeQxFootprint(fuid, gid, results, "2");
				// storeQxFootprint5(uid, gid, fuid, results, "3");
				// 存储匹配度,h5的模板5匹配数据单独存储
				qxMatchService.addNewMatchH5(Integer.parseInt(gid), uid, fuid, Double.parseDouble(results));
			} else {
				storeQxFootprint(fuid, gid, results, "2");
			}

			// qx_history_answer存储做题用户选项答案
			// 存储做题人答案
			saveQxhistoryanswer(fuid, gid, qidAnswer);

			// 为用户打上专题标签
			qxTagService.saveQxTagUserByuidgid(uid, Integer.parseInt(gid));

			// 为用户打上选项标签和结果页标签
			/*
			 * saveLabelUser(uid, Integer.parseInt(gid), resultsTag, qidAnswer,
			 * templateTag);
			 */

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());

			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 模板4存储用户做题足迹 模板5无匹配度
	 * 
	 * @param uid
	 * @param gid
	 * @param results
	 * @param footprintSort
	 */
	private void storeQxFootprint(String uid, String gid, String results, String footType) {
		qxFootprintService.saveFootprintTemplate4(uid, gid, results, footType);
	}

	/**
	 * 模板5有匹配度足迹存储
	 * 
	 * @param uid
	 * @param gid
	 * @param results
	 * @param footprintSort
	 */
	@SuppressWarnings("unused")
	private void storeQxFootprint5(String uid, String gid, String fuid, String results, String footType) {
		qxFootprintService.saveFootprintTemplate5(uid, gid, fuid, results, footType);
	}

	/**
	 * 获取用户做题记录
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getHistory", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getHistory(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String pageSize = map.get("pageSize");
		String pageNum = map.get("pageNum");
		// 必填，默认-1
		String totalResult = map.get("totalResult");

		DataBackInfo<UserHistory> backInfo = new DataBackInfo<UserHistory>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(pageSize) || StrUtil.isBlank(pageNum) || StrUtil.isBlank(totalResult)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			int num = Integer.parseInt(pageSize);
			int start = (Integer.parseInt(pageNum) - 1) * num;
			int total = -1;
			if (null == totalResult || "-1".equals(totalResult)) {
				int totalNum = qxHistoryService.totalByuid(uid);
				total = (totalNum % num > 0) ? totalNum / num + 1 : totalNum / num;
			} else {
				total = Integer.parseInt(totalResult);
			}
			backInfo.setTotalResult(total);
			List<UserHistory> frinds = qxFollowService.findUserHistory(uid, start, num);
			// logger.debug(JsonMapper.getInstance().toJson(frinds));
			backInfo.setData(frinds);
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
	 * 
	 * 获取用户以及其好友共同做过得某个专题记录
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getHistorybygid", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getHistorybygid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String gid = map.get("gid");
		String pageSize = map.get("pageSize");
		String pageNum = map.get("pageNum");
		// 必填，默认-1
		String totalResult = map.get("totalResult");

		DataBackInfo<FriendHistory> backInfo = new DataBackInfo<FriendHistory>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(pageSize) || StrUtil.isBlank(pageNum) || StrUtil.isBlank(totalResult)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			int num = Integer.parseInt(pageSize);
			int start = (Integer.parseInt(pageNum) - 1) * num;
			int total = -1;
			if (null == totalResult || "-1".equals(totalResult)) {
				int totalNum = qxHistoryService.totalByuidgid(uid, gid);
				total = (totalNum % num > 0) ? totalNum / num + 1 : totalNum / num;
			} else {
				total = Integer.parseInt(totalResult);
			}
			backInfo.setTotalResult(total);
			List<FriendHistory> frinds = qxFollowService.findHistorybyuidgidPage(uid, gid, start, num);
			// logger.debug(JsonMapper.getInstance().toJson(frinds));
			backInfo.setData(frinds);
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
	 * h5版本 获取用户以及其好友共同做过得某个专题记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getHistorybygid", method = RequestMethod.GET)
	public @ResponseBody String getHistorybygidH5(HttpServletRequest request, HttpServletResponse response) {

		String callback = request.getParameter("callback");
		String uid = request.getParameter("uid");
		String gid = request.getParameter("gid");
		DataBackInfo<FriendHistory> backInfo = new DataBackInfo<FriendHistory>();
		if (StringUtils.isBlank(gid) || StringUtils.isBlank(uid) || !StrUtil.isUID1(uid) || !StrUtil.isNum(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP(callback, backInfo);
		}

		try {
			List<FriendHistory> frinds = qxFollowService.findHistorybyuidgid(uid, gid);
			// logger.debug(JsonMapper.getInstance().toJson(frinds));
			backInfo.setData(frinds);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());

			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 模板四结果页--获取做题结果相同的小伙伴
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getUsersWithSameResult", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getUsersWithSameResult(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String gid = map.get("gid");
		String resultsTag = map.get("resultsTag");

		DataBackInfo<RegUserInfoToJson> backInfo = new DataBackInfo<RegUserInfoToJson>();
		List<RegUserInfoToJson> userList = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(resultsTag)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if (uid.length() != 32 || !StrUtil.isPositiveNum(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			userList = qxHistoryService.findUsersWithSameResult(uid, gid, resultsTag);
			backInfo.setData(userList);
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
	 * 模板四结果页--获取做题结果相同的小伙伴
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getUsersWithSameResult", method = RequestMethod.GET)
	public @ResponseBody String getUsersWithSameResultH5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String uid = request.getParameter("uid");
		String gid = request.getParameter("gid");
		String resultsTag = request.getParameter("resultsTag");

		DataBackInfo<RegUserInfoToJson> backInfo = new DataBackInfo<RegUserInfoToJson>();
		List<RegUserInfoToJson> userList = Lists.newArrayList();
		if (StrUtil.isBlank(callback) || StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(resultsTag)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("", backInfo);
		}
		if (uid.length() != 32 || !StrUtil.isPositiveNum(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP(callback, backInfo);
		}
		try {
			userList = qxHistoryService.findUsersWithSameResult(uid, gid, resultsTag);
			backInfo.setData(userList);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	// /**
	// * 统计该用户该频道做题数量
	// */
	// private void statNum(String uid, Integer fsid) {
	// qxStatNumService.saveDoneNum(uid, fsid);
	// }
	//
	// /**
	// * 根据运营配置的选项标签和模板4结果页标签，匹配用户做题的选项和结果，给用户打上选项标签和结果页标签
	// */
	// private void saveLabelUser(String uid, Integer gid, String resultsTag,
	// String qidAnswer, String templateTag) {
	// qxLabelService.saveLabelUser(uid, gid, resultsTag, qidAnswer,
	// templateTag);
	// }

}