/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.ads;

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
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.HomePageToJson;
import com.innovate.cms.modules.qs.entity.ads.QxScreenAd;
import com.innovate.cms.modules.qs.entity.ads.RotationGroup;
import com.innovate.cms.modules.qs.service.ads.QxPageHomeService;

/**
 * 首页配置Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxPageHomeController extends BaseController {
	@Autowired
	private QxPageHomeService qxPageHomeService;

	@RequestMapping(value = "/v1/ads/screenAd", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo screenAd(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		ItemBackInfo backInfo = new ItemBackInfo();
		try {
			// 获取闪屏广告
			QxScreenAd screenAd = qxPageHomeService.getScreenAd();
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setItem(screenAd);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 首页接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ads/homepage", method = RequestMethod.POST)
	public @ResponseBody String getHomePage(HttpServletRequest request, HttpServletResponse response) {
		DataBackInfo<HomePageToJson> backInfo = new DataBackInfo<HomePageToJson>();
		List<HomePageToJson> data = new ArrayList<HomePageToJson>();
		try {
			data = qxPageHomeService.findAll();
			backInfo.setData(data);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setStateCode(Global.intYES);
		} catch (Exception e) {
			logger.debug("[QxPageHomeController - getHomePage()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return JsonMapper.defaultMapper().toJson(backInfo);
	}

	@RequestMapping(value = "/v1/ads/setRotationGroup", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo setRotationGroup(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String showDateStr = map.get("showDate");
		String gids = map.get("gids");

		int showDate = 0;
		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(showDateStr) || StrUtil.isBlank(gids)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			showDate = Integer.parseInt(showDateStr);
			// 根据日期设置首页轮播专题
			String[] idsStrings = gids.trim().split(",");
			for (String gidStr : idsStrings) {
				qxPageHomeService.setRotationGroup(Integer.parseInt(gidStr), showDate);
			}

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	@RequestMapping(value = "/v1/ads/getRotationGroup", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getRotationGroup(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		DataBackInfo<RotationGroup> backInfo = new DataBackInfo<RotationGroup>();
		try {
			int currentDay = Integer.parseInt(DateUtils.getDate("yyyyMMdd"));
			//获取当天轮播专题
			List<RotationGroup> groups=qxPageHomeService.getRotationGroup(currentDay);
			backInfo.setData(groups);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

}