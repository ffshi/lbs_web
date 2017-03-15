/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.menus;

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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.qs.entity.menus.GroupRecommend;
import com.innovate.cms.modules.qs.entity.menus.Groups;
import com.innovate.cms.modules.qs.entity.menus.QxGroups;
import com.innovate.cms.modules.qs.service.menus.QxGroupsService;
import com.innovate.cms.modules.qs.service.sns.QxFollowDynamicStateService;

/**
 * 专题表Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxGroupsController extends BaseController {

	@Autowired
	private QxGroupsService qxGroupsService;
	@Autowired
	private QxFollowDynamicStateService qxFollowDynamicStateService;

	/**
	 * 热门专题推荐接口（结果页）
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/getHotRecommend", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getHotRecommend(@RequestBody Map<String, String> map,
			HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		String uid = map.get("uid");
		DataBackInfo<GroupRecommend> backInfo = new DataBackInfo<GroupRecommend>();
		
		// 简单校验
		if (StrUtil.isBlank(gid) || !StrUtil.isNum(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(StrUtil.isNotBlank(uid)) { //如果传了用户ID过来
			if(uid.length() != 32) { //判断uid是不是32位
				backInfo.setStateCode(Global.int300209);
				backInfo.setRetMsg(Global.str300209);
				return backInfo;
			}
		}

		try {
			List<GroupRecommend> recommends = qxGroupsService.getHotGroup(Integer.valueOf(gid), uid);
			//logger.debug(JsonMapper.getInstance().toJson(recommends));
			backInfo.setData(recommends);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupsController - getHotRecommend()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * H5热门专题推荐接口（结果页）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/getHotRecommend", method = RequestMethod.GET)
	public @ResponseBody String getHotRecommendH5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String gid = request.getParameter("gid");
		String uid =  request.getParameter("uid");
		DataBackInfo<GroupRecommend> backInfo = new DataBackInfo<GroupRecommend>();
		// 简单校验
		if (StrUtil.isBlank(callback) || StrUtil.isBlank(gid) || !StrUtil.isNum(gid) || StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("", backInfo);
		}
		if(uid.length() != 32) { //判断uid是不是32位
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("", backInfo);
		}

		try {
			List<GroupRecommend> recommends = qxGroupsService.getHotGroup(Integer.valueOf(gid), uid);
			//logger.debug(JsonMapper.getInstance().toJson(recommends));
			backInfo.setData(recommends);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupsController - getHotRecommendH5()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}
	
	/**
	 * 随机专题推荐接口（结果页）
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/getRandomRecommend", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getRandomRecommend(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		DataBackInfo<GroupRecommend> backInfo = new DataBackInfo<GroupRecommend>();
		List<GroupRecommend> recommends = Lists.newArrayList();
		// 简单校验
		if (StrUtil.isBlank(gid) || !StrUtil.isPositiveNum(gid)) {
			backInfo.setData(recommends);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			recommends = qxGroupsService.getRandomGroup(gid);
			backInfo.setData(recommends);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupsController - getRandomRecommend()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * H5随机专题推荐接口（结果页）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/getRandomRecommend", method = RequestMethod.GET)
	public @ResponseBody String getRandomRecommendH5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String gid = request.getParameter("gid");
		DataBackInfo<GroupRecommend> backInfo = new DataBackInfo<GroupRecommend>();
		List<GroupRecommend> recommends = Lists.newArrayList();
		// 简单校验
		if (StrUtil.isBlank(callback) || StrUtil.isBlank(gid) || !StrUtil.isNum(gid)) {
			backInfo.setData(recommends);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("", backInfo);
		}

		try {
			recommends = qxGroupsService.getRandomGroup(gid);
			backInfo.setData(recommends);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupsController - getRandomRecommendH5()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}
	
	/**
	 * 专题点赞或取消点赞接口
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/praiseOrCancel", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo savePraiseOrDel(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid"); //专题ID
		String flag = map.get("flag"); //0点赞 1取消点赞
		BaseBackInfo backInfo = new BaseBackInfo();
		// 简单校验
		if(Strings.isNullOrEmpty(gid) || Strings.isNullOrEmpty(flag) || !StrUtil.isPositiveNum(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(!"0".equals(flag) && !"1".equals(flag)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			qxGroupsService.savePraiseOrDel(Integer.valueOf(gid), flag);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupsController - savePraiseOrDel()方法报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * 创建专题(UGC内容)
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/createEssentialGroup", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo createEssentialGroup(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		final String uid = map.get("uid"); //创建者ID
		final String groupName = map.get("groupName"); //专题名称
		String optionA = map.get("optionA"); //选项A
		String optionB = map.get("optionB"); //选项B
		ItemBackInfo backInfo = new ItemBackInfo();
		// 简单校验
		if(Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(groupName) || Strings.isNullOrEmpty(optionA) || Strings.isNullOrEmpty(optionB) || uid.length() != 32) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			final int gid = qxGroupsService.saveEssentialGroup(uid, groupName, optionA, optionB);
			QxGroups groups = new QxGroups();
			groups.setGid(gid);
			groups.setFsid(1); //暂时默认站边专题的分类ID为1
			backInfo.setItem(groups);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			
			//新起一个线程，保存创建专题记录到好友动态表
			new Thread(new Runnable() {
				@Override
				public void run() {
					qxFollowDynamicStateService.saveCreateGroupDynamicState(uid, gid, groupName);
				}
			}).start();
		} catch (Exception e) {
			logger.debug("[QxGroupsController - createEssentialGroup()方法报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * 根据fsid获取专题列表--显示最新的10条数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/getGroupsListByFsid", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getGroupsListByFsid(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String fsid = map.get("fsid");
		// 参数简单校验
		if (Strings.isNullOrEmpty(fsid) || !StrUtil.isPositiveNum(fsid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<Groups> backInfo = new DataBackInfo<Groups>();
		List<Groups> groups = Lists.newArrayList();
		try {
			groups = qxGroupsService.getGroupsListByFsid(Integer.valueOf(fsid));
			if(groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
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
	 * 上拉显示更多专题--显示小于gid的10条数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/showMoreGroupsByFsid", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo showMoreGroupsByFsid(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String fsid = map.get("fsid");
		String gid = map.get("gid");
		// 参数简单校验
		if (Strings.isNullOrEmpty(fsid) || Strings.isNullOrEmpty(gid) || !StrUtil.isPositiveNum(fsid) || !StrUtil.isPositiveNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<Groups> backInfo = new DataBackInfo<Groups>();
		List<Groups> groups = Lists.newArrayList();
		try {
			if(Integer.valueOf(gid) > 1) { //gid为1的话,没有比gid小的专题,直接返回
				groups = qxGroupsService.showMoreGroupsByFsid(Integer.valueOf(fsid), Integer.valueOf(gid));
			}
			if(groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
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
	 * 下拉刷新专题列表--显示大于gid的所有数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/refreshGroupsByFsid", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo refreshGroupsByFsid(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String fsid = map.get("fsid");
		String gid = map.get("gid");
		// 参数简单校验
		if (Strings.isNullOrEmpty(fsid) || Strings.isNullOrEmpty(gid) || !StrUtil.isPositiveNum(fsid) || !StrUtil.isPositiveNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<Groups> backInfo = new DataBackInfo<Groups>();
		List<Groups> groups = Lists.newArrayList();
		try {
			groups = qxGroupsService.refreshGroupsByFsid(Integer.valueOf(fsid), Integer.valueOf(gid));
			if(groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
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
	
	
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex)
	{
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg))
		{
			logger.info("参数错误" + retMsg);
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}

}