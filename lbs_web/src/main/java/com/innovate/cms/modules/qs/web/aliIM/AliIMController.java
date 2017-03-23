package com.innovate.cms.modules.qs.web.aliIM;

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
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 
 * @author shifangfang
 * @date 2016年7月22日 下午2:18:54
 */
@Controller
@RequestMapping(value = "/api")
public class AliIMController extends BaseController {

	@Autowired
	private SystemUserService systemUserService;



	/**
	 * 全量同步趣选用户到aliIM系统
	 */
	@RequestMapping(value = "/v1/im/importAllUser", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delUserMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 默认同步最新1000个用户
		String lastest = map.get("lastest");
		// 添加用户的策略，如果是1就调用阿里逐个添加用户的接口
		String addSingleStr = map.get("addSingleStr");
		if (null == addSingleStr) {
			addSingleStr = "0";
		}
		int addSingle = Integer.parseInt(addSingleStr);
		// 删除标记
		String delFlag = map.get("delFlag");
		if (null == delFlag) {
			delFlag = "0";
		}
		int del = Integer.parseInt(delFlag);
		// 获取所有当前趣选用户
		List<SystemUser> users = Lists.newArrayList();

		if (null != lastest && "1".equals(lastest)) {
			users = systemUserService.findList(new SystemUser());
		} else {
			// 默认取最近新增的1000个用户进行同步
			logger.info("======================获取最近新增的1000个趣选用户======================");
			users = systemUserService.findLastest1000(new SystemUser());
		}

		if (del == 1) {
			logger.info("======================开始全量删除aliIM系统的用户======================");
			for (SystemUser user : users) {
				logger.info(IMUtils.delUser(user));
			}
			logger.info("======================全量删除aliIM系统的用户结束======================同步数量:"+users.size());
		}
		logger.info("======================开始全量同步趣选用户到aliIM系统======================");
		String res = "";
		if (addSingle == 1) {

			for (SystemUser user : users) {
				logger.info("添加用户：" + JsonMapper.toJsonString(user));
				logger.info(IMUtils.addUser(user));
			}
		} else {
			res = IMUtils.addUsers(users);
			logger.info(res);
		}
		logger.info("======================全量同步趣选用户到aliIM系统结束======================同步数量:"+users.size());
		ItemBackInfo backInfo = new ItemBackInfo();
		backInfo.setItem(res);
		backInfo.setStateCode(Global.intYES);
		backInfo.setRetMsg(Global.SUCCESS);
		return backInfo;

	}



	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex) {
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg)) {
			logger.info("参数错误" + retMsg);
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}
}
