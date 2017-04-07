package com.innovate.cms.modules.qs.web.msg;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgComment;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgInfo;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPrise;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPriseForService;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgCommentService;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgPriseService;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgService;

/**
 * 
 * @author shifangfang
 * @date 2017年3月15日 下午5:15:17
 */
@Controller
@RequestMapping(value = "/api")
public class DynamicMsgController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(DynamicMsgController.class);
	@Autowired
	private DynamicMsgService dynamicMsgService;
	@Autowired
	private DynamicMsgPriseService dynamicMsgPriseService;
	@Autowired
	private DynamicMsgCommentService dynamicMsgCommentService;

	/**
	 * 存储消息动态
	 * @param dynamicMsgToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/save", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo save(@RequestBody DynamicMsgToJson dynamicMsgToJson, HttpServletRequest request, HttpServletResponse response) {

		String description = dynamicMsgToJson.getDescription();
		String uidString = dynamicMsgToJson.getUid();
		int msgType = dynamicMsgToJson.getMsgType();
		String location = dynamicMsgToJson.getLocation();
		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(description) || StrUtil.isBlank(uidString)|| StrUtil.isBlank(location) || msgType < 0) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			dynamicMsgService.save(dynamicMsgToJson);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setItem(dynamicMsgToJson);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * store prise
	 * 
	 * @param dynamicMsgToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/savePrise", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo savePrise(@RequestBody DynamicMsgPrise dynamicMsgPrise, HttpServletRequest request, HttpServletResponse response) {

		int mid = dynamicMsgPrise.getMid();
		String uid = dynamicMsgPrise.getUid();

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid) || mid < 1) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {

			dynamicMsgPriseService.savePrise(dynamicMsgPrise);
			dynamicMsgService.addPriseNum(mid);
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
	 * 取消点赞
	 * 
	 * @param dynamicMsgPrise
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/cancelPrise", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo cancelPrise(@RequestBody DynamicMsgPrise dynamicMsgPrise, HttpServletRequest request, HttpServletResponse response) {

		int mid = dynamicMsgPrise.getMid();
		String uid = dynamicMsgPrise.getUid();

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid) || mid < 1) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			dynamicMsgPriseService.cancelPrise(dynamicMsgPrise);
			dynamicMsgService.subPriseNum(mid);
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
	 * 最新点赞列表 首次获取点赞列表
	 * 
	 * @param dynamicMsgPrise
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/priseList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo priseList(@RequestBody DynamicMsgPrise dynamicMsgPrise, HttpServletRequest request, HttpServletResponse response) {

		int mid = dynamicMsgPrise.getMid();

		DataBackInfo<DynamicMsgPriseForService> backInfo = new DataBackInfo<DynamicMsgPriseForService>();
		if (mid < 1) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			List<DynamicMsgPriseForService> data = dynamicMsgPriseService.priseList(mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(data);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 上拉获取更多
	 * 
	 * @param dynamicMsgPrise
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/upPriseList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo upPriseList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");
		String pidStr = map.get("pid");

		DataBackInfo<DynamicMsgPriseForService> backInfo = new DataBackInfo<DynamicMsgPriseForService>();
		if (StrUtil.isBlank(midStr) || StrUtil.isBlank(pidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			int pid = Integer.parseInt(pidStr);
			// 上拉获取更早时间点赞数据
			List<DynamicMsgPriseForService> data = dynamicMsgPriseService.upPriseList(mid, pid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(data);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 下拉获取最新点赞数据
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/downPriseList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo downPriseList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");
		String pidStr = map.get("pid");
		DataBackInfo<DynamicMsgPriseForService> backInfo = new DataBackInfo<DynamicMsgPriseForService>();
		if (StrUtil.isBlank(midStr) || StrUtil.isBlank(pidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			int pid = Integer.parseInt(pidStr);
			// 下拉获取最新点赞数据
			List<DynamicMsgPriseForService> data = dynamicMsgPriseService.downPriseList(mid, pid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(data);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * store comment 存储评论或者回复
	 * 
	 * @param dynamicMsgToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/saveComment", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo saveComment(@RequestBody DynamicMsgComment dynamicMsgComment, HttpServletRequest request, HttpServletResponse response) {

		int mid = dynamicMsgComment.getMid();
		String uid = dynamicMsgComment.getUid();
		String content = dynamicMsgComment.getContent();
		// 评论类型0-评论1-回复',
		int commentType = dynamicMsgComment.getCommentType();

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid) || mid < 0 || StrUtil.isBlank(content)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		// 回复类型
		if (commentType == 1) {
			if (StrUtil.isBlank(dynamicMsgComment.getReplyName()) || StrUtil.isBlank(dynamicMsgComment.getReplyUid())) {
				BaseBackInfo info = new BaseBackInfo();
				info.setStateCode(Global.int300209);
				info.setRetMsg(Global.str300209);
				return info;
			}
		}
		try {
			dynamicMsgCommentService.saveComment(dynamicMsgComment);
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
	 * 上拉获取更早时间的评论
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/upCommentList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo upCommentList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String cidStr = map.get("cid");
		String midStr = map.get("mid");

		DataBackInfo<DynamicMsgComment> backInfo = new DataBackInfo<DynamicMsgComment>();
		if (StrUtil.isBlank(cidStr) || StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			int cid = Integer.parseInt(cidStr);
			// 上拉获取更早时间的评论
			List<DynamicMsgComment> data = dynamicMsgCommentService.upCommentList(mid, cid);
			// 获取最新的评论
			// List<DynamicMsgComment> data1 =
			// dynamicMsgCommentService.latestCommentList(mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(data);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 消息详情页接口：返回最新点赞列表和最新评论列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/msgInfo", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo msgInfo(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			DynamicMsgInfo msgInfo = new DynamicMsgInfo();
			msgInfo.setMsgComments(dynamicMsgCommentService.latestCommentList(mid));
			msgInfo.setMsgPrises(dynamicMsgPriseService.priseList(mid));
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setItem(msgInfo);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 未获取用户位置信息时获取首页信息接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/lastesMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo lastesMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 未获取用户位置信息时获取首页信息接口
			List<DynamicMsgForService> msgs = dynamicMsgService.lastesMsg();
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	
	/**
	 * 根据消息id获取消息
	 * 
	 * h5分享获取消息信息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/getMsgByMid", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getMsgByMid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");

		if (StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			//根据消息id获取消息
			List<DynamicMsgForService> msgs = dynamicMsgService.getMsgByMid(mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 获取用户最新发布的消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userLatestMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userLatestMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			//获取用户最新发布的消息 前20条
			List<DynamicMsgForService> msgs = dynamicMsgService.userLatestMsg(uid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 上拉获取下一页消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userUpLatestMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userUpLatestMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String midStr = map.get("mid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			//上拉获取下一页消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userUpLatestMsg(uid,mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 下拉刷新获取最新
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userDownLatestMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userDownLatestMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String midStr = map.get("mid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			//下拉刷新获取最新
			List<DynamicMsgForService> msgs = dynamicMsgService.userDownLatestMsg(uid,mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 获取用户好友最新消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/friendLatestMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo friendLatestMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			//好友动态 获取用户好友最新消息
			List<DynamicMsgForService> msgs = dynamicMsgService.friendLatestMsg(uid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 上拉获取下一页好友消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/friendUpLatestMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo friendUpLatestMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String midStr = map.get("mid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			//好友动态 上拉获取下一页好友动态
			List<DynamicMsgForService> msgs = dynamicMsgService.friendUpLatestMsg(uid,mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 下拉刷新好友的最新消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/friendDownLatestMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo friendDownLatestMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String midStr = map.get("mid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			//好友动态 下拉刷新获取最新
			List<DynamicMsgForService> msgs = dynamicMsgService.friendDownLatestMsg(uid,mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}


	/**
	 * 获取附近消息
	 * 
	 * 根据指定消息mid获取一批消息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/nearMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo nearMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String idstr = map.get("mids");
		if (StrUtil.isBlank(idstr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		String[] ids = idstr.split(",");
		int[] mids = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			mids[i] = Integer.parseInt(ids[i]);
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 根据指定的消息mid获取一批消息
			List<DynamicMsgForService> msgs = dynamicMsgService.nearMsg(mids);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * -v1.0 用户点赞消息id列表
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userPriseList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userPriseList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		DataBackInfo<Integer> backInfo = new DataBackInfo<Integer>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			List<Integer> mids= dynamicMsgService.userPriseList(uid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(mids);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	
	
}
