package com.innovate.cms.modules.qs.web.ads;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;

/**
 * 配置埋点专题id集合,在cms文件内配置 app.maidian=5010,5011,5012,5013,5014
 * 
 * @author shifangfang
 * @date 2016年6月16日 下午6:35:42
 */
@Controller
@RequestMapping(value = "/api")
public class MaiDian extends BaseController {

	@RequestMapping(value = "/v1/ads/maidian", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo maidian(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// 动态加载变化，所以每次加载最新文件
		Resource resource = new ClassPathResource("/cms.properties");
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ItemBackInfo backInfo = new ItemBackInfo();
		try {

			backInfo.setItem(props.get("app.maidian"));
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	@RequestMapping(value = "/v1/ads/maidian", method = RequestMethod.GET)
	public @ResponseBody String maidian_get(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		// 动态加载变化，所以每次加载最新文件
		Resource resource = new ClassPathResource("/cms.properties");
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ItemBackInfo backInfo = new ItemBackInfo();
		try {
			backInfo.setItem(props.get("app.maidian"));
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
