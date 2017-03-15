/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.config;

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

import com.alibaba.fastjson.JSONObject;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.HttpClientUtil;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.TimeUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.ConfigTimeToJson;
import com.innovate.cms.modules.data.entity.UpTokenToJson;
import com.innovate.cms.modules.qs.service.config.SystemDictService;

/**
 * 用户其他信息管理Controller
 * 
 * @author gaoji
 * @version 2015-12-21
 */
@Controller
@RequestMapping(value = "/api")
public class WithConfigController extends BaseController {
	@Autowired
	private SystemDictService systemDictService;

	/**
	 * 
	 * @Title: getSystemTimeMillis
	 * @Description: v1.0 获取系统日期
	 * @param request
	 * @param response
	 * @return 返回类型 ItemBackInfo
	 *
	 */
	@RequestMapping(value = "/v1/config/time", method = RequestMethod.GET)
	public @ResponseBody ItemBackInfo getSystemTimeMillis(HttpServletRequest request, HttpServletResponse response) {
		ConfigTimeToJson configTime = new ConfigTimeToJson();
		configTime.setTimestamp(TimeUtils.getSystemTimeMillis());
		ItemBackInfo backInfo = new ItemBackInfo();
		backInfo.setStateCode(Global.intYES);
		backInfo.setRetMsg(Global.SUCCESS);
		backInfo.setItem(configTime);
		return backInfo;
	}
	
	/**
	 * 获取360图库(为小程序服务，小程序获取图片有问题)
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/get360pic", method = RequestMethod.POST)
	public @ResponseBody Object get360pic(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String keyword = map.get("keyword");

		ItemBackInfo  backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(keyword)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			String itemString = HttpClientUtil.doGet("http://m.image.so.com/j?ie=utf-8&pn=60&sn=60&src=hao_360so&q="+keyword);
			return JSONObject.parse(itemString);
//			backInfo.setItem(itemString);
//			backInfo.setStateCode(Global.intYES);
//			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}
	
	/**
	 * 
	 * @Title: getSystemTimeMillis
	 * @Description: v1.0 获取系统日期
	 * @param request
	 * @param response
	 * @return 返回类型 ItemBackInfo
	 *
	 */
	@RequestMapping(value = "/v1/config/time", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo get_SystemTimeMillis(HttpServletRequest request, HttpServletResponse response) {
		logger.info("enter /api/v1/config/time ...");
		ConfigTimeToJson configTime = new ConfigTimeToJson();
		configTime.setTimestamp(TimeUtils.getSystemTimeMillis());
		ItemBackInfo backInfo = new ItemBackInfo();
		backInfo.setStateCode(Global.intYES);
		backInfo.setRetMsg(Global.SUCCESS);
		backInfo.setItem(configTime);
		return backInfo;
	}

	/**
	 * 获取七牛token
	 * 
	 * @Title: getQNToken
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return 返回类型 ItemBackInfo
	 *
	 */
	@RequestMapping(value = "/v1/upToken", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo getQNToken(HttpServletRequest request, HttpServletResponse response) {
		ItemBackInfo backInfo = new ItemBackInfo();
		try {
			String token = Global.getUpTokenForHead();
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setItem(new UpTokenToJson(token));
		} catch (Exception e) {
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	@RequestMapping(value = "/v1/switch", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo doSwitch(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String state = map.get("state"); // 当前状态，1是打开，0是关闭。
		String uid = map.get("uid");// 操作人UID
		if (null == state) {
			state = "1";
		}
		try {
			systemDictService.doSwitch(state, uid);
			baseBackInfo.setStateCode(Global.intYES);
			baseBackInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			baseBackInfo.setStateCode(Global.intNO);
			baseBackInfo.setRetMsg(Global.ERROR);
		}
		return baseBackInfo;
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex) {
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg)) {
			logger.info("参数错误" + retMsg + "	ex:" + ex.getMessage());
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}
}