/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.ques;

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
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.common.entity.GroupQuesBackInfo;
import com.innovate.cms.modules.common.entity.GroupQuesBackInfo5;
import com.innovate.cms.modules.common.entity.GroupQuesBackInfoExtend;
import com.innovate.cms.modules.common.entity.ItemBackInfo;
import com.innovate.cms.modules.common.entity.ItemBackInfoDone;
import com.innovate.cms.modules.common.entity.ItemBackInfoDoneExtend;
import com.innovate.cms.modules.common.entity.T0QuesBackInfo;
import com.innovate.cms.modules.data.entity.EssentialGroupTestBToJson;
import com.innovate.cms.modules.data.entity.EssentialGroupToJson;
import com.innovate.cms.modules.data.entity.GroupForPcwebToJson;
import com.innovate.cms.modules.data.entity.MarketGroupConfigueToJson;
import com.innovate.cms.modules.data.entity.MarketGroupRecommendToJson;
import com.innovate.cms.modules.data.entity.UgcGroupToJson;
import com.innovate.cms.modules.data.entity.UgcHistoryGroupToJson;
import com.innovate.cms.modules.data.entity.XcxEssentialGroupToJson;
import com.innovate.cms.modules.qs.entity.menus.Group;
import com.innovate.cms.modules.qs.entity.ques.Group0;
import com.innovate.cms.modules.qs.entity.ques.Questions;
import com.innovate.cms.modules.qs.entity.ques.Result;
import com.innovate.cms.modules.qs.entity.ques.Result5;
import com.innovate.cms.modules.qs.entity.ques.Template4Result;
import com.innovate.cms.modules.qs.entity.ques.Template5Result;
import com.innovate.cms.modules.qs.entity.sns.TempFollow;
import com.innovate.cms.modules.qs.service.menus.QxGroupsService;
import com.innovate.cms.modules.qs.service.ques.QxHistoryAnswerService;
import com.innovate.cms.modules.qs.service.ques.QxHistoryService;
import com.innovate.cms.modules.qs.service.ques.QxQuestionsResultsService;
import com.innovate.cms.modules.qs.service.ques.QxQuestionsService;
import com.innovate.cms.modules.qs.service.sns.QxFootprintService;
import com.innovate.cms.modules.qs.service.sns.QxMatchService;
import com.innovate.cms.modules.qs.service.sns.TempFollowService;
import com.innovate.cms.modules.qs.service.user.SystemUserService;
import com.thoughtworks.xstream.mapper.Mapper.Null;

