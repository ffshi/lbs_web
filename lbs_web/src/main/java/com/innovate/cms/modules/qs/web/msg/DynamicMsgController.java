package com.innovate.cms.modules.qs.web.msg;

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
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;
import com.innovate.cms.modules.qs.entity.msg.DynamicMsgForService;
import com.innovate.cms.modules.qs.service.msg.DynamicMsgService;

/**
 * 
 * @author shifangfang
 * @date 2017年3月15日 下午5:15:17
 */
@Controller
@RequestMapping(value = "/api")
public class DynamicMsgController extends BaseController {

	@Autowired
	private DynamicMsgService dynamicMsgService;

	@RequestMapping(value = "/v1/msg/save", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo save(@RequestBody DynamicMsgToJson dynamicMsgToJson, HttpServletRequest request, HttpServletResponse response) {

		String description = dynamicMsgToJson.getDescription();
		String uidString = dynamicMsgToJson.getUid();

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(description) || StrUtil.isBlank(uidString)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			dynamicMsgService.save(dynamicMsgToJson);
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
	@RequestMapping(value = "/v1/msg/lastesMsg", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo lastesMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		DataBackInfo<DynamicMsgForService> backInfo = new DataBackInfo<DynamicMsgForService>();
		try {
			//未获取用户位置信息时获取首页信息接口
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

}
