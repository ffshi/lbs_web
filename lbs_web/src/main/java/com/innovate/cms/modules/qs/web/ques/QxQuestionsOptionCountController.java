/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.ques;

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
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.qs.service.ques.QxQuestionsOptionCountService;

/**
 * 趣选选项答案统计表Controller
 * 
 * @author shifangfang
 *
 */
@Controller
@RequestMapping(value = "/api")
public class QxQuestionsOptionCountController extends BaseController {

	@Autowired
	private QxQuestionsOptionCountService qxQuestionsOptionCountService;

	/**
	 * app统计问题选项被选择次数
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/countOption", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo countOption(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String qaid = map.get("qaid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(qaid)) {

			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			qxQuestionsOptionCountService.increaseCount(Integer.parseInt(qaid));
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
	 * H5统计问题选项被选择次数
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/countOption", method = RequestMethod.GET)
	public @ResponseBody String countOptionH5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String qaid = request.getParameter("qaid");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(qaid)||!StrUtil.isNum(qaid)) {

			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP(callback, backInfo);
		}
		try {
			qxQuestionsOptionCountService.increaseCount(Integer.parseInt(qaid));
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

}