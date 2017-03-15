package com.innovate.cms.modules.qs.web.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.qs.entity.tag.QxTagToJson;
import com.innovate.cms.modules.qs.service.tag.QxTagService;

/**
 * 趣选标签系统 qx_tag
 * 
 * @author shifangfang
 * @date 2016年8月23日 下午2:11:49
 */
@Controller
@RequestMapping(value = "/api")
public class QxTagController extends BaseController {

	@Autowired
	private QxTagService qxTagService;

	/**
	 * for test qxTagService
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/tag", method = RequestMethod.GET)
	public @ResponseBody String get(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String uid = request.getParameter("uid");
		String gidStr = request.getParameter("gid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(callback)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", info);

		}

		try {
			int gid = Integer.parseInt(gidStr);
			// 获取专题标签
			// List<QxTagToJson> tags = qxTagService.findBygid(gid);
			// 获取用户标签

			// QxTagGroup qxTagGroup = new QxTagGroup(4105,6,"黄赌毒");
			// QxTagUser qxTagUser = new
			// QxTagUser("01f6d764622b497b9a6306aa853ddb9b",6,"黄赌毒");
			// //存储专题标签
			// qxTagService.saveQxTagGroup(qxTagGroup);
			// //存储用户标签
			// qxTagService.saveQxTagUser(qxTagUser);
			//根据用户做过的专题给用户打标签
			qxTagService.saveQxTagUserByuidgid(uid,gid);

			List<QxTagToJson> tags1 = qxTagService.findByUid(uid);

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// 此处set返回对象
			backInfo.setItem(tags1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

}
