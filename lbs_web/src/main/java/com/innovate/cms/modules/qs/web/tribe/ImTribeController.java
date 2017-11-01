package com.innovate.cms.modules.qs.web.tribe;

import java.util.ArrayList;
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

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.sensitiveWord.ChatFilter;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.qs.entity.tribe.ImTribe;
import com.innovate.cms.modules.qs.entity.tribe.ImTribeToJSON;
import com.innovate.cms.modules.qs.service.tribe.ImTribeService;

/**
 * 群组
 * 
 * @author shifangfang
 * @date 2017年4月8日 上午10:44:22
 */
@Controller
@RequestMapping(value = "/api")
public class ImTribeController extends BaseController {

	@Autowired
	private ImTribeService imTribeService;

	private static ChatFilter filter = null;

	/**
	 * 存储群组
	 * 
	 * @param dynamicMsgToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/save", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo save(@RequestBody ImTribeToJSON imTribeToJSON, HttpServletRequest request, HttpServletResponse response) {

		BaseBackInfo backInfo = new BaseBackInfo();
		String name = imTribeToJSON.getName();
		String tribeImg = imTribeToJSON.getTribeImg();
		String introduction = imTribeToJSON.getIntroduction();
		String location = imTribeToJSON.getLocation();
		String locationName = imTribeToJSON.getLocationName();
		if (StrUtil.isBlank(name) || StrUtil.isBlank(tribeImg) || StrUtil.isBlank(introduction) || StrUtil.isBlank(locationName) || StrUtil.isBlank(location)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 存储群组创建信息
			imTribeService.save(imTribeToJSON);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setItem(imTribeToJSON);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	/**
	 * 存储为活动报名而建立的群组
	 * 
	 * @param dynamicMsgToJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/saveBelongtoMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo saveBelongtoMsg(@RequestBody ImTribeToJSON imTribeToJSON, HttpServletRequest request, HttpServletResponse response) {
		
		BaseBackInfo backInfo = new BaseBackInfo();
		String name = imTribeToJSON.getName();
		String tribeImg = imTribeToJSON.getTribeImg();
		String introduction = imTribeToJSON.getIntroduction();
		String location = imTribeToJSON.getLocation();
		String locationName = imTribeToJSON.getLocationName();
		if (StrUtil.isBlank(name) || StrUtil.isBlank(tribeImg) || StrUtil.isBlank(introduction) || StrUtil.isBlank(locationName) || StrUtil.isBlank(location) || imTribeToJSON.getMid()==0) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 存储群组创建信息
			imTribeService.save(imTribeToJSON);
			//关联报名建立的群组与消息
			imTribeService.connectMsgWithTribeId(imTribeToJSON.getTribeId(),imTribeToJSON.getMid());
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// backInfo.setItem(imTribeToJSON);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 获取附近群组
	 * 
	 * 根据指定群组tribeIds获取一批群组
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/nearTribe", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo nearTribe(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String tribeIds = map.get("tribeIds");
		if (StrUtil.isBlank(tribeIds)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		String[] ids = tribeIds.split(",");
		long[] mids = new long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			mids[i] = Long.parseLong(ids[i]);
		}
		DataBackInfo<ImTribe> backInfo = new DataBackInfo<ImTribe>();
		try {
			// 根据指定群组tribeIds获取一批群组
			List<ImTribe> msgs = imTribeService.nearTribe(mids);
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
	 * 获取运营团队维护的群组
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/operationTribe", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo operationTribe(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		String tribeIds = map.get("tribeIds");
		if (StrUtil.isBlank(tribeIds)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		String[] ids = tribeIds.split(",");
		long[] mids = new long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			mids[i] = Long.parseLong(ids[i]);
		}
		DataBackInfo<ImTribe> backInfo = new DataBackInfo<ImTribe>();
		try {
			// 获取运营团队维护的群组
			List<ImTribe> msgs = imTribeService.operationTribe();
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
	 * 获取用户所属群组列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/userTribes", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo userTribes(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		long[] tribeIds = IMUtils.getAllTribesId(uid);

		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}

		DataBackInfo<ImTribe> backInfo = new DataBackInfo<ImTribe>();
		try {
			// 根据指定群组tribeIds获取一批群组
			List<ImTribe> msgs = new ArrayList<>();
			if (tribeIds.length > 0) {
				msgs = imTribeService.nearTribe(tribeIds);
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
	 * 获取群信息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/tribeInfo", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo tribeInfo(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String tribeIdStr = map.get("tribeId");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(tribeIdStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			long tribeId = Long.parseLong(tribeIdStr);
			// 获取群信息
			ImTribe tribe = imTribeService.tribeInfo(tribeId);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setItem(tribe);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 解散群组 需要真正的删除该数据，因为阿里IM会重复使用id
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/delTribe", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delTribe(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String tribeIdStr = map.get("tribeId");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(tribeIdStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			long tribeId = Long.parseLong(tribeIdStr);
			// 解散群组
			imTribeService.delTribe(tribeId);
			//删除群组后dynamic_msg的关联tibleid设置为0
			imTribeService.delTribleId(tribeId);
			
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
	 * 更新/编辑群组信息
	 * 
	 * @param imTribeToJSON
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/updateTribe", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo updateTribe(@RequestBody ImTribeToJSON imTribeToJSON, HttpServletRequest request, HttpServletResponse response) {

		long tribeId = imTribeToJSON.getTribeId();

		//
		BaseBackInfo backInfo = new BaseBackInfo();
		if (tribeId == 0) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 更新/编辑群组信息
			imTribeService.updateTribe(imTribeToJSON);
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
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tribe/filterTribeName", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo filterTribeName(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String text = map.get("text");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(text)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {

			if (filter == null) {
				filter = new ChatFilter();
			}
			if (filter.filte(text).contains("*")) {
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			} else {
				backInfo.setStateCode(Global.intNO);
				backInfo.setRetMsg(Global.SUCCESS);
			}

		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

}
