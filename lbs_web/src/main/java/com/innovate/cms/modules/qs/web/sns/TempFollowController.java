/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.sns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.IdGen;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.qs.entity.sns.TempFollow;
import com.innovate.cms.modules.qs.service.sns.QxFollowService;
import com.innovate.cms.modules.qs.service.sns.TempFollowService;

/**
 * 好友临时表 Controller
 * 
 * @author shifangfang
 *
 */
@Controller
@RequestMapping(value = "/api")
public class TempFollowController extends BaseController {

	@Autowired
	private TempFollowService tempFollowService;

	@Autowired
	private QxFollowService qxFollowService;

	/**
	 * 推荐好友列表
	 * num每次获取推荐好友的数量,默认16
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/recommendFriends", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo recommendFriends(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String num = map.get("num");

		DataBackInfo<TempFollow> backInfo = new DataBackInfo<TempFollow>();
		if (StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if(StrUtil.isBlank(num)){
			num="16";
		}
		try {
			// 1：首先删除重复好友（去选好友与h5推广重复好友）
			tempFollowService.delDuplicateFriends(uid);
			// 2：查询出推荐的好友列表
			List<TempFollow> follows = tempFollowService.recommendFriends(uid,Integer.parseInt(num));
			if(follows==null ||follows.size()==0){
				backInfo.setData(new ArrayList<TempFollow>());
			}
			
			backInfo.setData(follows);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	public List<String> getFlids(List<TempFollow> follows) {
		ArrayList<String> idsArrayList = new ArrayList<String>();
		for (TempFollow follow : follows) {
			idsArrayList.add(String.valueOf(follow.getTfid()));
		}
		return idsArrayList;
	}

	/**
	 * 批量关注好友
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/sns/followsFrieds", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo followsFrieds(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String add = map.get("add");
		String del = map.get("del");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(uid)||StrUtil.isBlank(add)||StrUtil.isBlank(del)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {

			String[] addids = add.split(",");
			String[] delids = del.split(",");
			String[] all = ArrayUtils.addAll(addids,delids);
			
			for (String addid:addids) {
				String flid = IdGen.uuid();
				qxFollowService.saveFollows(flid,addid);
						
			}
			
			// 删除推荐的一批列表
			tempFollowService.delRecommendedFriends(Arrays.asList(all));
			
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