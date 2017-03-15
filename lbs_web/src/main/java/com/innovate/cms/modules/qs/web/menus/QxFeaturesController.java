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

import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.data.entity.MenuFeaturesToJson;
import com.innovate.cms.modules.qs.entity.menus.FeatureMenu;
import com.innovate.cms.modules.qs.service.menus.QxFeaturesService;

/**
 * 专题组Controller
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxFeaturesController extends BaseController {

	@Autowired
	private QxFeaturesService qxFeaturesService;
	
	/**
	 * 
	 * @Title: getFeaturesByCid
	 * @Description: 根据分类查询所有专题组
	 * @param map
	 * @param request
	 * @param response
	 * @return    返回类型 DataBackInfo<?>
	 *
	 */
	@RequestMapping(value = "/v1/menu/features", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getFeaturesByCid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		
		// 专题组id
		String cid = map.get("cid");
		DataBackInfo<MenuFeaturesToJson> backInfo = new DataBackInfo<MenuFeaturesToJson>();
		List<MenuFeaturesToJson> features = Lists.newArrayList();
		if (StrUtil.isBlank(cid)||!StrUtil.isNum(cid))
		{
			logger.debug("QxFeaturesController - getFeaturesByCid - 参数错误cid=：{}",cid);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);	
			backInfo.setData(features);
		}else{
			try {
				features = qxFeaturesService.getFeaturesByCid(Integer.parseInt(cid));
				backInfo.setData(features);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				backInfo.setRetMsg(Global.ERROR);
				backInfo.setStateCode(Global.intNO);
			}
		}	
		return backInfo;
	}
	
	@RequestMapping(value = "/v1/menu/allNavigationMenus", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> allNavigationMenus(HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<FeatureMenu> backInfo = new DataBackInfo<FeatureMenu>();

		try {
			List<FeatureMenu> menus = qxFeaturesService.getAllFeatures();
			backInfo.setData(menus);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
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