/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

import java.util.ArrayList;
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

import com.google.common.base.Strings;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.BeanMapper;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.FootProntBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.data.entity.VisibleToJson;
import com.innovate.cms.modules.qs.entity.sns.FootprintWithPraiseAndComment;
import com.innovate.cms.modules.qs.entity.sns.QxFootprint;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintWithPraiseAndComment;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.ques.QxHistoryService;
import com.innovate.cms.modules.qs.service.sns.QxFootprintService;
import com.innovate.cms.modules.qs.service.sns.QxMatchService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 用户足迹表 qx_footprint Controller
 * 
 * @author shifangfang
 *
 */
@Controller
@RequestMapping(value = "/api")
public class QxFootprintController extends BaseController {

	@Autowired
	private QxFootprintService qxFootprintService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private QxHistoryService qxHistoryService;
	@Autowired
	private QxMatchService qxMatchService;

	/**
	 * 获取用户足迹信息
	 * 
	 * 上拉加载历史： 小于footid的10条
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFootprintup", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getFootprintup(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		// 上拉加载历史： 小于footid的10条
		// 获取足迹的参照足迹id
		String footid = map.get("footid");

		FootProntBackInfo<QxFootprintWithPraiseAndComment> backInfo = new FootProntBackInfo<QxFootprintWithPraiseAndComment>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(footid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			List<QxFootprintWithPraiseAndComment> footPrints = qxFootprintService.getFootprintByuidup(uid, Integer.parseInt(footid));
			if (footPrints.size() > 0) {
				backInfo.setFootImg(footPrints.get(0).getFootImg());
				backInfo.setUid(footPrints.get(0).getUid());
				backInfo.setFootName(footPrints.get(0).getFootName());
			}
			backInfo.setData(footPrints);
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
	 * 
	 * 下拉刷新 获取最新
	 * 
	 * 无缓存：最新10条
	 * 
	 * 有缓存：返回大于footid所有最新数据
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFootprintdown", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getFootprintdown(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		// isCached=1有缓存 0无缓存
		String isCached = map.get("isCached");
		// 获取足迹的起始点
		String footid = map.get("footid");
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(isCached) || StrUtil.isBlank(footid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		FootProntBackInfo<QxFootprintWithPraiseAndComment> backInfo = new FootProntBackInfo<QxFootprintWithPraiseAndComment>();
		try {
			List<QxFootprintWithPraiseAndComment> footPrints = new ArrayList<QxFootprintWithPraiseAndComment>();
			if ("1".equals(isCached)) {// 返回大于footid所有最新数据
				footPrints = qxFootprintService.getFootprintByuidAllnew(uid, Integer.parseInt(footid));
			} else {
				// 返回最新10条足迹数据
				footPrints = qxFootprintService.getFootprintByuidtop10(uid);
			}

			if (footPrints.size() > 0) {
				backInfo.setFootImg(footPrints.get(0).getFootImg());
				backInfo.setUid(footPrints.get(0).getUid());
				backInfo.setFootName(footPrints.get(0).getFootName());
			} else {
				SystemUser sysUser = systemUserService.findByUid(uid);
				if (null != sysUser) {
					backInfo.setFootImg(sysUser.getHeadimgurl());
					backInfo.setUid(sysUser.getUid());
					backInfo.setFootName(sysUser.getNickname());
				} else {
					baseBackInfo.setRetMsg(Global.str300204);
					baseBackInfo.setStateCode(Global.int300204);
					return baseBackInfo;
				}
			}
			backInfo.setData(footPrints);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			return backInfo;
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setRetMsg(Global.str300302);
			baseBackInfo.setStateCode(Global.int300302);
			return baseBackInfo;
		}
	}

	/**
	 * 获取足迹详情
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getFootprint", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getFootprint(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String footid = map.get("footid");
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (Strings.isNullOrEmpty(footid) || !StrUtil.isPositiveNum(footid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		try {
			QxFootprintWithPraiseAndComment footprint = qxFootprintService.getFootprintByFootid(Integer.valueOf(footid));
			if (footprint == null) {
				baseBackInfo.setStateCode(Global.int300220);
				baseBackInfo.setRetMsg(Global.str300220);
				return baseBackInfo;
			}
			FootprintWithPraiseAndComment footprintWithPraiseAndComment = new FootprintWithPraiseAndComment();
			BeanMapper.copy(footprint, footprintWithPraiseAndComment);
			ItemBackInfo backInfo = new ItemBackInfo();
			backInfo.setItem(footprintWithPraiseAndComment);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			return backInfo;
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setRetMsg(Global.str300302);
			baseBackInfo.setStateCode(Global.int300302);
			return baseBackInfo;
		}
	}

	/**
	 * store footprint
	 * 
	 * @param footprint
	 */
	public void saveFootprint(QxFootprint footprint) {
		qxFootprintService.saveFootprint(footprint);
	}

	/*
	 * public QxFootprint buildFootprint(){ QxFootprint qxFootprint = new
	 * QxFootprint(); qxFootprint.setUid("uid"); qxFootprint.setUpdateDate(new
	 * Date()); qxFootprint.setFootContent("ssss");
	 * qxFootprint.setFootContentImg("dddd"); qxFootprint.setFootid("33");
	 * qxFootprint.setFootImg("dddd"); qxFootprint.setFootName("footName");
	 * qxFootprint.setFootToId("333"); qxFootprint.setFootType("1");
	 * qxFootprint.setFootVsImg("vsimg"); qxFootprint.setFootVsName("vsname");
	 * qxFootprint.setFootVsResult("0.58"); qxFootprint.setFootVsUid("uiod");
	 * return qxFootprint; }
	 */

	/**
	 * 好友可见 隐私设置
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/visible", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo visible(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String gidStr = map.get("gid");
		// 是否可见 0-可见,1-不可见
		String visibleStr = map.get("visible");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gidStr) || StrUtil.isBlank(visibleStr) || StrUtil.isBlank(uid) || !StrUtil.isNum(gidStr) || !StrUtil.isNum(visibleStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int visible = Integer.parseInt(visibleStr);
			int gid = Integer.parseInt(gidStr);

			// 设置模板4排行榜可见
			qxHistoryService.setVisible(uid, gid, visible);
			// 设置模板5匹配度可见
			qxMatchService.setVisible(uid, gid, visible);
			// 设置足迹可见
			qxFootprintService.setVisible(uid, gid, visible);

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
	 * 获取好友专题可见状态
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/getVisible", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getVisible(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");
		String gidStr = map.get("gid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(gidStr) || StrUtil.isBlank(uid) || !StrUtil.isNum(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);

			return info;
		}
		try {
			int gid = Integer.parseInt(gidStr);
			//获取好友可见标识
			VisibleToJson visible = qxHistoryService.getVisible(uid,gid);

			if(null==visible){
				backInfo.setItem(new VisibleToJson());
			}else {
				backInfo.setItem(visible);
			}
			
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