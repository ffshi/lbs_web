package com.innovate.cms.modules.qs.dao.tag;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.tag.QxTag;
import com.innovate.cms.modules.qs.entity.tag.QxTagGroup;
import com.innovate.cms.modules.qs.entity.tag.QxTagToJson;
import com.innovate.cms.modules.qs.entity.tag.QxTagUser;

/**
 * 趣选标签系统 qx_tag
 * 
 * @author shifangfang
 * @date 2016年8月23日 下午2:17:29
 */
@MyBatisDao
public interface QxTagDao extends CrudDao<QxTag> {

	/**
	 * 获取专题标签
	 * 
	 * @param gid
	 * @return
	 */
	public List<QxTagToJson> findBygid(int gid);

	/**
	 * 获取用户标签
	 * 
	 * @param uid
	 * @return
	 */
	public List<QxTagToJson> findByUid(String uid);

	/**
	 * 存储专题标签
	 * @param qxTagGroup
	 */
	public void saveQxTagGroup(QxTagGroup qxTagGroup);

	/**
	 * 存储用户标签
	 * 
	 * @param qxTagUser
	 */
	public void saveQxTagUser(QxTagUser qxTagUser);

	/**
	 * 根据用户做过的专题给用户打标签
	 * @param uid
	 * @param gid
	 */
	public void saveQxTagUserByuidgid(@Param("uid")String uid, @Param("gid")int gid);

}
