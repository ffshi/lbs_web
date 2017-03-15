/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.sns.QxFootprintDao;
import com.innovate.cms.modules.qs.entity.ques.Template4Result;
import com.innovate.cms.modules.qs.entity.ques.Template5Result;
import com.innovate.cms.modules.qs.entity.sns.QxFootprint;
import com.innovate.cms.modules.qs.entity.sns.QxFootprintWithPraiseAndComment;

/**
 * 用户足迹表 qx_footprint sevice
 * 
 * @author shifangfang
 *
 */
@Service
@Transactional(readOnly = true)
public class QxFootprintService extends CrudService<QxFootprintDao, QxFootprint> {

	public List<QxFootprint> getFootprintByuid(String uid) {
		// TODO Auto-generated method stub
		return super.dao.getFootprintByuid(uid);
	}

	/**
	 * 存储用户足迹
	 * 
	 * @param footprint
	 */
	@Transactional(readOnly = false)
	public void saveFootprint(QxFootprint footprint) {
		// TODO Auto-generated method stub
		super.dao.saveFootprint(footprint);
	}

	/**
	 * 根据uid，获取小于footid的10条足迹信息
	 * 
	 * @param uid
	 * @param footid
	 * @return
	 */
	public List<QxFootprintWithPraiseAndComment> getFootprintByuidup(String uid, int footid) {
		// TODO Auto-generated method stub
		return super.dao.getFootprintByuidup(uid, footid);
	}

	/**
	 * 返回大于footid所有最新数据
	 * 
	 * @param uid
	 * @param footid
	 * @return
	 */
	public List<QxFootprintWithPraiseAndComment> getFootprintByuidAllnew(String uid, int footid) {
		// TODO Auto-generated method stub
		return super.dao.getFootprintByuidAllnew(uid, footid);
	}

	/**
	 * 返回最新10条足迹数据
	 * 
	 * @param uid
	 * @return
	 */
	public List<QxFootprintWithPraiseAndComment> getFootprintByuidtop10(String uid) {
		// TODO Auto-generated method stub
		return super.dao.getFootprintByuidtop10(uid);
	}

	/**
	 * 模板4存储用户做题足迹 模板5无匹配度
	 * 
	 * @param uid
	 * @param gid
	 * @param results
	 * @param footType
	 */
	@Transactional(readOnly = false)
	public void saveFootprintTemplate4(String uid, String gid, String results, String footType) {
		// TODO Auto-generated method stub
		super.dao.saveFootprintTemplate4(uid, gid, results, footType);
	}

	/**
	 * 模板5有匹配度足迹存储
	 * 
	 * @param uid
	 * @param gid
	 * @param fuid
	 * @param results
	 * @param footType
	 */
	@Transactional(readOnly = false)
	public void saveFootprintTemplate5(String uid, String gid, String fuid, String results, String footType) {
		super.dao.saveFootprintTemplate5(uid, gid, fuid, results, footType);
	}

	/**
	 * 做推送到时候，当时为了获取足迹id而做的，后来又不需要了
	 * 
	 * 模板5有匹配度足迹存储
	 */
	// @Transactional(readOnly = false)
	// public void saveFootprintTemplate5new(StoreFootPrint storeFootPrint) {
	// // TODO Auto-generated method stub
	// super.dao.saveFootprintTemplate5new(storeFootPrint);
	// }

	/**
	 * 
	 * @Title: findTemplate4Result
	 * @Description: 用户已经做过模板4的时候 直接查询出结果
	 * @param gid
	 * @param uid
	 * @return 返回类型 Template4Result
	 *
	 */
	public Template4Result findTemplate4Result(int gid, String uid) {
		return super.dao.findTemplate4Result(gid, uid);
	}

	/**
	 * 
	 * @Title: findTemplate5Result
	 * @Description: 用户已经做过模板5的时候 直接查询出匹配度
	 * @param myUid
	 * @param followUid
	 * @return 返回类型 Template5Result
	 *
	 */
	public Template5Result findTemplate5Result(String myUid, String followUid, int gid) {
		return super.dao.findTemplate5Result(myUid, followUid, gid);
	}

	/**
	 * 陌生匹配度查询
	 * @param myUid
	 * @param followUid
	 * @param gid
	 * @return
	 */
	public Template5Result findTemplate5ResultStranger(String myUid, String followUid, int gid) {
		return super.dao.findTemplate5ResultStranger(myUid, followUid, gid);
	}

	/**
	 * h5专用 h5匹配度单独存储
	 * 
	 * @Title: findTemplate5Result
	 * @Description: 用户已经做过模板5的时候 直接查询出匹配度
	 * @param myUid
	 * @param followUid
	 * @return 返回类型 Template5Result
	 *
	 */
	public Template5Result findTemplate5ResultH5(String myUid, String followUid, int gid) {

		return super.dao.findTemplate5ResultH5(myUid, followUid, gid);
	}

	/**
	 * 根据uid更新足迹信息
	 * 
	 * @param uid
	 * @param nickname
	 * @param headimgurl
	 */
	@Transactional(readOnly = false)
	public void updateFootprint(String uid, String nickname, String headimgurl) {
		super.dao.updateFootprint(uid, nickname, headimgurl);
	}

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
	@Transactional(readOnly = false)
	public void saveFootprintT4stage4(String uid, String gid, String results, String footType, String resultsTag) {
		// TODO Auto-generated method stub
		super.dao.saveFootprintT4stage4(uid, gid, results, footType, resultsTag);
	}

	/**
	 * 根据足迹ID获取单条足迹
	 * 
	 * @param footid
	 * @return
	 */
	public QxFootprintWithPraiseAndComment getFootprintByFootid(Integer footid) {
		return super.dao.getFootprintByFootid(footid);
	}

	/**
	 * 设置足迹可见
	 * 
	 * @param uid
	 * @param gid
	 * @param visible
	 */
	@Transactional(readOnly = false)
	public void setVisible(String uid, int gid, int visible) {
		super.dao.setVisible(uid, gid, visible);

	}

	/**  
	 * 根据专题id和用户id获取足迹ID
	 * @param gid
	 * @param uid
	 * @return  
	 */    
	
	public Integer getFootid(Integer gid, String uid) {
		return super.dao.getFootidByGidAndUid(gid, uid);
	}
}