/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

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
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfoExtend;
import com.innovate.cms.modules.common.entity.RankDataBackInfo;
import com.innovate.cms.modules.data.entity.FootprintRankToJson;
import com.innovate.cms.modules.data.entity.Templet4RankExtendToJson;
import com.innovate.cms.modules.data.entity.Templet4RankToJson;
import com.innovate.cms.modules.data.entity.TempletRankToJson;
import com.innovate.cms.modules.qs.entity.menus.Group;
import com.innovate.cms.modules.qs.service.menus.QxGroupsService;
import com.innovate.cms.modules.qs.service.ques.QxHistoryAnswerService;
import com.innovate.cms.modules.qs.service.sns.QxMatchService;

/**
 * 
 * @ClassName: QxWithRankController
 * @Description: 排行榜相关接口
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年5月12日 下午4:30:41
 *
 */
@Controller
@RequestMapping(value = "/api")
public class QxWithRankController extends BaseController {

	@Autowired
	QxMatchService qxMatchService;
	@Autowired
	QxHistoryAnswerService qxHistoryAnswerService;
	@Autowired
	private QxGroupsService qxGroupsService;
	/**
	 * 
	 * @Title: findRankByFootprintByUid
	 * @Description: 查询足迹排行榜
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<FootprintRankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByFootprint", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<FootprintRankToJson> findRankByFootprintByUid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		// 用户id
		String uid = map.get("uid");
		// 专题gid
		String gid = map.get("gid");

		RankDataBackInfo<FootprintRankToJson> backInfo = new RankDataBackInfo<FootprintRankToJson>();
		List<FootprintRankToJson> footprintRankToJsons = Lists.newArrayList();

		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid)) {
			backInfo.setData(footprintRankToJsons);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据
			// 调用是否新增数据的逻辑
			/*
			 * try { // 更新排行榜数据 qxMatchService.updateRankDatas(uid, _gid); }
			 * catch (Exception e) { logger.debug(
			 * "[QxWithRankController - findRankByFootprintByUid(),更新排行榜异常：{}]",
			 * e.getMessage()); }
			 */
			List<FootprintRankToJson> footprintRanks = qxMatchService.findRankByFootprint(uid, _gid);
			if (footprintRanks.size() > 0) {
				backInfo.setTotalResult(1);// 无分页 总页数就0
				backInfo.setData(footprintRanks);
			} else {
				backInfo.setData(footprintRankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			//获取专题排行榜标题
			Group group = qxGroupsService.getGroupBygid(Integer.parseInt(gid));
			if(null!=group){
				backInfo.setRankTitle(group.getRankTitle());
			}else {
				backInfo.setRankTitle("");
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);

		} catch (Exception e) {
			logger.debug("[QxWithRankController - findRankByFootprintByUid()接口报错：{}]", e.getMessage());
			backInfo.setData(footprintRankToJsons);
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 
	 * @Title: findRankByTemplet5ByUid
	 * @Description: 查询模板5排行榜
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<TempletRankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByTemplet5", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<TempletRankToJson> findRankByTemplet5ByUid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 用户id
		String uid = map.get("uid");
		// 专题gid
		String gid = map.get("gid");

		RankDataBackInfo<TempletRankToJson> backInfo = new RankDataBackInfo<TempletRankToJson>();
		List<TempletRankToJson> templetRankToJsons = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid)) {
			backInfo.setData(templetRankToJsons);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据
			/*
			 * try { // 更新排行榜数据 qxMatchService.updateRankDatas(uid, _gid); }
			 * catch (Exception e) { logger.debug(
			 * "[QxWithRankController - findRankByTemplet5ByUid(),更新排行榜异常：{}]",
			 * e.getMessage()); }
			 */
			List<TempletRankToJson> templetRanks = qxMatchService.findRankByTemplet5(uid, _gid);
			if (templetRanks.size() > 0) {
				backInfo.setData(templetRanks);
				backInfo.setTotalResult(1);// 无分页 总页数就0
			} else {
				backInfo.setData(templetRankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			//获取专题排行榜标题
			Group group = qxGroupsService.getGroupBygid(Integer.parseInt(gid));
			if(null!=group){
				backInfo.setRankTitle(group.getRankTitle());
			}else {
				backInfo.setRankTitle("");
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			backInfo.setData(templetRankToJsons);
			logger.debug("[QxWithRankController - findRankByTemplet5ByUid()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return backInfo;
	}

	/**
	 * H5查询模板5排行榜,按照时间倒序输出
	 * 
	 * @Title: findRankByTemplet5ByUid
	 * @Description:
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<TempletRankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByTemplet5TimeDesc", method = RequestMethod.GET)
	public @ResponseBody String findRankByTemplet5ByUidTimeDesc(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		// 用户id
		String uid = request.getParameter("uid");
		// 专题gid
		String gid = request.getParameter("gid");

		DataBackInfo<TempletRankToJson> backInfo = new DataBackInfo<TempletRankToJson>();
		List<TempletRankToJson> templetRankToJsons = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(callback)||!StrUtil.isUID1(uid)||!StrUtil.isNum(gid)) {
			backInfo.setData(templetRankToJsons);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("param is blank", backInfo);

		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据
			// try {
			// // 更新排行榜数据，h5不需要
			// qxMatchService.updateRankDatas(uid, _gid);
			// } catch (Exception e) {
			// logger.debug("[" +
			// Thread.currentThread().getStackTrace()[1].getClassName() + " - "
			// + Thread.currentThread().getStackTrace()[1].getMethodName() +
			// "()接口报错：{}]", e.getMessage());
			//
			// }
			List<TempletRankToJson> templetRanks = qxMatchService.findRankByTemplet5ListTimeDescH5(uid, _gid);
			if (templetRanks.size() > 0) {
				backInfo.setData(templetRanks);
				backInfo.setTotalResult(1);// 无分页 总页数就0
			} else {
				backInfo.setData(templetRankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());

			backInfo.setData(templetRankToJsons);
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * H5版本 按照匹配度倒序
	 * 
	 * @Title: findRankByTemplet5ByUid
	 * @Description: 查询模板5排行榜
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<TempletRankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByTemplet5", method = RequestMethod.GET)
	public @ResponseBody String findRankByTemplet5ByUidH5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		// 用户id
		String uid = request.getParameter("uid");
		// 专题gid
		String gid = request.getParameter("gid");

		DataBackInfo<TempletRankToJson> backInfo = new DataBackInfo<TempletRankToJson>();
		List<TempletRankToJson> templetRankToJsons = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(callback)||!StrUtil.isUID1(uid)||!StrUtil.isNum(gid)) {
			backInfo.setData(templetRankToJsons);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("param is blank", backInfo);
		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据
			// try {
			// // 更新排行榜数据,h5不需要
			// qxMatchService.updateRankDatas(uid, _gid);
			// } catch (Exception e) {
			// logger.debug("[" +
			// Thread.currentThread().getStackTrace()[1].getClassName() + " - "
			// + Thread.currentThread().getStackTrace()[1].getMethodName() +
			// "()接口报错：{}]", e.getMessage());
			//
			// }findRankByTemplet5ListH5
			List<TempletRankToJson> templetRanks = qxMatchService.findRankByTemplet5ListH5(uid, _gid);
			if (templetRanks.size() > 0) {
				backInfo.setData(templetRanks);
				backInfo.setTotalResult(1);// 无分页 总页数就0
			} else {
				backInfo.setData(templetRankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			backInfo.setData(templetRankToJsons);
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());

			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 
	 * @Title: findRankByTemplet4ByUid
	 * @Description: 查询模板四排行榜
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<Templet4RankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByTemplet4", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<Templet4RankToJson> findRankByTemplet4ByUid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		// 用户id
		String uid = map.get("uid");
		// 专题gid
		String gid = map.get("gid");

		RankDataBackInfo<Templet4RankToJson> backInfo = new RankDataBackInfo<Templet4RankToJson>();
		List<Templet4RankToJson> templet4RankToJsons = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid)) {
			backInfo.setData(templet4RankToJsons);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据

			List<Templet4RankToJson> templet4Ranks = qxMatchService.findRankByTemplet4(uid, _gid);
			if (templet4Ranks.size() > 0) {
				backInfo.setData(templet4Ranks);
				backInfo.setTotalResult(1);// 无分页 总页数就0
			} else {
				backInfo.setData(templet4RankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			//获取专题排行榜标题
			Group group = qxGroupsService.getGroupBygid(Integer.parseInt(gid));
			if(null!=group){
				backInfo.setRankTitle(group.getRankTitle());
			}else {
				backInfo.setRankTitle("");
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			backInfo.setData(templet4RankToJsons);
			logger.debug("[QxWithRankController - findRankByTemplet4ByUid()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return backInfo;
	}

	/**
	 * h5 专用
	 * 
	 * @Title: findRankByTemplet4ByUid
	 * @Description: 查询模板四排行榜
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<Templet4RankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByTemplet4", method = RequestMethod.GET)
	public @ResponseBody String findRankByTemplet4ByUid(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		// 用户id
		String uid = request.getParameter("uid");
		// 专题gid
		String gid = request.getParameter("gid");
		
		
		DataBackInfo<Templet4RankToJson> backInfo = new DataBackInfo<Templet4RankToJson>();
		List<Templet4RankToJson> templet4RankToJsons = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(callback)||!StrUtil.isUID1(uid)||!StrUtil.isNum(gid)) {
			BaseBackInfo errorInfo = new BaseBackInfo(); 
			errorInfo.setStateCode(Global.int300209);
			errorInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", errorInfo);
		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据

			// h5模板4排行榜获取
			List<Templet4RankToJson> templet4Ranks = qxMatchService.findRankByTemplet4h5(uid, _gid);
			if (templet4Ranks.size() > 0) {
				backInfo.setData(templet4Ranks);
				backInfo.setTotalResult(1);// 无分页 总页数就0
			} else {
				backInfo.setData(templet4RankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			backInfo.setData(templet4RankToJsons);
			logger.debug("[QxWithRankController - findRankByTemplet4ByUid()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * h5病毒推广 专用 查询模板四排行榜
	 * 
	 * @Title: findRankByTemplet4ByUid
	 * @Description: 查询模板四排行榜
	 * @param request
	 * @param response
	 * @return 返回类型 DataBackInfo<Templet4RankToJson>
	 *
	 */
	@RequestMapping(value = "/v1/sns/findRankByTemplet4Extend", method = RequestMethod.GET)
	public @ResponseBody String findRankByTemplet4ByUidExtend(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		// 用户id
		String uid = request.getParameter("uid");
		// 专题gid
		String gid = request.getParameter("gid");

		DataBackInfoExtend<Templet4RankExtendToJson> backInfo = new DataBackInfoExtend<Templet4RankExtendToJson>();
		List<Templet4RankExtendToJson> templet4RankToJsons = Lists.newArrayList();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gid) || StrUtil.isBlank(callback)) {
			BaseBackInfo errorInfo = new BaseBackInfo();
			errorInfo.setStateCode(Global.int300209);
			errorInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", errorInfo);
		}

		try {
			int _gid = Integer.parseInt(gid);
			// 1.查询缓存
			// 2.如果缓存存在直接返回
			// 3.如果缓存不存在查询数据库
			// 4.写入缓存
			// 5.返回数据

			// h5推广模板4排行榜获取
			List<Templet4RankExtendToJson> templet4Ranks = qxMatchService.findRankByTemplet4h5Extend(uid, _gid);
			if (templet4Ranks.size() > 0) {
				backInfo.setData(templet4Ranks);
				backInfo.setRankTitle(templet4Ranks.get(0).getRankTitle());
				backInfo.setTotalResult(1);// 无分页 总页数就0
			} else {
				backInfo.setData(templet4RankToJsons);
				backInfo.setTotalResult(0);// 无分页 总页数就0
			}
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			backInfo.setData(templet4RankToJsons);
			logger.debug("[QxWithRankController - findRankByTemplet4ByUid()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
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