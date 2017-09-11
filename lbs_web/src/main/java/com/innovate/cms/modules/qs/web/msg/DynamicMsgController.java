package com.innovate.cms.modules.qs.web.msg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.AutoInviteBackInfo;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.DynamicMsgBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgApplyForService;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgComment;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgInfo;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPrise;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgPriseForService;
import com.innovate.cms.modules.qs.entity.tribe.ImTribe;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgCommentService;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgPriseService;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgService;
import com.innovate.cms.modules.qs.service.tribe.ImTribeService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

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
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private ImTribeService imTribeService;
	/**
	 * 存储消息动态
	 * 
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
		if (StrUtil.isBlank(description) || StrUtil.isBlank(uidString) || StrUtil.isBlank(location) || msgType < 0) {
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
	 * 统计消息被分享的次数
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/addShareNum", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo addShareNum(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			// 统计分享数
			dynamicMsgService.addShareNum(mid);
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

		DynamicMsgBackInfo backInfo = new DynamicMsgBackInfo();
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
			//获取消息详情
			DynamicMsgForService msg = dynamicMsgService.msgInfo(mid);
			backInfo.setMsg(msg);
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
	 * 过滤屏蔽通讯录的消息
	 * 
	 * @param uid
	 * @param msgs
	 * @return
	 */
	public List<DynamicMsgForService> filterShieldMsg(String uid, List<DynamicMsgForService> msgs) {

		if (msgs.size() < 1) {
			return msgs;
		}

		SystemUser user = systemUserService.findByUid(uid);
		if (user.getMobile() != null && user.getMobile().length() > 0) {
			// 获取有设置屏蔽功能的用户uid
			List<String> uids = systemUserService.getShieldUids(uid, user.getMobile());
			if (uids.size() == 0) {
				return msgs;
			}
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Set<String> uidsSet = new HashSet(Arrays.asList(uids));
			Iterator<DynamicMsgForService> ite = msgs.iterator();
			while (ite.hasNext()) {
				DynamicMsgForService m = ite.next();
				if (uidsSet.contains(m.getUid())) {
					ite.remove();
				}
			}
		} else {
			return msgs;
		}
		return msgs;
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

		String uid = map.get("uid");

		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 未获取用户位置信息时获取首页信息接口
			List<DynamicMsgForService> msgs = dynamicMsgService.lastesMsg();
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(filterShieldMsg(uid, msgs));
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	@RequestMapping(value = "/v1/msg/updateMsgState", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo updateMsgState(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");
		String msgStateStr = map.get("msgState");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(midStr) || StrUtil.isBlank(msgStateStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			int msgState = Integer.parseInt(msgStateStr);
			// 管理消息状态0-未完成(默认) 1-完成
			dynamicMsgService.updateMsgState(mid, msgState);
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
	 * 未获取用户位置信息时获取首页信息接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/virtualMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo virtualMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 获取虚拟消息
			List<DynamicMsgForService> msgs = dynamicMsgService.virtualMsg();
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
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
	 * 根据消息类型筛选虚拟消息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/virtualMsgByMsgtype", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo virtualMsgByMsgtype(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String msgTypeStr = map.get("msgType");
		String sexStr = map.get("sex");
		if (StrUtil.isBlank(msgTypeStr) || StrUtil.isBlank(sexStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 获取虚拟消息
			String[] msgTypes = msgTypeStr.split(",");
			int[] msgType = new int[msgTypes.length];
			for (int i = 0; i < msgTypes.length; i++) {
				msgType[i] = Integer.parseInt(msgTypes[i]);
			}

			int sex = Integer.parseInt(sexStr);
			List<DynamicMsgForService> msgs = null;
			if (!msgTypeStr.equals("-1") && sex != -1) {// 消息和性别都过滤
				// 根据消息类型和性别筛选虚拟最新消息
				msgs = dynamicMsgService.virtualMsgByMsgtypeSex(msgType, sex);
			} else if (msgTypeStr.equals("-1") && sex != -1) {// 只过滤性别
				// // 根据性别获取虚拟消息
				msgs = dynamicMsgService.virtualMsgBySex(sex);
			} else if (!msgTypeStr.equals("-1") && sex == -1) {// 只过滤消息类型
				// 根据消息类型获取虚拟消息
				msgs = dynamicMsgService.virtualMsgByMsgtype(msgType);
			}

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
	@CrossOrigin
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
			// 根据消息id获取消息
			List<DynamicMsgForService> msgs = dynamicMsgService.getMsgByMid(mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
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

	@CrossOrigin
	@RequestMapping(value = "/v1/msg/getMsgByMidJSON", method = RequestMethod.GET)
	public @ResponseBody BaseBackInfo getMsgByMidJSON(HttpServletRequest request, HttpServletResponse response) {

		String midStr = request.getParameter("mid");

		if (StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			// 根据消息id获取消息
			List<DynamicMsgForService> msgs = dynamicMsgService.getMsgByMid(mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
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
			// 获取用户最新发布的消息 前20条
			List<DynamicMsgForService> msgs = dynamicMsgService.userLatestMsg(uid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
	 * 获取用户最新发布的活动类消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userLatestEventMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userLatestEventMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 获取用户最新发布的活动类消息 前20条
			List<DynamicMsgForService> msgs = dynamicMsgService.userLatestEventMsg(uid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
	 * 获取用户最新报名的消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userApplyMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userApplyMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			// 获取用户最新报名的消息 前20条
			List<DynamicMsgForService> msgs = dynamicMsgService.userApplyMsg(uid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
	 * 按一级分类获取用户最新报名的消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userApplyMsgFilter", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userApplyMsgFilter(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String msgTypeStr = map.get("msgType");
		
		if (StrUtil.isBlank(uid)||StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int msgType = Integer.parseInt(msgTypeStr);
			//  按一级分类获取用户最新报名的消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userApplyMsgFilter(uid,msgType);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
	 * 按一级分类上拉获取用户最新报名的消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userApplyUpMsgFilter", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userApplyUpMsgFilter(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String msgTypeStr = map.get("msgType");
		String midStr = map.get("mid");
		
		if (StrUtil.isBlank(uid)||StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int msgType = Integer.parseInt(msgTypeStr);
			int mid = Integer.parseInt(midStr);
			// 按一级分类上拉获取用户最新报名的消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userApplyUpMsgFilter(uid,msgType,mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
	 * 上拉获取用户报名的消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userApplyUpMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userApplyUpMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String uid = map.get("uid");
		String midStr = map.get("mid");
		
		if (StrUtil.isBlank(uid)||StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			// 上拉获取用户报名的消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userApplyUpMsg(uid,mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
	 * 按一级分类获取用户最新发布的消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userLatestMsgType", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userLatestMsgType(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String msgTypeStr = map.get("msgType");

		if (StrUtil.isBlank(uid) || StrUtil.isBlank(msgTypeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int msgType = Integer.parseInt(msgTypeStr);
			// 获取用户最新发布的消息 前20条
			List<DynamicMsgForService> msgs = dynamicMsgService.userLatestMsgType(uid, msgType);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
			// 上拉获取下一页消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userUpLatestMsg(uid, mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
			// backInfo.setData(filterShieldMsg(uid, msgs));
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 
	 * 上拉获取下一页活动类消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userUpLatestEventMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userUpLatestEventMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
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
			// 上拉获取下一页活动类消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userUpLatestEventMsg(uid, mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
			// backInfo.setData(filterShieldMsg(uid, msgs));
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 
	 * 按照一级分类上拉获取用户发布下一页消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/userUpLatestMsgType", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userUpLatestMsgType(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String midStr = map.get("mid");
		String msgTypeStr = map.get("msgType");

		if (StrUtil.isBlank(uid) || StrUtil.isBlank(midStr) || StrUtil.isBlank(msgTypeStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			int msgType = Integer.parseInt(msgTypeStr);
			//  按照一级分类上拉获取用户发布下一页消息
			List<DynamicMsgForService> msgs = dynamicMsgService.userUpLatestMsgType(uid, mid, msgType);
			
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
			// 下拉刷新获取最新
			List<DynamicMsgForService> msgs = dynamicMsgService.userDownLatestMsg(uid, mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(msgs);
			// backInfo.setData(filterShieldMsg(uid, msgs));
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
			// 好友动态 获取用户好友最新消息
			List<DynamicMsgForService> msgs = dynamicMsgService.friendLatestMsg(uid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 
	 * 根据唯一消息类型获和性别取用户好友最新消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/friendLatestMsgByMsgtype", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo friendLatestMsgByMsgtype(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String sexStr = map.get("sex");
		String msgTypeStr = map.get("msgSingleType");

		if (StrUtil.isBlank(uid) || StrUtil.isBlank(msgTypeStr) || StrUtil.isBlank(sexStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			String[] msgTypes = msgTypeStr.split(",");
			int[] msgType = new int[msgTypes.length];
			for (int i = 0; i < msgTypes.length; i++) {
				msgType[i] = Integer.parseInt(msgTypes[i]);
			}
			int sex = Integer.parseInt(sexStr);
			List<DynamicMsgForService> msgs = null;
			if (!msgTypeStr.equals("-1") && sex != -1) {// 消息和性别都过滤
				// 根据消息类型和性别筛选用户好友最新消息
				msgs = dynamicMsgService.friendLatestMsgByMsgtypeSex(uid, msgType, sex);
			} else if (msgTypeStr.equals("-1") && sex != -1) {// 只过滤性别
				// 根据性别筛选用户好友最新消息
				msgs = dynamicMsgService.friendLatestMsgBySex(uid, sex);
			} else if (!msgTypeStr.equals("-1") && sex == -1) {// 只过滤消息类型
				// 好友动态 根据消息类型获取用户好友最新消息
				msgs = dynamicMsgService.friendLatestMsgByMsgtype(uid, msgType);
			}
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
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
			// 好友动态 上拉获取下一页好友动态
			List<DynamicMsgForService> msgs = dynamicMsgService.friendUpLatestMsg(uid, mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
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
	@RequestMapping(value = "/v1/msg/friendUpLatestMsgByMsgtype", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo friendUpLatestMsgByMsgtype(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String midStr = map.get("mid");
		String msgTypeStr = map.get("msgSingleType");
		String sexStr = map.get("sex");

		if (StrUtil.isBlank(uid) || StrUtil.isBlank(msgTypeStr) || StrUtil.isBlank(midStr) || StrUtil.isBlank(sexStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			String[] msgTypes = msgTypeStr.split(",");
			int[] msgType = new int[msgTypes.length];
			for (int i = 0; i < msgTypes.length; i++) {
				msgType[i] = Integer.parseInt(msgTypes[i]);
			}
			int sex = Integer.parseInt(sexStr);
			List<DynamicMsgForService> msgs = null;
			if (!msgTypeStr.equals("-1") && sex != -1) {// 消息和性别都过滤
				// 根据消息类型和性别筛选用户好友下一页消息
				msgs = dynamicMsgService.friendUpLatestMsgByMsgtypeSex(uid, mid, msgType, sex);
			} else if (msgTypeStr.equals("-1") && sex != -1) {// 只过滤性别
				// 根据性别筛选用户好友下一页消息
				msgs = dynamicMsgService.friendUpLatestMsgBySex(uid, mid, sex);
			} else if (!msgTypeStr.equals("-1") && sex == -1) {// 只过滤消息类型
				// 好友动态 根据消息类型上拉获取下一页好友动态
				msgs = dynamicMsgService.friendUpLatestMsgByMsgtype(uid, mid, msgType);
			}
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
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
			// 好友动态 下拉刷新获取最新
			List<DynamicMsgForService> msgs = dynamicMsgService.friendDownLatestMsg(uid, mid);
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 
	 * 根据消息类型下拉刷新好友的最新消息
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/friendDownLatestMsgByMsgType", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo friendDownLatestMsgByMsgType(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String midStr = map.get("mid");
		String msgTypeStr = map.get("msgSingleType");
		String sexStr = map.get("sex");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(msgTypeStr) || StrUtil.isBlank(midStr) || StrUtil.isBlank(sexStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			int mid = Integer.parseInt(midStr);
			String[] msgTypes = msgTypeStr.split(",");
			int[] msgType = new int[msgTypes.length];
			for (int i = 0; i < msgTypes.length; i++) {
				msgType[i] = Integer.parseInt(msgTypes[i]);
			}
			int sex = Integer.parseInt(sexStr);
			List<DynamicMsgForService> msgs = null;
			if (!msgTypeStr.equals("-1") && sex != -1) {// 消息和性别都过滤
				// 根据消息类型和性别筛选用户好友最新消息
				msgs = dynamicMsgService.friendDownLatestMsgByMsgTypeSex(uid, mid, msgType, sex);
			} else if (msgTypeStr.equals("-1") && sex != -1) {// 只过滤性别
				// 根据性别筛选用户好友最新消息
				msgs = dynamicMsgService.friendDownLatestMsgBySex(uid, mid, sex);
			} else if (!msgTypeStr.equals("-1") && sex == -1) {// 只过滤消息类型
				// 好友动态 根据消息类型下拉刷新好友的最新消息
				msgs = dynamicMsgService.friendDownLatestMsgByMsgType(uid, mid, msgType);
			}
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
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
		String uid = map.get("uid");
		if (StrUtil.isBlank(idstr) || StrUtil.isBlank(uid)) {
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
			for(DynamicMsgForService dynamicMsgForService:msgs){
				//获取最新10个点赞
				dynamicMsgForService.setPriseList(dynamicMsgPriseService.priseListLimit10(dynamicMsgForService.getMid()));
				//获取最新3条评论
				dynamicMsgForService.setCommentList(dynamicMsgCommentService.latestCommentListLimit3(dynamicMsgForService.getMid()));
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setData(msgs);
			backInfo.setData(filterShieldMsg(uid, msgs));
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * -v1.0 用户点赞消息id列表
	 * 
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
			List<Integer> mids = dynamicMsgService.userPriseList(uid);
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

	/**
	 * 活动类消息报名
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/applyFor", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo applyFor(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");
		String uid = map.get("uid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(midStr) || StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		try {
			int mid = Integer.parseInt(midStr);
			// 存储活动报名用户信息
			dynamicMsgService.applyFor(mid, uid);
			// 统计报名人数
			dynamicMsgService.addApplyForNum(mid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	// /审核活动报名人员 0-未审核 1-通过 2-拒绝
	@RequestMapping(value = "/v1/msg/updateCheckState", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo updateCheckState(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");
		String uid = map.get("uid");
		String checkStateStr = map.get("checkState");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(midStr) || StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			int checkState = Integer.parseInt(checkStateStr);
			// 审核活动报名人员 0-未审核 1-通过 2-拒绝
			dynamicMsgService.updateCheckState(mid, uid, checkState);
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
	 * //获取活动类消息报名用户列表
	 * 
	 * @param dynamicMsgPrise
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/applyForList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo applyForList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String midStr = map.get("mid");
		//没有的传0
		String tribeIdStr = map.get("tribeId");

		AutoInviteBackInfo<DynamicMsgApplyForService> backInfo = new AutoInviteBackInfo<DynamicMsgApplyForService>();
		if (StrUtil.isBlank(midStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			long tribeId = Long.parseLong(tribeIdStr);
			// 获取活动类消息报名用户列表
			List<DynamicMsgApplyForService> data = dynamicMsgService.applyForList(mid);
			if (tribeId>0) {
				// 获取群信息
				ImTribe tribe = imTribeService.tribeInfo(tribeId);
				backInfo.setAutoInvite(tribe.getAutoInvite());
			}
		
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
	 * 获取用户报名审核状态获取
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/msg/applyCheckState", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo applyCheckState(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String midStr = map.get("mid");
		String uid = map.get("uid");
		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(midStr)||StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int mid = Integer.parseInt(midStr);
			// 获取用户报名审核状态获取
			DynamicMsgApplyForService applyForService = dynamicMsgService.applyCheckState(mid,uid);
			if (null==applyForService) {
				applyForService = new DynamicMsgApplyForService();
				applyForService.setCheckState(-1);
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setItem(applyForService);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
}
