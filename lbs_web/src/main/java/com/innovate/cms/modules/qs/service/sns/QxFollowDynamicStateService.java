/**
 * 
 */
package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.data.entity.FollowDynamicStateToJson;
import com.innovate.cms.modules.qs.dao.sns.QxFollowDynamicStateDao;
import com.innovate.cms.modules.qs.entity.sns.QxFollowDynamicState;

/**
 * 好友动态service层
 * @author hushasha
 * @date 2016年12月1日
 */
@Service
@Transactional(readOnly = true)
public class QxFollowDynamicStateService extends CrudService<QxFollowDynamicStateDao, QxFollowDynamicState> {
	
	/**
	 * 保存用户创建专题记录到好友动态表
	 * @param uid
	 * @param gid
	 * @param groupName
	 */
	@Transactional(readOnly = false)
	public void saveCreateGroupDynamicState(String uid, Integer gid, String groupName) {
		super.dao.saveCreateGroupDynamicState(uid, gid, groupName);
	}
	
	/**
	 * 保存做题记录到好友动态表
	 * @param uid
	 * @param gid
	 * @param templateTag
	 */
	@Transactional(readOnly = false)
	public void saveGroupDoneDynamicState(String uid, Integer gid, String templateTag) {
		if(Global.TEMPLATE_ZERO.equals(templateTag)) { //如果是模板0，站边专题
			super.dao.saveGroupZeroDynamicState(uid, gid, Integer.valueOf(templateTag));
		} else if(Global.TEMPLATE_FOUR.equals(templateTag)) { //模板4
			super.dao.saveGroupFourDynamicState(uid, gid, Integer.valueOf(templateTag));
		}
	}

	/**  
	 * 
	 * 获取好友动态---最新的20条数据
	 * @param uid
	 * @return  
	 */    
	
	public List<FollowDynamicStateToJson> getFollowDynamicStateList(String uid) {
		return super.dao.getFollowDynamicStateList(uid);
	}

	/**  
	 * 上拉显示更多好友动态---小于dsid的20条数据
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	public List<FollowDynamicStateToJson> showMoreDynamicStates(String uid, Integer dsid) {
		return super.dao.showMoreDynamicStates(uid, dsid);
	}

	/**  
	 * 下拉刷新好友动态，显示大于dsid的所有数据
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	public List<FollowDynamicStateToJson> refreshDynamicStates(String uid, Integer dsid) {
		return super.dao.refreshDynamicStates(uid, dsid);
	}

	/**  
	 * // 获取足迹列表(显示最新的20条数据)
	 * @param uid
	 * @return  
	 */    
	
	public List<FollowDynamicStateToJson> getFootprintList(String uid) {
		return super.dao.getFootprintList(uid);
	}

	/**  
	 * 上拉显示更多足迹(显示小于dsid的20条数据)
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	public List<FollowDynamicStateToJson> showMoreFootprints(String uid, Integer dsid) {
		return super.dao.showMoreFootprints(uid, dsid);
	}

	/**  
	 * 下拉刷新足迹--显示大于dsid的所有数据
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	public List<FollowDynamicStateToJson> refreshFootprints(String uid, Integer dsid) {
		return super.dao.refreshFootprints(uid, dsid);
	}

}