/**
 * 趣选题库Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxQuestionsController extends BaseController {

	@Autowired
	private QxQuestionsService qxQuestionsService;

	@Autowired
	private QxQuestionsResultsService qxQuestionsResultsService;

	@Autowired
	private QxHistoryService qxHistoryService;
	@Autowired
	private QxFootprintService qxFootprintService;

	@Autowired
	private QxGroupsService qxGroupsService;

	@Autowired
	private QxHistoryAnswerService qxHistoryAnswerService;
	@Autowired
	private QxMatchService qxMatchService;

	@Autowired
	private TempFollowService tempFollowService;
	@Autowired
	private SystemUserService systemUserService;

	@RequestMapping(value = "/v1/sns/getDoneQuestionGid", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getDoneQuestionGid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String _templateTag = map.get("templateTag");
		int templateTag = 0;
		if (null != _templateTag) {
			templateTag = Integer.parseInt(_templateTag);
		}

		DataBackInfo<Integer> backInfo = new DataBackInfo<Integer>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 根据专题类型获取用户做过的所有专题gid
			List<Integer> gids = qxHistoryService.getDoneQuestionGid(uid, templateTag);
			backInfo.setData(gids);
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
	 * 获取用户作品
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/myUgc", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo myUgc(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");

		DataBackInfo<Group0> backInfo = new DataBackInfo<Group0>();
		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 获取用户作品
			List<Group0> groups = qxQuestionsService.myUgc(uid);
			backInfo.setData(groups);
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
	 * 获取模板0 站边专题
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getT0", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getT0(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String gidStr = map.get("gid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		int gid = Integer.parseInt(gidStr);
		try {
			// 按指定用户和gid获取模板0
			List<UgcHistoryGroupToJson> groups = qxQuestionsService.getT0(uid, gid);
			if (groups.size() > 0) {
				backInfo.setItem(groups.get(0));
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

	/**
	 * 好用动态-站边题详情：按指定uid, followUid,gid获取模板0相关信息，并返回uid,followUid做题结果
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getT0ByFollowDynamic", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getT0ByFollowDynamic(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String followUid = map.get("followUid");
		String gidStr = map.get("gid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(followUid) || StrUtil.isBlank(gidStr) || uid.length() != 32 || followUid.length() != 32 || !StrUtil.isPositiveNum(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		if (uid.equals(followUid)) { // 不能自己关注自己
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		int gid = Integer.parseInt(gidStr);
		try {
			// 按指定uid, followUid,gid获取模板0相关信息，并返回uid,followUid做题结果
			List<UgcHistoryGroupToJson> groups = qxQuestionsService.getT0ByFollowDynamic(uid, followUid, gid);
			if (groups.size() > 0) {
				backInfo.setItem(groups.get(0));
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

	/**
	 * 模板1
	 * 
	 * APP 获取专题问题组：问题、选项、以及专题结果
	 * 
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestions1", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGid1(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 获取专题问题和选项
				List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "1");
				if (questions.size() > 0) {
					GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
					backInfo.setQuestions(questions);
					backInfo.setGid(Integer.parseInt(gid));
					backInfo.setIcon(questions.get(0).getIcon());
					backInfo.setTemplateTag(questions.get(0).getTemplateTag());
					backInfo.setFsid(Integer.parseInt(questions.get(0).getFsid().toString()));
					backInfo.setGroupName(questions.get(0).getGroupName());
					backInfo.setGroupDescription(questions.get(0).getGroupDescription());
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					return backInfo;
				} else {
					logger.debug(Global.str300303 + "|questions对象={}", questions);
					baseBackInfo.setStateCode(Global.int300303);
					baseBackInfo.setRetMsg(Global.str300303);
				}
			} catch (Exception e) {
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return baseBackInfo;
	}

	/**
	 * H5模板1
	 * 
	 * APP 获取专题问题组：问题、选项、以及专题结果
	 * 
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestions1", method = RequestMethod.GET)
	public @ResponseBody String getQsbyGid1(HttpServletRequest request, HttpServletResponse response) {
		String gid = request.getParameter("gid");
		String callback = request.getParameter("callback");
		BaseBackInfo baseBackInfo = new BaseBackInfo();

		if (StrUtil.isBlank(gid) || StrUtil.isBlank(callback) || !StrUtil.isNum(gid)) {
			BaseBackInfo errorInfo = new BaseBackInfo();
			errorInfo.setStateCode(Global.int300209);
			errorInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", errorInfo);
		} else {
			try {
				// 获取专题问题和选项
				List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "1");
				if (questions.size() > 0) {
					GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
					backInfo.setQuestions(questions);
					backInfo.setGid(Integer.parseInt(gid));
					backInfo.setIcon(questions.get(0).getIcon());
					backInfo.setTemplateTag(questions.get(0).getTemplateTag());
					backInfo.setFsid(Integer.parseInt(questions.get(0).getFsid().toString()));
					backInfo.setGroupName(questions.get(0).getGroupName());
					backInfo.setGroupDescription(questions.get(0).getGroupDescription());
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					return JsonMapper.getInstance().toJsonP(callback, backInfo);
				} else {
					logger.debug(Global.str300303 + "|questions对象={}", questions);
					baseBackInfo.setStateCode(Global.int300303);
					baseBackInfo.setRetMsg(Global.str300303);
				}
			} catch (Exception e) {
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
	}

	/**
	 * 
	 * 模板4
	 * 
	 * 【未登录】获取专题问题组：问题、选项、以及专题结果
	 * 
	 * 废除
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsfeichu", method = RequestMethod.POST)
	public @ResponseBody GroupQuesBackInfo getQsbyGid(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");

		GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
		if (StrUtil.isBlank(gid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		try {
			// 获取专题问题和选项
			List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");
			// 获取专题的做题结果
			List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));

			if (questions.size() > 0 && res.size() > 0) {
				backInfo.setQuestions(questions);
				backInfo.setResult(res);
				backInfo.setGid(Integer.parseInt(gid));
				backInfo.setIcon(questions.get(0).getIcon());
				backInfo.setTemplateTag(questions.get(0).getTemplateTag());
				backInfo.setFsid(questions.get(0).getFsid());
				backInfo.setGroupName(questions.get(0).getGroupName());
				backInfo.setGroupDescription(questions.get(0).getGroupDescription());
				backInfo.setDone(0);// 未登录 默认0显式赋值
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			} else {
				logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
				backInfo.setStateCode(Global.int300303);
				backInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			backInfo.setStateCode(Global.int300302);
			backInfo.setRetMsg(Global.str300302);
		}
		return backInfo;
	}

	/**
	 * 
	 * 模板4
	 * 
	 * 【未登录】获取专题问题组：问题、选项、以及专题结果
	 * 
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestions4", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGid4(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		BaseBackInfo baseBackInfo = new BaseBackInfo();

		if (StrUtil.isBlank(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 获取专题问题和选项
				List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");
				// 获取专题的做题结果

				List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));
				if (questions.size() > 0 && res.size() > 0) {
					GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
					backInfo.setQuestions(questions);
					backInfo.setResult(res);
					backInfo.setGid(Integer.parseInt(gid));
					backInfo.setIcon(questions.get(0).getIcon());
					backInfo.setTemplateTag(questions.get(0).getTemplateTag());
					backInfo.setFsid(questions.get(0).getFsid());
					backInfo.setGroupName(questions.get(0).getGroupName());
					backInfo.setGroupDescription(questions.get(0).getGroupDescription());
					if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
						backInfo.setRankTitle("");
					} else {
						backInfo.setRankTitle(questions.get(0).getRankTitle());
					}
					if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
						backInfo.setResDecription("");
					} else {
						backInfo.setResDecription(questions.get(0).getResDecription());
					}
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					return backInfo;
				} else {
					logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
					baseBackInfo.setStateCode(Global.int300303);
					baseBackInfo.setRetMsg(Global.str300303);
				}
			} catch (Exception e) {
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return baseBackInfo;
	}

	/**
	 * 
	 * 模板5
	 * 
	 * 【未登录】获取专题问题组：问题、选项、以及专题结果
	 * 
	 * 
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestions5", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGid5(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");

		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 获取专题问题和选项
				List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "5");
				// 获取模板5专题结果匹配度文案
				List<Result5> res = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));
				if (questions.size() > 0 && res.size() > 0) {
					GroupQuesBackInfo5 backInfo = new GroupQuesBackInfo5();
					backInfo.setGid(Integer.parseInt(gid));
					backInfo.setResult(res);
					backInfo.setQuestions(questions);
					backInfo.setIcon(questions.get(0).getIcon());
					backInfo.setTemplateTag(questions.get(0).getTemplateTag());
					backInfo.setFsid(questions.get(0).getFsid());
					backInfo.setGroupName(questions.get(0).getGroupName());
					backInfo.setGroupDescription(questions.get(0).getGroupDescription());
					backInfo.setDone(0); // 要显示的赋值
					if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
						backInfo.setRankTitle("");
					} else {
						backInfo.setRankTitle(questions.get(0).getRankTitle());
					}
					if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
						backInfo.setResDecription("");
					} else {
						backInfo.setResDecription(questions.get(0).getResDecription());
					}

					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					return backInfo;
				} else {
					logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
					baseBackInfo.setStateCode(Global.int300303);
					baseBackInfo.setRetMsg(Global.str300303);
				}
			} catch (Exception e) {
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return baseBackInfo;
	}

	/**
	 * 模板4
	 * 
	 * 登录用户获取专题接口 废除
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsQxuserfeichu", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGidQxuser(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		String uid = map.get("uid");

		// 趣选用户不能做第二次
		GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			return backInfo;
		}
		// 判断用户是否做过该专题
		int num = qxHistoryService.doneGroup(uid, gid);
		if (num > 0) {// 做过该专题

			Template4Result res = qxFootprintService.findTemplate4Result(Integer.parseInt(gid), uid);
			// 用户已经做过模板5的时候 直接查询出匹配度findTemplate5Result
			// QxFootprintService.findTemplate5Result(String myUid,String
			// followUid);
			ItemBackInfoDone info = new ItemBackInfoDone();

			info.setStateCode(Global.intYES);
			info.setRetMsg(Global.SUCCESS);
			info.setDone(1);
			info.setItem(res);
			return info;
		} else {
			backInfo.setDone(0);
			backInfo.setUid(uid);
			try {
				// 获取专题问题和选项
				List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");

				// 获取专题的做题结果
				List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));

				backInfo.setQuestions(questions);
				backInfo.setResult(res);
				backInfo.setGid(Integer.parseInt(gid));
				if (questions.size() > 0) {
					backInfo.setIcon(questions.get(0).getIcon());
					backInfo.setTemplateTag(questions.get(0).getTemplateTag());
					backInfo.setFsid(questions.get(0).getFsid());
					backInfo.setGroupName(questions.get(0).getGroupName());
					backInfo.setGroupDescription(questions.get(0).getGroupDescription());
				}

				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());

				backInfo.setStateCode(Global.int300302);
				backInfo.setRetMsg(Global.str300302);
			}
		}
		return backInfo;
	}

	/**
	 * 模板4
	 * 
	 * 登录用户获取专题接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsQxuser4", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGidQxuser4(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		String uid = map.get("uid");
		// 在做一次标识
		String again = map.get("again");
		if (null == again) {
			again = "0";
		}

		// 趣选用户不能做第二次
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid) || StrUtil.isBlank(again)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(uid, gid);
				if (num > 0 && !again.equals("1")) {// 做过该专题 并且不是在做一次
					Template4Result res = qxFootprintService.findTemplate4Result(Integer.parseInt(gid), uid);
					// 用户已经做过模板5的时候 直接查询出匹配度findTemplate5Result
					// QxFootprintService.findTemplate5Result(String
					// myUid,String
					// followUid);
					ItemBackInfoDone itemBackInfoDone = new ItemBackInfoDone();
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setDone(1);
					itemBackInfoDone.setItem(res);
					return itemBackInfoDone;
				} else {
					// 获取专题问题和选项
					List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");
					// 获取专题的做题结果
					List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));
					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						backInfo.setStateCode(Global.intYES);
						if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
							backInfo.setRankTitle("");
						} else {
							backInfo.setRankTitle(questions.get(0).getRankTitle());
						}
						if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
							backInfo.setResDecription("");
						} else {
							backInfo.setResDecription(questions.get(0).getResDecription());
						}
						backInfo.setRetMsg(Global.SUCCESS);
						return backInfo;
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg(Global.str300303);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return baseBackInfo;
	}

	/**
	 * 模板4
	 * 
	 * H5专用
	 * 
	 * 登录用户获取专题接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsQxuser4", method = RequestMethod.GET)
	public @ResponseBody String getQsbyGidQxuser4H5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String gid = request.getParameter("gid");
		String uid = request.getParameter("uid");
		String again = request.getParameter("again");
		String fxuid = request.getParameter("fxuid");

		// 趣选用户不能做第二次
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid) || StrUtil.isBlank(again) || StrUtil.isBlank(callback) || !StrUtil.isUID1(uid) || !StrUtil.isNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);

		} else {
			// TODO 添加临时好友
			if (StringUtils.isNotBlank(fxuid)) {
				List<TempFollow> tempFollows = tempFollowService.findIsFollow(fxuid, uid);
				if (tempFollows.size() == 0) {
					TempFollow entity = new TempFollow();
					entity.setUid(fxuid);
					entity.setFollowUid(uid);
					try {
						tempFollowService.saveTempFollow(entity);
					} catch (Exception e) {
						logger.debug("tempFollowService.saveTempFollow(entity)添加临时好友出错，数据主动丢失：[{}]，fxuid={}, uid={}, user_nickname={}", e.getMessage(), fxuid, uid);
						// 不做处理，可以丢失
					}
				} else {
					logger.debug("已有好友数据，或者临时好友数据，不在插入新数据");
				}

			} else {
				logger.debug("fxuid 为空");
			}

			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(uid, gid);
				if (num > 0 && !again.equals("1")) {// 做过该专题 并且不是在做一次
					Template4Result res = qxFootprintService.findTemplate4Result(Integer.parseInt(gid), uid);
					// 用户已经做过模板5的时候 直接查询出匹配度findTemplate5Result
					// QxFootprintService.findTemplate5Result(String
					// myUid,String
					// followUid);
					ItemBackInfoDone itemBackInfoDone = new ItemBackInfoDone();
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setDone(1);
					itemBackInfoDone.setItem(res);

					return JsonMapper.getInstance().toJsonP(callback, itemBackInfoDone);
					// return itemBackInfoDone;
				} else {
					// 如果没做过，或者是在做一次 获取专题问题和选项
					List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");
					// 获取专题的做题结果
					List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));
					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
							backInfo.setRankTitle("");
						} else {
							backInfo.setRankTitle(questions.get(0).getRankTitle());
						}
						if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
							backInfo.setResDecription("");
						} else {
							backInfo.setResDecription(questions.get(0).getResDecription());
						}
						backInfo.setStateCode(Global.intYES);
						backInfo.setRetMsg(Global.SUCCESS);
						return JsonMapper.getInstance().toJsonP(callback, backInfo);
						// return backInfo;
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg(Global.str300303);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
	}

	/**
	 * 模板5
	 * 
	 * 登录用户获取专题接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsQxuser5", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGidQxuser5(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		String uid = map.get("uid");

		// 趣选用户不能做第二次
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(uid, gid);
				if (num > 0) {// 做过该专题,并且不是从足迹进入，只需告知做过该专题

					// 用户已经做过模板5的时候 直接查询出匹配度findTemplate5Result
					// Template5Result res =
					// QxFootprintService.findTemplate5Result(uid);

					ItemBackInfoDone itemBackInfoDone = new ItemBackInfoDone();
					Group group = qxGroupsService.getGroupBygid(Integer.parseInt(gid));

					itemBackInfoDone.setItem(group);
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setDone(1);
					List<Result5> res = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));
					if (res.size() > 0) {
						itemBackInfoDone.setResult(res);
					}
					return itemBackInfoDone;
				} else {
					// 获取专题问题和选项
					List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "5");
					// 获取专题的做题结果
					List<Result5> res = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));

					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfo5 backInfo = new GroupQuesBackInfo5();
						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
							backInfo.setRankTitle("");
						} else {
							backInfo.setRankTitle(questions.get(0).getRankTitle());
						}
						if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
							backInfo.setResDecription("");
						} else {
							backInfo.setResDecription(questions.get(0).getResDecription());
						}
						backInfo.setStateCode(Global.intYES);
						backInfo.setRetMsg(Global.SUCCESS);
						return backInfo;
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg(Global.str300303);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return baseBackInfo;
	}

	/**
	 * H5模板5
	 * 
	 * 登录用户获取专题接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsQxuser5", method = RequestMethod.GET)
	public @ResponseBody String getQsbyGidQxuser5H5(HttpServletRequest request, HttpServletResponse response) {
		String gid = request.getParameter("gid");
		String uid = request.getParameter("uid");
		String callback = request.getParameter("callback");
		BaseBackInfo baseBackInfo = new BaseBackInfo();

		if (StrUtil.isBlank(callback) || StrUtil.isBlank(uid) || !StrUtil.isUID1(uid) || !StrUtil.isNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("error", baseBackInfo);
		} else {
			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(uid, gid);
				if (num > 0) {// 做过该专题,并且不是从足迹进入，只需告知做过该专题

					// 用户已经做过模板5的时候 直接查询出匹配度findTemplate5Result
					// Template5Result res =
					// QxFootprintService.findTemplate5Result(uid);
					ItemBackInfoDone itemBackInfoDone = new ItemBackInfoDone();
					Group group = qxGroupsService.getGroupBygid(Integer.parseInt(gid));

					itemBackInfoDone.setItem(group);
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setDone(1);
					return JsonMapper.getInstance().toJsonP(callback, itemBackInfoDone);
				} else {
					// 趣选用户不能做第二次
					// 获取专题问题和选项
					List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "5");
					// 获取专题的做题结果
					List<Result5> res = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));
					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfo5 backInfo = new GroupQuesBackInfo5();
						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
							backInfo.setRankTitle("");
						} else {
							backInfo.setRankTitle(questions.get(0).getRankTitle());
						}
						if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
							backInfo.setResDecription("");
						} else {
							backInfo.setResDecription(questions.get(0).getResDecription());
						}
						backInfo.setStateCode(Global.intYES);
						backInfo.setRetMsg(Global.SUCCESS);
						return JsonMapper.getInstance().toJsonP(callback, backInfo);
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg(Global.str300303);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
	}

	/**
	 * 模板5 足迹内获取模板5专题
	 * 
	 * 趣选用户从用户足迹内获取专题，适合模板5
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsFromUserfoot5", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getQsbyGidFromUserfoot5(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String gid = map.get("gid");
		// 足迹用户id
		String uid = map.get("uid");
		// 要做题的用户uid
		String fuid = map.get("fuid");
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid) || StrUtil.isBlank(fuid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
		} else {
			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(fuid, gid);
				if (num > 0) {// 做过该专题
					try {
						// 更新排行榜数据
						qxMatchService.updateRankDatas(fuid, Integer.parseInt(gid));
					} catch (Exception e) {
						logger.debug("[QxWithRankController - findRankByTemplet5ByUid(),更新排行榜异常：{}]", e.getMessage());
					}

					Template5Result res = qxFootprintService.findTemplate5Result(fuid, uid, Integer.parseInt(gid));

					// 查询陌生人匹配
					if (null == res || null == res.getMyImg()) {
						qxFootprintService.findTemplate5ResultStranger(fuid, uid, Integer.parseInt(gid));
					}

					// 1：生成陌生人匹配度 2：插入匹配数据 3：去除匹配数据
					if (null == res || null == res.getMyImg()) {
						try {
							// 1：生成匹配度
							String matchResult = qxHistoryAnswerService.getMatchResult(uid, fuid, gid);
							// 2：插入匹配数据
							qxMatchService.addNewMatchStranger(Integer.parseInt(gid), uid, fuid, Double.parseDouble(matchResult));
							// 3：再次获取匹配数据
							res = qxFootprintService.findTemplate5ResultStranger(fuid, uid, Integer.parseInt(gid));
						} catch (Exception e) {
							logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
							baseBackInfo.setStateCode(Global.int300302);
							baseBackInfo.setRetMsg("uid:" + uid + " fuid:" + fuid + " gid:" + gid + " 生成匹配数据异常，有用户没有上传做题答案");

							return baseBackInfo;
						}

					}

					ItemBackInfoDone itemBackInfoDone = new ItemBackInfoDone();
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setItem(res);
					itemBackInfoDone.setDone(1);
					List<Result5> answer = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));
					if (answer.size() > 0) {
						itemBackInfoDone.setResult(answer);
					}

					return itemBackInfoDone;
				} else {
					// 获取专题问题，选项,同时获取用户的答案
					List<Questions> questions = qxQuestionsService.getQsbyGidFromUserfoot(uid, Integer.parseInt(gid), "5");
					// List<Questions> questions =
					// qxQuestionsService.getQsbyGid(Integer.parseInt(gid));
					// 获取专题的做题结果
					List<Result5> res = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));

					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfo5 backInfo = new GroupQuesBackInfo5();

						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						if (null == questions.get(0).getRankTitle() || questions.get(0).getRankTitle().trim().length() == 0) {
							backInfo.setRankTitle("");
						} else {
							backInfo.setRankTitle(questions.get(0).getRankTitle());
						}
						if (null == questions.get(0).getResDecription() || questions.get(0).getResDecription().trim().length() == 0) {
							backInfo.setResDecription("");
						} else {
							backInfo.setResDecription(questions.get(0).getResDecription());
						}
						backInfo.setStateCode(Global.intYES);
						backInfo.setRetMsg(Global.SUCCESS);
						return backInfo;
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg(Global.str300303);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return baseBackInfo;
	}

	/**
	 * 
	 * H5获取专题 模板5 足迹内获取模板5专题
	 * 
	 * 趣选用户从用户足迹内获取专题，适合模板5
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestionsFromUserfoot5", method = RequestMethod.GET)
	public @ResponseBody String getQsbyGidFromUserfoot5H5(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String gid = request.getParameter("gid");
		// 足迹用户id
		String uid = request.getParameter("uid");
		// 要做题的用户uid
		String fuid = request.getParameter("fuid");
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid) || StrUtil.isBlank(fuid) || StrUtil.isBlank(callback) || !StrUtil.isUID1(uid) || !StrUtil.isUID1(fuid) || !StrUtil.isNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("error:param is blank", baseBackInfo);
		} else {
			// TODO 添加临时好友
			if (StringUtils.isNotBlank(uid)) {
				List<TempFollow> tempFollows = tempFollowService.findIsFollow(uid, fuid);
				if (tempFollows.size() == 0) {
					TempFollow entity = new TempFollow();
					entity.setUid(uid);
					entity.setFollowUid(fuid);
					try {
						tempFollowService.saveTempFollow(entity);
					} catch (Exception e) {
						logger.debug("tempFollowService.saveTempFollow(entity)添加临时好友出错，数据主动丢失：[{}]，fxuid={}, uid={}, user_nickname={}", e.getMessage(), uid, fuid);
						// 不做处理，可以丢失
					}
				} else {
					logger.debug("已有好友数据，或者临时好友数据，不在插入新数据");
				}

			}
			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(fuid, gid);
				if (num > 0) {// 做过该专题
					Template5Result res = qxFootprintService.findTemplate5ResultH5(fuid, uid, Integer.parseInt(gid));

					// 处理H5如果二人都做过题，但是没有生成匹配度
					// 1：生成匹配度 2：插入匹配数据 3：去除匹配数据
					if (null == res || null == res.getMyImg()) {
						try {
							// 1：生成匹配度
							String matchResult = qxHistoryAnswerService.getMatchResult(uid, fuid, gid);
							// 2：插入匹配数据
							qxMatchService.addNewMatchH5(Integer.parseInt(gid), uid, fuid, Double.parseDouble(matchResult));
							// 3：再次获取匹配数据
							res = qxFootprintService.findTemplate5ResultH5(fuid, uid, Integer.parseInt(gid));
						} catch (Exception e) {
							logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
							baseBackInfo.setStateCode(Global.int300302);
							baseBackInfo.setRetMsg("uid:" + uid + " fuid:" + fuid + " gid:" + gid + " 生成匹配数据异常，有用户没有上传做题答案");

							return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
						}
					}

					ItemBackInfoDone itemBackInfoDone = new ItemBackInfoDone();
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setItem(res);
					itemBackInfoDone.setDone(1);
					return JsonMapper.getInstance().toJsonP(callback, itemBackInfoDone);
				} else {
					/**
					 * 防止接口调用乱传用户
					 */
					/*
					 * if(questions.size()==0){ BaseBackInfo info = new
					 * BaseBackInfo(); info.setStateCode(Global.int300209);
					 * info.setRetMsg(Global.str300209); return
					 * JsonMapper.getInstance().toJsonP(uid+"-notdone-gid-"+gid,
					 * info); }
					 */
					// List<Questions> questions =
					// qxQuestionsService.getQsbyGid(Integer.parseInt(gid));
					// 获取专题问题，选项,同时获取用户的答案
					List<Questions> questions = qxQuestionsService.getQsbyGidFromUserfoot(uid, Integer.parseInt(gid), "5");
					// 获取专题的做题结果
					List<Result5> res = qxQuestionsResultsService.findResultsBygid5(Integer.parseInt(gid));

					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfo5 backInfo = new GroupQuesBackInfo5();
						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						backInfo.setStateCode(Global.intYES);
						backInfo.setRetMsg(Global.SUCCESS);
						return JsonMapper.getInstance().toJsonP(callback, backInfo);
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg("用户：" + uid + "-notdone-gid-" + gid);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(uid + "-notdone-gid-" + gid);
			}
			return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
		}
	}

	/**
	 * h5版本 获取专题问题组：问题、选项、以及专题结果
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestions", method = RequestMethod.GET)
	public @ResponseBody String getQsbyGidH5(HttpServletRequest request, HttpServletResponse response) {
		String gid = request.getParameter("gid");
		String callback = request.getParameter("callback");
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(callback) || StrUtil.isBlank(gid) || !StrUtil.isNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("error", baseBackInfo);
		} else {
			try {
				// 获取专题问题和选项
				List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");
				// 获取专题的做题结果
				List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));

				if (questions.size() > 0 && res.size() > 0) {
					GroupQuesBackInfo backInfo = new GroupQuesBackInfo();
					backInfo.setQuestions(questions);
					backInfo.setResult(res);
					backInfo.setGid(Integer.parseInt(gid));
					backInfo.setIcon(questions.get(0).getIcon());
					backInfo.setTemplateTag(questions.get(0).getTemplateTag());
					backInfo.setFsid(questions.get(0).getFsid());
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					return JsonMapper.getInstance().toJsonP(callback, backInfo); // 成功的操作
																					// 提前return
																					// 其他操作统一最后return
				} else {
					logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
					baseBackInfo.setStateCode(Global.int300303);
					baseBackInfo.setRetMsg(Global.str300303);
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
			return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
		}
	}

	/**
	 * 首页--一次性获取所有精华专题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getEssentialGroups", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getEssentialGroups(HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		DataBackInfo<EssentialGroupToJson> backInfo = new DataBackInfo<EssentialGroupToJson>();
		try {
			// 获取所有精华专题
			List<EssentialGroupToJson> groups = qxQuestionsService.getEssentialGroups();
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}
	/**
	 * 首页--一次性获取所有精华专题--testB精选与专题混排
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getEssentialGroupsTestB", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getEssentialGroupsTestB(HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		DataBackInfo<EssentialGroupTestBToJson> backInfo = new DataBackInfo<EssentialGroupTestBToJson>();
		try {
			// 获取所有精华专题-testB精选与专题混排
			List<EssentialGroupTestBToJson> groups = qxQuestionsService.getEssentialGroupsTestB(DateUtils.getIntDate());
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}

	/**
	 * 小程序首页--一次性获取所有精华专题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getXcxEssentialGroups", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getXcxEssentialGroups(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");

		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		DataBackInfo<XcxEssentialGroupToJson> backInfo = new DataBackInfo<XcxEssentialGroupToJson>();
		try {
			// 小程序获取所有精华专题
			List<XcxEssentialGroupToJson> groups = qxQuestionsService.getXcxEssentialGroups(uid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}

	/**
	 * 社区--专题列表（显示最新的10条）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/communityGroupList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getCommunityGroupList(HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		DataBackInfo<EssentialGroupToJson> backInfo = new DataBackInfo<EssentialGroupToJson>();
		try {
			// 获取社区-专题列表(显示最新的10条数据)
			List<EssentialGroupToJson> groups = qxQuestionsService.getCommunityGroupList();
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}

	/**
	 * 小程序-社区--专题列表（显示最新的10条）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/XcxCommunityGroupList", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo getXcxCommunityGroupList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		String uid = map.get("uid");

		if (StrUtil.isBlank(uid)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		DataBackInfo<XcxEssentialGroupToJson> backInfo = new DataBackInfo<XcxEssentialGroupToJson>();
		try {
			// 小程序-获取社区-专题列表(显示最新的10条数据)
			List<XcxEssentialGroupToJson> groups = qxQuestionsService.getXcxCommunityGroupList(uid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}

	/**
	 * 上拉显示更多社区专题（显示小于gid的最近的10条记录）
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/showMoreGroups", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo showMoreGroups(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String gid = map.get("gid");
		if (StrUtil.isBlank(gid) || !StrUtil.isPositiveNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<EssentialGroupToJson> backInfo = new DataBackInfo<EssentialGroupToJson>();
		List<EssentialGroupToJson> groups = Lists.newArrayList();
		try {
			// 上拉显示更多社区专题（显示小于gid的最近的10条记录）
			if (Integer.valueOf(gid) > 1) {// gid为1的话,没有比gid小的专题，直接返回
				groups = qxQuestionsService.getMoreGroups(Integer.valueOf(gid));
			}
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}

		return baseBackInfo;
	}

	/**
	 * 小程序-上拉显示更多社区专题（显示小于gid的最近的10条记录）
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/showXcxMoreGroups", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo showXcxMoreGroups(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String gid = map.get("gid");
		String uid = map.get("uid");
		if (StrUtil.isBlank(gid) || !StrUtil.isPositiveNum(gid) || StrUtil.isBlank(uid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		DataBackInfo<XcxEssentialGroupToJson> backInfo = new DataBackInfo<XcxEssentialGroupToJson>();
		List<XcxEssentialGroupToJson> groups = Lists.newArrayList();
		try {
			// 小程序-上拉显示更多社区专题（显示小于gid的最近的10条记录）
			if (Integer.valueOf(gid) > 1) {// gid为1的话,没有比gid小的专题，直接返回
				groups = qxQuestionsService.getXcxMoreGroups(uid,Integer.valueOf(gid));
			}
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}

		return baseBackInfo;
	}

	/**
	 * 下拉刷新社区专题--返回大于gid的所有专题数据
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/refreshCommunityGroups", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo refreshCommunityGroups(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String gid = map.get("gid");
		if (StrUtil.isBlank(gid) || !StrUtil.isPositiveNum(gid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		try {
			DataBackInfo<EssentialGroupToJson> backInfo = new DataBackInfo<EssentialGroupToJson>();
			List<EssentialGroupToJson> groups = Lists.newArrayList();
			// 下拉刷新社区专题--返回大于gid的所有专题数据
			groups = qxQuestionsService.getAllNewCommunityGroups(Integer.valueOf(gid));
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}
	/**
	 * 小程序-下拉刷新社区专题--返回大于gid的所有专题数据
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/refreshXcxCommunityGroups", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo refreshXcxCommunityGroups(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		String gid = map.get("gid");
		String uid = map.get("uid");
		if (StrUtil.isBlank(gid) || !StrUtil.isPositiveNum(gid) || StrUtil.isBlank(uid)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);
			return baseBackInfo;
		}
		try {
			DataBackInfo<EssentialGroupToJson> backInfo = new DataBackInfo<EssentialGroupToJson>();
			List<EssentialGroupToJson> groups = Lists.newArrayList();
			// 小程序-下拉刷新社区专题--返回大于gid的所有专题数据
			groups = qxQuestionsService.getXcxAllNewCommunityGroups(uid,Integer.valueOf(gid));
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setStateCode(Global.intYES);
				backInfo.setRetMsg(Global.SUCCESS);
				return backInfo;
			} else {
				logger.debug(Global.str300303 + "|groups对象={}", groups);
				baseBackInfo.setStateCode(Global.int300303);
				baseBackInfo.setRetMsg(Global.str300303);
			}
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			baseBackInfo.setStateCode(Global.int300302);
			baseBackInfo.setRetMsg(Global.str300302);
		}
		return baseBackInfo;
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody DataBackInfo<?> exp(HttpMessageNotReadableException ex) {
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		DataBackInfo<?> backError = new DataBackInfo<Null>();
		if (StrUtil.isEmpty(retMsg)) {
			logger.info("参数错误" + retMsg);
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}

	/**
	 * 模板4
	 * 
	 * 模板4营销推广专用
	 * 
	 * 登录用户获取专题接口
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getQuestions4Extend", method = RequestMethod.GET)
	public @ResponseBody String getQsbyGidQxuser4Extend(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String gid = request.getParameter("gid");
		String uid = request.getParameter("uid");
		String again = request.getParameter("again");

		// 趣选用户不能做第二次
		BaseBackInfo baseBackInfo = new BaseBackInfo();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(uid) || StrUtil.isBlank(again) || StrUtil.isBlank(callback)) {
			baseBackInfo.setStateCode(Global.int300209);
			baseBackInfo.setRetMsg(Global.str300209);

		} else {
			try {
				// 判断用户是否做过该专题
				int num = qxHistoryService.doneGroup(uid, gid);
				if (num > 0 && !again.equals("1")) {// 做过该专题 并且不是在做一次
					Template4Result res = qxFootprintService.findTemplate4Result(Integer.parseInt(gid), uid);
					// 用户已经做过模板5的时候 直接查询出匹配度findTemplate5Result
					// QxFootprintService.findTemplate5Result(String
					// myUid,String
					// followUid);
					ItemBackInfoDoneExtend itemBackInfoDone = new ItemBackInfoDoneExtend();
					itemBackInfoDone.setStateCode(Global.intYES);
					itemBackInfoDone.setRetMsg(Global.SUCCESS);
					itemBackInfoDone.setDone(1);
					itemBackInfoDone.setItem(res);

					MarketGroupConfigueToJson config = qxQuestionsService.getConfig(Integer.parseInt(gid));
					itemBackInfoDone.setConfig(config);

					return JsonMapper.getInstance().toJsonP(callback, itemBackInfoDone);
					// return itemBackInfoDone;
				} else {
					// 如果没做过，或者是在做一次 获取专题问题和选项
					List<Questions> questions = qxQuestionsService.getQsbyGid(Integer.parseInt(gid), "4");
					// 获取专题的做题结果
					List<Result> res = qxQuestionsResultsService.findResultsBygid(Integer.parseInt(gid));
					if (questions.size() > 0 && res.size() > 0) {
						GroupQuesBackInfoExtend backInfo = new GroupQuesBackInfoExtend();
						backInfo.setDone(0);
						backInfo.setUid(uid);
						backInfo.setQuestions(questions);
						backInfo.setResult(res);
						backInfo.setGid(Integer.parseInt(gid));
						backInfo.setIcon(questions.get(0).getIcon());
						backInfo.setTemplateTag(questions.get(0).getTemplateTag());
						backInfo.setFsid(questions.get(0).getFsid());
						backInfo.setGroupName(questions.get(0).getGroupName());
						backInfo.setGroupDescription(questions.get(0).getGroupDescription());
						backInfo.setStateCode(Global.intYES);
						backInfo.setRetMsg(Global.SUCCESS);

						MarketGroupConfigueToJson config = qxQuestionsService.getConfig(Integer.parseInt(gid));
						backInfo.setConfig(config);
						return JsonMapper.getInstance().toJsonP(callback, backInfo);
						// return backInfo;
					} else {
						logger.debug(Global.str300303 + "|questions对象={}，res对象={}", questions, res);
						baseBackInfo.setStateCode(Global.int300303);
						baseBackInfo.setRetMsg(Global.str300303);
					}
				}
			} catch (Exception e) {
				logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
				baseBackInfo.setStateCode(Global.int300302);
				baseBackInfo.setRetMsg(Global.str300302);
			}
		}
		return JsonMapper.getInstance().toJsonP(callback, baseBackInfo);
	}

	/**
	 * H5推广运营-热门专题推荐配置 运营根绝专题自定义推荐内容
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/v1/marketGroupRecommend", method = RequestMethod.GET)
	public @ResponseBody String marketGroupRecommend(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String gid = request.getParameter("gid");

		DataBackInfo<MarketGroupRecommendToJson> backInfo = new DataBackInfo<MarketGroupRecommendToJson>();
		if (StrUtil.isBlank(gid) || StrUtil.isBlank(callback)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", "");

		}

		try {
			List<MarketGroupRecommendToJson> recommend = qxQuestionsService.marketGroupRecommend(Integer.parseInt(gid));
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(recommend);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 为pcweb端提供专题数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/user/getGroupForPcweb", method = RequestMethod.GET)
	public @ResponseBody String getGroupForPcweb(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String openid = request.getParameter("gid");

		ItemBackInfo backInfo = new ItemBackInfo();
		if (StrUtil.isBlank(openid) || StrUtil.isBlank(callback)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", info);
		}

		try {
			int gid = Integer.parseInt(openid);
			// 为pcweb端提供专题数据
			GroupForPcwebToJson groupInfo = qxQuestionsService.getGroupForPcweb(gid);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			// 此处set返回对象
			backInfo.setItem(groupInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

	/**
	 * 小程序获取站边题目
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/GetXcxT0", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo XcxGetT0(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String uid = map.get("uid");
		String gidStr = map.get("gid");
		T0QuesBackInfo<UgcGroupToJson> backInfo = new T0QuesBackInfo<UgcGroupToJson>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			int gid = Integer.parseInt(gidStr);
			// H5获取用户UGC专题/用户上传专题
			List<UgcGroupToJson> groups = qxQuestionsService.getUgcGroupsH5(uid, gid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setDone(groups.get(0).getDone());
				backInfo.setHeadimgurl(groups.get(0).getHeadimgurl());
				backInfo.setNickname(groups.get(0).getNickname());
				backInfo.setUgcCount(groups.size());

			}
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
			backInfo.setUid(uid);
			// 此处set返回对象
			backInfo.setData(groups);

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * H5 获取站边专题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/ques/getT0", method = RequestMethod.GET)
	public @ResponseBody String getT0(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String uid = request.getParameter("uid");
		String gidStr = request.getParameter("gid");

		T0QuesBackInfo<UgcGroupToJson> backInfo = new T0QuesBackInfo<UgcGroupToJson>();
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(callback) || StrUtil.isBlank(gidStr)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return JsonMapper.getInstance().toJsonP("paramIsBlank", info);

		}

		try {
			int gid = Integer.parseInt(gidStr);
			// H5获取用户UGC专题/用户上传专题
			List<UgcGroupToJson> groups = qxQuestionsService.getUgcGroupsH5(uid, gid);
			if (groups.size() > 0) {
				backInfo.setData(groups);
				backInfo.setDone(groups.get(0).getDone());
				backInfo.setHeadimgurl(groups.get(0).getHeadimgurl());
				backInfo.setNickname(groups.get(0).getNickname());
				backInfo.setUgcCount(groups.size());

			}
			// 获取用户关注数
			backInfo.setFollowsNum(systemUserService.followsNum(uid));
			// 获取获取粉丝数
			backInfo.setFollowersNum(systemUserService.followersNum(uid));
			backInfo.setUid(uid);
			// 此处set返回对象
			backInfo.setData(groups);

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}

		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}

}