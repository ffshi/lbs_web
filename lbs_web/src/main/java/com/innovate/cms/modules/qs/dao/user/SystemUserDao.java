/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.common.entity.UserPic;
import com.innovate.cms.modules.data.entity.RegUserInfoToJson;
import com.innovate.cms.modules.qs.entity.user.FollowerUser;
import com.innovate.cms.modules.qs.entity.user.SystemUser;

/**
 * 系统用户DAO接口
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface SystemUserDao extends CrudDao<SystemUser> {

	/**
	 * 
	 * @Title: findByUid
	 * @Description: 查询用户返回少量信息
	 * @param uid
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser findByUid(@Param("uid") String uid);

	/**
	 * 
	 * @Title: getToken
	 * @Description: 获取当前用户Token
	 * @param id
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getToken(@Param("uid") String id);

	/**
	 * 
	 * @Title: 更新token为空字符串
	 * @Description: 当前用户退出登录
	 * @param systemUser
	 * @return 返回类型 int
	 *
	 */
	public int logout(SystemUser systemUser);

	/**
	 * 
	 * @Title: getUserByThreeUnionid
	 * @Description: 根据unionid查询用户是否存在
	 * @param unionid
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserByThreeUnionid(@Param("unionid") String unionid);

	/**
	 * 
	 * @Title: insertUser
	 * @Description: 新增用户
	 * @param systemUser
	 *            返回类型 void
	 *
	 */
	public void insertUser(SystemUser systemUser);

	/**
	 * 
	 * @Title: updateUser
	 * @Description: 更新用户
	 * @param systemUser
	 *            返回类型 void
	 *
	 */
	public void updateUser(SystemUser systemUser);

	/**
	 * 
	 * @Title: getUserByLoginName
	 * @Description: 根据用户名+密码查询用户
	 * @param loginName
	 * @param password
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserByLoginName(@Param("loginName") String loginName, @Param("password") String password);

	/**
	 * 
	 * @Title: getUserByMobile
	 * @Description: 根据手机号+密码查询用户
	 * @param mobile
	 * @param password
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserByMobile(@Param("mobile") String mobile, @Param("password") String password);

	/**
	 * 
	 * @Title: getUserInfoByUid
	 * @Description: 根据UID获取用户资料
	 * @param _uid
	 * @return 返回类型 SystemUser
	 *
	 */
	public SystemUser getUserInfoByUid(@Param("uid") String _uid);

	/**
	 * 
	 * @Title: updateUserInfo
	 * @Description: 保存用户少量信息，用户个人设置功能
	 * @param systemUser
	 *            返回类型 void
	 *
	 */
	public void updateUserInfo(SystemUser systemUser);

	/**
	 * 分页获取注册用户信息
	 * 
	 * @param uid
	 *            用户ID
	 * @param offset
	 *            偏移量 : 第一条数据在表中的位置
	 * @param limit
	 *            限定数 : 每页的数量
	 * @return
	 */
	public List<RegUserInfoToJson> getUserList(@Param("uid") String uid, @Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 获取除参数uid以外的用户数量
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 */

	public int getUserCount(@Param("uid") String uid);

	/**
	 * 获取最近新增的1000个用户
	 * 
	 * @param systemUser
	 * @return
	 */
	public List<SystemUser> findLastest1000(SystemUser systemUser);

	/**
	 * 根据昵称搜索用户
	 * 
	 * @param nickname
	 * @return
	 */

	public List<RegUserInfoToJson> getUserInfoByNickname(@Param("nickname") String nickname);

	/**
	 * 获取用户关注的好友
	 * 
	 * @param uid
	 * @return
	 */
	public List<SystemUser> findFollows(@Param("uid") String uid);

	/**
	 * 获取用户粉丝
	 * 
	 * @param uid
	 * @return
	 */
	public List<FollowerUser> findFollowers(@Param("uid") String uid);

	/**
	 * 获取用户关注数
	 * 
	 * @param uid
	 * @return
	 */
	public int followsNum(@Param("uid") String uid);

	/**
	 * 获取用户粉丝数
	 * 
	 * @param uid
	 * @return
	 */
	public int followersNum(@Param("uid") String uid);

	/**
	 * 获取用户足迹数
	 * 
	 * @param uid
	 * @return
	 */
	public int historyNum(@Param("uid") String uid);

	/**
	 * 获取用户创建作品数
	 * 
	 * @param uid
	 * @return
	 */

	public int getUgcNum(@Param("uid") String uid);

	/**
	 * /** 根据手机号获取用户
	 * 
	 * @param mobile
	 * @return
	 */
	public SystemUser getUserOnlyByMobile(@Param("mobile") String mobile);

	/**
	 * 存储相册
	 * 
	 * @param uid
	 * @param pic
	 */
	public void savePhoto(@Param("uid") String uid, @Param("pic") String pic);

	/**
	 * 删除相册
	 * 
	 * @param picId
	 */
	public void delPhoto(@Param("picId") int picId);

	/**
	 * 获取用户相册
	 * @param uid
	 * @return
	 */
	public List<UserPic> userPics(String uid);

	/**
	 * 上传/屏蔽通信录
	 * @param uid
	 * @param addressList
	 * @param isShield
	 */
	public void shieldAddressList(@Param("uid")String uid, @Param("addressList")String addressList, @Param("isShield")int isShield);

	/**
	 * 修改背景图
	 * @param uid
	 * @param backgroundImage
	 */
	public void uploadBackgroundImg(@Param("uid")String uid, @Param("backgroundImage")String backgroundImage);

	/**
	 * 获取有设置屏蔽功能的用户uid
	 * @param uid
	 * @return
	 */
	public List<String> getShieldUids(@Param("uid")String uid,@Param("mobile")String mobile);

	/**
	 * 上拉获取用户相册
	 * @param uid
	 * @param picId
	 * @return
	 */
	public List<UserPic> userUpPics(@Param("uid")String uid, @Param("picId")int picId);

}