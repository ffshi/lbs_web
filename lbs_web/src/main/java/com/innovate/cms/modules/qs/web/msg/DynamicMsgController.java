package com.innovate.cms.modules.qs.web.msg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.data.entity.DynamicMsgToJson;

/**
 * 
 * @author shifangfang
 * @date 2017年3月15日 下午5:15:17
 */
@Controller
@RequestMapping(value = "/api")
public class DynamicMsgController extends BaseController {

//	@Autowired
//	private DynamicMsgService dynamicMsgService;
	
	@RequestMapping(value = "/v1/msg/test", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delUserMsg(@RequestBody DynamicMsgToJson dynamicMsgToJson, HttpServletRequest request, HttpServletResponse response) {

		String description = dynamicMsgToJson.getDescription();
		

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(description)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {

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
