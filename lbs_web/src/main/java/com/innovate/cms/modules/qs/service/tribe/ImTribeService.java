package com.innovate.cms.modules.qs.service.tribe;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.tribe.ImTribeDao;
import com.innovate.cms.modules.qs.entity.tribe.ImTribe;
import com.innovate.cms.modules.qs.entity.tribe.ImTribeToJSON;

/**
 * 
 * @author shifangfang
 * @date 2017年4月8日 上午10:49:26
 */
@Service
@Transactional(readOnly = true)
public class ImTribeService extends CrudService<ImTribeDao, ImTribe> {

	/**
	 * 存储群组创建信息
	 */
	@Transactional(readOnly = false)
	public void save(ImTribeToJSON imTribeToJSON) {
		super.dao.save(imTribeToJSON);
	}

	/**
	 * 根据指定群组tribeIds获取一批群组
	 * @param mids
	 * @return
	 */
	public List<ImTribe> nearTribe(long[] tribeIds) {
		
		return super.dao.nearTribe(tribeIds);
	}

	/**
	 * 获取群信息
	 * @param tribeId
	 * @return
	 */
	public ImTribe tribeInfo(int tribeId) {
		return super.dao.tribeInfo(tribeId);
	}

	/**
	 * 解散群组
	 * @param tribeId
	 */
	@Transactional(readOnly = false)
	public void delTribe(int tribeId) {
		super.dao.delTribe(tribeId);
	}

	/**
	 * 更新/编辑群组信息
	 * @param imTribeToJSON
	 */
	@Transactional(readOnly = false)
	public void updateTribe(ImTribeToJSON imTribeToJSON) {
		super.dao.updateTribe(imTribeToJSON);
	}
	
}
