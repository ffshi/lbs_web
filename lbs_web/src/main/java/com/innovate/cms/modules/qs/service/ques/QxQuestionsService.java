/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.ques;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.data.entity.EssentialGroupTestBToJson;
import com.innovate.cms.modules.data.entity.EssentialGroupToJson;
import com.innovate.cms.modules.data.entity.GroupForPcwebToJson;
import com.innovate.cms.modules.data.entity.MarketGroupConfigueToJson;
import com.innovate.cms.modules.data.entity.MarketGroupRecommendToJson;
import com.innovate.cms.modules.data.entity.UgcGroupToJson;
import com.innovate.cms.modules.data.entity.UgcHistoryGroupToJson;
import com.innovate.cms.modules.data.entity.XcxEssentialGroupToJson;
import com.innovate.cms.modules.qs.dao.ques.QxQuestionsDao;
import com.innovate.cms.modules.qs.entity.ques.Group0;
import com.innovate.cms.modules.qs.entity.ques.OptionsAndNum;
import com.innovate.cms.modules.qs.entity.ques.Questions;
import com.innovate.cms.modules.qs.entity.ques.QxQuestions;

/**
 * 趣选题库Service
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxQuestionsService extends CrudService<QxQuestionsDao, QxQuestions> {

	public QxQuestions get(String id) {
		return super.get(id);
	}

	public List<QxQuestions> findList(QxQuestions qxQuestions) {
		return super.findList(qxQuestions);
	}

	@Transactional(readOnly = false)
	public void save(QxQuestions qxQuestions) {
		if (StringUtils.isBlank(qxQuestions.getId())) {
			qxQuestions.setIsNewRecord(true);
		} else {
			qxQuestions.setIsNewRecord(false);
		}
		super.save(qxQuestions);
	}

	@Transactional(readOnly = false)
	public void delete(QxQuestions qxQuestions) {
		super.delete(qxQuestions);
	}

	/**
	 * 根据gid获取专题信息
	 * 
	 * @param gid
	 * @return
	 */
	public List<Questions> getQsbyGid(int gid, String templateTag) {
		return super.dao.getQsbyGid(gid, templateTag);
	}

	/**
	 * 获取专题问题，选项,同时获取用户的答案,从用户足迹内获取专题，需要返回用户做过的该专题的答案
	 * 
	 * @param uid
	 * @param parseInt
	 * @return
	 */
	public List<Questions> getQsbyGidFromUserfoot(String uid, int gid, String templateTag) {
		return super.dao.getQsbyGidFromUserfoot(uid, gid, templateTag);
	}

	/**
	 * 获取营销推广专题配置
	 * 
	 * @param gid
	 * @return
	 */
	public MarketGroupConfigueToJson getConfig(int gid) {
		// TODO Auto-generated method stub
		return super.dao.getConfig(gid);
	}

	/**
	 * H5推广运营-热门专题推荐配置 运营根绝专题自定义推荐内容
	 * 
	 * @param parseInt
	 * @return
	 */
	public List<MarketGroupRecommendToJson> marketGroupRecommend(int gid) {
		return super.dao.marketGroupRecommend(gid);
	}

	/**
	 * 为pcweb端提供专题数据
	 * 
	 * @param gid
	 * @return
	 */
	public GroupForPcwebToJson getGroupForPcweb(int gid) {
		return super.dao.getGroupForPcweb(gid);
	}

	/**
	 * 获取所有精华专题
	 * 
	 * @return
	 */
	public List<EssentialGroupToJson> getEssentialGroups() {
		Date createTime = new Date();
		List<EssentialGroupToJson> groupToJsons = Lists.newArrayList();
		// 当前时间小于8点，显示昨天的数据，大于8点，显示今天的数据
		if (DateUtils.getTime().compareTo(Global.SPLIT_TIME) < 0) {
			createTime = DateUtils.addDays(createTime, -1);
		}
		String startDate = DateUtils.formatDate(createTime, "yyyy-MM-dd");
		String endDate = DateUtils.formatDate(DateUtils.addDays(createTime, 1), "yyyy-MM-dd");
		groupToJsons = super.dao.getEssentialGroups(startDate, endDate);
		packageParticipateInfo(groupToJsons);
		return groupToJsons;
	}

	/**
	 * 获取用户UGC专题
	 * 
	 * @param uid
	 * @return
	 */
	public List<UgcGroupToJson> getUgcGroups(String uid) {
		return super.dao.getUgcGroups(uid);
	}

	/**
	 * 获取社区专题--显示最新的10个
	 * 
	 * @return
	 */

	public List<EssentialGroupToJson> getCommunityGroupList() {
		List<EssentialGroupToJson> groupToJsons = Lists.newArrayList();
		groupToJsons = super.dao.getCommunityGroupList();
		packageParticipateInfo(groupToJsons);
		return groupToJsons;
	}

	/**
	 * 上拉显示更多社区专题--显示小于gid的最近的10条数据
	 * 
	 * @param gid
	 * @return
	 */

	public List<EssentialGroupToJson> getMoreGroups(Integer gid) {
		List<EssentialGroupToJson> groupToJsons = Lists.newArrayList();
		groupToJsons = super.dao.getMoreGroups(gid);
		packageParticipateInfo(groupToJsons);
		return groupToJsons;
	}

	/**
	 * 下拉刷新社区专题--返回大于gid的所有最新专题
	 * 
	 * @param gid
	 * @return
	 */

	public List<EssentialGroupToJson> getAllNewCommunityGroups(Integer gid) {
		List<EssentialGroupToJson> groupToJsons = Lists.newArrayList();
		groupToJsons = super.dao.getAllNewCommunityGroups(gid);
		packageParticipateInfo(groupToJsons);
		return groupToJsons;
	}

	/**
	 * 获取用户做过的站边专题(模板0类型的)历史记录
	 * 
	 * @param uid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getHistoryGroups(String uid) {
		return super.dao.getHistoryGroups(uid);
	}

	/**
	 * 封装专题参与人数
	 * 
	 * @return
	 */
	private List<EssentialGroupToJson> packageParticipateInfo(List<EssentialGroupToJson> groupToJsons) {
		for (EssentialGroupToJson groupToJson : groupToJsons) {
			List<OptionsAndNum> optionsAndNums = groupToJson.getOptionsAndNums();
			int participateNum = 0;
			for (OptionsAndNum optionsAndNum : optionsAndNums) {
				participateNum += optionsAndNum.getCount();
			}
			groupToJson.setParticipateNum(participateNum);
		}
		return groupToJsons;
	}

	/**
	 * H5获取用户UGC专题/用户上传专题
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<UgcGroupToJson> getUgcGroupsH5(String uid, int gid) {
		return super.dao.getUgcGroupsH5(uid, gid);
	}

	/**
	 * 获取用户作品
	 * 
	 * @param uid
	 * @return
	 */
	public List<Group0> myUgc(String uid) {
		return super.dao.myUgc(uid);
	}

	/**
	 * 按指定用户和gid获取模板0
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getT0(String uid, int gid) {
		return super.dao.getT0(uid, gid);
	}

	/**
	 * 好用动态-站边题详情：按指定uid, followUid,gid获取模板0相关信息，并返回uid,followUid做题结果
	 * 
	 * @param uid
	 * @param followUid
	 * @param gid
	 * @return
	 */

	public List<UgcHistoryGroupToJson> getT0ByFollowDynamic(String uid, String followUid, int gid) {
		return super.dao.getT0ByFollowDynamic(uid, followUid, gid);
	}

	/**
	 * 小程序获取所有精华专题
	 * 
	 * @return
	 */
	public List<XcxEssentialGroupToJson> getXcxEssentialGroups(String uid) {
		Date createTime = new Date();
		List<XcxEssentialGroupToJson> groupToJsons = Lists.newArrayList();
		// 当前时间小于8点，显示昨天的数据，大于8点，显示今天的数据
		if (DateUtils.getTime().compareTo(Global.SPLIT_TIME) < 0) {
			createTime = DateUtils.addDays(createTime, -1);
		}
		String startDate = DateUtils.formatDate(createTime, "yyyy-MM-dd");
		String endDate = DateUtils.formatDate(DateUtils.addDays(createTime, 1), "yyyy-MM-dd");
		groupToJsons = super.dao.getXcxEssentialGroups(startDate, endDate, uid);
		// packageParticipateInfo(groupToJsons);
		return groupToJsons;
	}

	/**
	 * 小程序-获取社区-专题列表(显示最新的10条数据)
	 * @param uid
	 * @return
	 */
	public List<XcxEssentialGroupToJson> getXcxCommunityGroupList(String uid) {
		return super.dao.getXcxCommunityGroupList(uid);
	}

	/**
	 * 小程序-上拉显示更多社区专题（显示小于gid的最近的10条记录）
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<XcxEssentialGroupToJson> getXcxMoreGroups(String uid, Integer gid) {
		return super.dao.getXcxMoreGroups(uid,gid);
	}

	/**
	 * 小程序-下拉刷新社区专题--返回大于gid的所有专题数据
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<EssentialGroupToJson> getXcxAllNewCommunityGroups(String uid, Integer gid) {
		return super.dao.getXcxAllNewCommunityGroups(uid,gid);
	}

	/**
	 * 小程序-获取用户UGC专题/用户上传专题
	 * @param uid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getXcxUgcGroups(String uid) {
		return super.dao.getXcxUgcGroups(uid);
	}

	/**
	 * 小程序-获取好友用户UGC专题/用户上传专题
	 * @param uid
	 * @param fuid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getXcxfUgcGroups(String uid, String fuid) {
		return super.dao.getXcxfUgcGroups(uid,fuid);
	}

	/**
	 * 获取所有精华专题-testB精选与专题混排
	 * @param intDate
	 * @return
	 */
	public List<EssentialGroupTestBToJson> getEssentialGroupsTestB(int intDate) {
		return super.dao.getEssentialGroupsTestB(intDate);
	}

}