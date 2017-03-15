package com.innovate.cms.modules.qs.service.tag;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.tag.QxTagDao;
import com.innovate.cms.modules.qs.entity.tag.QxTag;
import com.innovate.cms.modules.qs.entity.tag.QxTagGroup;
import com.innovate.cms.modules.qs.entity.tag.QxTagToJson;
import com.innovate.cms.modules.qs.entity.tag.QxTagUser;

/**
 * 趣选标签系统 qx_tag
 * 
 * @author shifangfang
 * @date 2016年8月23日 下午2:15:30
 */
@Service
@Transactional(readOnly = true)
public class QxTagService extends CrudService<QxTagDao, QxTag> {

	/**
	 * 获取专题标签
	 * 
	 * @param gid
	 * @return
	 */
	public List<QxTagToJson> findBygid(int gid) {
		return super.dao.findBygid(gid);
	}

	/**
	 * 获取用户标签
	 * 
	 * @param uid
	 * @return
	 */
	public List<QxTagToJson> findByUid(String uid) {
		return super.dao.findByUid(uid);
	}

	/**
	 * 存储专题标签
	 * 
	 * @param qxTagGroup
	 */
	@Transactional(readOnly = false)
	public void saveQxTagGroup(QxTagGroup qxTagGroup) {
		super.dao.saveQxTagGroup(qxTagGroup);
	}

	/**
	 * 存储用户标签
	 * 
	 * @param qxTagUser
	 */
	@Transactional(readOnly = false)
	public void saveQxTagUser(QxTagUser qxTagUser) {
		super.dao.saveQxTagUser(qxTagUser);
	}

	/**
	 * 根据用户做过的专题给用户打标签
	 * 
	 * @param uid
	 * @param gid
	 */
	@Transactional(readOnly = false)
	public void saveQxTagUserByuidgid(String uid, int gid) {
		super.dao.saveQxTagUserByuidgid(uid, gid);
	}

}
