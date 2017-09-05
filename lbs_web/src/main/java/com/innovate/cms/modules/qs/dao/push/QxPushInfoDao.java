/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.push;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.push.PushContent2DB;
import com.innovate.cms.modules.qs.entity.msg.NoticeUserForService;
import com.innovate.cms.modules.qs.entity.push.QxPushInfo;

/**
 * 推送管理DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxPushInfoDao extends CrudDao<QxPushInfo> {
	/**
	 * 
	 * @Title: getInfoByDevID
	 * @Description: 根据设备ID查询对象
	 * @param device_id
	 * @return 返回类型 QxPushInfo
	 *
	 */
	public QxPushInfo getInfoByDevID(@Param("device_id") String device_id);

	/**
	 * 
	 * @Title: updateByDevID
	 * @Description: 更新设备ID
	 * @param pushInfo
	 *            返回类型 void
	 *
	 */
	public void updateByDevID(QxPushInfo pushInfo);

	/**
	 * 
	 * @Title: getPushInfoByUid
	 * @Description: 根据UID查询推送信息
	 * @param uid
	 * @return 返回类型 QxPushInfo
	 *
	 */
	public QxPushInfo getPushInfoByUid(@Param("uid") String uid);

	/**
	 * 保存推送记录
	 * 
	 * @param pushContent2DB
	 * @return
	 */
	public int savePushContent(PushContent2DB pushContent2DB);

	/**
	 * 获取用户通知列表
	 * @param uid
	 * @return
	 */
	public List<PushContent2DB> userNotices(@Param("uid")String uid);
	/**
	 * 上拉获取用户通知列表
	 * @param uid
	 * @param pushConId
	 * @return
	 */
	public List<PushContent2DB> userUpNotices(@Param("uid")String uid, @Param("pushConId")int pushConId);
	/**
	 * 获取消息报名通知用户列表
	 * @param mid
	 * @return
	 */
	public List<NoticeUserForService> msgNoticeUsers(@Param("mid")int mid);

}