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

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.qs.entity.menus.Group;
import com.innovate.cms.modules.qs.entity.menus.Menu;
import com.innovate.cms.modules.qs.service.menus.QxChannelsService;
import com.innovate.cms.modules.qs.service.menus.QxGroupsService;

/**
 * 分类表Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxChannelsController extends BaseController {

	@Autowired
	private QxChannelsService qxChannelsService;

	@Autowired
	private QxGroupsService qxGroupsService;

	/**
	 * 
	 * @Title: allMenus
	 * @Description: 拉取首页分类
	 * @param map
	 * @param request
	 * @param response
	 * @return    返回类型 DataBackInfo<?>
	 *
	 */
	@RequestMapping(value = "/v1/menu/allMenus", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> allMenus(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<Menu> backInfo = new DataBackInfo<Menu>();

		try {
			List<Menu> menus = qxChannelsService.getAllMenus();
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

	/**
	 * 根据专题组获取专题组下的所有专题
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menu/group", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<?> getGroupByfid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 专题组id
		String fsid = map.get("fsid");

		DataBackInfo<Group> backInfo = new DataBackInfo<Group>();

		try {
			List<Group> groups = qxGroupsService.getGroupByfsid(Integer.parseInt(fsid));
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