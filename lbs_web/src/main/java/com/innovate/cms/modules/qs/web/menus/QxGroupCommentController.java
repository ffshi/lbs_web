package com.innovate.cms.modules.qs.web.menus;

/**
 * 
 */


import java.util.Date;
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
import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.qs.entity.menus.GroupComment;
import com.innovate.cms.modules.qs.entity.menus.QxGroupComment;
import com.innovate.cms.modules.qs.entity.ques.QxGroupsComment;
import com.innovate.cms.modules.qs.service.menus.QxGroupCommentService;

/**
 * 专题评论
 * 
 * @author hushasha
 * @date 2016年9月9日
 */
@Controller
@RequestMapping(value = "/api")
public class QxGroupCommentController extends BaseController {
	@Autowired
	private QxGroupCommentService qxGroupCommentService;
	
	/**
	 * 保存专题评论
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/saveComment", method = RequestMethod.POST)
	public @ResponseBody ItemBackInfo saveComment(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		final String gid = map.get("gid");
		String fromCommentUid = map.get("fromCommentUid");
		String fromCommentName = map.get("fromCommentName");
		String fromCommentImg = map.get("fromCommentImg");
		String comment = map.get("comment");
		ItemBackInfo backInfo = new ItemBackInfo();
		// 参数简单校验
		if (Strings.isNullOrEmpty(gid) || Strings.isNullOrEmpty(fromCommentUid)
				|| Strings.isNullOrEmpty(fromCommentName) || Strings.isNullOrEmpty(fromCommentImg)
				|| Strings.isNullOrEmpty(comment)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		if (!StrUtil.isPositiveNum(gid) || fromCommentUid.length() != 32) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		QxGroupsComment qxGroupsComment = new QxGroupsComment();
		qxGroupsComment.setGid(Integer.valueOf(gid));
		qxGroupsComment.setFromCommentUid(fromCommentUid);
		qxGroupsComment.setFromCommentName(fromCommentName);
		qxGroupsComment.setFromCommentImg(fromCommentImg);
		qxGroupsComment.setComment(comment);
		qxGroupsComment.setCreateTime(new Date());
		try {
			qxGroupCommentService.saveComment(qxGroupsComment);
			GroupComment groupComment = new GroupComment();
			groupComment.setGcid(qxGroupsComment.getGcid());
			backInfo.setItem(groupComment);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			//新起一个线程去更新评论数量
			new Thread(new Runnable() {
				@Override
				public void run() {
					qxGroupCommentService.updateCommentNum(Integer.valueOf(gid));
				}
			}).start();
		} catch (Exception e) {
			logger.debug("[QxGroupCommentController - saveComment()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}


	/**
	 * 专题评论列表--获取最新的20条数据
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/commentList", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<QxGroupComment> getCommentList(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		String gid = map.get("gid");
		DataBackInfo<QxGroupComment> backInfo = new DataBackInfo<QxGroupComment>();
		// 参数简单校验
		if (Strings.isNullOrEmpty(gid) || !StrUtil.isPositiveNum(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		List<QxGroupComment> data = Lists.newArrayList();
		try {
			data = qxGroupCommentService.getCommentList(Integer.valueOf(gid));
			backInfo.setData(data);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupCommentController - getCommentList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	/**
	 * 获取更多评论（小于gcid的最近的20条数据）
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/showMoreComments", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<QxGroupComment> showMoreComments(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		String gid = map.get("gid");
		String gcid = map.get("gcid");
		DataBackInfo<QxGroupComment> backInfo = new DataBackInfo<QxGroupComment>();
		// 参数简单校验
		if (Strings.isNullOrEmpty(gid) || Strings.isNullOrEmpty(gcid) || !StrUtil.isPositiveNum(gid) || !StrUtil.isPositiveNum(gcid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		List<QxGroupComment> data = Lists.newArrayList();
		try {
			if(Integer.valueOf(gcid) > 1) { //gcid为1的话,没有比gcid小的评论,直接返回
				data = qxGroupCommentService.getMoreComments(Integer.valueOf(gid), Integer.valueOf(gcid));
			}
			backInfo.setData(data);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupCommentController - showMoreComments()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
	/**
	 * 下拉刷新专题评论列表--返回大于gcid的所有评论
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/menus/refreshComments", method = RequestMethod.POST)
	public @ResponseBody DataBackInfo<QxGroupComment> refreshComments(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		String gid = map.get("gid");
		String gcid = map.get("gcid");
		DataBackInfo<QxGroupComment> backInfo = new DataBackInfo<QxGroupComment>();
		// 参数简单校验
		if (Strings.isNullOrEmpty(gid) || Strings.isNullOrEmpty(gcid) || !StrUtil.isPositiveNum(gid) || !(StrUtil.isNum(gcid) && Integer.valueOf(gcid) >=0)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		List<QxGroupComment> data = Lists.newArrayList();
		try {
			data = qxGroupCommentService.getAllNewCommentList(Integer.valueOf(gid), Integer.valueOf(gcid));
			backInfo.setData(data);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[QxGroupCommentController - getCommentList()接口报错：{}]", e.getMessage());
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}
	
}
