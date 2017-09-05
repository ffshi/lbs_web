/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.push;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.push.PushContent2DB;
import com.innovate.cms.modules.qs.dao.push.QxPushInfoDao;
import com.innovate.cms.modules.qs.entity.msg.NoticeUserForService;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;

/**
 * 推送管理Service
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxPushInfoService extends CrudService<QxPushInfoDao, QxPushInfo> {

	public QxPushInfo get(String id) {
		return super.get(id);
	}
	
	public List<QxPushInfo> findList(QxPushInfo qxPushInfo) {
		return super.findList(qxPushInfo);
	}

	@Transactional(readOnly = false)
	public void save(QxPushInfo qxPushInfo) {
		if (StringUtils.isBlank(qxPushInfo.getId()))	
		{
			qxPushInfo.setIsNewRecord(true);
		}else {
			qxPushInfo.setIsNewRecord(false);
		}
		super.save(qxPushInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QxPushInfo qxPushInfo) {
		super.delete(qxPushInfo);
	}
	
	/**
	 * 
	 * @Title: getPushInfoByUid
	 * @Description: 根据UID查询推送信息
	 * @param uid
	 * @return    返回类型 QxPushInfo
	 *
	 */
	public QxPushInfo getPushInfoByUid(String uid) {
		return super.dao.getPushInfoByUid(uid);
	}
	/**
	 * 
	 * @Title: getInfoByDevID
	 * @Description: 查询设备ID是否存在
	 * @param device_id
	 * @return    返回类型 QxPushInfo
	 *
	 */
	public QxPushInfo getInfoByDevID(String device_id)
	{
		return super.dao.getInfoByDevID(device_id);
	}

	/**
	 * 保存/更新 推送设备ID
	 * @Title: saveByDevID
	 * @Description: 保存/更新
	 * @param pushInfo
	 * @param isUpdate    返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public void saveByDevID(QxPushInfo pushInfo, boolean isUpdate)
	{
		if (isUpdate)	
		{
			super.dao.updateByDevID(pushInfo);
		}else {
			pushInfo.setCreateTime(new Date());
			super.dao.insert(pushInfo);
		}
		
	}

	/**
	 * 保存推送记录
	 * @param pushContent2DB
	 * @return
	 */
	@Transactional(readOnly = false)
	public int savePushContent(PushContent2DB pushContent2DB) {
		return super.dao.savePushContent(pushContent2DB);
	}

	/**
	 * 获取用户通知列表
	 * @param uid
	 * @return
	 */
	public List<PushContent2DB> userNotices(String uid) {
		return super.dao.userNotices(uid);
	}

	/**
	 * 上拉获取用户通知列表
	 * @param uid
	 * @param pushConId
	 * @return
	 */
	public List<PushContent2DB> userUpNotices(String uid, int pushConId) {
		return super.dao.userUpNotices(uid,pushConId);
	}

	/**
	 * 获取消息报名通知用户列表
	 * @param mid
	 * @return
	 */
	public List<NoticeUserForService> msgNoticeUsers(int mid) {
		return super.dao.msgNoticeUsers(mid);
	}
	
}