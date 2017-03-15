/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.MessageToJson;
import com.innovate.cms.modules.qs.entity.sns.QxMessage;
import com.innovate.cms.modules.qs.entity.sns.QxMessagePost;

/**
 * 回复表DAO接口
 * @author gaoji
 * @version 2016-04-01
 */
@MyBatisDao
public interface QxMessagePostDao extends CrudDao<QxMessagePost> {

	/**
	 * 保存留言回复内容
	 * @param messagePost
	 */
	public void saveReplyMessage(QxMessagePost messagePost);

	/**  
	 * 根据回复ID 删除某一条回复
	 * @param id  回复ID
	 */    
	
	public void delReplyMessageById(String id);


	/**  
	 * 根据留言ID删除留言
	 * @param id  
	 */    
	
	public void delMessageById(String id);


	/**
	 * 根据操作人ID和 留言ID 判断用户是否有权限删除此留言
	 * @param uid
	 * @param id
	 * @return 记录条数
	 */
	
	public int isMessageExist(@Param("uid")String uid, @Param("id")String id);

	/**
	 * 根据操作人ID和 回复ID 判断用户是否有权限删除此回复
	 * @param uid
	 * @param id
	 * @return
	 */
	public QxMessagePost getReply(@Param("uid")String uid, @Param("id")String id);

	/**  
	 * 获取留言列表
	 * @param uid 用户ID 
	 * @param funType 功能来源，1，留言板留言 2，足迹留言
	 * @param offset 偏移量 : 第一条数据在表中的位置
	 * @param limit 限定数 : 每页的数量
	 * @return  
	 */    
	
	public List<MessageToJson> getMessageList(@Param("uid")String uid, @Param("funType")String funType, @Param("offset")int offset, @Param("limit")int limit);

	/**  
	 * 根据uid和 funType获取记录总数
	 * @param uid 用户ID
	 * @param funType 功能来源，1，留言板留言 2，足迹留言
	 * @return  
	 */    
	
	public int getCount(@Param("uid")String uid, @Param("funType")String funType);

	/**  
	 * 根据留言ID获取留言信息
	 * @param msid
	 * @return  
	 */    
	
	public QxMessage getMessageById(@Param("msid")String msid);

	/**  
	 * 根据uid更新回复表用户的昵称和头像
	 * @param uid
	 * @param nickname
	 * @param headimgurl  
	 */    
	
	public void updatePost(@Param("uid")String uid, @Param("nickname")String nickname, @Param("headimgurl")String headimgurl);

	
}