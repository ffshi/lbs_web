/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.mybatis.Page;
import com.innovate.cms.common.security.Digests;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.IdGen;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.common.entity.UserPic;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.qs.dao.user.SystemUserDao;
import com.innovate.cms.modules.qs.entity.user.FollowerUser;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;

/**
 * 系统用户Service
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class SystemUserService extends CrudService<SystemUserDao, SystemUser> {

	@Autowired
	SystemUserInfoService systemUserInfoService;

	public SystemUser get(String id) {
		return super.get(id);
	}

	public List<SystemUser> findList(SystemUser systemUser) {
		return super.findList(systemUser);
	}

	@Transactional(readOnly = false)
	public void save(SystemUser systemUser) {
		if (StringUtils.isBlank(systemUser.getId())) {
			systemUser.setIsNewRecord(true);
		} else {
			systemUser.setIsNewRecord(false);
		}
		super.save(systemUser);
	}

	@Transactional(readOnly = false)
	public void delete(SystemUser systemUser) {
		super.delete(systemUser);
	}

	/**
	 * 
	 * @Title: findByUid
	 * @Description: 根据uid查询返回好友的用户头像昵称等记录
	 * @param uid
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser findByUid(String uid) {
		return super.dao.findByUid(uid);
	}

	/**
	 * 
	 * @Title: getToken
	 * @Description: 查询token是否有效
	 * @param id
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getToken(String id) {
		return super.dao.getToken(id);
	}

	/**
	 * 
	 * @Title: logout
	 * @Description: 退出登录
	 * @param tokenLocal
	 * @param id
	 * @return 返回类型 int
	 *
	 */
	@Transactional(readOnly = false)
	public Integer logout(SystemUser systemUser) {
		return super.dao.logout(systemUser);
	}

	/**
	 * 
	 * @Title: getUserByThreeUnionid
	 * @Description: 查询unionid是否存在
	 * @param unionid
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserByThreeUnionid(String unionid) {
		return super.dao.getUserByThreeUnionid(unionid);
	}

	@Transactional(readOnly = false)
	public String saveUserAndInfo(SystemUser systemUser, SystemUserInfo systemUserInfo) {
		if (StringUtils.isBlank(systemUser.getUid())) {
			// 新增
			String _uuid = IdGen.uuid();
			systemUser.setUid(_uuid);// 插入主键
			systemUserInfo.setUid(_uuid);
			// 如果用户名为空,设置为空字符串(手机号注册默认没有用户名)
			if (systemUser.getNickname() == null && systemUser.getMobile() != null) {
				if (systemUser.getMobile().length() == 13) {
					//手机注册设置默认用户名:如13588849987设置为135****9987
					systemUser.setNickname(systemUser.getMobile().substring(0, 3) + "****" + systemUser.getMobile().substring(7));
				} else {
					systemUser.setNickname("");
				}

			}
			super.dao.insertUser(systemUser);
			systemUserInfoService.insertInfo(systemUserInfo);
			return _uuid;
		} else {
			// 更新
			super.dao.updateUser(systemUser);
			systemUserInfoService.updateInfo(systemUserInfo);
			return systemUser.getUid();
		}

	}

	/**
	 * 
	 * @Title: getUserByLoginName
	 * @Description: 根据用户名+密码查询用户
	 * @param loginName
	 * @param password
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserByLoginName(String loginName, String password) {
		String _password = Digests.md5(password);
		return super.dao.getUserByLoginName(loginName, _password);
	}

	/**
	 * 
	 * @Title: getUserByMobile
	 * @Description: 根据手机号+密码查询用户
	 * @param mobile
	 * @param password
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserByMobile(String mobile, String password) {
		String _password = Digests.md5(password);
		return super.dao.getUserByMobile(mobile, _password);
	}

	/**
	 * 根据手机号获取用户
	 * 
	 * @param mobile
	 * @return
	 */
	public SystemUser getUserOnlyByMobile(String mobile) {
		return super.dao.getUserOnlyByMobile(mobile);
	}

	/**
	 * 
	 * @Title: getUserInfoByUid
	 * @Description: 根据UID获取用户资料)
	 * @param _uid
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserInfoByUid(String _uid) {
		return super.dao.getUserInfoByUid(_uid);
	}

	/**
	 * 
	 * @Title: putUserInfo
	 * @Description: 更新用户信息
	 * @param systemUser
	 *            返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public void putUserInfo(SystemUser systemUser) {
		super.dao.updateUserInfo(systemUser);
	}

	/**
	 * 分页获取注册用户列表
	 * 
	 * @param uid
	 * @param page
	 * @return
	 */

	public List<RegUserInfoToJson> getUserList(String uid, Page<RegUserInfoToJson> page) {
		return super.dao.getUserList(uid, page.getOffset(), page.getLimit());
	}

	/**
	 * 获取除参数uid以外的用户数量
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 */

	public int getUserCount(String uid) {
		return super.dao.getUserCount(uid);
	}

	/**
	 * 获取最近新增的1000个用户
	 * 
	 * @param systemUser
	 * @return
	 */
	public List<SystemUser> findLastest1000(SystemUser systemUser) {
		return super.dao.findLastest1000(systemUser);
	}

	/**
	 * 根据昵称搜索用户
	 * 
	 * @param nickname
	 * @return
	 */

	public List<RegUserInfoToJson> getUserInfoByNickname(String nickname) {
		return super.dao.getUserInfoByNickname(nickname);
	}

	/**
	 * 获取用户关注的好友
	 * 
	 * @param uid
	 * @return
	 */
	public List<SystemUser> findFollows(String uid) {
		return super.dao.findFollows(uid);
	}

	/**
	 * 获取用户粉丝
	 * 
	 * @param uid
	 * @return
	 */
	public List<FollowerUser> findFollowers(String uid) {
		return super.dao.findFollowers(uid);
	}

	/**
	 * 获取用户关注数
	 * 
	 * @param uid
	 * @return
	 */
	public int followsNum(String uid) {
		return super.dao.followsNum(uid);
	}

	/**
	 * 获取获取粉丝数
	 * 
	 * @param uid
	 * @return
	 */
	public int followersNum(String uid) {
		return super.dao.followersNum(uid);
	}

	/**
	 * 获取用户足迹数
	 * 
	 * @param uid
	 * @return
	 */
	public int historyNum(String uid) {
		return super.dao.historyNum(uid);
	}

	/**
	 * 获取用户创建作品数
	 * 
	 * @param uid
	 * @return
	 */

	public int getUgcNum(String uid) {
		return super.dao.getUgcNum(uid);
	}

	/**
	 * 存储相册
	 * @param uid
	 * @param pic
	 */
	@Transactional(readOnly = false)
	public void savePhoto(String uid, String pic) {
		super.dao.savePhoto(uid,pic);
	}

	/**
	 * 删除相册
	 * @param picId
	 */
	@Transactional(readOnly = false)
	public void delPhoto(int picId) {
		super.dao.delPhoto(picId);
	}

	/**
	 * 获取用户相册
	 * @param uid
	 * @return
	 */
	public List<UserPic> userPics(String uid) {
		return super.dao.userPics(uid);
	}

	/**
	 * 上传/屏蔽通信录
	 * @param uid
	 * @param addressList
	 * @param isShield
	 */
	@Transactional(readOnly = false)
	public void shieldAddressList(String uid, String addressList, int isShield) {
		super.dao.shieldAddressList(uid,addressList,isShield);
	}

	/**
	 * 修改背景图
	 * @param uid
	 * @param backgroundImage
	 */
	@Transactional(readOnly = false)
	public void uploadBackgroundImg(String uid, String backgroundImage) {
		super.dao.uploadBackgroundImg(uid,backgroundImage);
	}

	/**
	 * 获取有设置屏蔽功能的用户uid
	 * @param uid
	 * @return
	 */
	public List<String> getShieldUids(String uid,String mobile) {
		return super.dao.getShieldUids(uid,mobile);
	}

}