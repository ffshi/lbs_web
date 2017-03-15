/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.ques;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.EssentialGroupTestBToJson;
import com.innovate.cms.modules.data.entity.EssentialGroupToJson;
import com.innovate.cms.modules.data.entity.GroupForPcwebToJson;
import com.innovate.cms.modules.data.entity.MarketGroupConfigueToJson;
import com.innovate.cms.modules.data.entity.MarketGroupRecommendToJson;
import com.innovate.cms.modules.data.entity.UgcGroupToJson;
import com.innovate.cms.modules.data.entity.UgcHistoryGroupToJson;
import com.innovate.cms.modules.data.entity.XcxEssentialGroupToJson;
import com.innovate.cms.modules.qs.entity.ques.Group0;
import com.innovate.cms.modules.qs.entity.ques.Questions;
import com.innovate.cms.modules.qs.entity.ques.QxQuestions;

/**
 * 趣选题库DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxQuestionsDao extends CrudDao<QxQuestions> {

	/**
	 * 根据gid获取专题信息
	 * 
	 * @param gid
	 * @return
	 */
	public List<Questions> getQsbyGid(@Param("gid") int gid, @Param("templateTag") String templateTag);

	/**
	 * 获取专题问题，选项,同时获取用户的答案,从用户足迹内获取专题，需要返回用户做过的该专题的答案
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<Questions> getQsbyGidFromUserfoot(@Param("uid") String uid, @Param("gid") int gid, @Param("templateTag") String templateTag);

	/**
	 * 获取营销推广专题配置
	 * 
	 * @param gid
	 * @return
	 */
	public MarketGroupConfigueToJson getConfig(int gid);

	/**
	 * H5推广运营-热门专题推荐配置 运营根绝专题自定义推荐内容
	 * 
	 * @param gid
	 * @return
	 */
	public List<MarketGroupRecommendToJson> marketGroupRecommend(@Param("gid") int gid);

	/**
	 * 为pcweb端提供专题数据
	 * 
	 * @param gid
	 * @return
	 */
	public GroupForPcwebToJson getGroupForPcweb(@Param("gid") int gid);


	/**
	 * 获取首页精华专题（当前时间小于8点，显示昨天的数据，大于8点，显示今天的数据）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<EssentialGroupToJson> getEssentialGroups(@Param("startDate")String startDate, @Param("endDate")String endDate);

	/**
	 * 获取用户UGC专题
	 * @param uid
	 * @return
	 */
	public List<UgcGroupToJson> getUgcGroups(@Param("uid")String uid);

	/**  
	 * 获取社区专题--显示最新的10个
	 * @return  
	 */    
	
	public List<EssentialGroupToJson> getCommunityGroupList();

	/**  
	 * 上拉显示更多社区专题--显示小于gid的最近的10条数据
	 * @param gid
	 * @return  
	 */    
	
	public List<EssentialGroupToJson> getMoreGroups(@Param("gid")Integer gid);

	/**  
	 * 下拉刷新社区专题--返回大于gid的所有最新专题
	 * @param gid
	 * @return  
	 */    
	
	public List<EssentialGroupToJson> getAllNewCommunityGroups(@Param("gid")Integer gid);

	/**
	 * 获取用户做过的站边专题(模板0类型的)历史记录
	 * @param uid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getHistoryGroups(@Param("uid")String uid);

	/**
	 * H5获取用户UGC专题/用户上传专题
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<UgcGroupToJson> getUgcGroupsH5(@Param("uid")String uid, @Param("gid")int gid);

	/**
	 * 根据日期设置首页轮播专题
	 * @param gid
	 * @param showDate
	 */
	public void setRotationGroup(@Param("gid")int gid, @Param("showDate")int showDate);

	/**
	 * 获取用户作品
	 * @param uid
	 * @return
	 */
	public List<Group0> myUgc(@Param("uid")String uid);

	/**
	 * 按指定用户和gid获取模板0
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getT0(@Param("uid")String uid, @Param("gid")int gid);

	/**  
	 * 好用动态-站边题详情：按指定uid, followUid,gid获取模板0相关信息，并返回uid,followUid做题结果
	 * @param uid
	 * @param followUid
	 * @param gid
	 * @return  
	 */    
	
	public List<UgcHistoryGroupToJson> getT0ByFollowDynamic(@Param("uid")String uid, @Param("followUid")String followUid, @Param("gid")int gid);

	
	/**
	 * 小程序获取所有精华专题（当前时间小于8点，显示昨天的数据，大于8点，显示今天的数据）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<XcxEssentialGroupToJson> getXcxEssentialGroups(@Param("startDate")String startDate, @Param("endDate")String endDate,@Param("uid")String uid);

	/**
	 * 小程序-获取社区-专题列表(显示最新的10条数据)
	 * @param uid
	 * @return
	 */
	public List<XcxEssentialGroupToJson> getXcxCommunityGroupList(String uid);

	/**
	 * 小程序-上拉显示更多社区专题（显示小于gid的最近的10条记录）
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<XcxEssentialGroupToJson> getXcxMoreGroups(@Param("uid")String uid, @Param("gid")Integer gid);

	/**
	 * 小程序-下拉刷新社区专题--返回大于gid的所有专题数据
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<EssentialGroupToJson> getXcxAllNewCommunityGroups(@Param("uid")String uid, @Param("gid")Integer gid);

	/**
	 * 小程序-获取用户UGC专题/用户上传专题
	 * @param uid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getXcxUgcGroups(@Param("uid")String uid);

	/**
	 * 小程序-获取好友用户UGC专题/用户上传专题
	 * @param uid
	 * @param fuid
	 * @return
	 */
	public List<UgcHistoryGroupToJson> getXcxfUgcGroups(@Param("uid")String uid, @Param("fuid")String fuid);

	/**
	 * 获取所有精华专题-testB精选与专题混排
	 * @param intDate
	 * @return
	 */
	public List<EssentialGroupTestBToJson> getEssentialGroupsTestB(@Param("intDate")int intDate);



}