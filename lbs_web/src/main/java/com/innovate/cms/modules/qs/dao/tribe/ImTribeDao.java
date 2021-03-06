package com.innovate.cms.modules.qs.dao.tribe;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.tribe.ImTribe;
import com.innovate.cms.modules.qs.entity.tribe.ImTribeToJSON;

/**
 * 群组
 * 
 * @author shifangfang
 * @date 2017年4月8日 上午10:52:53
 */
@MyBatisDao
public interface ImTribeDao extends CrudDao<ImTribe> {

	/**
	 * 存储群组创建信息
	 */
	void save(ImTribeToJSON imTribeToJSON);

	/**
	 * 根据指定群组tribeIds获取一批群组
	 * @param mids
	 * @return
	 */
	List<ImTribe> nearTribe(long[] tribeIds);

	/**
	 * 获取群信息
	 * @param tribeId
	 * @return
	 */
	ImTribe tribeInfo(@Param("tribeId")long tribeId);

	/**
	 * 解散群组
	 * @param tribeId
	 */
	void delTribe(long tribeId);

	/**
	 * 更新/编辑群组信息
	 * @param imTribeToJSON
	 */
	void updateTribe(ImTribeToJSON imTribeToJSON);
	/**
	 * 关联报名建立的群组与消息
	 * @param mid
	 */
	int connectMsgWithTribeId(@Param("tribeId")long tribeId,@Param("mid")int mid);

	/**
	 * 删除群组后dynamic_msg的关联tibleid设置为0
	 * @param tribeId
	 */
	int delTribleId(@Param("tribeId")long tribeId);

	/**
	 * 获取运营团队维护的群组
	 * @return
	 */
	List<ImTribe> operationTribe();
	
}
