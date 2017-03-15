/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.ques.Template4Result;
import com.innovate.cms.modules.qs.entity.ques.Template5Result;
import com.innovate.cms.modules.qs.entity.sns.QxFootprint;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintWithPraiseAndComment;

/**
 * 用户足迹表 qx_footprint Dao
 * 
 * @author shifangfang
 *
 */
@MyBatisDao
public interface QxFootprintDao extends CrudDao<QxFootprint> {

	public List<QxFootprint> getFootprintByuid(@Param("uid") String uid);

	public void saveFootprint(QxFootprint footprint);

	/**
	 * 根据uid，获取小于footid的10条足迹信息
	 * 
	 * @param uid
	 * @param footid
	 * @return
	 */
	public List<QxFootprintWithPraiseAndComment> getFootprintByuidup(@Param("uid") String uid, @Param("footid") int footid);

	/**
	 * 返回大于footid所有最新数据
	 * 
	 * @param uid
	 * @param footid
	 * @return
	 */
	public List<QxFootprintWithPraiseAndComment> getFootprintByuidAllnew(@Param("uid") String uid, @Param("footid") int footid);

	/**
	 * 返回最新10条足迹数据
	 * 
	 * @param uid
	 * @return
	 */
	public List<QxFootprintWithPraiseAndComment> getFootprintByuidtop10(@Param("uid") String uid);

	/**
	 * 模板4存储用户做题足迹 模板5无匹配度
	 * 
	 * @param uid
	 * @param gid
	 * @param results
	 * @param footType
	 */
	public void saveFootprintTemplate4(@Param("uid") String uid, @Param("gid") String gid, @Param("results") String results, @Param("footType") String footType);

	/**
	 * 模板5有匹配度足迹存储
	 * 
	 * @param uid
	 * @param gid
	 * @param fuid
	 * @param results
	 * @param footType
	 */
	public void saveFootprintTemplate5(@Param("uid") String uid, @Param("gid") String gid, @Param("fuid") String fuid, @Param("results") String results, @Param("footType") String footType);

	/**
	 * 
	 * @Title: findTemplate4Result
	 * @Description: 用户已经做过模板4的时候 直接查询出结果
	 * @param gid
	 * @param uid
	 * @return 返回类型 Template4Result
	 *
	 */
	public Template4Result findTemplate4Result(@Param("gid") int gid, @Param("uid") String uid);

	/**
	 * 
	 * @Title: findTemplate5Result
	 * @Description: 用户已经做过模板5的时候 直接查询出匹配度
	 * @param myUid
	 * @param followUid
	 * @return 返回类型 Template5Result
	 *
	 */
	public Template5Result findTemplate5Result(@Param("myUid") String myUid, @Param("followUid") String followUid, @Param("gid") int gid);

	/**
	 * h5专用 h5匹配度单独存储
	 * 
	 * @Description: 用户已经做过模板5的时候 直接查询出匹配度
	 * @param myUid
	 * @param followUid
	 * @return 返回类型 Template5Result
	 *
	 */
	public Template5Result findTemplate5ResultH5(@Param("myUid") String myUid, @Param("followUid") String followUid, @Param("gid") int gid);

	/**
	 * 根据uid更新足迹信息
	 * 
	 * @param uid
	 * @param nickname
	 * @param headimgurl
	 */

	public void updateFootprint(@Param("uid") String uid, @Param("nickname") String nickname, @Param("headimgurl") String headimgurl);

	/**
	 * <!-- 四期需求 改为插入结果图片，没有图片插入小图g.small_icon 20160712，增加结果描述foot_result_desc
	 * -->
	 * 
	 * @param uid
	 * @param gid
	 * @param results
	 * @param footType
	 * @param resultsTag
	 *            模板4选项结果A/B...
	 */
	public void saveFootprintT4stage4(@Param("uid") String uid, @Param("gid") String gid, @Param("results") String results, @Param("footType") String footType, @Param("resultsTag") String resultsTag);

	/**
	 * 根据足迹ID获取单条足迹
	 * 
	 * @param footid
	 * @return
	 */
	public QxFootprintWithPraiseAndComment getFootprintByFootid(@Param("footid") Integer footid);

	/**
	 * 设置足迹可见
	 * 
	 * @param uid
	 * @param gid
	 * @param visible
	 */
	public void setVisible(@Param("uid") String uid, @Param("gid") int gid, @Param("visible") int visible);

	/**
	 * 陌生匹配度查询
	 * @param myUid
	 * @param followUid
	 * @param gid
	 * @return
	 */
	public Template5Result findTemplate5ResultStranger(@Param("myUid") String myUid, @Param("followUid") String followUid, @Param("gid") int gid);

	/**  
	 * 根据专题id和用户id获取足迹ID
	 * @param gid
	 * @param uid
	 * @return  
	 */    
	
	public Integer getFootidByGidAndUid(@Param("gid")Integer gid, @Param("uid")String uid);

	/**
	 * 做推送到时候，当时为了获取足迹id而做的，后来又不需要了 模板5有匹配度足迹存储
	 */
	// public void saveFootprintTemplate5new(StoreFootPrint storeFootPrint);

}