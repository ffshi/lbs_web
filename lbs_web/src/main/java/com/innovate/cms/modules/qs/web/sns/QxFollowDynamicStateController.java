/**
 * 
 */
package com.innovate.cms.modules.qs.web.sns;

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

import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.FootprintBackInfo;
import com.innovate.cms.modules.data.entity.FollowDynamicStateToJson;
import com.innovate.cms.modules.qs.service.sns.QxFollowDynamicStateService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 好友动态控制层
 * @author hushasha
 * @date 2016年12月2日
 */

@Controller
@RequestMapping(value = "/api")
public class QxFollowDynamicStateController extends BaseController {
	@Autowired
	private QxFollowDynamicStateService qxFollowDynamicStateService;
	@Autowired
	private SystemUserService systemUserService;
	
	/**
	 * 获取好友动态---最新的20条数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFollowDynamicStateList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getFollowDynamicStateList(@RequestBody Map<String, String>map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String uid = map.get("uid");
		if (StrUtil.isBlank(uid) || uid.length() != 32) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<FollowDynamicStateToJson> backInfo = new DataBackInfo<FollowDynamicStateToJson>();
		List<FollowDynamicStateToJson> data = Lists.newArrayList();
		try {
			// 获取好友动态列表(显示最新的20条数据)
			data = qxFollowDynamicStateService.getFollowDynamicStateList(uid);
			if(data.size() > 0) {
				backInfo.setData(data);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|FollowDynamicStateToJson对象={}", data);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}
	
	/**
	 * 上拉显示更多好友动态(显示小于dsid的20条数据)
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/showMoreDynamicStates", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo showMoreDynamicStates(@RequestBody Map<String, String>map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String uid = map.get("uid");
		String dsid = map.get("dsid");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(dsid) || uid.length() != 32 || !StrUtil.isPositiveNum(dsid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<FollowDynamicStateToJson> backInfo = new DataBackInfo<FollowDynamicStateToJson>();
		List<FollowDynamicStateToJson> data = Lists.newArrayList();
		try {
			// 上拉显示更多好友动态(显示小于dsid的20条数据)
			if(Integer.valueOf(dsid) > 1) {
				data = qxFollowDynamicStateService.showMoreDynamicStates(uid, Integer.valueOf(dsid));
			}
			if(data.size() > 0) {
				backInfo.setData(data);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|FollowDynamicStateToJson对象={}", data);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}
	
	/**
	 * 下拉刷新好友动态，显示大于dsid的所有数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/refreshDynamicStates", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo refreshDynamicStates(@RequestBody Map<String, String>map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String uid = map.get("uid");
		String dsid = map.get("dsid");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(dsid) || uid.length() != 32 || !StrUtil.isPositiveNum(dsid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<FollowDynamicStateToJson> backInfo = new DataBackInfo<FollowDynamicStateToJson>();
		List<FollowDynamicStateToJson> data = Lists.newArrayList();
		try {
			// 下拉刷新好友动态，显示大于dsid的所有数据
			data = qxFollowDynamicStateService.refreshDynamicStates(uid, Integer.valueOf(dsid));
			if(data.size() > 0) {
				backInfo.setData(data);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|FollowDynamicStateToJson对象={}", data);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}
	
	/**
	 * 获取足迹列表---最新的20条数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFootprintList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getFootprintList(@RequestBody Map<String, String>map, HttpServletRequest request, HttpServletResponse response) {
		FootprintBackInfo<FollowDynamicStateToJson> backInfo = new FootprintBackInfo<FollowDynamicStateToJson>();
		String uid = map.get("uid");
		if (StrUtil.isBlank(uid) || uid.length() != 32) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		List<FollowDynamicStateToJson> data = Lists.newArrayList();
		try {
			// 获取足迹列表(显示最新的20条数据)
			data = qxFollowDynamicStateService.getFootprintList(uid);
			backInfo.setData(data);
			//获取作品数
			backInfo.setUgcCount(systemUserService.getUgcNum(uid));
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
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
	 * 上拉显示更多足迹(显示小于dsid的20条数据)
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/showMoreFootprints", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo showMoreFootprints(@RequestBody Map<String, String>map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String uid = map.get("uid");
		String dsid = map.get("dsid");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(dsid) || uid.length() != 32 || !StrUtil.isPositiveNum(dsid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<FollowDynamicStateToJson> backInfo = new DataBackInfo<FollowDynamicStateToJson>();
		List<FollowDynamicStateToJson> data = Lists.newArrayList();
		try {
			// 上拉显示更多好友动态(显示小于dsid的20条数据)
			if(Integer.valueOf(dsid) > 1) {
				data = qxFollowDynamicStateService.showMoreFootprints(uid, Integer.valueOf(dsid));
			}
			backInfo.setData(data);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			return backInfo;
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}
	
	/**
	 * 下拉刷新好友动态，显示大于dsid的所有数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/refreshFootprints", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo refreshFootprints(@RequestBody Map<String, String>map, HttpServletRequest request, HttpServletResponse response) {
		FootprintBackInfo<FollowDynamicStateToJson> backInfo = new FootprintBackInfo<FollowDynamicStateToJson>();
		String uid = map.get("uid");
		String dsid = map.get("dsid");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(dsid) || uid.length() != 32 || !StrUtil.isPositiveNum(dsid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		
		List<FollowDynamicStateToJson> data = Lists.newArrayList();
		try {
			// 下拉刷新好友动态，显示大于dsid的所有数据
			data = qxFollowDynamicStateService.refreshFootprints(uid, Integer.valueOf(dsid));
			backInfo.setData(data);
			//获取作品数
			backInfo.setUgcCount(systemUserService.getUgcNum(uid));
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);	
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

}
